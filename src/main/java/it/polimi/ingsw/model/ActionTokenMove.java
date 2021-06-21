package it.polimi.ingsw.model;

import it.polimi.ingsw.model.cards.ActionTokenDeck;
import it.polimi.ingsw.model.gameboard.CardMarket;
import it.polimi.ingsw.model.player.PopeRoad;

/**
 * this class represents an action token of type move
 */
public class ActionTokenMove extends ActionToken{


    private final int steps;
    private final boolean shuffle;

    ActionTokenMove(int steps, Boolean shuffle){
        this.steps = steps;
        this.shuffle = shuffle;
    }

    @Override
    public  boolean useEffect(PopeRoad lorenzoPopeRoad, CardMarket cardMarket, ActionTokenDeck actionTokenDeck){

        lorenzoPopeRoad.move(steps);
        if(shuffle)
            actionTokenDeck.shuffle();
        return false;
    }

    /**
     * @return the number of steps to take
     */
    public int getSteps() {
        return steps;
    }

    /**
     * @return true if the action token deck has to be shuffled
     */
    public boolean isShuffle() {
        return shuffle;
    }

}
