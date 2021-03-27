package it.polimi.ingsw;

public class Discount implements EffectStrategy{
    ResourceType discountType;
    int discountAmount;

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
    public void useEffect(Player p) throws LeaderCardException {

    }
}
