package it.polimi.ingsw.model.cards.leadercards;

import it.polimi.ingsw.model.cards.DevelopmentCardType;
import it.polimi.ingsw.model.gameboard.Producible;
import it.polimi.ingsw.model.gameboard.ResourceType;
import it.polimi.ingsw.model.player.Effects;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * this class represents the extra production leader cards
 */
public class ExtraProduction extends LeaderCard{

    private List<Producible> productionResult;
    private Map<ResourceType, Integer> productionRequirement;
    private LeaderCardType leaderCardType;

    public ExtraProduction(String id, Map<ResourceType, Integer> costResource, Map<Integer,Map<DevelopmentCardType, Integer>> costDevelopment, int victoryPoints, ArrayList<Producible> productionResult, Map<ResourceType, Integer> productionRequirement, LeaderCardType leaderCardType) {
        super(id, costResource, costDevelopment, victoryPoints);
        this.productionResult = productionResult;
        this.productionRequirement = productionRequirement;
        this.leaderCardType = leaderCardType;
    }

    @Override
    public void useEffect(Effects activeEffects) {
        activeEffects.activateExtraProduction(productionRequirement, productionResult);
    }

    @Override
    public LeaderCardType getLeaderType() {
        return leaderCardType;
    }

    /**
     * @return the requirements to start the card's production
     */
    public Map<ResourceType, Integer> getProductionRequirement() {
        return productionRequirement;
    }

    /**
     * @return the results of the card's production
     */
    public List<Producible> getProductionResult(){
        return productionResult;
    }

    public String getId() {
        return id;
    }

}
