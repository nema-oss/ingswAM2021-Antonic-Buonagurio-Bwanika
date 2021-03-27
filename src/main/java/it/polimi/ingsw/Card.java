package it.polimi.ingsw;

import java.util.Map;

public interface Card {

    int getVictoryPoints();
    Map<Resource, Integer> getCost();

}
