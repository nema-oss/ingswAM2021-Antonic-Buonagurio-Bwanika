package it.polimi.ingsw.controller;

import it.polimi.ingsw.model.ActionToken;
import it.polimi.ingsw.model.Game;
import it.polimi.ingsw.model.cards.DevelopmentCard;
import it.polimi.ingsw.model.cards.DevelopmentDeck;
import it.polimi.ingsw.model.cards.leadercards.AuxiliaryDeposit;
import it.polimi.ingsw.model.cards.leadercards.ExtraProduction;
import it.polimi.ingsw.model.cards.leadercards.LeaderCard;
import it.polimi.ingsw.model.cards.leadercards.LeaderCardType;
import it.polimi.ingsw.model.exception.*;
import it.polimi.ingsw.model.gameboard.*;
import it.polimi.ingsw.model.player.Board;
import it.polimi.ingsw.model.player.Player;
import it.polimi.ingsw.model.player.Strongbox;
import it.polimi.ingsw.view.server.VirtualViewInterface;

import javax.naming.InsufficientResourcesException;
import java.util.*;
import java.util.stream.Collectors;


/**
 * This class represents the controller
 */
public class MatchController implements ControllerInterface{


    private final Game game;
    private VirtualViewInterface viewInterface;

    private boolean developmentProductionActivated;
    private boolean leaderProductionActivated;
    private boolean boardProductionActivated;
    private boolean firstSkipped;

    private boolean isLastRound;


    /**
     * this is the class constructor
     */
    public MatchController() {

        game = new Game();
        this.viewInterface = null;
        developmentProductionActivated = false;
        leaderProductionActivated = false;
        boardProductionActivated = false;
        isLastRound = false;
        firstSkipped = false;
    }


    /**
     * this method sets the virtual view for the controller
     * @param virtualView the game's virtualView
     */
    @Override
    public void setVirtualView(VirtualViewInterface virtualView){
        this.viewInterface = virtualView;
    }


    /**
     * this method adds a player to the game
     * @param nickname the chosen nickname
     * @return the list of errors generated
     */
    @Override
    public List<Error> onNewPlayer(String nickname){

        List<Error> errors = new ArrayList<>();
        if(!game.getGamePhase().equals(GamePhase.LOGIN)) {
            errors.add(Error.WRONG_GAME_PHASE);
            return errors;
        }

        if(nickname == null || nickname.isEmpty()) throw new IllegalArgumentException("Invalid nickname");

        return game.addPlayer(new Player(nickname, game.getGameBoard(), game));

    }

    /**
     * this method starts the game
     * @return the list of errors generated
     */
    @Override
    public List<Error> onStartGame() {

        List<Error> errors = new ArrayList<>();
        if(!game.getGamePhase().equals(GamePhase.LOGIN)){
            errors.add(Error.WRONG_GAME_PHASE);
            return errors;
        }
        int numberOfPlayers = game.getListOfPlayers().size();
        if(numberOfPlayers == 1){
            game.setSinglePlayerCPU();
        }
        game.setPlayersOrder();

        game.setGamePhase(GamePhase.CHOOSE_LEADERS);

        viewInterface.sendGameBoard(game.getGameBoard().getCardMarket().getCardMarket(), game.getGameBoard().getMarket().marbles(), game.getGameBoard().getMarket().getFreeMarble());
        sendChooseLeaderCards();

        return errors;
    }


    /**
     * this method calls the virtualView's method to send message "Choose Leader Cards"
     */
    @Override
    public void sendChooseLeaderCards() {

        List<LeaderCard> leaders = new ArrayList<>();

        game.getLeaderDeck().shuffle();
        for (int i=0; i<4 && game.getLeaderDeck().getListOfCards().size() > 0; i++){
            leaders.add(game.getLeaderDeck().drawCard());
        }

        viewInterface.toDoChooseLeaderCards(game.getCurrentPlayer().getNickname(), leaders );
        //List<String> users = game.getListOfPlayers().stream().map(Player::getNickname).collect(Collectors.toList());
        //viewInterface.toDoChooseLeaderCards(users, leaders );

    }


