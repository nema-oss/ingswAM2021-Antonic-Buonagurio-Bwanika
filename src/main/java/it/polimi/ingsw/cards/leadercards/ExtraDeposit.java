package it.polimi.ingsw.cards.leadercards;


import it.polimi.ingsw.exception.LeaderCardException;
import it.polimi.ingsw.gameboard.Resource;
import it.polimi.ingsw.gameboard.ResourceType;
import it.polimi.ingsw.player.Effects;

import java.util.Map;

/*
*ExtraDepositLeaderCard represents the LeaderCard with the effect of having two extra slots for a resource
*@author Nemanja
 */

public class ExtraDeposit extends LeaderCard{

    private final ResourceType storageType;
    private Map<ResourceType, Integer> costDevelopment;
    private LeaderCardType leaderCardType;

    public ExtraDeposit(Map<ResourceType, Integer> costResource, Map<Map<ResourceType, Integer>, Integer> costDevelopment, int victoryPoints, ResourceType storageType, Map<ResourceType, Integer> costDevelopment1, LeaderCardType leaderCardType) {
        super(costResource, costDevelopment, victoryPoints);
        this.storageType = storageType;
        this.costDevelopment = costDevelopment1;
        this.leaderCardType = leaderCardType;
    }

    @Override
    public void useEffect(Effects activeEffects){
        activeEffects.activateExtraDeposit(storageType);
    }

    @Override
    public LeaderCardType getLeaderType() {
        return LeaderCardType.EXTRA_DEPOSIT;
    }

    public ResourceType getStorageType() {
        return storageType;
    }

}
