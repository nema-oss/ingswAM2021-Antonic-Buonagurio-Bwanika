package it.polimi.ingsw.model.gameboard;

import it.polimi.ingsw.model.gameboard.FaithPoint;
import it.polimi.ingsw.model.gameboard.FaithType;
import it.polimi.ingsw.model.player.CellFactory;
import it.polimi.ingsw.model.player.PopeRoad;
import org.junit.Test;
import org.junit.jupiter.api.DisplayName;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;

public class FaithPointTest {

    FaithPoint faithPoint;

    @Test
    public void getTypeTest(){
        faithPoint = new FaithPoint(FaithType.FAITH);
        assertEquals(FaithType.FAITH, faithPoint.getType());
    }

    @Test
    @DisplayName("tests effect of a faith point on a player's pope road")
    public void useEffectTest() {
        faithPoint = new FaithPoint(FaithType.FAITH);
        PopeRoad popeRoad = new PopeRoad(Arrays.asList(new CellFactory().getCells().clone()));
        faithPoint.useEffect(popeRoad);
        assertEquals(1,popeRoad.getCurrentPositionIndex());

        faithPoint.useEffect(popeRoad);
        faithPoint.useEffect(popeRoad);
        faithPoint.useEffect(popeRoad);
        assertEquals(4,popeRoad.getCurrentPositionIndex());

        assertTrue(faithPoint.useEffect(popeRoad));
    }

    @Test
    @DisplayName("testing getter of class name")
    public void getClassNameTest(){
        faithPoint = new FaithPoint(FaithType.FAITH);
        assertEquals("it.polimi.ingsw.model.gameboard.FaithPoint",faithPoint.getClassName());
    }
}
