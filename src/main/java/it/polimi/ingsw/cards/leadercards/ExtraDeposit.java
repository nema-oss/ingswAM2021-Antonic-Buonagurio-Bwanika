package it.polimi.ingsw.cards.leadercards;


import it.polimi.ingsw.exception.LeaderCardException;
import it.polimi.ingsw.gameboard.Resource;
import it.polimi.ingsw.gameboard.ResourceType;

import java.util.Map;

/*
*ExtraDepositLeaderCard represents the LeaderCard with the effect of having two extra slots for a resource
*@author Nemanja
 */

public class ExtraDeposit extends LeaderCard {

    private final ResourceType storageType;
    private Map<Resource, Integer> costResource;
    private Map<Resource, Integer> costDevelopment;
    private int victoryPoints;

    public ExtraDeposit(Map<Resource, Integer> costResource, Map<Resource, Integer> costDevelopment, int victoryPoints, ResourceType storageType, Map<Resource, Integer> costResource1, Map<Resource, Integer> costDevelopment1, int victoryPoints1) {
        super(costResource, costDevelopment, victoryPoints);
        this.storageType = storageType;
        this.costResource = costResource1;
        this.costDevelopment = costDevelopment1;
        this.victoryPoints = victoryPoints1;
    }

    public AuxiliaryDeposit useEffect(){

        return new AuxiliaryDeposit(storageType);

    }

    public ResourceType getStorageType() {
        return storageType;
    }

    @Override
    public Map<Resource, Integer> getCostResource() {
        return costResource;
    }

    @Override
    public Map<Resource, Integer> getCostDevelopment() {
        return costDevelopment;
    }

    @Override
    public int getVictoryPoints() {
        return victoryPoints;
    }
}
