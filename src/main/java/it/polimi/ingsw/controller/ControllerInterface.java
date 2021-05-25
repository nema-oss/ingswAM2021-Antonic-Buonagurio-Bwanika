package it.polimi.ingsw.controller;


import it.polimi.ingsw.model.cards.Card;
import it.polimi.ingsw.model.cards.DevelopmentCard;
import it.polimi.ingsw.model.cards.leadercards.LeaderCard;
import it.polimi.ingsw.model.cards.leadercards.LeaderDeck;
import it.polimi.ingsw.model.gameboard.Resource;
import it.polimi.ingsw.model.gameboard.ResourceType;
import it.polimi.ingsw.model.player.Board;
import it.polimi.ingsw.view.server.VirtualViewInterface;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public interface ControllerInterface {

    List<Error> onNewPlayer(String nickname);
    List<Error> onStartGame();
    List<Error> onLeaderCardsChosen(String nickname, List<LeaderCard> leaderCardList);
    List<Error> onResourcesChosen (String nickname, Map<ResourceType, Integer> resourceTypeList);
    List<Error> onActivateProduction(String nickname);
    List<Error> onActivateDevelopmentProduction(String nickname, List<DevelopmentCard> developmentCardCards);
    List<Error> onActivateLeaderProduction(String nickname, List<LeaderCard> leaderCards);
    List<Error> onActivateBoardProduction(String nickname, Map<ResourceType, List<ResourceType>> userChoice);
    List<Error> onEndProduction(String nickname);
    List<Error> onBuyDevelopmentCards(String nickname, int row, int column);
    List<Error> onBuyResources(String nickname, int row, int column);
    List<Error> onPlaceResources(String nickname, Map<Resource, Integer> resources);
    List<Error> onActivateLeader(String nickname, LeaderCard leaderCard);
    List<Error> onDiscardLeader(String nickname, LeaderCard leaderCard);
    List<Error> onPlayerDisconnection(String nickname);
    List<Error> onEndTurn(String nickname);
    List<Error> onMoveDeposit (String nickname, int x, int y);
    List<Error> onDiscardResource (String nickname, ResourceType res);
    void sendPlayTurn();
    void sendEndTurn();
    void sendChooseResources ();
    void sendChooseLeaderCards();
    void setVirtualView(VirtualViewInterface virtualView);
    Board sendBoardUpdate(String user);

    void onPlayerReconnection(String disconnectedPlayer);
}
