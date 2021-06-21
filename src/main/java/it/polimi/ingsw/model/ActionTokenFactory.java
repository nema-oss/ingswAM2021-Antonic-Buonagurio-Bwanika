package it.polimi.ingsw.model;

import it.polimi.ingsw.model.cards.DevelopmentCardType;

import java.util.ArrayList;
import java.util.List;

/**
 * this class creates the action tokens from a json file
 */
public class ActionTokenFactory {

    private final List<ActionToken> actionTokens;
    private static final int DISCARD_AMOUNT = 2;
    private static final int MOVE_STEP_SHUFFLE = 1;
    private static final int MOVE_STEP_NO_SHUFFLE = 2;

    public ActionTokenFactory(){

        actionTokens = new ArrayList<>();
    }

    /**
     * this method creates the action tokens
     * @return the tokens created
     */
    public List<ActionToken> getTokens() {

        ActionToken moveShuffle = new ActionTokenMove(MOVE_STEP_SHUFFLE, true);
        moveShuffle.setId("7");
        ActionToken moveNoShuffle = new ActionTokenMove(MOVE_STEP_NO_SHUFFLE, false);
        moveNoShuffle.setId("5");
        ActionToken discardBlue = new ActionTokenDiscard(DevelopmentCardType.BLUE, DISCARD_AMOUNT);
        discardBlue.setId("1");
        ActionToken discardYellow = new ActionTokenDiscard(DevelopmentCardType.YELLOW, DISCARD_AMOUNT);
        discardYellow.setId("4");
        ActionToken discardPurple = new ActionTokenDiscard(DevelopmentCardType.PURPLE, DISCARD_AMOUNT);
        discardPurple.setId("3");
        ActionToken discardGreen = new ActionTokenDiscard(DevelopmentCardType.GREEN, DISCARD_AMOUNT);
        discardGreen.setId("2");

        actionTokens.add(moveShuffle);
        actionTokens.add(moveNoShuffle);
        actionTokens.add(discardBlue);
        actionTokens.add(discardPurple);
        actionTokens.add(discardGreen);
        actionTokens.add(discardYellow);
        return actionTokens;
    }
}
