package it.polimi.ingsw;

import sun.security.provider.HmacDrbg;

import java.math.RoundingMode;
import java.util.*;
import java.util.concurrent.ThreadLocalRandom;

public class Game {


    private ArrayList<Player> listOfPlayers;
    private Player currentPlayer;
    private ArrayList<Player> leaderboard;
    private Player winner;
    private Queue<Integer> popeSpaces;
    private PopeRoad lorenzoPopeRoad;
    private Deck actionDeck;
    private LeaderDeck leaderDeck;
    private GameBoard gameBoard;
    private ConfigFactory configFactory;
    private GameBoard gameBoard;
    private int lorenzoPoints;
    private int lastPopeSpace;

    /*
        *constructor
     */
    Game(){

        configFactory = new ConfigFactory();
        leaderDeck = configFactory.getLeaderDeck();
        developmentDeck = configFactory.getDevelopmentDeck();
        gameBoard = new GameBoard();
        popeSpaces = configFactory.getPopeSpaces();

    }

    /*
       * this method start a match and allocate the resources and turn for each player
     */
    public void startGame(){

        for(Player p: listOfPlayers){
            ArrayList hand = new ArrayList<LeaderCard>();
            for(int i = 0; i < 4; i++){
                hand.add(leaderDeck.drawCard());

            }
            p.setHand(hand);
        }

        int randomNum= ThreadLocalRandom.current().nextInt(0, listOfPlayers.size()-1);
        currentPlayer = listOfPlayers.get(randomNum);
        // players should be able to select a resource + give points: this part will be hardcoded

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

        else if(currentPlayerPositionIndex == lastPopeSpace) return endGame();

    }

    /*
        * this method prepare the settings to start a single player match
     */

    /*public void setSinglePlayerCPU(){

        lorenzoPopeRoad = new PopeRoad();
        actionDeck = new ActionDeck();


    }

     */

    /*
        * this method play the turn of the CPU in an single player match the actions of the CPU in a single player match
    */

    public void LorenzoTurn(){

        ActionToken actionToken = actionDeck.draw();


    }



    /*
     * this method is called each time a player arrives to a pope Space and
     * check the position of all the players in the popeRoad and allocates the victory points.
     * (single player mode)
     */
    public void checkLorenzoPosition(int lorenzoPosition){

        if(lorenzoPosition > popeSpaces.peek()){
            int lorenzoVaticanId = lorenzoPopeRoad.getCurrentPosition().vaticanReportSectionId;
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
        if(lorenzoPopeRoad.getCurrentPosition().isPopeSpace()) checkLorenzoPosition(lorenzoPopeRoad.getCurrentPositionIndex());
        if(actionToken.isShuffle()) actionDeck.shuffle();

    }

    /*
     * this method use the selected action Token in a single player match
     * @param the action token (type: ActionTokenDiscard)
    */

    public void useActionToken(ActionTokenDiscard actionToken){

        Market market = gameBoard.getMarket();
        market.discardCard(actionToken.getAmount());
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

       return Math.floorDiv(p.getDeposit().getAll().size() , 5);

    }

    /*
        * this method manage the buying resource request of a player
        * @param coordinates of the selected space in the market
     */

    public ArrayList<Resource> buyResource(int x, int y) {

        Market market = gameBoard.getMarket();
        return market.buy(x,y)
    }

    /*
     * this method manage the buying card request of a player
     * @param coordinates of the selected card in the market
    */

    public void buyCard(int x, int y) {

        CardMarket cardMarket = gameBoar.getCardMarket();
        DevelopmentCard selectedCard = cardMarket.getCard(x,y);
        Map<ResourceType,Integer> cost = selectedCard.getCost();
        ArrayList<Resource> currentResources:
        for(ResourceType type: cost.keySet()){
            if(currentPlayer.getDeposit())
        }

    }
}


