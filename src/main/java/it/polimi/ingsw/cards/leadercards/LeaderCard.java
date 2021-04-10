package it.polimi.ingsw.cards.leadercards;

import it.polimi.ingsw.cards.DevelopmentCardType;
import it.polimi.ingsw.gameboard.Resource;
import it.polimi.ingsw.cards.Card;
import it.polimi.ingsw.gameboard.ResourceType;
import it.polimi.ingsw.player.Effects;

import java.util.Map;

public abstract class LeaderCard implements Card {

    private Map<ResourceType, Integer> costResource;
    private Map<Integer, Map<DevelopmentCardType, Integer>> costDevelopment;
    private int victoryPoints;

    public LeaderCard(Map<ResourceType, Integer> costResource, Map<Integer,Map<DevelopmentCardType, Integer>> costDevelopment, int victoryPoints) {
        this.costResource = costResource;
        this.costDevelopment = costDevelopment;
        this.victoryPoints = victoryPoints;
    }

    @Override
    public int getVictoryPoints() {
        return victoryPoints;
    }

    public Map<ResourceType, Integer> getCostResource() {
        return costResource;
    }

    public  Map<Map<ResourceType, Integer> , Integer> getCostDevelopment() {
        return costDevelopment;
    }

    public abstract LeaderCardType getLeaderType();

    public abstract void useEffect(Effects activeEffects);

}
