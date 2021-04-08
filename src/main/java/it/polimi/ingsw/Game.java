package it.polimi.ingsw;

import it.polimi.ingsw.cards.ActionTokenDeck;
import it.polimi.ingsw.cards.Deck;
import it.polimi.ingsw.cards.DevelopmentCard;
import it.polimi.ingsw.cards.leadercards.LeaderCard;
import it.polimi.ingsw.cards.leadercards.LeaderDeck;
import it.polimi.ingsw.exception.InsufficientPaymentException;
import it.polimi.ingsw.exception.NonexistentCardException;
import it.polimi.ingsw.gameboard.CardMarket;
import it.polimi.ingsw.gameboard.GameBoard;
import it.polimi.ingsw.gameboard.Resource;
import it.polimi.ingsw.gameboard.ResourceType;
import it.polimi.ingsw.player.*;

import java.util.*;
import java.util.concurrent.ThreadLocalRandom;

public class Game {


    private ArrayList<Player> listOfPlayers;
    private Player currentPlayer;
    private ArrayList<Player> leaderboard;
    private Player winner;
    private Queue<Integer> popeSpaces;
    private PopeRoad lorenzoPopeRoad;
    private ActionTokenDeck actionDeck;
    private LeaderDeck leaderDeck;
    private GameBoard gameBoard;
    private int lorenzoPoints;
    private int lastPopeSpace;
    private final int RESOURCE_VICTORY_POINTS_RATIO = 5;
    /*
        *constructor
     */
    Game(){



    }

    /*
       * this method start a match and allocate the resources and turn for each player
     */
    public void startGame(){

        for(Player p: listOfPlayers){
            ArrayList<LeaderCard> hand = new ArrayList<LeaderCard>();
            for(int i = 0; i < 4; i++){
                hand.add(leaderDeck.drawCard());

            }
            p.setHand(hand);
        }

        Collections.shuffle(listOfPlayers);
        currentPlayer = listOfPlayers.get(0);
        // player know choose the resource they want, could be passed to the method as a map Player:ResourceType

        // this is based on the game logic, hardcoded, need a fix
        for(int i = 0; i < listOfPlayers.size(); i++){
            //give victory points to third and fourth
            if(i + 1 == 2 || i + 1 == 4) listOfPlayers.get(i).addVictoryPoints(1);
        }

    }

    /*
        * this method return the next player
     */

    public Player nextPlayer(){

        int i = listOfPlayers.indexOf(currentPlayer);
        Player nextPlayer;
        if(i < listOfPlayers.size()-1)
             nextPlayer = listOfPlayers.get(i+1);
        else{
            i = 0;
            nextPlayer = listOfPlayers.get(i);
        }
        return nextPlayer;
    }

    /*
        * this method receive a nickname and return the correspondent player
     */
    public Player getPlayerByNickname(String nickname) throws Exception {


        for (Player p : listOfPlayers) {
            if (p.getNickname().equals(nickname)) {
                return p;
            }
        }
        throw new Exception();

    }

    /*
        * this method add a new player to the match
     */

    public void addPlayer(Player p){
        listOfPlayers.add(p);
    }

    /*
        * this method return the winner of the match
     */
    public Player getWinner() {
        return winner;
    }


    /*
       * this method return the list of players in the current match
     */

    public ArrayList<Player> getListOfPlayers() {
        return listOfPlayers;
    }

    /*
        * this method return the current player
     */
    public Player getCurrentPlayer(){ return currentPlayer;}

    /*
        * this method is called each time a player arrives to a pope Space and
        * check the position of all the players in the popeRoad and allocates the victory points
     */

    public void checkPlayersPosition(int currentPlayerPositionIndex) {

        Cell currentPlayerPosition = currentPlayer.getPosition();
        int currentPlayerVaticanId = currentPlayerPosition.getVaticanReportSectionId();

        if(currentPlayerPositionIndex > popeSpaces.peek()){
            int victoryPoints = currentPlayer.getPopeRoad().VaticanReport(currentPlayerPosition);
            currentPlayer.addVictoryPoints(victoryPoints);
            for(Player p: listOfPlayers){
                if(p.getPosition().getVaticanReportSectionId() >= currentPlayerVaticanId)
                    p.getPopeRoad().VaticanReport(p.getPosition());
            }
            popeSpaces.remove();
        }

        else if(currentPlayerPositionIndex == lastPopeSpace) endGame();

    }

