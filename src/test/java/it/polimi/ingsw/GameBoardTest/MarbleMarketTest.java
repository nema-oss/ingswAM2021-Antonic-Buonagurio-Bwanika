package it.polimi.ingsw.GameBoardTest;

import it.polimi.ingsw.gameboard.Marble;
import it.polimi.ingsw.gameboard.MarbleFactory;
import it.polimi.ingsw.gameboard.MarbleMarket;
import org.junit.Test;
import org.junit.jupiter.api.BeforeEach;
import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;

public class MarbleMarketTest {

    private MarbleMarket marbleMarket;
    private Marble freeMarble;

    @BeforeEach
    public void setUp(){

        MarbleFactory marbleFactory = new MarbleFactory();
        ArrayList<Marble> marbles = marbleFactory.getMarbles();
        marbleMarket = new MarbleMarket(3,4, marbles);

        assertEquals(1, marbles.size());

        freeMarble = marbles.get(0);
    }

    @Test
    public void getFreeMarbleTest(){
        assertEquals(freeMarble, marbleMarket.getFreeMarble());
    }

    @Test
    public void buyTest(){

        //row==-1: pesca colonna
        for(int i=0; i<4; i++) {
            ArrayList<Marble> marbles = new ArrayList<>();
            for(int j=0; j<3; j++)
                marbles.add(marbleMarket.getMarble(j,i));
            assertEquals(marbles, marbleMarket.buy(-1,i));
        }


        //column==-1 pesca riga
        for(int i=0; i<3; i++) {
            ArrayList<Marble> marbles = new ArrayList<>();
            for(int j=0; j<4; j++)
                marbles.add(marbleMarket.getMarble(i,j));
            assertEquals(marbles, marbleMarket.buy(i, -1));
        }

    }

}
