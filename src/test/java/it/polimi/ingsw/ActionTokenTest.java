package it.polimi.ingsw;

import it.polimi.ingsw.model.ActionToken;
import it.polimi.ingsw.model.ActionTokenDiscard;
import it.polimi.ingsw.model.ActionTokenFactory;
import it.polimi.ingsw.model.ActionTokenMove;
import it.polimi.ingsw.model.cards.ActionTokenDeck;
import it.polimi.ingsw.model.cards.DevelopmentCardType;
import it.polimi.ingsw.model.gameboard.CardMarket;
import it.polimi.ingsw.model.player.Cell;
import it.polimi.ingsw.model.player.CellFactory;
import it.polimi.ingsw.model.player.PopeRoad;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

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



    }

}