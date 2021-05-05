package it.polimi.ingsw.GameBoardTest;

import it.polimi.ingsw.model.gameboard.*;
import org.junit.Test;
import org.junit.jupiter.api.DisplayName;

import static org.junit.jupiter.api.Assertions.*;

import java.util.Optional;

public class MarbleTest {

    @Test
    @DisplayName("tests creation of marbles and correspondence between color and product")
    public void marbleTest(){

        Marble marble =  new Marble(MarbleType.WHITE, null);
        assertEquals(Optional.empty(), marble.getProduct());
        assertEquals(MarbleType.WHITE, marble.getColor());

        marble = new  Marble(MarbleType.BLUE, new Resource(ResourceType.SHIELD));
        if(marble.getProduct().isPresent())
            assertEquals(ResourceType.SHIELD, marble.getProduct().get().getType());
        assertEquals(MarbleType.BLUE, marble.getColor());

        marble = new Marble(MarbleType.RED, new FaithPoint(FaithType.FAITH));
        if(marble.getProduct().isPresent())
            assertEquals(FaithType.FAITH, marble.getProduct().get().getType());
        assertEquals(MarbleType.RED, marble.getColor());
    }
}
