package it.polimi.ingsw.model.player;

import it.polimi.ingsw.model.Game;
import it.polimi.ingsw.model.cards.CardFactory;
import it.polimi.ingsw.model.cards.DevelopmentCard;
import it.polimi.ingsw.model.cards.DevelopmentDeck;
import it.polimi.ingsw.model.exception.NonExistentCardException;
import it.polimi.ingsw.model.gameboard.GameBoard;
import it.polimi.ingsw.model.gameboard.Resource;
import it.polimi.ingsw.model.gameboard.ResourceType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class BoardTest {

    private Player player;
    private Game game;
    private GameBoard gameBoard;
    private DevelopmentDeck developmentDeck;
    private Board board;

    @BeforeEach
    void setUp(){
        game = new Game();
        gameBoard = new GameBoard();
        player = new Player("Testing",gameBoard, game);
        board = player.getPlayerBoard();
        CardFactory cardFactory = new CardFactory();
        developmentDeck = new DevelopmentDeck(cardFactory.getDevelopmentCards());

    }


    @Test
    void useProductionPower() throws Exception {

        List<Resource> toGive = new ArrayList<>();
        Strongbox strongbox = player.getStrongbox();
        toGive.add(new Resource(ResourceType.COIN));
        toGive.add(new Resource(ResourceType.SERVANT));
        board.useProductionPower(toGive, ResourceType.STONE);
        Map<ResourceType,List<Resource>> availableResources = strongbox.getAll();
//        assertEquals(1, availableResources.get(ResourceType.STONE).size());

    }

    @Test
    void addDevelopmentCard() throws NonExistentCardException {
        DevelopmentCard developmentCard = developmentDeck.drawCard();
        board.addDevelopmentCard(developmentCard, 1);
        assertEquals(developmentCard, board.getDevelopmentCard(1));

        CardFactory cardFactory = new CardFactory();
        DevelopmentCard secondCard = cardFactory.getDevelopmentCards().get(17);
        board.addDevelopmentCard(secondCard, 1);
        assertEquals(secondCard, board.getDevelopmentCard(1));

        DevelopmentCard illegalCard = developmentDeck.drawCard();
        assertThrows(IllegalArgumentException.class,()-> board.addDevelopmentCard(illegalCard, 1));

    }
}