package it.polimi.ingsw.cards.leadercards;

import it.polimi.ingsw.gameboard.Resource;
import it.polimi.ingsw.gameboard.ResourceType;

import java.util.Map;

public class WhiteToResource extends LeaderCard{

    private ResourceType result;
    private Map<Resource, Integer> costResource;
    private int victoryPoints;

    public ResourceType getResult() {
        return result;
    }

    public WhiteToResource(Map<Resource, Integer> costResource, Map<Resource, Integer> costDevelopment, int victoryPoints, ResourceType result, Map<Resource, Integer> costResource1, int victoryPoints1) {
        super(costResource, costDevelopment, victoryPoints);
        this.result = result;
        this.costResource = costResource1;
        this.victoryPoints = victoryPoints1;
    }

    public Resource useEffect() {
        return new Resource(result);
    }

    @Override
    public Map<Resource, Integer> getCostResource() {
        return costResource;
    }

    @Override
    public int getVictoryPoints() {
        return victoryPoints;
    }
}