    /**
     * this method assigns the leaderCards chosen to the player's hand
     * @param nickname of the player
     * @param leaderCardsChosen cards chosen by the player
     * @return the list of errors generated
     */
    @Override
    public List<Error> onLeaderCardsChosen(String nickname, List<LeaderCard> leaderCardsChosen){

        List<Error> errors = new ArrayList<>();


        if(!game.getGamePhase().equals(GamePhase.CHOOSE_LEADERS)){
            errors.add(Error.WRONG_GAME_PHASE);
            return errors;
        }

        if(!nickname.equals(game.getCurrentPlayer().getNickname())) {
            errors.add(Error.NOT_YOUR_TURN);
            return errors;
        }

        if(leaderCardsChosen == null || leaderCardsChosen.size() != 2) {
            errors.add(Error.INVALID_ACTION);
            return errors;
        }

        game.getCurrentPlayer().setHand(leaderCardsChosen);
        game.getCurrentPlayer().getHand().forEach(System.out::println);

        game.nextPlayer();

        boolean cardSelectionDone = true;
        for(Player p: game.getListOfPlayers()){
            if (p.getHand() == null || p.getHand().size() < 2) {
                cardSelectionDone = false;
                break;
            }
        }
        if(cardSelectionDone){
            game.setGamePhase(GamePhase.CHOOSE_RESOURCES);
            sendChooseResources();
        }
        else
            sendChooseLeaderCards();

        return errors;
    }

    /**
     * ths method calls the virtualView's method to send message "Choose initial Resources" and distributes initial FaithPoints
     */
    @Override
    public void sendChooseResources() {

        Player currentPlayer = game.getCurrentPlayer();
        int size = game.getListOfPlayers().size();


        if(size > 1 && currentPlayer.equals(game.getListOfPlayers().get(0)) && !firstSkipped){
            firstSkipped = true;
            game.nextPlayer();
            sendChooseResources();
        }
        else if(size >= 2 && currentPlayer.equals(game.getListOfPlayers().get(1)))
            viewInterface.toDoChooseResources(currentPlayer.getNickname(), 1);

        else if (size >= 3 && currentPlayer.equals(game.getListOfPlayers().get(2))) {
            viewInterface.toDoChooseResources(currentPlayer.getNickname(), 1);
            currentPlayer.moveOnPopeRoad();
            viewInterface.updatePlayerPosition(currentPlayer.getNickname());
        }
        else if(size >= 4 && currentPlayer.equals(game.getListOfPlayers().get(3))) {
            viewInterface.toDoChooseResources(currentPlayer.getNickname(), 2);
            game.getCurrentPlayer().moveOnPopeRoad();
            viewInterface.updatePlayerPosition(currentPlayer.getNickname());
        }
        else if(game.getCurrentPlayer().equals(game.getListOfPlayers().get(0))){
            game.setGamePhase(GamePhase.PLAY_TURN);
            viewInterface.playTurn(game.getCurrentPlayer().getNickname());
        }

    }

    /**
     * this method gives the initial resources chosen to the player
     * @param nickname player's nickname
     * @param resourcesChosen resourceTypes chosen
     * @return the list of errors generated
     */
    @Override
    public List<Error> onResourcesChosen(String nickname, Map<ResourceType,Integer> resourcesChosen){

        List<Error> errors = new ArrayList<>();

        if(!game.getGamePhase().equals(GamePhase.CHOOSE_RESOURCES)){
            errors.add(Error.WRONG_GAME_PHASE);
            return errors;
        }

        if(!nickname.equals(game.getCurrentPlayer().getNickname())) {
            errors.add(Error.NOT_YOUR_TURN);
            return errors;
        }

        int j=3;

        for(ResourceType r : resourcesChosen.keySet()) {
            boolean putCorrectly = false;
            try {
                for(int i=0; i<resourcesChosen.get(r); i++)
                    game.getCurrentPlayer().addResourceToDeposit(j, new Resource(r));
                j--;
            }catch(FullDepositException e){
                if(game.getCurrentPlayer().getActiveEffects().isExtraDeposit()){
                        for(AuxiliaryDeposit auxiliaryDeposit : game.getCurrentPlayer().getActiveEffects().getAuxiliaryDeposits())
                            if(!putCorrectly && auxiliaryDeposit.getType().equals(r))
                                putCorrectly = auxiliaryDeposit.addResource(new Resource(r));
                }
                 else errors.add(Error.DEPOSIT_IS_FULL);
            }
        }

        game.nextPlayer();

        if(game.getCurrentPlayer().equals(game.getListOfPlayers().get(0))) {
            game.setGamePhase(GamePhase.PLAY_TURN);
            viewInterface.playTurn(game.getCurrentPlayer().getNickname());
        }
        else
            sendChooseResources();

        return errors;
    }

