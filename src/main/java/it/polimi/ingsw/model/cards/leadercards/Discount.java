package it.polimi.ingsw.model.cards.leadercards;

import it.polimi.ingsw.model.cards.DevelopmentCardType;
import it.polimi.ingsw.model.gameboard.ResourceType;
import it.polimi.ingsw.model.player.Effects;

import java.util.Map;

/**
 * this class represents the discount leader cards
 */
public class Discount extends LeaderCard{

    private final ResourceType discountType;
    private final int discountAmount;
    private LeaderCardType leaderCardType;

    public Discount(String id, Map<ResourceType, Integer> costResource, Map<Integer, Map<DevelopmentCardType, Integer>> costDevelopment, int victoryPoints, ResourceType discountType, int discountAmount, LeaderCardType leaderCardType) {
        super(id, costResource, costDevelopment, victoryPoints);
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

    /**
     * @return the type of discount
     */
    public ResourceType getDiscountType() {
        return discountType;
    }

    /**
     * @return the quantity discounted
     */
    public int getDiscountAmount() {
        return discountAmount;
    }

}
