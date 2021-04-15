package it.polimi.ingsw.player;

import it.polimi.ingsw.gameboard.Resource;
import it.polimi.ingsw.gameboard.ResourceType;
import it.polimi.ingsw.player.Strongbox;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;


import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

class StrongboxTest {

    private Strongbox strongbox;
    private ArrayList<Resource> resources;
    private Resource singleResource;

    @BeforeEach
    void setUp(){
        Resource gold = new Resource(ResourceType.COIN);
        Resource servant = new Resource(ResourceType.SERVANT);
        Resource shield = new Resource(ResourceType.SHIELD);
        resources = new ArrayList<Resource>();
        resources.add(gold);
        resources.add(servant);
        singleResource = new Resource(ResourceType.SHIELD);

    }
    @Test
    @DisplayName("Test adding functionality of strongbox")
    void addResources() throws Exception {

        strongbox = new Strongbox();
        Map<ResourceType, Integer> freq = resources.stream().collect(Collectors.groupingBy(Resource::getType, Collectors.summingInt(e -> 1)));
        strongbox.addResource(resources);
        for (ResourceType type : freq.keySet()) {
            List<Resource> getResource = strongbox.getResource(type, freq.get(type));
            assertEquals(freq.get(type), getResource.size());
            for (Resource res : getResource)
                assertEquals(type, res.getType());

        }
    }

    @Test
    @DisplayName("Test adding single resource functionality of strongbox")
    void addSingleResource() throws Exception{

        strongbox = new Strongbox();
        strongbox.addResource(singleResource);
        List<Resource> resourcesFromStrongbox = strongbox.getResource(singleResource.getType(),1);
        assertEquals(resourcesFromStrongbox.get(0).getType(),singleResource.getType());
        assertEquals(resourcesFromStrongbox.size(),1);

    }


    @Test
    @DisplayName("Test getting functionality of strongbox")
    void askTooMuchResources() {

        strongbox = new Strongbox();
        assertThrows(Exception.class, ()->strongbox.getResource(ResourceType.STONE,30));
        strongbox.addResource(resources);
        assertThrows(Exception.class, () -> strongbox.getResource(ResourceType.STONE, 30));
    }
}