    /**
     * this method controls if the player canactivate the production
     * @param nickname player's nickname
     * @return the list of errors generated
     */
    @Override
    public List<Error> onActivateProduction(String nickname) {

        List<Error> errors = new ArrayList<>(controlTurn(nickname));

        if(errors.isEmpty())
            errors = controlStandardAction();

        //if(errors.isEmpty())
            //viewInterface.playTurn(nickname);


        return errors;
    }

    /**
     * this method activates the production on DevelopmentCards
     * @param nickname the player's nickname
     * @param developmentCards tha cards chosen for production
     * @return the list of errors generated
     */
    @Override
    public List<Error> onActivateDevelopmentProduction(String nickname, List<DevelopmentCard> developmentCards){

        Player currPlayer = game.getCurrentPlayer();
        List<Error> errors;


        errors = controlTurn(nickname);
        if(!errors.isEmpty())
            return errors;

        if(developmentCards.isEmpty()) {
            errors.add(Error.GENERIC);
            return errors;
        }

        if(developmentProductionActivated)
            errors.add(Error.INVALID_ACTION);
        else {
            for (DevelopmentCard c : developmentCards) {
                try {
                    int i = 0;

                    for (Stack<DevelopmentCard> s : currPlayer.getPlayerBoard().getDevelopmentCards()) {

                        if (!s.isEmpty() && s.peek().getId().equals(c.getId())) {
                            currPlayer.activateProduction(i);
                            game.getCurrentPlayer().setStandardActionPlayed(true);
                            developmentProductionActivated = true;
                            if (currPlayer.getPosition().isPopeSpace())
                                game.vaticanReport(currPlayer.getPositionIndex());
                        }
                        i++;
                    }

                } catch (InsufficientPaymentException | InsufficientResourcesException e) {
                    errors.add(Error.INSUFFICIENT_PAYMENT);
                } catch (Exception e) {
                    e.printStackTrace();
                    errors.add(Error.GENERIC);
                }
            }
        }

        return errors;

    }

    /**
     * this method activates production on active leader cards
     * @param nickname the player's nickname
     * @param userChoice map with the leaderCards chosen for production ant the chosen resources to get
     * @return the list of errors generated
     */
    @Override
    public List<Error> onActivateLeaderProduction(String nickname, Map<LeaderCard, ResourceType> userChoice){

        Player currPlayer = game.getCurrentPlayer();
        List<Error> errors;

        errors = controlTurn(nickname);
        if(!errors.isEmpty())
            return errors;

        if(leaderProductionActivated)
            errors.add(Error.INVALID_ACTION);
        else {
            int i = 0;
            for (LeaderCard c : userChoice.keySet()) {
                if (c.getLeaderType().equals(LeaderCardType.EXTRA_PRODUCTION)) {

                    for(LeaderCard l : game.getCurrentPlayer().getActiveLeaderCards()) {
                        if (l.getId().equals(c.getId())) {
                            try {
                                ((ExtraProduction)l).setProductionResult(userChoice.get(c));
                                currPlayer.activateProductionLeader(i);
                                leaderProductionActivated = true;
                                game.getCurrentPlayer().setStandardActionPlayed(true);
                                if (currPlayer.getPosition().isPopeSpace())
                                    game.vaticanReport(currPlayer.getPositionIndex());;
                            } catch (InsufficientPaymentException e) {
                                errors.add(Error.INSUFFICIENT_PAYMENT);
                            }
                        }
                    }
                    if(!leaderProductionActivated) {
                        errors.add(Error.CARD_DOESNT_EXIST);
                        return errors;
                    }
                } else {
                    errors.add(Error.INVALID_ACTION);
                    return errors;
                }
                i++;
            }
        }
        return errors;
    }

