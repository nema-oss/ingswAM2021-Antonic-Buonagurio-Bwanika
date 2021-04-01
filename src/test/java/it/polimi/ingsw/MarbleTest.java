package it.polimi.ingsw;

import org.junit.Test;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

public class MarbleTest {

    private Marble marble;

    @Test
    public void testGetColor(){
        MarbleType color = MarbleType.BLUE;
        marble =  new Marble(color, Optional.of(new Resource(ResourceType.SHIELD)));

        assertEquals (color, marble.getColor(), "Marble should return the marble's color");

    }

    @Test
    public void testGetProduct() throws Exception {
        MarbleType color = MarbleType.RED;
        marble=new Marble(color, Optional.of(new FaithPoint()));
        assertEquals(new FaithPoint(), marble.getProduct().orElseThrow(Exception::new), "Marble should return a FaithPoint");

        color = MarbleType.YELLOW;
        marble = new Marble(color, Optional.of(new Resource(ResourceType.COIN)));
        assertEquals(new Resource(ResourceType.COIN), marble.getProduct().orElseThrow(Exception::new), "Marble should return the resource corresponding to its color");
    }
}
