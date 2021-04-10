package it.polimi.ingsw.cards.leadercards;

import it.polimi.ingsw.cards.DevelopmentCardType;
import it.polimi.ingsw.exception.InsufficientPaymentException;
import it.polimi.ingsw.gameboard.Producible;
import it.polimi.ingsw.gameboard.Resource;
import it.polimi.ingsw.gameboard.ResourceType;

import java.util.ArrayList;
import java.util.Map;
import java.util.stream.Collectors;

public class ExtraProduction extends LeaderCard<ArrayList<Producible>> {

    private ArrayList<Producible> productionResult;
    private Map<ResourceType, Integer> productionRequirement;
    private LeaderCardType leaderCardType;

    public ExtraProduction(Map<ResourceType, Integer> costResource, Map<Integer,Map<DevelopmentCardType, Integer>> costDevelopment, int victoryPoints, ArrayList<Producible> productionResult, Map<ResourceType, Integer> productionRequirement, LeaderCardType leaderCardType) {
        super(costResource, costDevelopment, victoryPoints);
        this.productionResult = productionResult;
        this.productionRequirement = productionRequirement;
        this.leaderCardType = leaderCardType;
    }

    @Override
    public ArrayList<Producible> useEffect() { //note: Player has to check for the available resources

        return productionResult;
    }


    public ArrayList<Producible> getProductionResult() {
        return productionResult;
    }

    public Map<ResourceType, Integer> getProductionRequirement() {
        return productionRequirement;
    }

    @Override
    public LeaderCardType getLeaderType() {
        return leaderCardType.EXTRA_PRODUCTION;
    }
}
