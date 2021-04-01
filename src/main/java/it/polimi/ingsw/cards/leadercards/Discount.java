package it.polimi.ingsw.cards.leadercards;

import it.polimi.ingsw.exception.LeaderCardException;
import it.polimi.ingsw.gameboard.Resource;
import it.polimi.ingsw.gameboard.ResourceType;

import java.util.ArrayList;

public class Discount implements EffectStrategy {

    ResourceType discountType;
    int discountAmount;
    ArrayList<Resource> result;

    public Discount(ResourceType discountType, int discountAmount) {
        this.discountType = discountType;
        this.discountAmount = discountAmount;
    }

    public ResourceType getDiscountType() {
        return discountType;
    }

    public int getDiscountAmount() {
        return discountAmount;
    }

    @Override
    public ArrayList<Resource> useEffect() throws LeaderCardException {

        result = new ArrayList<Resource>();
        for(int i = 0; i < discountAmount; i++)
            result.add(new Resource(discountType));
        return result;
    }

}
