package it.polimi.ingsw;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class StrongboxTest {

    private Strongbox strongbox;

    @Test
    @DisplayName("Test adding functionality of strongbox")
    void addResource() {

        ArrayList<Resource> resources = new ArrayList<Resource>();
        Resource gold = new Resource(ResourceType.YELLOW);
        Resource servant = new Resource(ResourceType.VIOLET);
        resources.add(gold);
        resources.add(servant);
        strongbox.addResource(resources);

    }

    @Test
    @DisplayName("Test getting functionality of strongbox")
    void getResource() {
    }
}