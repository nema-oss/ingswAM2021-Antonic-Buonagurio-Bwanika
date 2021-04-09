package it.polimi.ingsw.cards.leadercards;

import it.polimi.ingsw.exception.LeaderCardException;
import it.polimi.ingsw.gameboard.Resource;
import it.polimi.ingsw.gameboard.ResourceType;

import java.util.ArrayList;
import java.util.Map;

public class Discount extends LeaderCard<ArrayList<Resource>>{

    private ResourceType discountType;
    private int discountAmount;
    private ArrayList<Resource> result;
    private LeaderCardType leaderCardType;

    public Discount(Map<ResourceType, Integer> costResource, Map<Map<ResourceType, Integer>, Integer> costDevelopment, int victoryPoints, Boolean isActive, ResourceType discountType, int discountAmount, ArrayList<Resource> result, LeaderCardType leaderCardType) {
        super(costResource, costDevelopment, victoryPoints, isActive);
        this.discountType = discountType;
        this.discountAmount = discountAmount;
        this.result = result;
        this.leaderCardType = leaderCardType;
    }

    public ResourceType getDiscountType() {
        return discountType;
    }

    public int getDiscountAmount() {
        return discountAmount;
    }

    @Override
    public ArrayList<Resource> useEffect(){

        result = new ArrayList<Resource>();
        for(int i = 0; i < discountAmount; i++)
            result.add(new Resource(discountType));
        return result;
    }

    public LeaderCardType getLeaderType(){
        return leaderCardType;
    }


}
