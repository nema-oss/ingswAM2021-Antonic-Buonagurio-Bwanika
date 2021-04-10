package it.polimi.ingsw.player;

import it.polimi.ingsw.Game;
import it.polimi.ingsw.cards.Card;
import it.polimi.ingsw.cards.DevelopmentCard;
import it.polimi.ingsw.cards.DevelopmentCardType;
import it.polimi.ingsw.cards.leadercards.ExtraDeposit;
import it.polimi.ingsw.cards.leadercards.LeaderCard;
import it.polimi.ingsw.cards.leadercards.WhiteToResource;
import it.polimi.ingsw.exception.FullDepositException;
import it.polimi.ingsw.exception.InsufficientPaymentException;
import it.polimi.ingsw.exception.NonExistentCardException;
import it.polimi.ingsw.exception.ProductionRequirementsException;
import it.polimi.ingsw.gameboard.CardMarket;
import it.polimi.ingsw.gameboard.GameBoard;
import it.polimi.ingsw.gameboard.Resource;
import it.polimi.ingsw.gameboard.ResourceType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import javax.naming.InsufficientResourcesException;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class PlayerTest {

    private Player player;
    private GameBoard gameBoard;
    private Game game;
    private static final String nickname = "testing";



    @BeforeEach
    void setUp(){
        game = new Game();
        gameBoard = new GameBoard();
        player = new Player("PlayerTest", gameBoard, game);
        Deposit deposit = player.getDeposit();
        deposit.addResource(1, new Resource(ResourceType.COIN));
        deposit.addResource(2, new Resource(ResourceType.SHIELD));
        deposit.addResource(2, new Resource(ResourceType.SHIELD));
        List<LeaderCard> leaderCards = new ArrayList<>();
        //leaderCards.add(new WhiteToResource());
        //leaderCards.add(new ExtraDeposit());
        player.setHand(leaderCards);

    }

    @Test
    @DisplayName("Testing the buy a Card action")
    void buyDevelopmentCard() throws InsufficientResourcesException, NonExistentCardException, InsufficientPaymentException {

        DevelopmentCard developmentCard = gameBoard.getCardMarket().getCard(1,2);
        player.buyDevelopmentCard(1, 2);
        assertEquals(player.getPlayerBoard().getDevelopmentCard(0),developmentCard);
        assertThrows(InsufficientPaymentException.class, ()-> player.buyDevelopmentCard(1,2));
    }

    @Test
    @DisplayName("Testing the buy a Resource action")
    void buyResources() throws FullDepositException {

        player.buyResources(1,2);

    }

    @Test
    @DisplayName("Testing the activate production action")
    void activateProduction() throws FullDepositException, Exception {

        List<Resource> resources = new ArrayList<Resource>();
        resources.add(new Resource(ResourceType.SHIELD));
        resources.add(new Resource(ResourceType.SHIELD));
        player.getStrongbox().addResource(resources);
        int previousPosition = player.getPositionIndex();

        Map<ResourceType, Integer> previousResources;
        //player.getPlayerBoard().addDevelopmentCard(new DevelopmentCard(2, DevelopmentCardType.PURPLE,)); // produce 1 stone, 1 shield 1 faith
        player.activateProduction(0);
        assertEquals(3, player.getStrongbox().getResource(ResourceType.SHIELD, 3).size());
        assertEquals(1, player.getStrongbox().getResource(ResourceType.STONE, 3).size());
        assertEquals(previousPosition + 1, player.getPositionIndex());
        assertThrows(Exception.class, ()-> player.getStrongbox().getResource(ResourceType.COIN, 2));
        assertThrows(Exception.class, ()-> player.getStrongbox().getResource(ResourceType.SHIELD, 6));


    }

    @Test
    @DisplayName("Testing the movement on popeRoad")
    void moveOnPopeRoad() {

        int previousPosition = player.getPositionIndex();
        player.moveOnPopeRoad();
        assertEquals(previousPosition + 1, player.getPositionIndex());
    }


    @Test
    @DisplayName("Testing the discard a Leader Card action")
    void discardLeader() {

        int previousPosition = player.getPositionIndex();
        player.discardLeader(0);
        assertEquals(previousPosition + 1, player.getPositionIndex());
        assertEquals(player.getHand().size(), 1);
        player.discardLeader(0);
        assertEquals(previousPosition + 1, player.getPositionIndex());
        assertEquals(player.getHand().size(), 0);

    }

    @Test
    @DisplayName("Testing the activate a Leader Card action")
    void activateLeaderCard() throws NonExistentCardException {

        player.activateLeaderCard(0);
        Effects effects = player.getActiveEffects();
        assertTrue(effects.isWhiteToResource());
    }
}