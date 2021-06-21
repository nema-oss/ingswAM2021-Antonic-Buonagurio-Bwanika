package it.polimi.ingsw.model.cards.leadercards;

import it.polimi.ingsw.model.cards.DevelopmentCardType;
import it.polimi.ingsw.model.cards.Card;
import it.polimi.ingsw.model.gameboard.ResourceType;
import it.polimi.ingsw.model.player.Effects;

import java.io.Serializable;
import java.util.Map;

/**
 * this class represents a leader card
 */
public abstract class LeaderCard implements Card, Serializable {

    public String id;
    public Map<ResourceType, Integer> costResource;
    public Map<Integer, Map<DevelopmentCardType, Integer>> costDevelopment;
    public int victoryPoints;


    public LeaderCard(String id, Map<ResourceType, Integer> costResource, Map<Integer,Map<DevelopmentCardType, Integer>> costDevelopment, int victoryPoints) {
        this.id = id;
        this.costResource = costResource;
        this.costDevelopment = costDevelopment;
        this.victoryPoints = victoryPoints;
    }

    @Override
    public int getVictoryPoints() {
        return victoryPoints;
    }

    /**
     * @return the card's cost in terms of resources
     */
    public Map<ResourceType, Integer> getCostResource() {
        return costResource;
    }

    /**
     * @return the card's cost in terms of development cards
     */
    public  Map<Integer, Map<DevelopmentCardType, Integer>> getCostDevelopment() {
        return costDevelopment;
    }

    /**
     * @return the leader card type (its effect)
     */
    public abstract LeaderCardType getLeaderType();

    /**
     * this method activates the effect on a leader card
     * @param activeEffects the active effects on the card
     */
    public abstract void useEffect(Effects activeEffects);

    public String getId() {
        return id;
    }

}
