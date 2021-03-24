package it.polimi.ingsw;

import java.util.ArrayList;
import java.util.HashMap;

/*
    * PopeRoad represent the road on player's game board
    *
    * @author René
 */

public class PopeRoad {

    private ArrayList<Cell> popeRoad;
    private ArrayList<Cell> popeSpaces;
    private HashMap<Integer, Integer> vaticanReportSectionsId;
    private Cell currentPosition;
    private int currentPositionIndex;

    PopeRoad(){
        popeRoad = new ArrayList<Cell>(20);
        currentPositionIndex = 0;
        currentPosition = popeRoad.get(0);
    }

    /*
        * this method return the length of the popeRoad
        * @return size(type:int) of popeRoad
     */

    public int getSize(){
        return popeRoad.size();
    }

    /*
        * this method return the current Cell occupied by the player
        * @return current player position (type:Cell)
     */

    public Cell getCurrentPosition(){
        return currentPosition;
    }

    /*
     * this method return the current index position occupied by the player
     * @return current player position (type:int)
     */

    public int getCurrentPositionIndex() {
        return currentPositionIndex;
    }

    /*
     * this method move the player by a given number of steps
     * @param steps(type:int) number of steps forward
    */

    public void move(int steps){

        currentPositionIndex += steps;
        currentPosition = popeRoad.get(currentPositionIndex);

    }
    /*
     * this method move the player by a one step
    */

    public void move(){

        currentPositionIndex += 1;
        currentPosition = popeRoad.get(currentPositionIndex);

    }

    /*
     * this method give the points to the player based on the position
     * @return number of points in the occupied Cell
     */

    public int getPoints(){
        return currentPosition.getPoints();
    }


}