    /**
     * this method activates the board production
     * @param nickname the player's nickname
     * @param userChoiceMap a map containing the resource the player wants to get e the ones to put in the production
     * @return the list of errors generated
     */
    @Override
    public List<Error> onActivateBoardProduction(String nickname, Map<Resource, List<ResourceType>> userChoiceMap){


        Map<ResourceType, List<ResourceType>> userChoice = new HashMap<>();

        for(Resource resource: userChoiceMap.keySet()){
            userChoice.put(resource.getType(), userChoiceMap.get(resource));
        }

        Player currPlayer = game.getCurrentPlayer();
        ResourceType toGet = null;
        List<Resource> toGive = new ArrayList<>();
        Map<ResourceType, Integer> requirements = new HashMap<>();

        List<Error> errors = controlTurn(nickname);
        if(!errors.isEmpty())
            return errors;

        if(boardProductionActivated)
            errors.add(Error.INVALID_ACTION);

        else if(userChoice.keySet().size()==1) {

            try {
                for(ResourceType r : userChoice.keySet())
                    toGet = r;

                if(toGet != null && userChoice.get(toGet)!=null) {
                    for (ResourceType r : userChoice.get(toGet)) {
                        toGive.add(new Resource(r));
                        requirements.merge(r,1, Integer::sum);
                    }

                    try{
                        currPlayer.checkCardRequirements(requirements);
                        currPlayer.getPlayerBoard().useProductionPower(toGive, toGet);
                        currPlayer.takeResourceForAction(requirements);
                        game.getCurrentPlayer().setStandardActionPlayed(true);
                        boardProductionActivated = true;

                    } catch(InsufficientPaymentException | InsufficientResourcesException e){
                        errors.add(Error.INSUFFICIENT_PAYMENT);
                        return errors;
                    }
                }
                else errors.add(Error.INVALID_ACTION);
                if(currPlayer.getPosition().isPopeSpace())
                    game.vaticanReport(currPlayer.getPositionIndex());
            } catch (Exception e) {
                errors.add(Error.GENERIC);
            }
        }
        else
            errors.add(Error.INVALID_ACTION);

        return errors;
    }

    /**
     * this method end a player's production
     * @param nickname the player's nickname
     * @return the list of errors generated
     */
    @Override
    public List<Error> onEndProduction(String nickname){

        List<Error> errors = new ArrayList<>(controlTurn(nickname));

        if(errors.isEmpty()) {
            if (developmentProductionActivated || leaderProductionActivated || boardProductionActivated) {
                Strongbox strongbox = game.getCurrentPlayer().getStrongbox();
                strongbox.moveFromTemporary();
                List<List<Resource>> warehouse = game.getCurrentPlayer().getPlayerBoard().getDeposit().getWarehouse();
                viewInterface.updateDepositAfterAction(game.getCurrentPlayer().getNickname(), strongbox.getAll(), warehouse);
                developmentProductionActivated = false;
                leaderProductionActivated = false;
                boardProductionActivated = false;

            } else {
                errors.add(Error.INVALID_ACTION);
            }

        }

        nextTurn();
        return errors;

    }

    /**
     * this method gives 50 of each resource to the player
     * @param nickname of the player
     * @return the list of errors generated
     */
    @Override
    public List<Error> onCheat(String nickname){

        List<Error> errors = new ArrayList<>(controlTurn(nickname));

        if(errors.isEmpty()) {
            Strongbox strongbox = game.getCurrentPlayer().getStrongbox();
            strongbox.addResourceCheat();
            List<List<Resource>> warehouse = game.getCurrentPlayer().getPlayerBoard().getDeposit().getWarehouse();
            viewInterface.updateDepositAfterAction(game.getCurrentPlayer().getNickname(), strongbox.getAll(), warehouse);
            game.getCurrentPlayer().setStandardActionPlayed(true);
        }

        nextTurn();
        return errors;
    }


