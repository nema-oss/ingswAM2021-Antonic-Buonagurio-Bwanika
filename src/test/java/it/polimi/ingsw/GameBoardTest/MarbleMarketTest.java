package it.polimi.ingsw.GameBoardTest;

import it.polimi.ingsw.gameboard.Marble;
import it.polimi.ingsw.gameboard.MarbleFactory;
import it.polimi.ingsw.gameboard.MarbleMarket;
import org.junit.Test;
import org.junit.jupiter.api.BeforeEach;
import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MarbleMarketTest {

    @Test
    public void setUp(){

        MarbleFactory marbleFactory = new MarbleFactory();
        ArrayList<Marble> marbles = (marbleFactory.getMarbles());
        MarbleMarket marbleMarket = new MarbleMarket(3,4, marbles);

        assertEquals(1, marbles.size());

        Marble freeMarble = marbles.get(0);
    }

    @Test
    public void getFreeMarbleTest(){
        MarbleFactory marbleFactory = new MarbleFactory();
        ArrayList<Marble> marbles = (marbleFactory.getMarbles());
        MarbleMarket marbleMarket = new MarbleMarket(3,4, marbles);
        Marble freeMarble = marbles.get(0);

        assertEquals(freeMarble, marbleMarket.getFreeMarble());
    }

    @Test
    public void buyTest(){
        MarbleFactory marbleFactory = new MarbleFactory();
        ArrayList<Marble> marbles = (marbleFactory.getMarbles());
        MarbleMarket marbleMarket = new MarbleMarket(3,4, marbles);
        Marble freeMarble = marbles.get(0);

        //row==-1: pesca colonna
        for(int j=0; j<4; j++) {
            List<Marble> marbles2 = new ArrayList<>();
            for(int i=0; i<3; i++)
                marbles2.add(marbleMarket.getMarble(i,j));
            assertEquals(marbles2, marbleMarket.buy(-1,j));
        }


        //column==-1 pesca riga
        for(int i=0; i<3; i++) {
            List<Marble> marbles3 = new ArrayList<>();
            for(int j=0; j<4; j++)
                marbles3.add(marbleMarket.getMarble(i,j));
            assertEquals(marbles3, marbleMarket.buy(i, -1));
        }

    }

}
