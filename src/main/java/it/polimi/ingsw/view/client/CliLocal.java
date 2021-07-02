package it.polimi.ingsw.view.client;

import it.polimi.ingsw.messages.Message;
import it.polimi.ingsw.messages.actions.*;
import it.polimi.ingsw.messages.setup.client.UpdateClientPlayerBoardsMessage;
import it.polimi.ingsw.messages.setup.server.ChooseLeadersMessage;
import it.polimi.ingsw.messages.setup.server.ChooseResourcesMessage;
import it.polimi.ingsw.model.cards.DevelopmentCard;
import it.polimi.ingsw.model.cards.leadercards.LeaderCard;
import it.polimi.ingsw.model.gameboard.Resource;
import it.polimi.ingsw.model.gameboard.ResourceType;
import it.polimi.ingsw.view.client.utils.Formatting;
import it.polimi.ingsw.view.client.utils.InputValidator;
import it.polimi.ingsw.view.client.utils.Point;
import it.polimi.ingsw.view.client.utils.TurnActions;

import java.util.*;
import java.util.concurrent.atomic.AtomicBoolean;

public class CliLocal extends Cli {

    public CliLocal() {
        super();
    }


    /**
     * Asks the users to choose its leader card
     *
     * @param cardChoice the card pool
     */
    @Override
    public void setLeaderCardChoice(List<LeaderCard> cardChoice) {

        Formatting.clearScreen();

        getCliGraphics().showLeaderCards(cardChoice);

        List<LeaderCard> userChoice = new ArrayList<>();
        System.out.println("Select 2 cards among these leader cards. The cards are numbered from" +
                " 1 to 4. Write L + the correspondent number (e.g. 'L1' for the first one) to select a card. Press enter to continue...");
        inputWithTimeout();

        while (userChoice.size() < 2) {

            switch (inputWithTimeout()) {
                case "L1":
                    userChoice.add(cardChoice.get(0));
                    System.out.println("First card selected");
                    break;
                case "L2":
                    userChoice.add(cardChoice.get(1));
                    System.out.println("Second card selected");
                    break;
                case "L3":
                    userChoice.add(cardChoice.get(2));
                    System.out.println("Third card selected");
                    break;
                case "L4":
                    userChoice.add(cardChoice.get(3));
                    System.out.println("Forth card selected");
                    break;
                case "timeoutExpired":
                    return;

            }
            if (Thread.interrupted()) return;
        }

        System.out.println("\nWait a minute, we are preparing the match...");
        sendMessage(socket, new ChooseLeadersMessage(player.getNickname(), userChoice, true));


    }

    /**
     * Asks the user to choose its resource
     *
     * @param numberOfResources number of resources the user can choose
     */
    @Override
    public void setResourceTypeChoice(int numberOfResources) {

        Formatting.clearScreen();

        getCliGraphics().showGameBoard(gameBoard);
        getCliGraphics().showAllAvailableResources();


        AtomicBoolean correct = new AtomicBoolean(true);


        System.out.println("Choose " + numberOfResources + " among the resource type available. You can choose" +
                numberOfResources + "resources. Press Enter to continue");

        Map<ResourceType, Integer> resourceTypesChoice = new HashMap<>();
        inputWithTimeout();

        do {
            String input = inputWithTimeout();
            resourceTypesChoice = InputValidator.isValidChooseResourceType(input, numberOfResources);
            correct.set(resourceTypesChoice != null);
            if (!correct.get())
                System.out.println("Incorrect resource type name");

            if (Thread.interrupted()) return;

        } while (!correct.get());

        sendMessage(socket, new ChooseResourcesMessage(player.getNickname(), resourceTypesChoice, true));
    }

    /**
     * Asks the user to play its turn action
     */
    @Override
    public void askTurnAction() {

        System.out.println("It's your turn. You can choose both a turn action among these " + TurnActions.getLocalMatchTurnAction() +
                " Press Enter to continue.");

        boolean correct;
        String enter = inputWithTimeout();
        do {
            String input = inputWithTimeout();
            TurnActions action = InputValidator.isValidAction(input.toLowerCase(Locale.ROOT), player.isStandardActionPlayed());
            correct = action != null;
            if (!correct) {
                System.out.println("Incorrect action. Try again");
            } else {
                switch (action) {
                    case BUY_RESOURCES:
                        setBuyResourceAction(false);
                        break;
                    case BUY_CARD:
                        setBuyCardAction(false);
                        break;
                    case ACTIVATE_PRODUCTION:
                        sendMessage(socket, new ActivateProductionMessage(player.getNickname()));
                        setProductionChoice(player.getDevelopmentCards(), player.getProductionLeaderCards(), false);
                        break;
                    case LEADER_ACTION:
                        setLeaderCardAction(player.getHand(), false);
                        break;
                    case SHOW_GAMEBOARD:
                        Formatting.clearScreen();
                        getCliGraphics().showGameBoard(gameBoard);
                        askTurnAction();
                        break;
                    case CHEAT:
                        player.setStandardActionDone();
                        sendMessage(socket, new CheatMessage(player.getNickname()));
                        //askTurnAction();
                        break;
                    case RESET:
                        askTurnAction();
                        return;
                    case END_TURN:
                        if (!checkTurnEnd()) {
                            System.out.println("You can't end your turn without playing a standard action");
                            askTurnAction();
                            break;
                        } else {
                            player.resetTurnActionCounter();
                            Message message = new UpdateClientPlayerBoardsMessage(player.getNickname(), player.getPlayerBoard());
                            sendMessage(socket, message);
                            sendMessage(socket, new EndTurnMessage(player.getNickname()));
                            return;
                        }
                }
            }

        } while (!correct && !player.allPossibleActionDone());
    }