    /**
     * this method calls Player's method buyDevelopmentCard
     * @param nickname the player's nickname
     * @param row the row of the cardMarket
     * @param column the column of the cardMarket
     * @return the list of errors generated
     */
    @Override
    public List<Error> onBuyDevelopmentCards(String nickname, int row, int column) {

        List<Error> errors = new ArrayList<>(controlTurn(nickname));

        if(errors.isEmpty())
            errors.addAll(controlStandardAction());

        if(errors.isEmpty()) {
            if (game.getCurrentPlayer().hasPlayedStandardAction()) {
                errors.add(Error.INVALID_ACTION);
                return errors;
            }

            try {
                System.out.println("row = " + row + "col = " + column);
                DevelopmentCard card = game.getGameBoard().getCardMarket().getCard(row,column);
                System.out.println(card.getCost());
                game.getCurrentPlayer().buyDevelopmentCard(row, column);
                game.getCurrentPlayer().setStandardActionPlayed(true);
                controlEndOfGame();
            } catch (InsufficientPaymentException e) {
                errors.add(Error.INSUFFICIENT_PAYMENT);
            } catch (NonExistentCardException e) {
                errors.add(Error.CARD_DOESNT_EXIST);
            } catch (Exception e) {
                errors.add(Error.GENERIC);
            }
        }

        nextTurn();

        return errors;
    }


    public List<Error> onPlaceCard(String nickname, DevelopmentCard card, int index){

        List<Error> errors = new ArrayList<>(controlTurn(nickname));

        if(errors.isEmpty()){
           try {
               game.getCurrentPlayer().getPlayerBoard().addDevelopmentCard(card, index);
           }
           catch (IllegalArgumentException e){
               errors.add(Error.INVALID_ACTION);
           }
        }
        return errors;
    }

    /**
     * this method calls the Player's method buyResoources
     * @param nickname the player's nickname
     * @param row the row of the marbleMarket
     * @param column the column of the marbleMarket
     * @return the list of errors generated
     */
    @Override
    public List<Error> onBuyResources(String nickname, int row, int column) {

        List<Error> errors = new ArrayList<>(controlTurn(nickname));
        List<Resource> resourcesBought;

        if(errors.isEmpty()){
            errors.addAll(controlStandardAction());
        }

        if(errors.isEmpty()) {

            Player player = game.getCurrentPlayer();
            resourcesBought = player.buyResources(row, column);
            player.setStandardActionPlayed(true);
            if(player.getActiveEffects().isExtraDeposit()){
                int numberOfExtraDeposit = player.getActiveEffects().getAuxiliaryDeposits().size();
                while(numberOfExtraDeposit > 0){
                    player.getActiveEffects().useExtraDepositEffect(resourcesBought,numberOfExtraDeposit - 1);
                    --numberOfExtraDeposit;
                }
                System.out.println("[SERVER]");
                System.out.println("extra deposit size == " + player.getActiveEffects().getAuxiliaryDeposits().get(0).getSize());
            }

            if (game.getCurrentPlayer().getPosition().isPopeSpace())
                game.vaticanReport(game.getCurrentPlayer().getPositionIndex());
            controlEndOfGame();
            viewInterface.sendResourcesBought(resourcesBought);

        }


        nextTurn();

        return errors;
    }



    /**
     * this method activates a player's leaderCard
     * @param nickname the player's nickname
     * @param leaderCard the leaderCard to activate
     * @return the list of errors generated
     */
    @Override
    public List<Error> onActivateLeader(String nickname, LeaderCard leaderCard) {

        List<Error> errors = new ArrayList<>(controlTurn(nickname));

        if(errors.isEmpty())
            errors.addAll(controlLeaderAction());

        if(errors.isEmpty()) {
            try {
                int index;
                List<LeaderCard> leaderCardList = (ArrayList<LeaderCard>) ((ArrayList<LeaderCard>) game.getCurrentPlayer().getHand()).clone();
                for(LeaderCard l : leaderCardList)
                    if(l.getId().equals(leaderCard.getId())) {
                        index = game.getCurrentPlayer().getHand().indexOf(l);
                        game.getCurrentPlayer().activateLeaderCard(index);
                        game.getCurrentPlayer().setLeaderActionPlayed(true);
                        controlEndOfGame();
                    }
            } catch (NonExistentCardException e) {
                errors.add(Error.CARD_DOESNT_EXIST);
            } catch (InsufficientResourcesException | InsufficientDevelopmentCardsException e) {
                errors.add(Error.INSUFFICIENT_PAYMENT);
            } catch (Exception e){
                e.printStackTrace();
            }
        }

        nextTurn();

        System.out.println(errors);
        System.out.println(errors.size());
        return errors;
    }

