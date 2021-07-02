package it.polimi.ingsw.view.client;

import it.polimi.ingsw.messages.actions.*;
import it.polimi.ingsw.messages.Message;
import it.polimi.ingsw.messages.setup.client.UpdateClientPlayerBoardsMessage;
import it.polimi.ingsw.messages.setup.server.ChooseLeadersMessage;
import it.polimi.ingsw.messages.setup.server.ChooseResourcesMessage;
import it.polimi.ingsw.messages.setup.server.DoLoginMessage;
import it.polimi.ingsw.messages.setup.client.LoginRequest;
import it.polimi.ingsw.model.ActionToken;
import it.polimi.ingsw.model.ActionTokenDiscard;
import it.polimi.ingsw.model.ActionTokenMove;
import it.polimi.ingsw.model.cards.DevelopmentCard;
import it.polimi.ingsw.model.cards.DevelopmentCardType;
import it.polimi.ingsw.model.cards.DevelopmentDeck;
import it.polimi.ingsw.model.cards.leadercards.*;
import it.polimi.ingsw.model.gameboard.*;
import it.polimi.ingsw.network.LocalMatchHandler;
import it.polimi.ingsw.network.client.EchoClient;
import it.polimi.ingsw.view.client.utils.*;
import it.polimi.ingsw.view.client.viewComponents.*;

import java.util.*;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicBoolean;

import static it.polimi.ingsw.view.client.utils.Formatting.ColorCode.*;
import static it.polimi.ingsw.view.client.utils.Formatting.Unicode.*;

/**
 * this class implements the CLI view in the client
 */
public class Cli extends View {

    protected final Scanner scanner;
    private boolean disconnected;

    protected final InputValidator inputValidator;
    ExecutorService inputExecutor;
    Future inputThread;
    CliGraphics cliGraphics;


    public Cli() {
        this.scanner = new Scanner(System.in);
        this.inputValidator = new InputValidator();
        inputExecutor = Executors.newSingleThreadExecutor();
        this.disconnected = false;
        cliGraphics = new CliGraphics();
        //gameSetup();
    }


    /**
     * Get user's input without a timeout.
     *
     * @return user's input
     */
    public String inputWithoutTimeout() {

        return InputCli.readLine();
    }

    /**
     * Get user's input with a timeout. Close the client if timeout expires
     *
     * @return user's input
     */
    public String inputWithTimeout() {
        String input = "";

        ExecutorService executor = Executors.newSingleThreadExecutor();
        Future<String> result = executor.submit(InputCli::readLine);

        try {
            input = result.get(2, TimeUnit.MINUTES);
        } catch (TimeoutException | InterruptedException | ExecutionException e) {
            if (!disconnected) {
                new Thread(this::disconnectionForInputExpiredTimeout).start();
            }
            Thread.currentThread().interrupt();
            result.cancel(true);
        }

        return input;
    }

    private void disconnectionForInputExpiredTimeout() {
    }


    /**
     * This method tells the user that it has to play its turn
     *
     * @param currentPlayer the player that it's playing now
     */
    @Override
    public void showPlayTurn(String currentPlayer) {

        Formatting.clearScreen();

        cliGraphics.showBoard(gameBoard, player);

        cliGraphics.showLeaderCards(player.getLeaderCards());

        otherPlayerBoards.forEach((k, v) -> cliGraphics.showOtherPlayerBoard(k, v));

        if (currentPlayer.equals(player.getNickname())) {
            askTurnAction();
        } else {
            System.out.println(currentPlayer + " is playing its turn...");
        }

    }

    /**
     * This method represents the game setup
     */

    public void gameSetup() {
        System.out.println("Press enter button to start");
        inputWithoutTimeout();

        //Connection setup
        setMyIp();
        setMyPort();

        //start connection
        new EchoClient(myIp, myPort, this).start();
    }


    /**
     * Setup local Match
     */
    public void gameSetupLocalMatch() {

        isLocalMatch = true;
        localMatchHandler = new LocalMatchHandler(this);
        DoLoginMessage message = new DoLoginMessage();
        message.setFirstPlayer(true);
        showLogin(message);
    }


