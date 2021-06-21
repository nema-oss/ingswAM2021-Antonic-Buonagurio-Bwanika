package it.polimi.ingsw.model.gameboard;

import it.polimi.ingsw.model.player.PopeRoad;

public interface Producible {

        /**
         * @return the producible type
         */
        ProducibleType getType();

        /**
         * @return class name
         */
        String getClassName();

        /**
         * @param popeRoad the pope road
         * @return true if action was performed on pope road
         */
        boolean useEffect(PopeRoad popeRoad);
}
