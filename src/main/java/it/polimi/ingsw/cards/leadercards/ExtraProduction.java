package it.polimi.ingsw.cards.leadercards;

import it.polimi.ingsw.exception.InsufficientPaymentException;
import it.polimi.ingsw.gameboard.Producible;
import it.polimi.ingsw.gameboard.Resource;
import it.polimi.ingsw.gameboard.ResourceType;
import it.polimi.ingsw.player.Effects;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class ExtraProduction extends LeaderCard{

    private List<Producible> productionResult;
    private Map<ResourceType, Integer> productionRequirement;
    private LeaderCardType leaderCardType;

    public ExtraProduction(Map<ResourceType, Integer> costResource, Map<Map<ResourceType, Integer>, Integer> costDevelopment, int victoryPoints, ArrayList<Producible> productionResult, Map<ResourceType, Integer> productionRequirement, LeaderCardType leaderCardType) {
        super(costResource, costDevelopment, victoryPoints);
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
        return LeaderCardType.EXTRA_PRODUCTION;
    }

    public Map<ResourceType, Integer> getProductionRequirement() {
        return productionRequirement;
    }

}
