package it.polimi.ingsw.gameboard;

import it.polimi.ingsw.player.Player;

public class FaithPoint implements Producible {

    public FaithPoint() {
    }

    public String getType(){
        return "FAITH";
    }
    /*
     *This method applies the effect of a Faith Point, increasing the player's position by one in his PopeRoad
     */

    public void useEffect(Player p){

        p.getPopeRoad().move();
    }

}
