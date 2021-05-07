package it.polimi.ingsw.controller;


import it.polimi.ingsw.model.cards.leadercards.LeaderCard;
import it.polimi.ingsw.model.cards.leadercards.LeaderDeck;
import it.polimi.ingsw.model.gameboard.ResourceType;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public interface ControllerInterface {

    List<Error> onNewPlayer(String nickname);
    List<Error> onStartGame();
    void sendChooseLeaderCards();
    List<Error> onLeaderCardsChosen(String nickname, List<LeaderCard> leaderCardList);
    void sendChooseResources ();
    List<Error> onResourcesChosen (String nickname, Map<ResourceType, Integer> resourceTypeList);
    List<Error> onActivateProduction(String nickname, int cardIndex);
    List<Error> onBuyDevelopmentCards(String nickname, int row, int column);
    List<Error> onBuyResources(String nickname, int row, int column);
    List<Error> onActivateLeader(String nickname, int index);
    List<Error> onDiscardLeader(String nickname, int index);
    void sendPlayTurn();
    List<Error> onPlayerDisconnection(String nickname);
    List<Error> onEndTurn(String nickname);
    List<Error>

    //mancano: controllo di fine game (vaticano, check dei punti, etc)
    // controllo possibili azioni (se va qui)
    // cambio piani del deposito,
    //activate production leader card
}
