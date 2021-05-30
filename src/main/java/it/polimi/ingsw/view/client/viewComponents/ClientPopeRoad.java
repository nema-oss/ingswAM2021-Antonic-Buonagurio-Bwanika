package it.polimi.ingsw.view.client.viewComponents;

import it.polimi.ingsw.model.player.Cell;

import java.io.Serializable;
import java.util.List;

public class ClientPopeRoad implements Serializable {

    private List<Cell> popeRoad;
    private List<Cell> popeSpaces;
    private Cell currentPosition;
    private int currentPositionIndex;

    public ClientPopeRoad(List<Cell> cells){

        popeRoad = cells;
        currentPositionIndex = 0;
        currentPosition = popeRoad.get(0);
    }

    /**
     * this method returns the length of the popeRoad
     * @return size of popeRoad
     */

    public int getSize(){
        return popeRoad.size();
    }

    /**
     * this method return the current Cell occupied by the player
     * @return current player position
     */
    public Cell getCurrentPosition(){
        return currentPosition;
    }

    /**
     * this method return the current index position occupied by the player
     * @return current player position
     */
    public int getCurrentPositionIndex() {
        return currentPositionIndex;
    }


    public void setCurrentPosition(int currentPositionIndex) {
        this.currentPosition = popeRoad.get(currentPositionIndex);
    }

    public void setCurrentPositionIndex(int currentPositionIndex) {
        this.currentPositionIndex = currentPositionIndex;
    }

    /**
     * this method move the player by a given number of steps
     * @param steps number of steps forward
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