    /**
     * Asks the user what resources it wants to buy
     *
     * @param actionRejectedBefore true if the action was rejected before
     */
    @Override
    public void setBuyResourceAction(boolean actionRejectedBefore) {

        Formatting.clearScreen();

        getCliGraphics().showGameBoard(gameBoard);

        if (actionRejectedBefore)
            System.out.println("Your previous buy resource request has been rejected. Try again");
        else
            System.out.println("Select where you want to place the free marble. E.g. 1,2 to select the first row " +
                    "second column.");


        boolean correct;
        Point userChoice = null;
        do {
            String input = inputWithTimeout();
            if (input.equals("reset")) {
                askTurnAction();
                return;
            }
            userChoice = InputValidator.isValidBuyResourcesAction(input);
            correct = userChoice != null;
            if (!correct)
                System.out.println("Incorrect buy request. Try again. Remember, write X,Y to select the Xth row " +
                        "and Yth column");
        } while (!correct);


        BuyResourcesMessage message = new BuyResourcesMessage(player.getNickname(), userChoice.getX(), userChoice.getY(), true);

        if (player.getActiveEffects().isWhiteToResource() && player.getActiveEffects().getWhiteToResourceList().size() > 1) {

            do {
                List<ResourceType> whiteToResourceList = player.getActiveEffects().getWhiteToResourceList();
                System.out.println("You have more than one whiteToResource effect active. Choose one among " +
                        whiteToResourceList + ". Write the correspondent resource type.");
                String input = inputWithTimeout();
                ResourceType resourceType = InputValidator.isResourceType(input);
                correct = resourceType != null && !whiteToResourceList.contains(resourceType);
                if (correct)
                    message.setWhiteToResourceChoice(resourceType);
            } while (!correct);
        }
        sendMessage(socket, message);

        player.setStandardActionDone();

    }

    /**
     * This method set the phase to choose where to place the resources after a buy resource action
     */
    @Override
    public void setPlaceResourcesAction() {

        // should handle both move deposit and place resources requests -> all the other resources are discarded

        Formatting.clearScreen();

        getCliGraphics().showBoard(gameBoard, player);


        List<Resource> resourceList = player.getBoughtResources();

        System.out.println("Move your deposit floor. Write 'x,y' to swap the Xth floor with the Yth one. Write 'done' to finish. Press " +
                "Enter to continue.");

        getCliGraphics().showAllAvailableResources(resourceList);

        String input = inputWithTimeout();
        while (!input.equals("done")) {
            List<String> splitInput = Arrays.asList(input.split("\\s*,\\s*"));
            if (splitInput.size() == 2) {
                String first = splitInput.get(0);
                String second = splitInput.get(1);
                try {
                    int a = Integer.parseInt(first);
                    int b = Integer.parseInt(second);
                    Message message = new MoveDepositMessage(player.getNickname(), a, b, true);
                    sendMessage(socket, message);
                } catch (NumberFormatException e) {
                    System.out.println("Incorrect number format. Try again");
                }
            }
            input = inputWithTimeout();

        }

        Formatting.clearScreen();
        getCliGraphics().showBoard(gameBoard, player);

        System.out.println("Place you resources in the deposit. Write e.g. 'shield 1, stone 2' to place a shield in " +
                "the first floor and a stone in the second floor. ");

        getCliGraphics().showAllAvailableResources(resourceList);

        input = inputWithTimeout();
        boolean correct = false;
        Map<Resource, Integer> userChoice = new HashMap<>();

        while (!input.equals("done") && !correct) {
            userChoice = InputValidator.isValidPlaceResourceAction(resourceList, input);
            correct = userChoice != null;
            if (!correct) {
                System.out.println("Incorrect selection. Try again.");
                input = inputWithTimeout();
            }
        }
        PlaceResourcesMessage message = new PlaceResourcesMessage(player.getNickname(), userChoice);
        message.setDiscardedResources(Math.abs(resourceList.size() - userChoice.size()));
        sendMessage(socket, message);


    }

