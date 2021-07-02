package it.polimi.ingsw.model;

import it.polimi.ingsw.controller.GamePhase;
import it.polimi.ingsw.model.cards.ActionTokenDeck;
import it.polimi.ingsw.model.cards.CardFactory;
import it.polimi.ingsw.model.cards.DevelopmentCard;
import it.polimi.ingsw.model.cards.DevelopmentDeck;
import it.polimi.ingsw.model.cards.leadercards.LeaderCard;
import it.polimi.ingsw.model.cards.leadercards.LeaderDeck;
import it.polimi.ingsw.model.gameboard.GameBoard;
import it.polimi.ingsw.model.gameboard.Resource;
import it.polimi.ingsw.model.gameboard.ResourceType;
import it.polimi.ingsw.model.player.*;
import it.polimi.ingsw.controller.Error;

import java.util.*;

/**
 * this class represents the game
 */
public class Game {

    private GamePhase gamePhase;

    private final List<Player> listOfPlayers;
    private Player currentPlayer;
    private Player winner;
    private final LeaderDeck leaderDeck;
    private final DevelopmentDeck developmentDeck;
    private final GameBoard gameBoard;
    private int lorenzoPoints;
    private final List<PopeSection> popeSectionList;
    private PopeSection lastPopeSection;
    private final Map<String,Player> disconnectedPlayers;

    private PopeRoad lorenzoPopeRoad;
    private ActionTokenDeck actionDeck;

    private static final int LAST_POPE_SPACE = 24;
    private final int RESOURCE_VICTORY_POINTS_RATIO = 5;
    private static final int STARTING_VICTORY_POINTS = 1;


    public Game(){

        gameBoard = new GameBoard();
        CardFactory cardFactory = new CardFactory();
        leaderDeck = new LeaderDeck(cardFactory.getLeaderCards());
        developmentDeck = new DevelopmentDeck(cardFactory.getDevelopmentCards());
        listOfPlayers = new ArrayList<>();
        PopeSectionFactory popeSectionFactory = new PopeSectionFactory();
        popeSectionList = popeSectionFactory.getPopeSections();
        lastPopeSection = popeSectionList.remove(0);
        disconnectedPlayers = new HashMap<>();

        gamePhase = GamePhase.LOGIN;

    }


    /**
     * this method return the next player
     */
    public void nextPlayer(){

        int i = listOfPlayers.indexOf(currentPlayer);

        if(i == -1){
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
        } catch (Exception ignored){}

        return null;
    }

    /**
     * this method add a new player to the match
     */
    public List<Error> addPlayer(Player p){

        List<Error> errors = new ArrayList<>();
        if(listOfPlayers.size() >= 4)
            errors.add(Error.GAME_ALREADY_FULL);
        for(Player player : listOfPlayers)
            if(p.getNickname().equals(player.getNickname()))
                errors.add(Error.NICKNAME_ALREADY_EXISTS);
        if(errors.isEmpty())
          listOfPlayers.add(p);

        return errors;
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
    public ActionToken lorenzoTurn(){

        ActionToken actionToken = actionDeck.drawCard();
        if(actionToken.useEffect(lorenzoPopeRoad, gameBoard.getCardMarket(), actionDeck)) lostGame();
        if(lorenzoPopeRoad.getCurrentPosition().isPopeSpace())
            checkLorenzoPosition(lorenzoPopeRoad.getCurrentPositionIndex());
        return actionToken;
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
     * @return
     */
    public Map<String, Integer> endGame(){

        for(Player p: listOfPlayers){
            p.addVictoryPoints(checkCardsPoints(p));
            p.addVictoryPoints(checkResourcePoints(p));
        }
        winner = listOfPlayers.stream().max(Comparator.comparing(Player::getVictoryPoints)).get();
        Map<String,Integer> leaderboard = new TreeMap<>();
        for(Player player: listOfPlayers ){
            leaderboard.put(player.getNickname(), player.getVictoryPoints());
        }
        return leaderboard;
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
     * @param nickname the current player that discarded the resources
     * @param steps the number of steps forward for each player
     */
    public void movePlayersDiscard(String nickname, int steps){

        listOfPlayers.stream()
                        .filter(p -> !p.getNickname().equals(nickname))
                        .forEach(p-> p.moveOnPopeRoadDiscard(steps));
        listOfPlayers
                .stream()
                .filter(p -> p.getPosition().isPopeSpace())
                .forEach(p -> vaticanReport(p.getPositionIndex()));

    }

    /**
     * @return the deck of leader cards
     */
    public LeaderDeck getLeaderDeck(){
        return leaderDeck;
    }

    /**
     * @return the deck of development cards
     */
    public DevelopmentDeck getDevelopmentDeck(){ return developmentDeck;
    }

    /**
     * this method establishes the player's order to play
     */
    public void setPlayersOrder(){
        Collections.shuffle(listOfPlayers);
        currentPlayer = listOfPlayers.get(0);
    }

    /**
     * this method removes a player from the game
     * @param nickname the nickname of the player to remove
     */
    public void removePlayer(String nickname){

        Player p = getPlayerByNickname(nickname);
        if(p != null) {
            disconnectedPlayers.put(p.getNickname(), p);
            listOfPlayers.remove(p);
        }
    }

    /**
     * this method sets the current game phase
     * @param gamePhase the game phase to set
     */
    public void setGamePhase(GamePhase gamePhase){
        this.gamePhase = gamePhase;
    }

    /**
     * @return current game phase
     */
    public GamePhase getGamePhase() {
        return gamePhase;
    }

    /**
     * this method sets the current player
     * @param p current player
     */
    public void setCurrentPlayer (Player p){
        currentPlayer = p;
    }

    /**
     * Reconnects a previously disconnected player
     * @param disconnectedPlayer the player that reconnected
     */
    public void reconnectPlayer(String disconnectedPlayer) {

        if(disconnectedPlayers.containsKey(disconnectedPlayer)){
            Player p = disconnectedPlayers.get(disconnectedPlayer);
            listOfPlayers.add(p);
        }
    }

    /**
     * @return lorenzo's pope road
     */
    public PopeRoad getLorenzoPopeRoad() {
        return lorenzoPopeRoad;
    }
}


