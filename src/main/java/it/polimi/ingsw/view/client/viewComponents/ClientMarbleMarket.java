package it.polimi.ingsw.view.client.viewComponents;

import it.polimi.ingsw.model.gameboard.Marble;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;

/**
 * this class represents the marble market client side
 */
public class ClientMarbleMarket implements Serializable {

    private Marble[][] marbleMarket;
    private Marble freeMarble;
    private final int nRow;
    private final int nCol;


    public ClientMarbleMarket(int nRow, int nCol, ArrayList<Marble> marbles ){
        marbleMarket = new Marble[nRow][nCol];
        this.nRow = nRow;
        this.nCol = nCol;


        Collections.shuffle(marbles);
        for(int i=0; i<nRow; i++)
            for(int j=0; j<nCol; j++){
                marbleMarket[i][j] = marbles.remove(0);
            }

        freeMarble = marbles.get(0);
    }

    /**
     * @return the free marble
     */
    public Marble getFreeMarble(){
        return freeMarble;
    }

    /**
     * tihs method returns the marble in a given position
     * @param row the row index in the marble market
     * @param column the column index in the marble market
     * @return the marble requested
     */
    public Marble getMarble(int row, int column){
        return marbleMarket[row][column];
    }

    /**
     * this method returns a row of the marble market
     * @param row the row requested
     * @return the array of marbles present in that row
     */
    public Marble[] getRow(int row){return marbleMarket[row];}

    /**
     * this method returns a column of the marble market
     * @param column the row requested
     * @return the array of marbles present in that column
     */
    public Marble[] getColumn(int column){

        Marble[] marbles = new Marble[nRow];
        for(int i=0; i<nRow; i++){
            marbles[i] = marbleMarket[i][nCol];
        }
        return marbles;
    }

    /**
     * this method updates the marble market
     * @param marbleMarket the modified market
     * @param freeMarble the new free marble
     */
    public void update(Marble[][] marbleMarket, Marble freeMarble) {

        for(int i= 0 ; i < nRow; i++) {
            for (int j = 0; j < nCol; j++) {
                if (this.marbleMarket[i][j].getColor().equals(marbleMarket[i][j].getColor())){
                    break;
                }
            }
        }
        this.marbleMarket = marbleMarket;
        this.freeMarble = freeMarble;
    }
}
