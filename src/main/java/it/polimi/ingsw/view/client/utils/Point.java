package it.polimi.ingsw.view.client.utils;

/**
 * This class represents coordinates in 2D space
 */
public class Point {

    private int x;
    private int y;

    public Point(){

    }

    /**
     * Setter
     * @param x x coordinate
     */
    public void setX(int x) {
        this.x = x;
    }

    /**
     * Setter
     * @param y y coordinate
     */
    public void setY(int y) {
        this.y = y;
    }

    /**
     * Getter
     * @return x coordinate
     */
    public int getX() {
        return x;
    }

    /**
     * Getter
     * @return y coordinate
     */
    public int getY() {
        return y;
    }
}