    /**
     * this method discards the player's chosen LeaderCard
     * @param nickname the player's nickname
     * @param leaderCard the card to discard
     * @return the list of errors generated
     */
    @Override
    public List<Error> onDiscardLeader(String nickname, LeaderCard leaderCard) {

        List<Error> errors = new ArrayList<>(controlTurn(nickname));
        Player curr = game.getCurrentPlayer();

        if(errors.isEmpty())
            errors.addAll(controlLeaderAction());

        if(errors.isEmpty()) {

            try {

                for(LeaderCard l : curr.getHand())
                    if(l.getId().equals(leaderCard.getId())) {
                        curr.discardLeader(curr.getHand().indexOf(l));
                        curr.setLeaderActionPlayed(true);
                        if (curr.getPosition().isPopeSpace())
                            game.vaticanReport(curr.getPositionIndex());
                        controlEndOfGame();
                        break;
                    }
            } catch (NonExistentCardException e) {
                errors.add(Error.CARD_DOESNT_EXIST);
            }
        }

        nextTurn();

        return errors;
    }


    /**
     * this method manages the case in which a player decides to end his turn before playing all the possible actions
     * @param nickname the player's nickname
     * @return the list of errors generated
     */
    @Override
    public List<Error> onEndTurn(String nickname){

        List<Error> errors = new ArrayList<>(controlTurn(nickname));

        if(errors.isEmpty()) {
            if (!game.getCurrentPlayer().hasPlayedStandardAction()) {
                errors.add(Error.INVALID_ACTION);
                return errors;
            }

            game.getCurrentPlayer().setStandardActionPlayed(false);
            game.getCurrentPlayer().setLeaderActionPlayed(false);

            controlEndOfGame();
            game.nextPlayer();
            if (game.getListOfPlayers().size() == 1) {
                onLorenzoTurn();
            }
            sendPlayTurn();
        }

        System.out.println(errors.size());
        return errors;
    }

    /**
     * Manages the Lorenzo turn
     */
    public void onLorenzoTurn(){

        ActionToken lorenzoAction = game.lorenzoTurn();
        int lorenzoPosition = game.getLorenzoPopeRoad().getCurrentPositionIndex();
        if(game.getLorenzoPopeRoad().getCurrentPosition().isPopeSpace())
            game.checkLorenzoPosition(game.getLorenzoPopeRoad().getCurrentPositionIndex());
        viewInterface.sendLorenzoTurn(lorenzoAction, lorenzoPosition);
    }

    /**
     * this method adds the resources just bought from the marbleMarket to the player's deposit
     * @param nickname the player's nickname
     * @param resources the resources and the floor to put them in
     * @return the list of errors generated
     */
    @Override
    public List<Error> onPlaceResources(String nickname, Map<Resource, Integer> resources){

        List<Error> errors = new ArrayList<>(controlTurn(nickname));

        Map<Resource, Integer> resourcesPut = new HashMap<>();

        if(errors.isEmpty()){
            for(Resource r : resources.keySet()){
                boolean putCorrectly = false;
                try {
                    game.getCurrentPlayer().addResourceToDeposit(resources.get(r), r );
                    resourcesPut.put(r, resources.get(r));
                } catch (FullDepositException e) {
                    /*
                    if(game.getCurrentPlayer().getActiveEffects().isExtraDeposit()){


                        for(AuxiliaryDeposit auxiliaryDeposit : game.getCurrentPlayer().getActiveEffects().getAuxiliaryDeposits())
                            if(!putCorrectly && auxiliaryDeposit.getType().equals(r.getType()))
                                putCorrectly = auxiliaryDeposit.addResource(r);
                    }


                    if(!putCorrectly) {
                        errors.add(Error.DEPOSIT_IS_FULL);

                        for (Resource put : resourcesPut.keySet()) {
                            game.getCurrentPlayer().getDeposit().getFloor(resourcesPut.get(put)).remove(put);
                        }
                        break;
                    }

                     */

                    errors.add(Error.DEPOSIT_IS_FULL);
                    break;
                } catch (Exception e) {
                    errors.add(Error.INVALID_ACTION);
                    for(Resource put : resourcesPut.keySet())
                        game.getCurrentPlayer().getDeposit().getFloor(resourcesPut.get(put)).remove(put);
                    break;
                }
            }
        }
        return errors;
    }


