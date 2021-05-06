package it.polimi.ingsw.controller;

import it.polimi.ingsw.model.cards.DevelopmentCard;
import it.polimi.ingsw.model.cards.leadercards.LeaderCard;
import it.polimi.ingsw.model.gameboard.ResourceType;

import java.util.List;

public class MatchController implements ControllerInterface{
    @Override
    public List<Error> onChooseLeaderCard(LeaderCard leaderCard) {
        return null;
    }

    @Override
    public List<Error> onChooseResourceType(ResourceType resourceType) {
        return null;
    }

    @Override
    public List<Error> onMoveDeposit(int a, int b) {
        return null;
    }

    @Override
    public List<Error> onBuyDevelopmentCards(int x, int y) {
        return null;
    }

    @Override
    public List<Error> onBuyResources(int x, int y) {
        return null;
    }

    @Override
    public List<Error> onActivateProductionDevelopmentCard(DevelopmentCard card) {
        return null;
    }

    @Override
    public List<Error> onActivateProductionBoard(ResourceType resourceType) {
        return null;
    }

    @Override
    public List<Error> onActivateProductionLeaderCard(LeaderCard card) {
        return null;
    }

    @Override
    public List<Error> onActivateLeaderCard(LeaderCard card) {
        return null;
    }

    @Override
    public List<Error> onDiscardLeaderCard(LeaderCard card) {
        return null;
    }
}
