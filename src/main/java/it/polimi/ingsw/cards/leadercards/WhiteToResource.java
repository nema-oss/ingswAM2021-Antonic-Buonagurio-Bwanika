package it.polimi.ingsw.cards.leadercards;

import it.polimi.ingsw.gameboard.Resource;
import it.polimi.ingsw.gameboard.ResourceType;
import it.polimi.ingsw.player.Effects;

import java.util.Map;

public class WhiteToResource extends LeaderCard{

    private ResourceType result;
    private int victoryPoints;
    private LeaderCardType leaderCardType;

    public ResourceType getResult() {
        return result;
    }

    public WhiteToResource(Map<ResourceType, Integer> costResource, Map<Map<ResourceType, Integer>, Integer> costDevelopment, int victoryPoints, ResourceType result, LeaderCardType leaderCardType) {
        super(costResource, costDevelopment, victoryPoints);
        this.result = result;
        this.leaderCardType = leaderCardType;
    }

    @Override
    public void useEffect(Effects activeEffects) {

        activeEffects.activateWhiteToResource(result);
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