    /**
     * this method calls player's method swapDepositFloor
     * @param nickname the player's nickname
     * @param x first floor
     * @param y second floor
     * @return the list of errors generated
     */
    @Override
    public List<Error> onMoveDeposit (String nickname, int x, int y){

        List<Error> errors = new ArrayList<>(controlTurn(nickname));

        if(errors.isEmpty()) {
            try {
                game.getCurrentPlayer().swapDepositFloors(x, y);
            } catch (WrongDepositSwapException e) {
                errors.add(Error.NON_VALID_DEPOSIT_SWAP);
            }
        }
        return errors;
    }


    /**
     * this method calls the player's method discardResources
     * @param nickname the player's nickname
     * @param numberOfResourcesToDiscard the number of resources to discard
     * @return the list of errors generated
     */
    @Override
    public List<Error> onDiscardResource(String nickname, int numberOfResourcesToDiscard){

        List<Error> errors = new ArrayList<>(controlTurn(nickname));

        if(errors.isEmpty()) {
            game.movePlayersDiscard(nickname,numberOfResourcesToDiscard);
            game.getListOfPlayers().stream().map(Player::getNickname).filter(pNickname -> !pNickname.equals(nickname)).collect(Collectors.toList()).forEach(viewInterface::updatePlayerPosition);
        }

        return errors;

    }

    /**
     * this method controls if a player has reached one of the conditions to end the game, which means:
     * if he has drawn the 7th DevelopmentCard, or
     * if he has reached the last popespace
     */
    public void controlEndOfGame(){
        if(!isLastRound) {

            int numOfCards = 0;
            for (Stack<DevelopmentCard> stack : game.getCurrentPlayer().getPlayerBoard().getDevelopmentCards())
                numOfCards = numOfCards + stack.size();

            if (numOfCards == 7 || game.getCurrentPlayer().getPositionIndex() == game.getCurrentPlayer().getPopeRoad().getSize() - 1) {

                viewInterface.lastRound();
                isLastRound=true;
            }
        }else{
            if(game.getListOfPlayers().get(0).equals(game.getCurrentPlayer())) {
                Player winner = game.endGame();
                viewInterface.notifyWinner(winner.getNickname());
            }
        }

    }

    /**
     * this method controls if the player has played all the possible actions and, if so, it passes the turn to the next player
     * if it is the last turn, game ends
     */
    public void nextTurn(){


        if(game.getCurrentPlayer().hasPlayedStandardAction() && game.getCurrentPlayer().hasPlayedLeaderAction()) {

            sendEndTurn();

            game.nextPlayer();

            if(isLastRound && game.getListOfPlayers().get(0).equals(game.getCurrentPlayer())) {
                Player winner = game.endGame();
                viewInterface.notifyWinner(winner.getNickname());
            }

            else {
                game.getCurrentPlayer().setStandardActionPlayed(false);
                game.getCurrentPlayer().setLeaderActionPlayed(false);
                if (game.getListOfPlayers().size() == 1)
                    game.lorenzoTurn();
                sendPlayTurn();
            }

        }

    }

    /**
     * this method controls if the message received matches the Game phase and if it is the player's turn
     * @param nickname the nickname of the player who has sent the request
     * @return the list of errors generated
     */
    public List<Error> controlTurn(String nickname){

        List<Error> errors = new ArrayList<>();

        if(!game.getGamePhase().equals(GamePhase.PLAY_TURN)){
            errors.add(Error.WRONG_GAME_PHASE);
        }

        if(!nickname.equals(game.getCurrentPlayer().getNickname()))
            errors.add(Error.NOT_YOUR_TURN);

        return errors;
    }


