package it.polimi.ingsw.model;

import it.polimi.ingsw.model.ActionToken;
import it.polimi.ingsw.model.ActionTokenFactory;
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

import static org.junit.jupiter.api.Assertions.*;

class ActionTokenTest {

    private ActionTokenFactory actionTokenFactory;
    private List<ActionToken> tokens;
    private  ActionTokenDeck actionTokenDeck;
    private PopeRoad popeRoad;
    private CardMarket cardMarket;

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

    @Test
    void tokenDeckTest(){

        assertEquals(tokens.get(0), actionTokenDeck.getTop());
        assertEquals(tokens.get(0), actionTokenDeck.drawCard());

        actionTokenDeck = new ActionTokenDeck(tokens);

        actionTokenDeck.shuffle();
        assertNotEquals(new ActionTokenDeck(tokens), actionTokenDeck);
    }

    @Test
    void getAmount(){
        ActionTokenDiscard actionTokenDiscard = new ActionTokenDiscard(DevelopmentCardType.BLUE, 2);
        assertEquals(2, actionTokenDiscard.getAmount());

    }

    @Test
    void specificTokenTest(){
        for(ActionToken token : tokens){
            if(token.getId().equals("7")){
                assertTrue(((ActionTokenMove)token).isShuffle());
                assertEquals(1, ((ActionTokenMove)token).getSteps());
            }
            else if(token.getId().equals("5")){
                assertFalse(((ActionTokenMove) token).isShuffle());
                assertEquals(2, ((ActionTokenMove)token).getSteps());
            }
            else if(token.getId().equals("1")){
                assertEquals(DevelopmentCardType.BLUE, ((ActionTokenDiscard)token).getType());
            }
        }
    }
}