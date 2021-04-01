package it.polimi.ingsw;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

class StrongboxTest {

    private Strongbox strongbox;

    @Test
    @DisplayName("Test adding functionality of strongbox")
    void addResource() {

        ArrayList<Resource> resources = new ArrayList<Resource>();
        Resource gold = new Resource(ResourceType.COIN);
        Resource servant = new Resource(ResourceType.SERVANT);
        resources.add(gold);
        resources.add(servant);
        strongbox.addResource(resources);

    }

    @Test
    @DisplayName("Test getting functionality of strongbox")
    void getResource() {
    }
}