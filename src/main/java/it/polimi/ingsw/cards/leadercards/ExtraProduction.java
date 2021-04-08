package it.polimi.ingsw.cards.leadercards;

import it.polimi.ingsw.exception.InsufficientPaymentException;
import it.polimi.ingsw.gameboard.Producible;
import it.polimi.ingsw.gameboard.Resource;
import it.polimi.ingsw.gameboard.ResourceType;

import java.util.ArrayList;
import java.util.Map;
import java.util.stream.Collectors;

public class ExtraProduction extends LeaderCard {

    private ArrayList<Producible> productionResult;
    private Map<Resource, Integer> costResource;
    private Map<Resource, Integer> productionRequirement;
    private int victoryPoints;
    private LeaderCardType leaderCardType;

    public ExtraProduction(Map<Resource, Integer> costResource, Map<Resource, Integer> costDevelopment, int victoryPoints, ArrayList<Producible> productionResult, Map<Resource, Integer> costResource1, Map<Resource, Integer> productionRequirement, int victoryPoints1) {
        super(costResource, costDevelopment, victoryPoints);
        this.productionResult = productionResult;
        this.costResource = costResource1;
        this.productionRequirement = productionRequirement;
        this.victoryPoints = victoryPoints1;
    }

    public ArrayList<Producible> useEffect(ArrayList<Resource> resources) throws InsufficientPaymentException {

        Map<ResourceType, Long> frequencyMap = resources.stream().collect(Collectors.groupingBy(Resource::getType, Collectors.counting()));

        for (ResourceType type : frequencyMap.keySet()) {
            if (frequencyMap.get(type).intValue() != (productionRequirement.get(type))) throw new InsufficientPaymentException();
        }

        return productionResult;
    }

    public ArrayList<Producible> getProductionResult() {
        return productionResult;
    }

    @Override
    public Map<Resource, Integer> getCostResource() {
        return costResource;
    }

    public Map<Resource, Integer> getProductionRequirement() {
        return productionRequirement;
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
