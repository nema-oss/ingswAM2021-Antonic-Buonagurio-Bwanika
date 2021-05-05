package it.polimi.ingsw.model.gameboard;

import it.polimi.ingsw.model.player.PopeRoad;

public interface Producible {


        //void useEffect(Player p);

        ProducibleType getType();
        String getClassName();
        boolean useEffect(PopeRoad popeRoad);
}
