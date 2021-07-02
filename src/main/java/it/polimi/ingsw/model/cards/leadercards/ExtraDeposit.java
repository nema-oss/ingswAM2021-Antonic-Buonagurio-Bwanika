package it.polimi.ingsw.model.cards.leadercards;


import it.polimi.ingsw.model.cards.DevelopmentCardType;
import it.polimi.ingsw.model.gameboard.ResourceType;
import it.polimi.ingsw.model.player.Effects;

import java.util.Map;

/**
 * this class represents the extra deposit leader cards
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

    /**
     * @return the type of resources that can be stored
     */
    public ResourceType getStorageType() {
        return storageType;
    }


}
