package it.polimi.ingsw.controller;


import it.polimi.ingsw.model.cards.DevelopmentCard;
import it.polimi.ingsw.model.cards.leadercards.LeaderCard;
import it.polimi.ingsw.model.gameboard.ResourceType;

import java.util.List;

public interface ControllerInterface {

    //TODO this are the method that the virtual view can call

    List<Error> onChooseLeaderCard(LeaderCard leaderCard);

    List<Error> onChooseResourceType(ResourceType resourceType);

    List<Error> onMoveDeposit(int a, int b);

    List<Error> onBuyDevelopmentCards(int x, int y);

    List<Error> onBuyResources(int x, int y);

    List<Error> onActivateProductionDevelopmentCard(DevelopmentCard card);

    List<Error> onActivateProductionBoard(ResourceType resourceType);

    List<Error> onActivateProductionLeaderCard(LeaderCard card);

    List<Error> onActivateLeaderCard(LeaderCard card);

    List<Error> onDiscardLeaderCard(LeaderCard card);
}
