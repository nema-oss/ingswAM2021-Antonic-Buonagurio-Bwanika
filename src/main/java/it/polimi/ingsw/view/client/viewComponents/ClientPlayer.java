package it.polimi.ingsw.view.client.viewComponents;

import it.polimi.ingsw.model.Game;
import it.polimi.ingsw.model.cards.DevelopmentCard;
import it.polimi.ingsw.model.cards.leadercards.LeaderCard;
import it.polimi.ingsw.model.cards.leadercards.LeaderCardType;
import it.polimi.ingsw.model.gameboard.GameBoard;
import it.polimi.ingsw.model.gameboard.Resource;
import it.polimi.ingsw.model.gameboard.ResourceType;
import it.polimi.ingsw.model.player.*;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Stack;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class ClientPlayer {

    private ClientPlayerBoard playerBoard;
    private boolean standardActionPlayed;
    private boolean actionLeaderPlayed;
    private String nickname;
    private final int victoryPoints;
    private final ClientActiveEffects activeEffects;
    private final List<LeaderCard> activeLeaderCards;
    private List<LeaderCard> hand;
    private ClientGameBoard gameBoard;
    private List<Resource> boughtResources;

    public ClientPlayer(String nickname, ClientGameBoard gameBoard) {

        playerBoard = new ClientPlayerBoard();
        this.nickname = nickname;
        victoryPoints = 0;
        activeEffects = new ClientActiveEffects();
        activeLeaderCards = new ArrayList<>();
        this.standardActionPlayed = false;
        this.actionLeaderPlayed = false;
        this.gameBoard = gameBoard;

    }


    public List<LeaderCard> getHand() {
        return hand;
    }

    public ClientPlayerBoard getPlayerBoard() {
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

    public ClientDeposit getDeposit() {
        return playerBoard.getDeposit();
    }

    public ClientStrongbox getStrongbox() {
        return playerBoard.getStrongbox();
    }

    public ClientPopeRoad getPopeRoad() {
        return playerBoard.getPopeRoad();
    }

    public String getNickname() {
        return nickname;
    }

    public ClientActiveEffects getActiveEffects() {
        return activeEffects;
    }

    public int getVictoryPoints() {
        return victoryPoints;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public boolean isStandardActionPlayed() {
        return standardActionPlayed;
    }

    public int getPositionIndex() {
        return playerBoard.getPopeRoad().getCurrentPositionIndex();
    }

    public List<DevelopmentCard> getDevelopmentCards() {

        List<DevelopmentCard> developmentCards = new ArrayList<>();
        for (Stack<DevelopmentCard> s : playerBoard.getDevelopmentCards())
            developmentCards.addAll(s);
        return developmentCards;
    }

    public List<LeaderCard> getProductionLeaderCards() {

        Stream<LeaderCard> stream = hand.stream().filter(leaderCard -> leaderCard.getLeaderType().equals(LeaderCardType.EXTRA_PRODUCTION));
        return stream.collect(Collectors.toList());

    }

    public boolean allPossibleActionDone() {
        return standardActionPlayed && actionLeaderPlayed;
    }

    public void setStandardActionDone() {
        standardActionPlayed = true;
    }

    public boolean isActionLeaderPlayed() {
        return actionLeaderPlayed;
    }

    public void setLeaderActionDone() {
        actionLeaderPlayed = true;
    }

    public void resetTurnActionCounter() {
        standardActionPlayed = false;
        actionLeaderPlayed = false;
    }

    public DevelopmentCard buyDevelopmentCard(int x, int y) {
        DevelopmentCard developmentCard = gameBoard.getCardMarket().getCard(x, y);
        playerBoard.addDevelopmentCard(developmentCard);
        return developmentCard;
    }


    public void setBoughtResources(List<Resource> resources) {
        this.boughtResources = resources;
    }

    public List<Resource> getBoughtResources() {
        return boughtResources;
    }

    public void setPlayerBoard(ClientPlayerBoard board) {
        playerBoard = board;
    }

    public void addResource(Map<Resource, Integer> userChoice) {

        ClientDeposit clientDeposit = playerBoard.getDeposit();

        for(Resource resource: userChoice.keySet()){
            clientDeposit.addResource(userChoice.get(resource), resource);
        }
    }

    public void updateCurrentPosition(int position) {

        ClientPopeRoad popeRoad = playerBoard.getPopeRoad();
        popeRoad.setCurrentPositionIndex(position);
        popeRoad.setCurrentPosition(position);
    }

    public void useLeaderCard(LeaderCard card, boolean activate){

        if(activate) {
            activeLeaderCards.add(card);
            playerBoard.addActiveLeaderCard(card);
        }
        else
            hand.remove(card);
    }

    public void updateDeposit(Map<ResourceType, List<Resource>> updatedStrongbox, List<List<Resource>> updatedWarehouse) {

        ClientStrongbox strongbox = playerBoard.getStrongbox();
        ClientDeposit deposit = playerBoard.getDeposit();

        strongbox.update(updatedStrongbox);
        deposit.update(updatedWarehouse);
    }
}

