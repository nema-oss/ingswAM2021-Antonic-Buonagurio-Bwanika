package it.polimi.ingsw.view.client.viewComponents;

import it.polimi.ingsw.model.player.Cell;

import java.util.List;

public class ClientPopeRoad {

    private List<Cell> popeRoad;
    private List<Cell> popeSpaces;
    private Cell currentPosition;
    private int currentPositionIndex;

    public ClientPopeRoad(List<Cell> cells){

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

}
