package it.polimi.ingsw.cards.leadercards;

import it.polimi.ingsw.exception.InsufficientPaymentException;
import it.polimi.ingsw.gameboard.Producible;
import it.polimi.ingsw.gameboard.Resource;
import it.polimi.ingsw.gameboard.ResourceType;

import java.util.ArrayList;
import java.util.Map;
import java.util.stream.Collectors;

public class ExtraProduction extends LeaderCard<ArrayList<Producible>> {

    private ArrayList<Producible> productionResult;
    private Map<ResourceType, Integer> costResource;
    private Map<ResourceType, Integer> productionRequirement;
    private int victoryPoints;
    private LeaderCardType leaderCardType;

    public ExtraProduction(Map<ResourceType, Integer> costResource, Map<Map<ResourceType, Integer>, Integer> costDevelopment, int victoryPoints, Boolean isActive, ArrayList<Producible> productionResult, Map<ResourceType, Integer> costResource1, Map<ResourceType, Integer> productionRequirement, int victoryPoints1, LeaderCardType leaderCardType) {
        super(costResource, costDevelopment, victoryPoints, isActive);
        this.productionResult = productionResult;
        this.costResource = costResource1;
        this.productionRequirement = productionRequirement;
        this.victoryPoints = victoryPoints1;
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
        return null;
    }
}
