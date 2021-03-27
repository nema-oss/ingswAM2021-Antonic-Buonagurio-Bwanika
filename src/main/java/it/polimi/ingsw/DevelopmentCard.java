package it.polimi.ingsw;

import java.util.Map;

/*
* sketch of class DevelopmentCard - unfinished
 */

public class DevelopmentCard implements Card {

    public DevelopmentCardColor color;
    public int level, victoryPoints;
    private Map<Resource, Integer> cost;

    public DevelopmentCard(DevelopmentCardColor color, int level) {
        this.color = color;
        this.level = level;
    }

    public Map<Resource, Integer> getCost() {
        return cost;
    }

    public DevelopmentCardColor getColor() {
        return color;
    }

    public int getLevel() {
        return level;
    }

    public int getVictoryPoints(){
        return victoryPoints;
    }

}
