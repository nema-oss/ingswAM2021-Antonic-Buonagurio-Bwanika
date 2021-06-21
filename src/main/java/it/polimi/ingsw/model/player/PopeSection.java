package it.polimi.ingsw.model.player;

import java.io.Serializable;

/**
 * this class represents a pope section
 */
public class PopeSection implements Serializable {

    private int start;
    private int end;
    private int points;
    private int ID;

    public PopeSection(int start, int end, int points, int ID){

        this.start = start;
        this.end = end;
        this.points = points;
        this.ID = ID;

    }

    /**
     * @return the section's points
     */
    public int getPoints() {
        return points;
    }

    public int getID() {
        return ID;
    }

}
