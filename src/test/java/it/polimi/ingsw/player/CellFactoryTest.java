package it.polimi.ingsw.player;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class CellFactoryTest {

    private CellFactory cellFactory;
    private List<Cell> cellList;

    @BeforeEach
    void setUp(){
        cellFactory = new CellFactory();
        cellList = Arrays.asList(cellFactory.getCells());
    }

    @Test
    @DisplayName("Testing json parsing of popeRoad's cells")
    void getCells() {

        assertEquals(0, cellList.get(0).getPoints());
        assertEquals(true, cellList.get(cellList.size()-1).isPopeSpace());
        assertEquals(3, cellList.get(cellList.size()-1).getVaticanReportSectionId());
    }
}