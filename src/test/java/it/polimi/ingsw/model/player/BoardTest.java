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
import java.util.stream.Stream;

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

        CardFactory cardFactory = new CardFactory();
        Stream<DevelopmentCard> level1cards = cardFactory.getDevelopmentCards().stream().filter(DevelopmentCard -> DevelopmentCard.getLevel()==1);

        DevelopmentCard developmentCard = level1cards.findFirst().get();
        board.addDevelopmentCard(developmentCard, 0);
        assertEquals(developmentCard, board.getDevelopmentCard(0));

        for (DevelopmentCard c : cardFactory.getDevelopmentCards()) {
            if (c.getType().equals(developmentCard.getType()) && c.getLevel() == developmentCard.getLevel() + 1) {
                board.addDevelopmentCard(c);
                break;
            }
        }

        DevelopmentCard illegalCard = developmentDeck.drawCard();
        assertThrows(IllegalArgumentException.class,()-> board.addDevelopmentCard(illegalCard, 0));

    }
}