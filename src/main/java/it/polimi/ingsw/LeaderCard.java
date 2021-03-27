package it.polimi.ingsw;

import java.util.Map;

public class LeaderCard implements Card{
    private Map<Resource, Integer> costResource;
    private Map<Resource, Integer> costDevelopment;
    private int victoryPoints;
    private EffectStrategy effectStrategy;

    public LeaderCard(Map<Resource, Integer> costResource, Map<Resource, Integer> costDevelopment, int victoryPoints, EffectStrategy effectStrategy) {
        this.costResource = costResource;
        this.costDevelopment = costDevelopment;
        this.victoryPoints = victoryPoints;
        this.effectStrategy = effectStrategy;
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
}
