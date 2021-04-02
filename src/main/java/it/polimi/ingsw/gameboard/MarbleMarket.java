package it.polimi.ingsw.gameboard;

import java.util.*;

public class MarbleMarket {

    private final Marble[][] marbleMarket;
    private Marble freeMarble;
    private final int nRow;
    private final int nCol;

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


    public Marble getFreeMarble(){
        return freeMarble;
    }

    public ArrayList<Marble> buy(int row, int column) {

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

        else {
            //pesca una riga
            marbles = new Marble[nCol];
            if (nCol >= 0) System.arraycopy(marbleMarket[row], 0, marbles, 0, nCol);

            //riordina riga
            tempFreeMarble = marbleMarket[row][0];
            if (nCol - 1 >= 0) System.arraycopy(marbleMarket[row], 1, marbleMarket[row], 0, nCol - 1);
            marbleMarket[nCol-1][row]=freeMarble;
            freeMarble= tempFreeMarble;
        }


        return new ArrayList<>(Arrays.asList(marbles));

    }
}
