package it.polimi.ingsw.player;

import it.polimi.ingsw.gameboard.Producible;
import it.polimi.ingsw.player.Cell;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

/*
    * PopeRoad represents the road on player's game board
    *
    * @author René
 */

public class PopeRoad {

    private List<Cell> popeRoad;
    private List<Cell> popeSpaces;
    private Cell currentPosition;
    private int currentPositionIndex;

    public PopeRoad(List<Cell> cells){

        popeRoad = cells;
        currentPositionIndex = 0;
        currentPosition = popeRoad.get(0);

    }

    /*
        * this method returns the length of the popeRoad
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

        if(currentPositionIndex + steps >= popeRoad.size() - 1) {
            currentPositionIndex = popeRoad.size()-1;
        }
        else{
            currentPositionIndex += steps;
        }
        currentPosition = popeRoad.get(currentPositionIndex);



    }
    /*
     * this method move the player by a one step
    */

    public void move(){

        if(currentPositionIndex + 1 >= popeRoad.size()-1) {
            currentPositionIndex = popeRoad.size() - 1;
        }
        else{
            currentPositionIndex += 1;
        }

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
