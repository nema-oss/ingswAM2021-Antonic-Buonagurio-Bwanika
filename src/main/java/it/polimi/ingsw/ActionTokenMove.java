package it.polimi.ingsw;

import it.polimi.ingsw.cards.ActionTokenDeck;
import it.polimi.ingsw.gameboard.CardMarket;
import it.polimi.ingsw.player.PopeRoad;

public class ActionTokenMove extends ActionToken{


    private int steps;
    private boolean shuffle;

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

    public int getSteps() {
        return steps;
    }

    public boolean isShuffle() {
        return shuffle;
    }

}
