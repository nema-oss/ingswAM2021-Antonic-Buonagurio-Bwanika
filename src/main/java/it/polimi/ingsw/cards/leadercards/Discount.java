package it.polimi.ingsw.cards.leadercards;

import it.polimi.ingsw.cards.DevelopmentCardType;
import it.polimi.ingsw.exception.LeaderCardException;
import it.polimi.ingsw.gameboard.Resource;
import it.polimi.ingsw.gameboard.ResourceType;

import java.util.ArrayList;
import java.util.Map;

public class Discount extends LeaderCard<Integer>{

    private ResourceType discountType;
    private int discountAmount;
    private LeaderCardType leaderCardType;

    public Discount(Map<ResourceType, Integer> costResource, Map<Integer, Map<DevelopmentCardType, Integer>> costDevelopment, int victoryPoints, ResourceType discountType, int discountAmount, LeaderCardType leaderCardType) {
        super(costResource, costDevelopment, victoryPoints);
        this.discountType = discountType;
        this.discountAmount = discountAmount;
        this.leaderCardType = leaderCardType;
    }

    public ResourceType getDiscountType() {
        return discountType;
    }

    public int getDiscountAmount() {
        return discountAmount;
    }

    @Override
    public Integer useEffect(){ //useless as well
        return discountAmount;
    }

    public LeaderCardType getLeaderType(){
        return leaderCardType;
    }


}
