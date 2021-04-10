package it.polimi.ingsw.cards.leadercards;

import it.polimi.ingsw.cards.DevelopmentCardType;
import it.polimi.ingsw.gameboard.Resource;
import it.polimi.ingsw.gameboard.ResourceType;

import java.util.Map;

public class WhiteToResource extends LeaderCard<Resource>{

    private ResourceType result;
    private LeaderCardType leaderCardType;

    public ResourceType getResult() {
        return result;
    }

    public WhiteToResource(Map<ResourceType, Integer> costResource, Map<Integer,Map<DevelopmentCardType, Integer>> costDevelopment, int victoryPoints, ResourceType result, LeaderCardType leaderCardType) {
        super(costResource, costDevelopment, victoryPoints);
        this.result = result;
        this.leaderCardType = leaderCardType;
    }

    @Override
    public Resource useEffect() {
        return new Resource(result);
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
