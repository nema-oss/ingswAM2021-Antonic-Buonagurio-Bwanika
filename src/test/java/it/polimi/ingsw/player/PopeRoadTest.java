package it.polimi.ingsw.player;


import static org.junit.jupiter.api.Assertions.*;

import it.polimi.ingsw.player.PopeRoad;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

class PopeRoadTest {

        private PopeRoad popeRoad;
        private List<Cell> cells;
        private CellFactory cellFactory;

        @BeforeEach
        public void setUp() throws Exception {
            cellFactory = new CellFactory();
            cells = Arrays.asList(cellFactory.getCells());
            popeRoad = new PopeRoad(cells);
        }

        @Test
        @DisplayName("Test movement by one step on popeRoad")
        public void testMoveOneStep() {

            int prev_position = popeRoad.getCurrentPositionIndex();
            popeRoad.move();
            assertEquals(prev_position + 1, popeRoad.getCurrentPositionIndex());
        }

        @Test
        @DisplayName("Test movement by a given number of steps on popeRoad")
        public void testMoveGivenSteps() {

            int[] testSteps = {3,4,7,6,0};
            int prevPosition;
            int p = 0;
            for (int i = 0; i < popeRoad.getSize(); i++) {
                prevPosition = popeRoad.getCurrentPositionIndex();
                System.out.println(popeRoad.getCurrentPosition().getPoints());
                p += popeRoad.getCurrentPosition().getPoints();
                popeRoad.move();
                //assertEquals(prevPosition + i, popeRoad.getCurrentPositionIndex());
            }
            System.out.println(p);
        }

        @Test
        @DisplayName("Test movement on the boundaries of the popeRoad")
        public void testMoveOnBoundaries() {

            int popeRoadSize = popeRoad.getSize();
            int steps = popeRoad.getSize() + 1;
            int prevPosition;
            popeRoad.move(steps);
            assertEquals(popeRoad.getSize() - 1, popeRoad.getCurrentPositionIndex(),
                    "Players should be able to move till the end");
            popeRoad.move();
            assertEquals(popeRoad.getSize() - 1, popeRoad.getCurrentPositionIndex(),
                    "Players should be able to move till the end");
        }


}