package it.polimi.ingsw.model.player;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class PopeSectionTest {

    @Test
    void getID(){
        PopeSection popeSection = new PopeSection(3, 6, 2, 2);
        assertEquals(2, popeSection.getID());
    }

    @Test
    void getPoints(){
        PopeSection popeSection = new PopeSection(3, 6, 2, 2);
        assertEquals(2, popeSection.getPoints());
    }
}
