package it.polimi.ingsw.player;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CellTest {

    private Cell cell;


    @Test
    void getPoints() {
        int points = 5;
        cell = new Cell(points,Boolean.TRUE,2);
        assertEquals(points, cell.getPoints(), "Cell should return the number of points");
    }

    @Test
    void isPopeSpace() {

        cell = new Cell(0,Boolean.TRUE,2);
        assertEquals(Boolean.TRUE, cell.isPopeSpace(), "Cell should return if is a popeSpace or not");
        cell = new Cell(0,Boolean.FALSE,2);
        assertEquals(Boolean.FALSE, cell.isPopeSpace(), "Cell should return if is a popeSpace or not");

    }

    @Test
    void getVaticanReportSectionId() {

        int vaticanReportSectionId = 5;
        cell = new Cell(1,Boolean.TRUE,5);
        assertEquals(vaticanReportSectionId, cell.getVaticanReportSectionId(), "Cell should return the vatican report //" +
                " section id");
    }
}