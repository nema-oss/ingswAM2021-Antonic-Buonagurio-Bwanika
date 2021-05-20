package it.polimi.ingsw.model.gameboard;

import it.polimi.ingsw.model.player.PopeRoad;

import java.io.Serializable;

/*
* this class represents a FaithPoint (red cross)
* @author Chiara Buonagurio
 */

public class FaithPoint implements Producible, Serializable {

    FaithType type;
    private String className;
    public static final int STEPS = 1;

    public FaithPoint(FaithType type) {
        this.className = getClass().getName();
        this.type = type;
    }

    /*
     *This method returns the faithPoint's type
     * @return type (Type: FaithType)
     */
    public FaithType getType(){
        return type;
    }

    /*
     *This method returns this class' name and it is used to build FaithPoints
     */
    @Override
    public String getClassName() {
        return className;
    }

    /*
     *This method applies the effect of a Faith Point, increasing the player's position by one in his PopeRoad
     */
    public boolean useEffect(PopeRoad popeRoad){
        popeRoad.move();
        return true;
    }

}
