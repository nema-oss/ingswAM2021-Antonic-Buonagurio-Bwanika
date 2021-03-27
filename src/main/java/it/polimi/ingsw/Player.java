package it.polimi.ingsw;

/*
       * sketch of the Player class, most methods are unfinished
 */
import java.util.ArrayList;

public class Player {

    //Game game;

    private String nickname;
    private ArrayList<Card> hand;
    private ArrayList<Card> activeLeaderCards;
    private Cell position;
    private Board playerBoard;

    Player(String nickname){
        playerBoard = new Board();
        position = playerBoard.getPopeRoad().getCurrentPosition();
        this.nickname = nickname;
    }

    public void buyResource(){

    }

    public void moveOnPopeRoad(int steps){
        playerBoard.getPopeRoad().move(steps);
    }

    public void moveOnPopeRoad(){
        playerBoard.getPopeRoad().move();
    }

    public void discardLeader(Card leaderCard){

    }

    public Board getPlayerBoard() {
        return playerBoard;
    }
}
