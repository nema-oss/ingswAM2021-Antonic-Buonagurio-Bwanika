package it.polimi.ingsw.cards.leadercards;


import it.polimi.ingsw.exception.LeaderCardException;
import it.polimi.ingsw.gameboard.Resource;
import it.polimi.ingsw.gameboard.ResourceType;

import java.util.Map;

/*
*ExtraDepositLeaderCard represents the LeaderCard with the effect of having two extra slots for a resource
*@author Nemanja
 */

public class ExtraDeposit extends LeaderCard<AuxiliaryDeposit> {

    private final ResourceType storageType;
    private Map<ResourceType, Integer> costResource;
    private Map<ResourceType, Integer> costDevelopment;
    private int victoryPoints;
    private LeaderCardType leaderCardType;

    public ExtraDeposit(Map<ResourceType, Integer> costResource, Map<Map<ResourceType, Integer>, Integer> costDevelopment, int victoryPoints, Boolean isActive, ResourceType storageType, Map<ResourceType, Integer> costResource1, Map<ResourceType, Integer> costDevelopment1, int victoryPoints1, LeaderCardType leaderCardType) {
        super(costResource, costDevelopment, victoryPoints, isActive);
        this.storageType = storageType;
        this.costResource = costResource1;
        this.costDevelopment = costDevelopment1;
        this.victoryPoints = victoryPoints1;
        this.leaderCardType = leaderCardType;
    }

    @Override
    public AuxiliaryDeposit useEffect(){

        return new AuxiliaryDeposit(storageType);

    }

    public ResourceType getStorageType() {
        return storageType;
    }

    @Override
    public LeaderCardType getLeaderType() {
        return null;
    }
}
