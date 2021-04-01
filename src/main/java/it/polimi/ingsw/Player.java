package it.polimi.ingsw;

/*
       * sketch of the Player class, most methods are unfinished
 */

import java.util.ArrayList;
import java.util.Optional;

public class Player{

    private GameBoard gameBoard;
    private Game currentGame;


    private ArrayList<LeaderCard> hand;
    private ArrayList<LeaderCard> activeLeaderCards;
    private int victoryPoints;
    //private Optional<AuxiliaryDeposit> auxiliaryDeposit;

    public int getPositionIndex() {
        return playerBoard.getPopeRoad().getCurrentPositionIndex();
    }

    private int positionIndex;
    private Cell position;


    private Board playerBoard;

    Player(String nickname, GameBoard gameBoard, Game currentGame){
        playerBoard = new Board();
        position = playerBoard.getPopeRoad().getCurrentPosition();
        this.nickname = nickname;
        this.gameBoard = gameBoard;
        this.currentGame = currentGame;
        victoryPoints = 0;
    }

    private String nickname;

    public ArrayList<LeaderCard> getHand() {
        return hand;
    }

    public Board getPlayerBoard() {
        return playerBoard;
    }

    public ArrayList<LeaderCard> getActiveLeaderCards() {
        return activeLeaderCards;
    }

    public void setHand(ArrayList<LeaderCard> hand) {
        this.hand = hand;
    }


    public Cell getPosition() {
        return position;
    }

    public Deposit getDeposit(){
        return playerBoard.getDeposit();
    }

    public Strongbox getStrongbox(){
        return playerBoard.getStrongbox();
    }

    public PopeRoad getPopeRoad(){
        return playerBoard.getPopeRoad();
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public void buyDevelopmentCard(int x, int y){

        currentGame.buyCard(x,y);

    }

    public void buyResources(int x, int y) throws Exception {

        getDeposit().addResources(currentGame.buyResource(x,y));

    }


    public void activateProduction(int positionIndex){

    }

    public void moveOnPopeRoad(int steps){

        getPopeRoad().move();
        if(getPosition().popeSpace) currentGame.checkPlayersPosition(getPositionIndex());
        ;
    }

    public void moveOnPopeRoad(){
        getPopeRoad().move();
        Cell position = getPosition();
        addVictoryPoints(position.getPoints());
        if(position.isPopeSpace()) currentGame.checkPlayersPosition(getPositionIndex());

    }

    /*
        * this method discard a Leader card from hand and move the player on poperoad
        * @param the card to discard (type: LeaderCard)
     */
    public void discardLeader(LeaderCard leaderCard) throws Exception {

        if(!hand.contains(leaderCard)) throw new Exception();

        hand.remove(leaderCard);
        moveOnPopeRoad();

    }

    /*
        * this method activate the effect of a Leader Card
        * @param the leader Card selected
     */

    public void activateLeaderCard(LeaderCard leaderCard) throws Exception{

        if(!hand.contains(leaderCard)) throw new Exception();

        for(int i = 0; i < hand.size(); i++){
            if(hand.get(i).equals(leaderCard)){
                LeaderCard toPlay = hand.remove(i);
                toPlay.useLeaderAction();
                activeLeaderCards.add(toPlay);
            }
        }

    }


    public void addVictoryPoints(int victoryPoints) {

        this.victoryPoints += victoryPoints;
    }

    public int getVictoryPoints() {
        return victoryPoints;
    }
}
