package it.polimi.ingsw;

import it.polimi.ingsw.cards.ActionTokenDeck;
import it.polimi.ingsw.cards.Card;
import it.polimi.ingsw.cards.DevelopmentCardType;
import it.polimi.ingsw.gameboard.CardMarket;
import it.polimi.ingsw.player.Cell;
import it.polimi.ingsw.player.CellFactory;
import it.polimi.ingsw.player.PopeRoad;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class ActionTokenTest {

    private ActionTokenFactory actionTokenFactory;
    private List<ActionToken> tokens;
    private  ActionTokenDeck actionTokenDeck;
    private PopeRoad popeRoad;
    private CardMarket cardMarket;

    @Test
    @BeforeEach
    void setUp(){

        actionTokenFactory = new ActionTokenFactory();
        tokens = actionTokenFactory.getTokens();
        actionTokenDeck = new ActionTokenDeck(tokens);
        CellFactory cellFactory = new CellFactory();
        List<Cell> cells = new ArrayList<>(Arrays.asList(cellFactory.getCells()));
        popeRoad = new PopeRoad(cells);

    }

    @Test
    void useEffect(){

        ActionTokenDiscard actionTokenDiscard = new ActionTokenDiscard(DevelopmentCardType.BLUE, 2);
        ActionTokenMove actionTokenMove = new ActionTokenMove(2, false);
        ActionTokenMove actionTokenMoveShuffle = new ActionTokenMove(1, true);


    }

}