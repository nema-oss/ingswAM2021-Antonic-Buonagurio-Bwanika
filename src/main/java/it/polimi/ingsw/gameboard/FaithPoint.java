package it.polimi.ingsw.gameboard;

import it.polimi.ingsw.player.Player;
import it.polimi.ingsw.player.PopeRoad;

public class FaithPoint implements Producible {

    public static final int STEPS = 1;

    public FaithPoint() {
    }

    /*
     *This method applies the effect of a Faith Point, increasing the player's position by one in his PopeRoad
     */

    public boolean useEffect(PopeRoad popeRoad){

        popeRoad.move();
        return true;
    }

}
