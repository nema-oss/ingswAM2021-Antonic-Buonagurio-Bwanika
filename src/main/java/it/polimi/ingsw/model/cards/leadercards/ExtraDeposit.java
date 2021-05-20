package it.polimi.ingsw.model.cards.leadercards;


import it.polimi.ingsw.model.cards.DevelopmentCardType;
import it.polimi.ingsw.model.gameboard.ResourceType;
import it.polimi.ingsw.model.player.Effects;

import java.util.Map;

/*
*ExtraDepositLeaderCard represents the LeaderCard with the effect of having two extra slots for a resource
*@author Nemanja
 */

public class ExtraDeposit extends LeaderCard{

    private final ResourceType storageType;
    private LeaderCardType leaderCardType;

    public ExtraDeposit(String id, Map<ResourceType, Integer> costResource, Map<Integer,Map<DevelopmentCardType, Integer>> costDevelopment, int victoryPoints, ResourceType storageType, LeaderCardType leaderCardType) {
        super(id, costResource, costDevelopment, victoryPoints);
        this.storageType = storageType;
        this.leaderCardType = leaderCardType;
    }

    @Override
    public void useEffect(Effects activeEffects){
        activeEffects.activateExtraDeposit(storageType);
    }

    @Override
    public LeaderCardType getLeaderType() {
        return leaderCardType;
    }

    public ResourceType getStorageType() {
        return storageType;
    }

    public String getId() {
        return id;
    }

}
