package it.polimi.ingsw;

import org.junit.Test;

import java.util.HashMap;
import java.util.Map;
import static it.polimi.ingsw.ResourceType.*;
import static org.junit.Assert.assertEquals;

public class DevelopmentCardTest {
    private DevelopmentCard developmentCard;

    @Test
    public void getLevel(){
        int level = 2;
        Map<ResourceType, Integer> cost = new HashMap<ResourceType, Integer>();
        cost.put(SHIELD, 2);
        Map<ResourceType, Integer> reqMap = new HashMap<ResourceType, Integer>();
        reqMap.put(STONE, 1);
        Producible p = new Resource(COIN);
        Map<Producible, Integer> resMap = new HashMap<Producible, Integer>();
        resMap.put(p, 1);
        developmentCard = new DevelopmentCard(level, DevelopmentCardType.YELLOW, cost, reqMap, resMap, 1);
        assertEquals(level, developmentCard.getLevel());
    }

    @Test
    public void getType(){
        DevelopmentCardType type = DevelopmentCardType.YELLOW;
        Map<ResourceType, Integer> cost = new HashMap<ResourceType, Integer>();
        cost.put(SHIELD, 2);
        Map<ResourceType, Integer> reqMap = new HashMap<ResourceType, Integer>();
        reqMap.put(STONE, 1);
        Producible p = new Resource(COIN);
        Map<Producible, Integer> resMap = new HashMap<Producible, Integer>();
        resMap.put(p, 1);
        developmentCard = new DevelopmentCard(1, type, cost, reqMap, resMap, 1);
        assertEquals(type, developmentCard.getType());
    }

}
