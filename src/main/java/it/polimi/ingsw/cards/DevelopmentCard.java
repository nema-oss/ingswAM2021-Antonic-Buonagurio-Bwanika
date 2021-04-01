package it.polimi.ingsw.cards;

import it.polimi.ingsw.gameboard.Resource;
import it.polimi.ingsw.gameboard.ResourceType;

import java.util.ArrayList;
import java.util.Map;
import java.util.stream.Collectors;

/*
    * DevelopmentCard represents a development card
    * @author Nemanja
 */
public class DevelopmentCard implements Card {

    private int level;
    private DevelopmentCardType type;
    private Map<ResourceType, Integer> cost;
    private Map<ResourceType, Integer> productionRequirements;
    private ArrayList<Resource> productionResults;
    private int victoryPoints;

    public DevelopmentCard(int level, DevelopmentCardType type, Map<ResourceType, Integer> cost, Map<ResourceType, Integer> productionRequirements, ArrayList<Resource> productionResults, int victoryPoints) {
        this.level = level;
        this.type = type;
        this.cost = cost;
        this.productionRequirements = productionRequirements;
        this.productionResults = productionResults;
        this.victoryPoints = victoryPoints;
    }

    /*
     * this method returns the level of the developmentCard
     * @return level(type:int) of DevelopmentCard
     */
    public int getLevel() {
        return level;
    }
    /*
     * this method returns the type of the developmentCard
     * @return type(type:DevelopmentCardType) of DevelopmentCard
     */
    public DevelopmentCardType getType() {
        return type;
    }
    /*
     * this method returns the cost in terms of resources of the developmentCard
     * @return cost(type: Map<Resource, Integer>) of DevelopmentCard
     */
    public Map<ResourceType, Integer> getCost() {
        return cost;
    }
    /*
     * this method returns the production requirements in terms of resources of the developmentCard
     * @return productionRequirements(type: Map<Resource, Integer>) of DevelopmentCard
     */
    public Map<ResourceType, Integer> getProductionRequirements() {
        return productionRequirements;
    }
    /*
     * this method returns the resulting resources of the production of the developmentCard
     * @return productionResults(type: Map<Resource, Integer>) of DevelopmentCard
     */
    public ArrayList<Resource> getProductionResults() {
        return productionResults;
    }
    /*
     * this method returns the victory points of the developmentCard
     * @return victoryPoints(type:int) of DevelopmentCard
     */
    @Override
    public int getVictoryPoints() {
        return victoryPoints;
    }

    public ArrayList<Resource> activateProduction(ArrayList<Resource> resources) throws Exception {

        Map<ResourceType, Long> frequencyMap = resources.stream().collect(Collectors.groupingBy(Resource::getType, Collectors.counting()));

        for (ResourceType type : frequencyMap.keySet()) {
            if (frequencyMap.get(type).intValue() != (productionRequirements.get(type))) throw new Exception();
        }

        return productionResults;

    }
}