    /*
        * this method prepare the settings to start a single player match
     */

    public void setSinglePlayerCPU(){

        CellFactory cellFactory = new CellFactory();
        ActionTokenFactory actionTokenFactory = new ActionTokenFactory();
        List<Cell> cells = Arrays.asList(cellFactory.getCells());
        lorenzoPopeRoad = new PopeRoad(cells);
        ArrayList<ActionToken> actionTokenList = actionTokenFactory.getTokens();
        actionDeck = new ActionTokenDeck(actionTokenList);


    }

    /*
        * this method play the turn of the CPU in an single player match the actions of the CPU in a single player match
    */

    public void LorenzoTurn(){

        ActionToken actionToken = actionDeck.drawCard();
        //useActionToken(actionToken.getService);
    }



    /*
     * this method is called each time a player arrives to a pope Space and
     * check the position of all the players in the popeRoad and allocates the victory points.
     * (single player mode)
     */
    public void checkLorenzoPosition(int lorenzoPosition){

        if(lorenzoPosition > popeSpaces.peek()){
            int lorenzoVaticanId = lorenzoPopeRoad.getCurrentPosition().getVaticanReportSectionId();
            if(currentPlayer.getPosition().getVaticanReportSectionId() >= lorenzoVaticanId)
                currentPlayer.getPopeRoad().VaticanReport(currentPlayer.getPosition());
            popeSpaces.remove();
        }
        else if(lorenzoPosition == lastPopeSpace) lostGame();
    }

    /*
     * this method use the selected action Token in a single player match
     * @param the action token (type: ActionTokenMove)
    */

    public void useActionToken(ActionTokenMove actionToken){

        lorenzoPopeRoad.move(actionToken.getSteps());
        if(lorenzoPopeRoad.getCurrentPosition().isPopeSpace())
            checkLorenzoPosition(lorenzoPopeRoad.getCurrentPositionIndex());
        if(actionToken.isShuffle())
            actionDeck.shuffle();

    }

    /*
     * this method use the selected action Token in a single player match
     * @param the action token (type: ActionTokenDiscard)
    */

    public void useActionToken(ActionTokenDiscard actionToken){

        CardMarket market = gameBoard.getCardMarket();
        market.discardCard(actionToken.getAmount(), actionToken.getType());
        //lostGame();

    }

    /*
        * this method manage the loss in a single player match
     */
    private void lostGame() {
    }

    /*
        * this method manage the end of match and assign the winner
     */

    public void endGame(){

        for(Player p: listOfPlayers){

            p.addVictoryPoints(checkCardsPoints(p));
            p.addVictoryPoints(checkResourcePoints(p));
        }

        winner = listOfPlayers.stream().max(Comparator.comparing(Player::getVictoryPoints)).get();

    }

    /*
        * this method add the victory points to the player based on player's card
        * @param the player
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

    /*
     * this method add the victory points to the player based on player's resources
     * @param the player
     */

    private int checkResourcePoints(Player p) {

       return Math.floorDiv(p.getDeposit().getAll().size() , RESOURCE_VICTORY_POINTS_RATIO);

    }

    /*
        * this method move the other players after current player discard resources
        * and distributes the faith points
        * @param the number of steps forward for each player
     */

    public void movePlayersDiscard(int steps){

        listOfPlayers.stream()
                        .filter(p -> !p.equals(currentPlayer))
                        .forEach(p-> p.moveOnPopeRoadDiscard(steps));

        listOfPlayers
                .stream()
                .filter(p -> p.getPosition().isPopeSpace())
                .forEach(p -> checkPlayersPosition(p.getPositionIndex()));

    }

}


