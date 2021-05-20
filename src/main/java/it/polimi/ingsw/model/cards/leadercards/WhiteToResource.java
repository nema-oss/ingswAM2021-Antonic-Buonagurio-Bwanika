package it.polimi.ingsw.model.cards.leadercards;

import it.polimi.ingsw.model.cards.DevelopmentCardType;
import it.polimi.ingsw.model.gameboard.ResourceType;
import it.polimi.ingsw.model.player.Effects;

import java.util.Map;

public class WhiteToResource extends LeaderCard{

    private final ResourceType result;
    private final LeaderCardType leaderCardType;

    public ResourceType getResult() {
        return result;
    }

    public WhiteToResource(String id, Map<ResourceType, Integer> costResource, Map<Integer,Map<DevelopmentCardType, Integer>> costDevelopment, int victoryPoints, ResourceType result, LeaderCardType leaderCardType) {
        super(id, costResource, costDevelopment, victoryPoints);
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

    public String getId() {
        return id;
    }


}
