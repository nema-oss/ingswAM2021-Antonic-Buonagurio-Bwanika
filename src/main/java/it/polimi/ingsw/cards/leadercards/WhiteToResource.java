package it.polimi.ingsw.cards.leadercards;

import it.polimi.ingsw.gameboard.Resource;
import it.polimi.ingsw.gameboard.ResourceType;

import java.util.Map;

public class WhiteToResource extends LeaderCard<Resource>{

    private ResourceType result;
    private Map<ResourceType, Integer> costResource;
    private int victoryPoints;
    private LeaderCardType leaderCardType;

    public ResourceType getResult() {
        return result;
    }

    public WhiteToResource(Map<ResourceType, Integer> costResource, Map<Map<ResourceType, Integer>, Integer> costDevelopment, int victoryPoints, Boolean isActive, ResourceType result, Map<ResourceType, Integer> costResource1, int victoryPoints1, LeaderCardType leaderCardType) {
        super(costResource, costDevelopment, victoryPoints, isActive);
        this.result = result;
        this.costResource = costResource1;
        this.victoryPoints = victoryPoints1;
        this.leaderCardType = leaderCardType;
    }

    @Override
    public Resource useEffect() {
        return new Resource(result);
    }

    @Override
    public Map<ResourceType, Integer> getCostResource() {
        return costResource;
    }

    @Override
    public int getVictoryPoints() {
        return victoryPoints;
    }

    @Override
    public LeaderCardType getLeaderType(){
        return leaderCardType;
    }
}
