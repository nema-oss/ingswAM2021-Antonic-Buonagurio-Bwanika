package it.polimi.ingsw.gameboard;

import it.polimi.ingsw.player.PopeRoad;

public interface Producible {

        //void useEffect(Player p);

        ProducibleType getType();
        String getClassName();
        boolean useEffect(PopeRoad popeRoad);
}
