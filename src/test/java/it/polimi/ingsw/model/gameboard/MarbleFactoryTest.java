package it.polimi.ingsw.model.gameboard;

import it.polimi.ingsw.model.gameboard.*;
import org.junit.Test;
import org.junit.jupiter.api.DisplayName;

import java.util.ArrayList;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

public class MarbleFactoryTest {


    @Test

    public void setUp(){

        ArrayList<Marble> marbles;
        MarbleFactory marbleFactory = new MarbleFactory();
        marbles = marbleFactory.getMarbles();
        assertEquals(13, marbles.size());
    }

    @Test
    @DisplayName("tests creation of marbles")
    public void getMarblesTest(){

        ArrayList<Marble> marbles;
        MarbleFactory marbleFactory = new MarbleFactory();
        marbles = marbleFactory.getMarbles();

        ArrayList<Marble> oneColor = new ArrayList<>();

        for(MarbleType color : MarbleType.values()) {
            oneColor.clear();

            for (Marble marble : marbles) {
                if (marble.getColor().equals(color))
                    oneColor.add(marble);
            }



            if (color.equals(MarbleType.WHITE)) {
                assertEquals(4, oneColor.size());
                for (Marble m : oneColor)
                    assertEquals(Optional.empty(), m.getProduct());
            }

            else if (color.equals(MarbleType.BLUE)) {
                assertEquals(2, oneColor.size());
                for (Marble m : oneColor)
                    if(m.getProduct().isPresent())
                        assertEquals(ResourceType.SHIELD, m.getProduct().get().getType());
            }

            else if (color.equals(MarbleType.PURPLE)) {
                assertEquals(2, oneColor.size());
                for (Marble m : oneColor)
                    if(m.getProduct().isPresent())
                        assertEquals(ResourceType.SERVANT, m.getProduct().get().getType());
            }

            else if (color.equals(MarbleType.YELLOW)) {
                assertEquals(2, oneColor.size());
                for (Marble m : oneColor)
                    if(m.getProduct().isPresent())
                        assertEquals(ResourceType.COIN, m.getProduct().get().getType());
            }

            else if (color.equals(MarbleType.GREY)) {
                assertEquals(2, oneColor.size());
                for (Marble m : oneColor)
                    if(m.getProduct().isPresent())
                        assertEquals(ResourceType.STONE, m.getProduct().get().getType());
            }

            else if (color.equals(MarbleType.RED)) {
                assertEquals(1, oneColor.size());
                for (Marble m : oneColor)
                    if(m.getProduct().isPresent())
                        assertEquals(FaithType.FAITH, m.getProduct().get().getType());
            }
        }

    }


}
