package it.polimi.ingsw.cards.leadercards;


import it.polimi.ingsw.cards.DevelopmentCardType;
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
    private Map<ResourceType, Integer> costDevelopment;
    private LeaderCardType leaderCardType;

    public ExtraDeposit(Map<ResourceType, Integer> costResource, Map<Integer,Map<DevelopmentCardType, Integer>> costDevelopment, int victoryPoints, ResourceType storageType, LeaderCardType leaderCardType) {
        super(costResource, costDevelopment, victoryPoints);
        this.storageType = storageType;
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
