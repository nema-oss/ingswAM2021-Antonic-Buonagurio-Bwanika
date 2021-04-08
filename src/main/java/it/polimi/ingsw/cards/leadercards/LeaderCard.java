package it.polimi.ingsw.cards.leadercards;

import it.polimi.ingsw.gameboard.Resource;
import it.polimi.ingsw.cards.Card;

import java.util.Map;

public abstract class LeaderCard implements Card {

    private Map<Resource, Integer> costResource;
    private Map<Resource, Integer> costDevelopment;
    private int victoryPoints;

    public LeaderCard(Map<Resource, Integer> costResource, Map<Resource, Integer> costDevelopment, int victoryPoints) {
        this.costResource = costResource;
        this.costDevelopment = costDevelopment;
        this.victoryPoints = victoryPoints;
    }

    public Map<Resource, Integer> getCostResource() {
        return costResource;
    }

    public Map<Resource, Integer> getCostDevelopment() {
        return costDevelopment;
    }

    @Override
    public int getVictoryPoints() {
        return victoryPoints;
    }

    public void useLeaderAction(){

    }

    public abstract LeaderCardType getLeaderType();
}