    //View Override methods

    /**
     * This method allows to insert the server ip.
     */

    @Override
    public void setMyIp() {
        System.out.println("Insert the server IP address!");
        String ip = inputWithoutTimeout();
        while (!InputValidator.validateIP(ip)) {
            System.out.println("Invalid IP address! Please, try again!");
            ip = inputWithoutTimeout();
        }

        myIp = ip;
    }

    /**
     * This method allows to insert the server port.
     */

    @Override
    public void setMyPort() {
        System.out.println("Insert the server port!");
        String port = inputWithoutTimeout();
        while (!InputValidator.validatePORT(port)) {
            System.out.println("Invalid port! Please, try again.");
            port = inputWithoutTimeout();
        }

        myPort = Integer.parseInt(port);
    }

    /**
     * Asks the users to choose its leader card
     *
     * @param cardChoice the card pool
     */
    @Override
    public void setLeaderCardChoice(List<LeaderCard> cardChoice) {

        Formatting.clearScreen();

        cliGraphics.showLeaderCards(cardChoice);

        List<LeaderCard> userChoice = new ArrayList<>();
        inputThread = inputExecutor.submit(() -> {
            System.out.println("Select 2 cards among these leader cards. The cards are numbered from" +
                    " 1 to 4. Write L + the correspondent number (e.g. 'L1' for the first one) to select a card. Press enter to continue...");
            inputWithTimeout();

            if (!Thread.interrupted()) {
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
            }

            if (!Thread.interrupted()) {
                System.out.println("\nWait a minute, we are preparing the match...");
                sendMessage(socket, new ChooseLeadersMessage(player.getNickname(), userChoice, true));
            }

        });

    }

    /**
     * This method add the leader cards to the user's hand
     *
     * @param choice user choice
     */
    @Override
    public void showLeaderCardsSelectionAccepted(List<LeaderCard> choice) {
        player.setHand(choice);
        Message message = new UpdateClientPlayerBoardsMessage(player.getNickname(), player.getPlayerBoard());
        sendMessage(socket, message);
    }

    /**
     * Asks the user to choose its resource
     *
     * @param numberOfResources number of resources the user can choose
     */
    @Override
    public void setResourceTypeChoice(int numberOfResources) {

        Formatting.clearScreen();

        cliGraphics.showGameBoard(gameBoard);
        cliGraphics.showAllAvailableResources();


        AtomicBoolean correct = new AtomicBoolean(true);

        inputThread = inputExecutor.submit(() -> {

            System.out.println("Choose " + numberOfResources + " among the resource type available. You can choose " +
                    numberOfResources + " resource(s). Press Enter to continue");

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

            if (!Thread.interrupted())
                sendMessage(socket, new ChooseResourcesMessage(player.getNickname(), resourceTypesChoice, true));
        });
    }



