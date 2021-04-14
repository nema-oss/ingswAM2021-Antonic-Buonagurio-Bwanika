package it.polimi.ingsw.player;

public class PopeSection {

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

    public int getPoints() {
        return points;
    }

    public int getID() {
        return ID;
    }

}
