package it.polimi.ingsw;

import com.google.gson.Gson;
import it.polimi.ingsw.cards.DevelopmentCardType;
import it.polimi.ingsw.player.Cell;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.Reader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ActionTokenFactory {

    private List<ActionToken> actionTokens;
    private static final int DISCARD_AMOUNT = 2;
    private static final int MOVE_STEP_SHUFFLE = 1;
    private static final int MOVE_STEP_NOSHUFFLE = 2;

    public ActionTokenFactory(){

        actionTokens = new ArrayList<>();
    }

    public List<ActionToken> getTokens() {

        ActionToken moveShuffle = new ActionTokenMove(MOVE_STEP_SHUFFLE, true);
        ActionToken moveNoShuffle = new ActionTokenMove(MOVE_STEP_NOSHUFFLE, false);
        ActionToken discardBlue = new ActionTokenDiscard(DevelopmentCardType.BLUE, DISCARD_AMOUNT);
        ActionToken discardYellow = new ActionTokenDiscard(DevelopmentCardType.YELLOW, DISCARD_AMOUNT);
        ActionToken discardPurple = new ActionTokenDiscard(DevelopmentCardType.PURPLE, DISCARD_AMOUNT);
        ActionToken discardGreen = new ActionTokenDiscard(DevelopmentCardType.GREEN, DISCARD_AMOUNT);

        actionTokens.add(moveShuffle);
        actionTokens.add(moveNoShuffle);
        actionTokens.add(discardBlue);
        actionTokens.add(discardPurple);
        actionTokens.add(discardGreen);
        actionTokens.add(discardYellow);
        return actionTokens;
    }
}