    /**
     * This method set the phase to choose where to place the resources after a buy resource action
     */
    @Override
    public void setPlaceResourcesAction() {

        // should handle both move deposit and place resources requests -> all the other resources are discarded

        Formatting.clearScreen();

        cliGraphics.showBoard(gameBoard, player);


        inputThread = inputExecutor.submit(() -> {

            List<Resource> resourceList = player.getBoughtResources();

            System.out.println("Move your deposit floor. Write 'x,y' to swap the Xth floor with the Yth one. Write 'done' to finish. Press " +
                    "Enter to continue.");

            cliGraphics.showAllAvailableResources(resourceList);

            String input = inputWithTimeout();
            if (!Thread.interrupted()) {
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
                cliGraphics.showBoard(gameBoard, player);

                System.out.println("Place you resources in the deposit. Write e.g. 'shield 1, stone 2' to place a shield in " +
                        "the first floor and a stone in the second floor. ");

                cliGraphics.showAllAvailableResources(resourceList);

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
                    if (Thread.interrupted()) return;
                }
                if (!Thread.interrupted()) {
                    PlaceResourcesMessage message = new PlaceResourcesMessage(player.getNickname(), userChoice);
                    message.setDiscardedResources(Math.abs(resourceList.size() - userChoice.size()));
                    sendMessage(socket, message);
                }

            }

        });

    }

    /**
     * This method tells the user that the leader card action has been accepted
     *
     * @param user     the current user
     * @param card     the card
     * @param activate true to activate leader card, false to discard
     */
    @Override
    public void showAcceptedLeaderAction(String user, LeaderCard card, boolean activate) {

        if (user.equals(player.getNickname())) {
            System.out.println("Leader action accepted.");
            player.setLeaderActionDone();
            player.useLeaderCard(card, activate);
            askTurnAction();
        } else
            System.out.println(user + " has activated a leader of type " + card.getLeaderType());
    }

    /**
     * This method tells the user that the leader card action has been rejected
     */
    @Override
    public void showRejectedLeaderAction() {
        setLeaderCardAction(player.getHand(), true);
    }

    /**
     * This method tells the user that the buy card action has been accepted
     */
    @Override
    public void showAcceptedBuyDevelopmentCard(String user, int x, int y) {

        if(player.getNickname().equals(user)) {
            player.buyDevelopmentCard(x, y);
            player.setStandardActionDone();
            System.out.println("Buy development card request accepted");
            askTurnAction();
        }else{

        }
    }

    /**
     * This method tells the user that the activate production request has been rejected
     *
     * @param user     the current player
     * @param accepted true if accepted
     */
    @Override
    public void showProductionRequestResults(String user, boolean accepted) {

        if (player.getNickname().equals(user)) {
            if (!accepted) {
                setProductionChoice(player.getDevelopmentCards(), player.getProductionLeaderCards(), true);
            } else {
                System.out.println("Your production request has been accepted");
            }
        } else {
            if (accepted)
                System.out.println(user.toUpperCase() + " has used the production power");
        }
    }

    /**
     * Shows the results of the move deposit request.
     *
     * @param x,y      the floors to swap
     * @param accepted true if the request has been accepted, false if rejected
     */
    @Override
    public void showMoveDepositResult(int x, int y, boolean accepted) {

        if (accepted) {
            player.getDeposit().swapFloors(x, y);
            //System.out.println("Move deposit request accepted. Press Enter to continue");
        } else {
            System.out.println("Move deposit request rejected. Try again");
            setPlaceResourcesAction();
        }
    }


    @Override
    public void showPlaceResourcesResult(String user, boolean accepted, Map<Resource, Integer> userChoice) {

        if (player.getNickname().equals(user)){
            if (accepted) {
                System.out.println("The other resources will be discarded. Press Enter to continue");
                player.addResource(userChoice);
                inputWithTimeout();
                askTurnAction();
            } else {
                System.out.println("Incorrect place resources. Try again.");
                setPlaceResourcesAction();
            }
        } else if (accepted)
            System.out.println(user + " has bought resources from market");
    }

    /**
     * Show the results of the selection the initial resources
     *
     * @param resourceChoice the user choice
     */
    @Override
    public void showResourceSelectionAccepted(Map<ResourceType, Integer> resourceChoice) {

        ClientDeposit deposit = player.getDeposit();

        int j = 3;
        for (ResourceType resourceType : resourceChoice.keySet()) {
            for (int i = 0; i < resourceChoice.get(resourceType); i++)
                deposit.addResource(j, new Resource(resourceType));
            j--;
        }
        UpdateClientPlayerBoardsMessage message = new UpdateClientPlayerBoardsMessage(player.getNickname(), player.getPlayerBoard());
        sendMessage(socket, message);
    }

    @Override
    public void showReconnectionToMatch() {
        System.out.println("Reconnection to match...");
    }

    @Override
    public void updatePlayerPosition(int position) {
        player.updateCurrentPosition(position);
    }

    @Override
    public void updateOtherPlayerBoards(String user, ClientPlayerBoard clientPlayerBoard) {
        otherPlayerBoards.put(user, clientPlayerBoard);
    }


    public void updateGameBoard(DevelopmentDeck[][] cardMarket, Marble[][] market, Marble freeMarble) {
        gameBoard.getMarket().update(market, freeMarble);
        gameBoard.getCardMarket().update(cardMarket);
    }

    @Override
    public void showLorenzoAction(ActionToken lorenzoAction, int lorenzoPosition) {

        if (lorenzoAction instanceof ActionTokenDiscard) {
            int amount = ((ActionTokenDiscard) lorenzoAction).getAmount();
            DevelopmentCardType developmentCardType = ((ActionTokenDiscard) lorenzoAction).getType();
            Formatting.clearScreen();
            Formatting.printWhiteSpaceBlock(0.3);
            System.out.println("Lorenzo discarded " + amount + " card of type " + developmentCardType + " from the market");
        } else if (lorenzoAction instanceof ActionTokenMove) {
            int steps = ((ActionTokenMove) lorenzoAction).getSteps();
            Formatting.clearScreen();
            Formatting.printWhiteSpaceBlock(0.3);
            System.out.println("Lorenzo moved " + steps + " forward on his Poperoad");
        }

        try {
            Thread.sleep(5000);
        } catch (InterruptedException ignored) {

        }
    }

    @Override
    public void showProductionResult(Map<ResourceType, List<Resource>> updatedStrongbox, List<List<Resource>> updatedWarehouse, List<AuxiliaryDeposit> auxiliaryDeposit) {
        player.updateDeposit(updatedStrongbox, updatedWarehouse, auxiliaryDeposit);
        //askTurnAction();
    }

    /**
     * Alerts the users that it's the last round of the match
     */
    @Override
    public void showLastRound() {
        System.out.println("GET READY!\n We are at the end of the journey! LAST ROUND START!");
    }

    /**
     * Shows the vatican report action
     *
     * @param view the client's view
     */
    @Override
    public void showVaticanReport(View view) {

        System.out.println("VATICAN REPORT! Be prepared to pay your duties to the Pope!");
    }

    /**
     * Asks the user to play its turn action
     */
    @Override
    public void askTurnAction() {

        if (isLocalMatch)
            System.out.println("It's your turn. You can choose both a turn action among these " + TurnActions.getLocalMatchTurnAction() +
                    " Press Enter to continue.");
        else
            System.out.println("It's your turn. You can choose both a turn action among these " + Arrays.asList(TurnActions.values()) +
                    " Press Enter to continue.");

        //AtomicBoolean correct = new AtomicBoolean(false);
        inputWithTimeout();
        inputThread = inputExecutor.submit(() -> {

            boolean correct = false;
            while(!correct){
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
                                cliGraphics.showGameBoard(gameBoard);
                                askTurnAction();
                                break;
                            case CHEAT:
                                sendMessage(socket, new CheatMessage(player.getNickname()));
                                player.setStandardActionDone();
                                askTurnAction();
                                return;
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
            }
        });
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

        cliGraphics.showBoard(gameBoard, player);

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
        inputThread = inputExecutor.submit(() -> {

            inputWithTimeout();
            List<DevelopmentCard> developmentCardChoice;
            Map<LeaderCard, ResourceType> leaderCardChoice;
            Map<Resource, List<ResourceType>> boardProductionChoice;

            if (!Thread.interrupted()) {
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

                    if (Thread.interrupted()) return;
                } while (!selectionDone.get());

                if (!Thread.interrupted()) {
                    sendMessage(socket, new EndProductionMessage(player.getNickname()));
                }

                player.setStandardActionDone();
            }

        });


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

        cliGraphics.showLeaderCards(leaderCards);

        if (actionRejectedBefore)
            System.out.println("Leader card selection incorrect. Try again.");
        else
            System.out.println("Select which leader card you want to select. " +
                    "E.g. 'D1, A2' if you want to discard the first leader card and activate the second " +
                    ". Write 'done' if you have finished");


        AtomicBoolean correct = new AtomicBoolean(false);
        AtomicBoolean selectionDone = new AtomicBoolean(false);
        inputThread = inputExecutor.submit(() -> {

            Map<LeaderCard, Boolean> userChoice = new HashMap<>();

            if (!Thread.interrupted()) {

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

                    if (Thread.interrupted()) return;
                }

                if (!Thread.interrupted())
                    sendMessage(socket, new LeaderActionMessage(player.getNickname(), userChoice, true));

            }

        });


    }

    /**
     * Asks the user what card it wants to buy
     *
     * @param actionRejectedBefore true if the action was rejected before
     */
    @Override
    public void setBuyCardAction(boolean actionRejectedBefore) {

        Formatting.clearScreen();

        cliGraphics.showGameBoard(gameBoard);

        if (actionRejectedBefore)
            System.out.println("Your previous buy card request has been rejected. Try a different one." +
                    "Remember that to buy a card you must have enough resources on your deposits.");
        else
            System.out.println("Select the card you want to buy. E.g. 1,2 to select the first row " +
                    "second column.");

        inputThread = inputExecutor.submit(() -> {

            boolean correct = false;
            Point userChoice = null;
            if (!Thread.interrupted()) {
                do {
                    String input = inputWithTimeout();
                    if (input.equals("reset")) {
                        askTurnAction();
                        return;
                    }
                    userChoice = InputValidator.isValidBuyCardAction(input);
                    correct = userChoice != null;
                    if (!correct)
                        System.out.println("Incorrect buy request. Try again. Remember, 1,2 to select the first row " +
                                "second column.");
                    if (Thread.interrupted()) return;
                } while (!correct);
            }

            if (!Thread.interrupted())
                sendMessage(socket, new BuyDevelopmentCardMessage(player.getNickname(), userChoice.getX(), userChoice.getY(), true));

        });


    }

    /**
     * Asks the user what resources it wants to buy
     *
     * @param actionRejectedBefore true if the action was rejected before
     */
    @Override
    public void setBuyResourceAction(boolean actionRejectedBefore) {

        Formatting.clearScreen();

        cliGraphics.showGameBoard(gameBoard);

        if (actionRejectedBefore)
            System.out.println("Your previous buy resource request has been rejected. Try again");
        else
            System.out.println("Select where you want to place the free marble. E.g. row,1 to select the first row " +
                    "; column,2 to select the second column");

        inputThread = inputExecutor.submit(() -> {

            boolean correct;
            Point userChoice = null;
            if (!Thread.interrupted()) {
                do {
                    String input = inputWithTimeout();
                    if (input.equals("reset")) {
                        askTurnAction();
                        return;
                    }
                    userChoice = InputValidator.isValidBuyResourcesAction(input);
                    correct = userChoice != null;
                    if (!correct)
                        System.out.println("Incorrect buy request. Try again. Remember, write row,1 to select the first row " +
                                "; column,2 to select the second column");
                    if (Thread.interrupted()) return;
                } while (!correct);
            }

            if (!Thread.interrupted()) {
                BuyResourcesMessage message = new BuyResourcesMessage(player.getNickname(), userChoice.getX(), userChoice.getY(), true);

                if (player.getActiveEffects().isWhiteToResource() && player.getActiveEffects().getWhiteToResourceList().size() > 1) {

                    correct = false;
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
            }
            player.setStandardActionDone();

        });
    }

    /**
     * This method show the login view, asks nickname and number of players to the user and checks if the input is valid
     *
     * @param message the login message sent by the server
     */
    @Override
    public void showLogin(DoLoginMessage message) {

        boolean correct;
        String nickname;

        do {
            System.out.println("Insert your username (must be at least 3 characters long and no more than 10, valid characters: A-Z, a-z, 1-9, _)");
            nickname = scanner.nextLine();
            correct = inputValidator.isNickname(nickname);
            if (!correct)
                System.out.println("Invalid username, try again");
        } while (!correct);

        LoginRequest loginRequest = new LoginRequest(nickname);

        if (message.isFirstPlayer()) {
            do {
                System.out.println("Insert number of players [1..4]");
                int numberOfPlayers = scanner.nextInt();
                correct = inputValidator.isNumberOfPlayers(numberOfPlayers, isLocalMatch);
                if (!correct) {
                    System.out.println("Incorrect number of players. Try again");
                } else {
                    loginRequest.setNumberOfPlayers(numberOfPlayers);
                }
            } while (!correct);
        }

        sendMessage(socket, loginRequest);
    }

    /**
     * This method tells the user that login has been successful and asks to wait for other players to join
     */
    @Override
    public void showLoginDone(String user) {

        //if(!isLocalMatch)
        //sendMessage(socket, new LoginDoneMessage(user,true));
        newMatch(user);
        System.out.println("Login done, matchmaking ...");
    }

    /**
     * This method tells the user that a new player has joined the match
     *
     * @param username: username of the newly logged in player
     */
    @Override
    public void showNewUserLogged(String username) {

        System.out.println(username + " has joined the match");
    }




    @Override
    public void serverNotFound() {
        System.out.println("Server is not at home now. Try again later.");
    }

    /**
     * This method tells the user that another player has disconnected
     *
     * @param otherClient the disconnected player
     */
    @Override
    public void showAnotherClientDisconnection(String otherClient) {
        System.out.println(otherClient + " has disconnected from the match.");
        otherPlayerBoards.remove(otherClient);
    }

    /**
     * This method alerts the user that the server disconnected
     */
    @Override
    public void showServerDisconnection() {

        System.out.println("Server says bye, bye!");
        System.out.println("Do you want to reconnect again? Type 'YES' to reconnect.");
        if (inputWithTimeout().toLowerCase(Locale.ROOT).equals("yes"))
            gameSetup();

    }

    /**
     * This method alerts the user that it has lost the game and tells who is the winner
     *
     * @param winner : username of the winner
     */
    @Override
    public void showYouLose(String winner) {
        Formatting.clearScreen();
        System.out.println("You LOSE. " + winner + " won the match");

        try {
            Thread.sleep(10000);
        } catch (InterruptedException ignored) {
        }

        System.out.println("Do you want to reconnect again? Type 'YES' to reconnect.");
        if (inputWithTimeout().toLowerCase(Locale.ROOT).equals("yes"))
            gameSetup();
    }

    /**
     * This method tells the winner he won the match
     */
    @Override
    public void showYouWin() {

        Formatting.clearScreen();
        System.out.println("You are the champion! VICTORY!");

        try {
            Thread.sleep(10000);
        } catch (InterruptedException ignored) {
        }

        System.out.println("Do you want to reconnect again? Type 'YES' to reconnect.");
        if (inputWithTimeout().toLowerCase(Locale.ROOT).equals("yes"))
            gameSetup();


    }

    /**
     * Show end game
     * @param leaderboard the leaderboard
     */
    @Override
    public void showEndGame(Map<String, Integer> leaderboard) {


        Formatting.printWhiteSpaceBlock(0.3);
        for(String player: leaderboard.keySet()){
            System.out.println(player + "   victory: " + leaderboard.get(player));
        }


        try {
            Thread.sleep(30000);
        } catch (InterruptedException ignored) {
        }

        System.out.println("Do you want to reconnect again? Type 'YES' to reconnect.");
        if (inputWithTimeout().toLowerCase(Locale.ROOT).equals("yes"))
            gameSetup();


    }

    /**
     * This method tells the user that login has been rejected
     */
    @Override
    public void showLoginFailed(boolean isFirstPlayer) {

        System.out.println("Login failed. Try again with a different username");
        DoLoginMessage doLoginMessage = new DoLoginMessage();
        doLoginMessage.setFirstPlayer(isFirstPlayer);
        showLogin(doLoginMessage);
    }

    public CliGraphics getCliGraphics() {
        return cliGraphics;
    }
}
