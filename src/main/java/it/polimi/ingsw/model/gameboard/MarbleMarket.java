package it.polimi.ingsw.model.gameboard;

import java.io.Serializable;
import java.util.*;

/*
 * this class represents the MarbleMarket containing all marbles.
 * @author Chiara Buonagurio
 */

public class MarbleMarket implements Serializable {

    private final Marble[][] marbleMarket;
    private Marble freeMarble;
    private final int nRow;
    private final int nCol;

    /*
     *This method is the class constructor
     * @param nRow, nCol number of rows and columns of the marble market;
     * @param marbles : list of marbles to put in the market
     */

    public MarbleMarket(int nRow, int nCol, ArrayList<Marble> marbles ){
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


    /*
     *This method returns the free marble, which is the one that is not in the grid and has to be inserted after purchase
     * @return free marble (Type: Marble)
     */
    public Marble getFreeMarble(){
        return freeMarble;
    }

    /**
    * this method returns the list of marbles selected to the player; they cabn correspond to a row or a column of the market.
    * this method also rearranges the marble market after purchase
    * @param row : if the player wants to buy a row, it is the index of that row. if the player wants to buy a column it's -1;
    * @param column : it is the column index if the player wants to buy a column, otherwise it is -1;
    * @return list of marbles selected (Type : List<Marble>)
    */
    public List<Marble> buy(int row, int column) {

        Marble[] marbles;
        Marble tempFreeMarble;


        if (row == -1) {

            //pesca una colonna
            marbles = new Marble[nRow];
            for(int i=0; i<nRow; i++)
                marbles[i] = marbleMarket[i][column];

            //riordina colonna
            tempFreeMarble = marbleMarket[0][column];
            for(int i=0; i<nRow-1; i++)
                marbleMarket[i][column] = marbleMarket[i+1][column];
            marbleMarket[nRow-1][column] = freeMarble;
            freeMarble = tempFreeMarble;
        }

        else{
            //pesca una riga
            marbles = new Marble[nCol];
            if (nCol >= 0) System.arraycopy(marbleMarket[row], 0, marbles, 0, nCol);

            //riordina riga
            tempFreeMarble = marbleMarket[row][0];
            if (nCol - 1 >= 0) System.arraycopy(marbleMarket[row], 1, marbleMarket[row], 0, nCol - 1);
            marbleMarket[row][nCol-1]=freeMarble;
            freeMarble= tempFreeMarble;
        }


        return Arrays.asList(marbles);

    }


    public Marble getMarble(int row, int column){
        return marbleMarket[row][column];
    }

    public Marble[][] marbles(){
        return marbleMarket;
    }
}
