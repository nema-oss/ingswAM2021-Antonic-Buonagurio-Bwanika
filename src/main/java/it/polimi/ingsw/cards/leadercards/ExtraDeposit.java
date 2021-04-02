package it.polimi.ingsw.cards.leadercards;


import it.polimi.ingsw.exception.LeaderCardException;
import it.polimi.ingsw.gameboard.ResourceType;

import java.util.Map;

/*
*ExtraDepositLeaderCard represents the LeaderCard with the effect of having two extra slots for a resource
*@author Nemanja
 */

public class ExtraDeposit implements EffectStrategy {

    private final ResourceType storageType;
    private int victoryPoints;
    private Map<ResourceType, Integer> costResource;

    public ExtraDeposit(ResourceType storageType) {

        this.storageType = storageType;
    }

    public AuxiliaryDeposit useEffect() throws LeaderCardException {

        return new AuxiliaryDeposit(storageType);

    }

}
