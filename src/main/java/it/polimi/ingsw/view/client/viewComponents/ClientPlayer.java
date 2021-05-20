package it.polimi.ingsw.view.client.viewComponents;

import it.polimi.ingsw.model.Game;
import it.polimi.ingsw.model.cards.DevelopmentCard;
import it.polimi.ingsw.model.cards.leadercards.LeaderCard;
import it.polimi.ingsw.model.cards.leadercards.LeaderCardType;
import it.polimi.ingsw.model.gameboard.GameBoard;
import it.polimi.ingsw.model.player.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class ClientPlayer {

    private final Board playerBoard;
    private boolean standardActionPlayed;
    private boolean actionLeaderPlayed;
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
        this.standardActionPlayed = false;
        this.actionLeaderPlayed = false;

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
        return playerBoard.getPopeRoad().getCurrentPositionIndex();
    }

    public List<DevelopmentCard> getDevelopmentCards() {
        List<DevelopmentCard> developmentCards = new ArrayList<>();
        for(Stack<DevelopmentCard> s : playerBoard.getDevelopmentCards())
            developmentCards.addAll(s);
        return developmentCards;
    }

    public List<LeaderCard> getProductionLeaderCards() {

        Stream<LeaderCard> stream =  hand.stream().filter(leaderCard -> leaderCard.getLeaderType().equals(LeaderCardType.EXTRA_PRODUCTION));
        return  stream.collect(Collectors.toList());

    }

    public boolean allPossibleActionDone() {
        return standardActionPlayed && actionLeaderPlayed;
    }

    public void standardActionDone() {
        standardActionPlayed = true;
    }

    public void leaderActionDone(){
        actionLeaderPlayed = true;
    }

    public void resetTurnActionCounter() {
        standardActionPlayed = false;
        actionLeaderPlayed = false;
    }
}

