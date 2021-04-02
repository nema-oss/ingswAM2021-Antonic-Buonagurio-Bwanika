package it.polimi.ingsw;

public class ActionTokenMove {


    private int steps;
    private boolean shuffle;

    ActionTokenMove(int steps, Boolean shuffle){
        this.steps = steps;
        this.shuffle = shuffle;
    }

    public int getSteps() {
        return steps;
    }

    public boolean isShuffle() {
        return shuffle;
    }

}
