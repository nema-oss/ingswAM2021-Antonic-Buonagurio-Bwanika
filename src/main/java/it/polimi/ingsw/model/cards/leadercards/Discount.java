package it.polimi.ingsw.model.cards.leadercards;

import it.polimi.ingsw.model.cards.DevelopmentCardType;
import it.polimi.ingsw.model.gameboard.ResourceType;
import it.polimi.ingsw.model.player.Effects;

import java.util.Map;

public class Discount extends LeaderCard{

    private ResourceType discountType;
    private int discountAmount;
    private LeaderCardType leaderCardType;

    public Discount(Map<ResourceType, Integer> costResource, Map<Integer, Map<DevelopmentCardType, Integer>> costDevelopment, int victoryPoints, ResourceType discountType, int discountAmount, LeaderCardType leaderCardType) {
        super(costResource, costDevelopment, victoryPoints);
        this.discountType = discountType;
        this.discountAmount = discountAmount;
        this.leaderCardType = leaderCardType;
    }

    @Override
    public void useEffect(Effects activeEffects){ //useless as well
        activeEffects.activateDiscount(discountType, discountAmount);
    }

    @Override
    public LeaderCardType getLeaderType(){
        return LeaderCardType.DISCOUNT;
    }

    public ResourceType getDiscountType() {
        return discountType;
    }

    public int getDiscountAmount() {
        return discountAmount;
    }

}
