package it.polimi.ingsw;

public class ActionTokenDiscard {

    private DevelopmentCardType type;
    private int amount;

    ActionTokenDiscard(DevelopmentCardType type, int amount){
        this.type = type;
        this.amount = amount;
    }


    public int getAmount() {
        return amount;
    }

    public DevelopmentCardType getType() {
        return type;
    }
}
