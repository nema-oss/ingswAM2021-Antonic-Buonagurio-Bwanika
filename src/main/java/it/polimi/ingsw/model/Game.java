package it.polimi.ingsw.model;

import it.polimi.ingsw.controller.GamePhase;
import it.polimi.ingsw.model.cards.ActionTokenDeck;
import it.polimi.ingsw.model.cards.CardFactory;
import it.polimi.ingsw.model.cards.DevelopmentCard;
import it.polimi.ingsw.model.cards.leadercards.LeaderCard;
import it.polimi.ingsw.model.cards.leadercards.LeaderDeck;
import it.polimi.ingsw.model.exception.FullDepositException;
import it.polimi.ingsw.model.gameboard.GameBoard;
import it.polimi.ingsw.model.gameboard.Resource;
import it.polimi.ingsw.model.gameboard.ResourceType;
import it.polimi.ingsw.model.player.*;
import it.polimi.ingsw.controller.Error;

import java.util.*;

public class Game {

    private GamePhase gamePhase;

    private List<Player> listOfPlayers;
    private Player currentPlayer;
    private Player winner;
    private LeaderDeck leaderDeck;
    private GameBoard gameBoard;
    private int lorenzoPoints;
    private List<PopeSection> popeSectionList;
    private PopeSection lastPopeSection;

    // single player mode
    private PopeRoad lorenzoPopeRoad;
    private ActionTokenDeck actionDeck;

    // constants
    private static final int LAST_POPE_SPACE = 24;
    private final int RESOURCE_VICTORY_POINTS_RATIO = 5;
    private static final int STARTING_VICTORY_POINTS = 1;


    public Game(){

        gameBoard = new GameBoard();
        CardFactory cardFactory = new CardFactory();
        leaderDeck = new LeaderDeck(cardFactory.getLeaderCards());
        listOfPlayers = new ArrayList<>();
        PopeSectionFactory popeSectionFactory = new PopeSectionFactory();
        popeSectionList = popeSectionFactory.getPopeSections();
        lastPopeSection = popeSectionList.remove(0);

        gamePhase = GamePhase.LOGIN;

    }

    /*
       * this method start a match and allocate the resources and turn for each player
     */
    public void startGame(Map<String, List<LeaderCard>> playerCardChoices, Map<String,ResourceType> playerResourceChoices) throws Exception, FullDepositException {

        /* this part should go outside the model in the player creation part
        for(Player p: listOfPlayers){
            ArrayList<LeaderCard> hand = new ArrayList<LeaderCard>();
            for(int i = 0; i < 4; i++){
                hand.add(leaderDeck.drawCard());

            }
            p.setHand(hand);

            Collections.shuffle(listOfPlayers);
            currentPlayer = listOfPlayers.get(0);
        }

         */

        for(String nickname: playerCardChoices.keySet()){
            getPlayerByNickname(nickname).getHand().removeAll(playerCardChoices.get(nickname));
        }

        // not exactly correct since a player could choose two different resources

        for(String nickname: playerResourceChoices.keySet()){
            Player p = getPlayerByNickname(nickname);
            int playerIndex = listOfPlayers.indexOf(p);
            if(playerIndex + 1 == 3){
                p.addResourceToDeposit(1, new Resource(playerResourceChoices.get(nickname)));
                p.addVictoryPoints(STARTING_VICTORY_POINTS);
            }
            else if(playerIndex + 1 == 4){
                p.addResourceToDeposit(1, new Resource(playerResourceChoices.get(nickname)));
                p.addResourceToDeposit(2, new Resource(playerResourceChoices.get(nickname)));
                p.addVictoryPoints(STARTING_VICTORY_POINTS);

            }

        }

    }

    /**
     * this method return the next player
     */
    public void nextPlayer(){

        int i = listOfPlayers.indexOf(currentPlayer);

        if(i == -1){ // only for testing since we don't have startGame yet
            currentPlayer = listOfPlayers.get(0);
            return;
        }
        Player nextPlayer;
        if(i < listOfPlayers.size()-1)
             nextPlayer = listOfPlayers.get(i+1);
        else{
            i = 0;
            nextPlayer = listOfPlayers.get(i);
        }
        currentPlayer = nextPlayer;
    }
    /**
     * this method receive a nickname and return the correspondent player TODO fix null
     */
    public Player getPlayerByNickname(String nickname) {

        try {
            for (Player p : listOfPlayers) {
                if (p.getNickname().equals(nickname)) {
                    return p;
                }
            }
            throw new Exception();
        } catch (Exception e){e.printStackTrace();}

        return null;
    }

    /**
     * this method add a new player to the match
     */
    public List<Error> addPlayer(Player p){
        List<Error> errors = new ArrayList<>();
        if(listOfPlayers.size() >= 4 && !errors.contains(Error.GAME_ALREADY_FULL))
            errors.add(Error.GAME_ALREADY_FULL);
        for(Player player : listOfPlayers)
            if(p.getNickname().equals(player.getNickname()))
                errors.add(Error.NICKNAME_ALREADY_EXISTS);
        listOfPlayers.add(p);
        return errors; //loro usano unmodifiable
    }

    /**
     * this method return the list of players in the current match
     */
    public List<Player> getListOfPlayers() {
        return listOfPlayers;
    }

    /**
     * this method return the current player
     */
    public Player getCurrentPlayer(){ return currentPlayer;}

    /**
     * this method return the action token deck
     */
    public ActionTokenDeck getActionDeck() {
        return actionDeck;
    }

