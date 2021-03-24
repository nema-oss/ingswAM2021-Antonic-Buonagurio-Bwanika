package it.polimi.ingsw;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.RepeatedTest;
import org.junit.jupiter.api.Test;

class PopeRoadTest {

        private PopeRoad popeRoad;

        @BeforeEach
        public void setUp() throws Exception {
            popeRoad = new PopeRoad();
        }

        @Test
        @DisplayName("Test movement by one step on popeRoad")
        public void testMoveOneStep() {

            int prev_position = popeRoad.getCurrentPositionIndex();
            popeRoad.move();
            assertEquals(prev_position + 1, popeRoad.getCurrentPositionIndex(),
                    "Regular multiplication should work");
        }

        @Test
        @DisplayName("Test movement by a given number of steps on popeRoad")
        public void testMoveGivenSteps() {

            int[] testSteps = {3,4,7,8,0};
            int prevPosition;
            popeRoad.move();
            for (int testStep : testSteps) {
                prevPosition = popeRoad.getCurrentPositionIndex();
                assertEquals(prevPosition + testStep, popeRoad.getCurrentPositionIndex(),
                        "Regular multiplication should work");
            }
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