    /**
     * this method controls if the player has already played a Standard Action in this turn
     * standardActions are: ActivateProduction, BuyDevelopmentCard, BuyResources
     * @return the list of errors generated
     */
    public List<Error> controlStandardAction(){

        List<Error> errors = new ArrayList<>();

        if(game.getCurrentPlayer().hasPlayedStandardAction())
            errors.add(Error.INVALID_ACTION);

        return errors;
    }


    /**
     * this class controls if the player has already played all the Leader Action he could in this turn
     * leaderActions are: activateLeader, discardLeader
     * @return the list of errors generated
     */
    public List<Error> controlLeaderAction(){

        List<Error> errors = new ArrayList<>();

        if(game.getCurrentPlayer().hasPlayedLeaderAction())
            errors.add(Error.INVALID_ACTION);

        return errors;

    }

    /**
     * this method reacts to a disconnection removing the player who has disconnected from the game
     * @param nickname nickname of the player who has disconnected
     * @return the list of errors generated
     */
    @Override
    public List<Error> onPlayerDisconnection(String nickname) {
        List<Error> errors = new ArrayList<>();

        if(!game.getListOfPlayers().contains(game.getPlayerByNickname(nickname)))
            errors.add(Error.INVALID_ACTION);
        else {
            //game.nextPlayer();
            game.removePlayer(nickname);


            if(game.getListOfPlayers().size() <= 1)
                viewInterface.endMatch();

        }

        return errors;
    }

    /**
     * this method calls the virtualView's method PlayTurn
     */
    @Override
    public void sendPlayTurn(){
        viewInterface.playTurn(game.getCurrentPlayer().getNickname());
    }


    /**
     * this method calls the virtualView's method endTurn
     */
    @Override
    public void sendEndTurn(){
        viewInterface.endTurn(game.getCurrentPlayer().getNickname());
    }

    public Game getGame(){
        return this.game;
    }

    /**
     * Sends the updated board to the client after a successful standard action
     */
    public Board sendBoardUpdate(String user){
        return game.getPlayerByNickname(user).getPlayerBoard();
    }

    /**
     * Add the previously disconnected player to the game
     * @param disconnectedPlayer the previously disconnected player
     */
    @Override
    public void onPlayerReconnection(String disconnectedPlayer) {

        game.reconnectPlayer(disconnectedPlayer);
    }

    @Override
    public Marble[][] getMarbleMarket() {
        return game.getGameBoard().getMarket().marbles();
    }

    @Override
    public DevelopmentDeck[][] getCardMarket() {
        return game.getGameBoard().getCardMarket().getCardMarket();
    }

    public int getPlayerCurrentPosition(String nickname){
        return game.getPlayerByNickname(nickname).getPositionIndex();
    }

    @Override
    public Marble getFreeMarble() {
        return game.getGameBoard().getMarket().getFreeMarble();
    }

    public List<List<Resource>> getUpdatedDeposit(String player){
        List<List<Resource>> warehouse = game.getPlayerByNickname(player).getPlayerBoard().getDeposit().getWarehouse();
        return warehouse;
    }

    public Map<ResourceType, List<Resource>> getUpdatedStrongbox(String player) {
        Strongbox strongbox = game.getPlayerByNickname(player).getStrongbox();
        {
            return strongbox.getAll();
        }
    }

    /**
     * Get the player active leader cards
     *
     * @param player player
     * @return player's active leader cards
     */
    @Override
    public List<LeaderCard> getPlayerActiveLeaderCards(String player) {
        return game.getPlayerByNickname(player).getActiveLeaderCards();
    }

    /**
     * Get the player hand
     *
     * @param player player
     * @return player's hand
     */
    @Override
    public List<LeaderCard> getPlayerHand(String player) {
        return game.getPlayerByNickname(player).getHand();
    }

    /**
     * Get the updated auxiliary deposit
     *
     * @param user the player
     * @return the updated auxiliary deposit
     */
    @Override
    public List<AuxiliaryDeposit> getUpdateAuxiliaryDeposit(String user) {
        Player player = game.getPlayerByNickname(user);
        if(player.getActiveEffects().isExtraDeposit()){
            return player.getActiveEffects().getAuxiliaryDeposits();
        }
        return new ArrayList<AuxiliaryDeposit>();
    }

}