    /**
     * this method return the gameBoard
     */
    public GameBoard getGameBoard() {
        return gameBoard;
    }

    /**
     * this method is called each time a player arrives to a pope Space and
     * check the position of all the players in the popeRoad and allocates the victory points
     */
    public void vaticanReport(int currentPlayerPositionIndex) {

        Cell currentPlayerPosition = currentPlayer.getPosition();
        int currentPlayerVaticanId = currentPlayerPosition.getVaticanReportSectionId();

        if(currentPlayerVaticanId == lastPopeSection.getID()){
            for(Player p: listOfPlayers){
                if(p.getPosition().getVaticanReportSectionId() == lastPopeSection.getID())
                    p.addVictoryPoints(lastPopeSection.getPoints());
            }
            lastPopeSection = popeSectionList.remove(0);

        }

        else if(currentPlayerPositionIndex == LAST_POPE_SPACE) endGame();

    }

    /**
     * this method prepare the settings to start a single player match
     */
    public void setSinglePlayerCPU(){

        CellFactory cellFactory = new CellFactory();
        ActionTokenFactory actionTokenFactory = new ActionTokenFactory();
        List<Cell> cells = Arrays.asList(cellFactory.getCells());
        lorenzoPopeRoad = new PopeRoad(cells);
        List<ActionToken> actionTokenList = actionTokenFactory.getTokens();
        actionDeck = new ActionTokenDeck(actionTokenList);
    }

    /**
     * * this method play the turn of the CPU in an single player match the actions of the CPU in a single player match
    */
    public void lorenzoTurn(){

        ActionToken actionToken = actionDeck.drawCard();
        if(actionToken.useEffect(lorenzoPopeRoad, gameBoard.getCardMarket(), actionDeck)) lostGame();
        if(lorenzoPopeRoad.getCurrentPosition().isPopeSpace())
            checkLorenzoPosition(lorenzoPopeRoad.getCurrentPositionIndex());
    }

    /**
     * this method is called each time a Lorenzo arrives to a pope Space and
     * check the position of all the players in the popeRoad and allocates the victory points.
     * (single player mode)
     */
    public void checkLorenzoPosition(int lorenzoPosition){

        if(lorenzoPopeRoad.getCurrentPosition().getVaticanReportSectionId() == lastPopeSection.getID()){
            for(Player p: listOfPlayers){
                if(p.getPosition().getVaticanReportSectionId() == lastPopeSection.getID())
                    p.addVictoryPoints(lastPopeSection.getPoints());
            }
            lastPopeSection = popeSectionList.remove(0);
        }

        else if(lorenzoPosition == LAST_POPE_SPACE) lostGame();

    }

    /**
     * this method manage the loss in a single player match
     */
    private void lostGame(){}

    /**
     * this method manage the end of match and assign the winner
     */
    public Player endGame(){

        for(Player p: listOfPlayers){
            p.addVictoryPoints(checkCardsPoints(p));
            p.addVictoryPoints(checkResourcePoints(p));
        }
        winner = listOfPlayers.stream().max(Comparator.comparing(Player::getVictoryPoints)).get();
        return winner;
    }

    /**
     * this method add the victory points to the player based on player's card
     * @param p the player
     */
    private int checkCardsPoints(Player p) {

        int points = 0;
        for(Stack<DevelopmentCard> cards: p.getPlayerBoard().getDevelopmentCards()){
            for(DevelopmentCard card: cards){
                points += card.getVictoryPoints();
            }
        }
        for(LeaderCard card: p.getActiveLeaderCards()){
            points += card.getVictoryPoints();
        }
        return points;
    }

    /**
     * this method add the victory points to the player based on player's resources
     * @param p the player
     */
    private int checkResourcePoints(Player p) {

        int amount = 0;
        Map<ResourceType,List<Resource>> deposit = p.getDeposit().getAll();
        Map<ResourceType,List<Resource>> strongbox = p.getStrongbox().getAll();
        for(ResourceType resourceType: deposit.keySet()){
            amount += deposit.get(resourceType).size();
        }
        for(ResourceType resourceType: strongbox.keySet()){
            amount += strongbox.get(resourceType).size();
        }
        Effects effects = p.getActiveEffects();
        if(effects.isExtraDeposit()){
            amount += effects.getAuxiliaryDeposit(0).getSize();
        }
       return Math.floorDiv(amount , RESOURCE_VICTORY_POINTS_RATIO);

    }

    /**
     * this method move the other players after current player discard resources
     * and distributes the faith points
     * @param steps the number of steps forward for each player
     */
    public void movePlayersDiscard(int steps){

        listOfPlayers.stream()
                        .filter(p -> !p.equals(currentPlayer))
                        .forEach(p-> p.moveOnPopeRoadDiscard(steps));
        listOfPlayers
                .stream()
                .filter(p -> p.getPosition().isPopeSpace())
                .forEach(p -> vaticanReport(p.getPositionIndex()));

    }

    public LeaderDeck getLeaderDeck(){
        return leaderDeck;
    }

    public void setPlayersOrder(){
        Collections.shuffle(listOfPlayers);
        currentPlayer = listOfPlayers.get(0);
    }

    public void removePlayer(String nickname){

        Player p = getPlayerByNickname(nickname);
        listOfPlayers.remove(p);
    }

    public void setGamePhase(GamePhase gamePhase){
        this.gamePhase = gamePhase;
    }

    public GamePhase getGamePhase() {
        return gamePhase;
    }
}


