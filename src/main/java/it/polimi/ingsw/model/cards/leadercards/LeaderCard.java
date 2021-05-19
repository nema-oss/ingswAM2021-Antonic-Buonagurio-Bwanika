package it.polimi.ingsw.model.cards.leadercards;

import it.polimi.ingsw.model.cards.DevelopmentCardType;
import it.polimi.ingsw.model.cards.Card;
import it.polimi.ingsw.model.gameboard.ResourceType;
import it.polimi.ingsw.model.player.Effects;

import java.io.Serializable;
import java.util.Map;

public abstract class LeaderCard implements Card, Serializable {

    public Map<ResourceType, Integer> costResource;
    public Map<Integer, Map<DevelopmentCardType, Integer>> costDevelopment;
    public int victoryPoints;


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


    public  Map<Integer, Map<DevelopmentCardType, Integer>> getCostDevelopment() {
        return costDevelopment;
    }

    public abstract LeaderCardType getLeaderType();

    public abstract void useEffect(Effects activeEffects);

}