    /**
     * Asks the user what card it wants to buy
     *
     * @param actionRejectedBefore true if the action was rejected before
     */
    @Override
    public void setBuyCardAction(boolean actionRejectedBefore) {

        Formatting.clearScreen();

        getCliGraphics().showGameBoard(gameBoard);

        if (actionRejectedBefore)
            System.out.println("Your previous buy card request has been rejected. Try a different one." +
                    "Remember that to buy a card you must have enough resources on your deposits.");
        else
            System.out.println("Select the card you want to buy. E.g. 1,2 to select the first row " +
                    "second column.");


        boolean correct = false;
        Point userChoice = null;
        do {
            String input = inputWithTimeout();
            if (input.equals("reset")) {
                askTurnAction();
                return;
            }
            userChoice = InputValidator.isValidBuyCardAction(input);
            correct = userChoice != null;
            if (!correct)
                System.out.println("Incorrect buy request. Try again. Remember, write X,Y to select the Xth row " +
                        "and Yth column");
            if (Thread.interrupted()) return;
        } while (!correct);


        sendMessage(socket, new BuyDevelopmentCardMessage(player.getNickname(), userChoice.getX(), userChoice.getY(), true));

        player.setStandardActionDone();

    }

    /**
     * Asks the user what card productions it wants to use
     *
     * @param developmentCards     its development card
     * @param leaderCards          its leader card type production
     * @param actionRejectedBefore true if the action was rejected before
     */
    @Override
    public void setProductionChoice(List<DevelopmentCard> developmentCards, List<LeaderCard> leaderCards, boolean actionRejectedBefore) {

        Formatting.clearScreen();

        getCliGraphics().showBoard(gameBoard, player);

        if (actionRejectedBefore)
            System.out.println("Your production request has been rejected. Try again.");
        else
            System.out.println("Select which production power you want to activate. " +
                    "E.g. 'board/shield,coin=stone' to activate the board production or " +
                    "'develop 1' to select the first development card. " +
                    "or write \"l1-shield\" to receive 1 shield using the first leader card. " +
                    "Write 'done' to end the production action. Press Enter to continue.");

        AtomicBoolean correct = new AtomicBoolean(true);
        AtomicBoolean selectionDone = new AtomicBoolean(false);

        inputWithTimeout();
        List<DevelopmentCard> developmentCardChoice;
        Map<LeaderCard, ResourceType> leaderCardChoice;
        Map<Resource, List<ResourceType>> boardProductionChoice;

        do {
            String input = inputWithTimeout();
            if (input.equals("done"))
                selectionDone.set(true);
            if (input.equals("reset")) {
                askTurnAction();
                return;
            }
            developmentCardChoice = InputValidator.isValidDevelopmentCardChoice(developmentCards, input); // develop 1 or develop 3
            if (developmentCardChoice != null)
                sendMessage(socket, new ActivateCardProductionMessage(player.getNickname(), developmentCardChoice, true));

            leaderCardChoice = InputValidator.isValidLeaderCardChoice(leaderCards, input); // l1,coin or l2,shield
            if (leaderCardChoice != null)
                sendMessage(socket, new ActivateLeaderProductionMessage(player.getNickname(), leaderCardChoice, true));

            boardProductionChoice = InputValidator.isValidBoardProductionChoice(input); // board/shield,coin=stone
            if (boardProductionChoice != null)
                sendMessage(socket, new ActivateBoardProductionMessage(player.getNickname(), boardProductionChoice, true));

            correct.set(developmentCardChoice != null || leaderCardChoice != null || boardProductionChoice != null);
            if (!correct.get() && !selectionDone.get())
                System.out.println("Incorrect production choices. Try again");

        } while (!selectionDone.get());

        sendMessage(socket, new EndProductionMessage(player.getNickname()));

        player.setStandardActionDone();

    }

    /**
     * Asks the user what leader card it wants to use.
     *
     * @param leaderCards          its leader card
     * @param actionRejectedBefore true if the action was rejected before
     */
    @Override
    public void setLeaderCardAction(List<LeaderCard> leaderCards, boolean actionRejectedBefore) {

        Formatting.clearScreen();

        getCliGraphics().showLeaderCards(leaderCards);

        if (actionRejectedBefore)
            System.out.println("Leader card selection incorrect. Try again.");
        else
            System.out.println("Select which leader card you want to select. " +
                    "E.g. 'D1, A2' if you want to discard the first leader card and activate the second " +
                    ". Write 'done' if you have finished");


        AtomicBoolean correct = new AtomicBoolean(false);
        AtomicBoolean selectionDone = new AtomicBoolean(false);

        Map<LeaderCard, Boolean> userChoice = new HashMap<>();


        String input = inputWithTimeout();
        while (!input.equals("done")) {

            switch (input) {
                case "A1":
                    userChoice.put(leaderCards.get(0), true);
                    break;
                case "A2":
                    userChoice.put(leaderCards.get(1), true);
                    break;
                case "D1":
                    userChoice.put(leaderCards.get(0), false);
                    break;
                case "D2":
                    userChoice.put(leaderCards.get(1), false);
            }

            if (input.equals("reset")) {
                askTurnAction();
                return;
            }
            //userChoice = InputValidator.isValidLeaderCardAction(leaderCards,input);
            input = inputWithTimeout();

        }

        sendMessage(socket, new LeaderActionMessage(player.getNickname(), userChoice, true));


    }
}
