package it.polimi.ingsw.controller;


import it.polimi.ingsw.model.cards.leadercards.LeaderCard;
import it.polimi.ingsw.model.cards.leadercards.LeaderDeck;
import it.polimi.ingsw.model.gameboard.ResourceType;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public interface ControllerInterface {

    void onNewPlayer(String nickname);
    void onSTartGame();
    void onChooseLeaderCards(String nickname);
    void onLeaderCardsChosen(String nickname, List<LeaderCard> leaderCardList);
    void onChooseResources (String nickname, ArrayList<ResourceType> resourceTypes);
    void onResourcesChosen (String nickname, Map<ResourceType, Integer> resourceTypeList);
    void onActivateProduction(String nickname);
    void onBuyDevelopmentCards(String nickname, int row, int column);
    void onBuyResources(String nickname, int row, int column);
    void onActivateLeader(String nickname, LeaderCard leaderCard);
    void onDiscardLeader(String nickname, LeaderCard leaderCard);
    void onEndTurn(String nickname);
    void sendChooseLeaderCards();
    void sendChooseResources();
    void sendPlayTurn();
}
