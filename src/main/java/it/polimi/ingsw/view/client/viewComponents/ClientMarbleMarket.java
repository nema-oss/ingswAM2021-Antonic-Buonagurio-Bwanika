package it.polimi.ingsw.view.client.viewComponents;

import it.polimi.ingsw.model.gameboard.Marble;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;

public class ClientMarbleMarket implements Serializable {

    private Marble[][] marbleMarket;
    private Marble freeMarble;
    private final int nRow;
    private final int nCol;

    /*
     *This method is the class constructor
     * @param nRow, nCol number of rows and columns of the marble market;
     * @param marbles : list of marbles to put in the market
     */

    public ClientMarbleMarket(int nRow, int nCol, ArrayList<Marble> marbles ){
        marbleMarket = new Marble[nRow][nCol];
        this.nRow = nRow;
        this.nCol = nCol;


        //inserisco casualmente le biglie nella matrice
        Collections.shuffle(marbles);
        for(int i=0; i<nRow; i++)
            for(int j=0; j<nCol; j++){
                marbleMarket[i][j] = marbles.remove(0);
            }

        //la rimanente è una freeMarble
        freeMarble = marbles.get(0);
    }

    public Marble getFreeMarble(){
        return freeMarble;
    }

    public Marble getMarble(int row, int column){
        return marbleMarket[row][column];
    }

    public Marble[] getRow(int row){return marbleMarket[row];}

    public Marble[] getColumn(int column){

        Marble[] marbles = new Marble[nRow];
        for(int i=0; i<nRow; i++){
            marbles[i] = marbleMarket[i][nCol];
        }
        return marbles;
    }

    public void update(Marble[][] marbleMarket, Marble freeMarble) {

        if(this.freeMarble.getColor().equals(freeMarble.getColor()))
            System.out.println("Non ho modificato le bibl");
        for(int i= 0 ; i < nRow; i++) {
            for (int j = 0; j < nCol; j++) {
                if (this.marbleMarket[i][j].getColor().equals(marbleMarket[i][j].getColor())){
                    System.out.println("Non ho aggiornato il mercato");
                    break;
                }
            }
        }
        this.marbleMarket = marbleMarket;
        this.freeMarble = freeMarble;
    }
}
