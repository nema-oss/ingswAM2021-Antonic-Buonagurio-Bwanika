package it.polimi.ingsw.view.client.viewComponents;

import it.polimi.ingsw.model.Game;
import it.polimi.ingsw.model.cards.DevelopmentCard;
import it.polimi.ingsw.model.cards.leadercards.LeaderCard;
import it.polimi.ingsw.model.gameboard.GameBoard;
import it.polimi.ingsw.model.player.*;

import java.util.ArrayList;
import java.util.List;

public class ClientPlayer {

    private final Board playerBoard;
    private String nickname;
    private final int victoryPoints;
    private final Effects activeEffects;
    private final List<LeaderCard> activeLeaderCards;
    private List<LeaderCard> hand;

    public ClientPlayer(String nickname, ClientGameBoard gameBoard){

        playerBoard = new Board();
        this.nickname = nickname;
        victoryPoints = 0;
        activeEffects = new Effects();
        activeLeaderCards = new ArrayList<>();

        boolean standardActionPlayed = false;
        boolean[] leaderActionPlayed = new boolean[2];

    }


    public List<LeaderCard> getHand() {
        return hand;
    }

    public Board getPlayerBoard() {
        return playerBoard;
    }

    public List<LeaderCard> getActiveLeaderCards() {
        return activeLeaderCards;
    }

    public void setHand(List<LeaderCard> hand) {
        this.hand = hand;
    }

    public Cell getPosition() {
        return getPopeRoad().getCurrentPosition();
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

    public Effects getActiveEffects(){
        return activeEffects;
    }

    public int getVictoryPoints() {
        return victoryPoints;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }


    public int getPositionIndex() {
        return 0;
    }

    public List<DevelopmentCard> getDevelopmentCards() {
        return null;
    }

    public List<LeaderCard> getProductionLeaderCards() {
        return null;
    }
}

