package it.polimi.ingsw.model.player;

import java.io.Serializable;

public class Cell implements Serializable {


    private int points;
    private Boolean popeSpace;
    private int vaticanReportSectionId;


    public Cell(int points, Boolean popeSpace, int vaticanReportSectionId){
        this.points = points;
        this.popeSpace = popeSpace;
        this.vaticanReportSectionId = vaticanReportSectionId;
    }

    /*
     * this method give the points to the player based on the position
     * @return number of points in the occupied Cell
    */

    public int getPoints() {
        return points;
    }

    /*
     * this method tells if a Cell is a popeSpace or not
     * @return true if Cell is a popeSpace, else false
    */

    public Boolean isPopeSpace() {
        return popeSpace;
    }
    /*
     * this method give the Vatican Report Section Id of the Cell
     * @return the Vatican Report Section Id of the Cell
    */

    public int getVaticanReportSectionId() {
        return vaticanReportSectionId;
    }

}
