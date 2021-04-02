package it.polimi.ingsw.cards.leadercards;

import it.polimi.ingsw.exception.LeaderCardException;
import it.polimi.ingsw.gameboard.Resource;
import it.polimi.ingsw.gameboard.ResourceType;

import java.util.ArrayList;
import java.util.Map;

public class Discount extends LeaderCard{

    ResourceType discountType;
    int discountAmount;
    ArrayList<Resource> result;

    public Discount(Map<Resource, Integer> costResource, Map<Resource, Integer> costDevelopment, int victoryPoints, ResourceType discountType, int discountAmount, ArrayList<Resource> result) {
        super(costResource, costDevelopment, victoryPoints);
        this.discountType = discountType;
        this.discountAmount = discountAmount;
        this.result = result;
    }

    public ResourceType getDiscountType() {
        return discountType;
    }

    public int getDiscountAmount() {
        return discountAmount;
    }

    public ArrayList<Resource> useEffect(){

        result = new ArrayList<Resource>();
        for(int i = 0; i < discountAmount; i++)
            result.add(new Resource(discountType));
        return result;
    }


}
