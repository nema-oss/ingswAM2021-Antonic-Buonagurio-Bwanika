package it.polimi.ingsw.player;

import it.polimi.ingsw.Game;
import it.polimi.ingsw.cards.Card;
import it.polimi.ingsw.cards.CardFactory;
import it.polimi.ingsw.cards.DevelopmentCard;
import it.polimi.ingsw.cards.DevelopmentCardType;
import it.polimi.ingsw.cards.leadercards.*;
import it.polimi.ingsw.exception.*;
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
import java.util.Stack;
import java.util.stream.IntStream;

import static org.junit.jupiter.api.Assertions.*;

class PlayerTest {

    private Player player;
    private GameBoard gameBoard;
    private Game game;
    private static final String nickname = "testing";
    private LeaderDeck leaderDeck;


    @BeforeEach
    void setUp(){

        game = new Game();
        gameBoard = new GameBoard();
        player = new Player("PlayerTest", gameBoard, game);
        CardFactory cardFactory = new CardFactory();
        leaderDeck = new LeaderDeck(cardFactory.getLeaderCards());
        List<LeaderCard> leaderCards = new ArrayList<>();
        leaderCards.add(leaderDeck.drawCard());
        leaderCards.add(leaderDeck.drawCard());
        player.setHand(leaderCards);

    }

    @Test
    @DisplayName("Testing the buy a Card action")
    void buyDevelopmentCard() throws Exception, NonExistentCardException, InsufficientPaymentException {

        DevelopmentCard developmentCard = gameBoard.getCardMarket().getCard(1,1);
        System.out.println(developmentCard.getCost());
        Strongbox strongbox = player.getStrongbox();
        List<Resource> resources = new ArrayList<>();
        Map<ResourceType, Integer> cost = developmentCard.getCost();
        for(ResourceType type: cost.keySet()){
            IntStream.range(0, cost.get(type)).mapToObj(i -> new Resource(type)).forEach(resources::add);
        }
        Deposit deposit = player.getDeposit();
        deposit.addResource(1, new Resource(ResourceType.COIN));
        deposit.addResource(2, new Resource(ResourceType.STONE));
        deposit.addResource(3, new Resource(ResourceType.SERVANT));
        strongbox.addResource(resources);
        player.buyDevelopmentCard(1, 1);
        System.out.println(strongbox.getAll().keySet());
        System.out.println(deposit.getAll().keySet());
        List<Stack<DevelopmentCard>> cards = player.getPlayerBoard().getDevelopmentCards();
        DevelopmentCard developmentCard1 = cards.get(0).peek();
        assertEquals(developmentCard1,developmentCard);
        assertThrows(InsufficientPaymentException.class, ()-> player.buyDevelopmentCard(1,2));
    }

    @Test
    @DisplayName("Testing the buy a Resource action")
    void buyResources() throws FullDepositException, WrongDepositSwapException {

        int previousPosition = player.getPositionIndex();
        List<Resource> newResources = player.buyResources(2,2);
        Deposit deposit = player.getDeposit();
        deposit.addResource(1, newResources.get(0));
        deposit.addResource(2, newResources.get(1));
        deposit.swapFloors(1,2);


    }

    @Test
    @DisplayName("Testing the activate production action")
    void activateProduction() throws FullDepositException, Exception, NonExistentCardException {

        DevelopmentCard developmentCard = gameBoard.getCardMarket().getCard(1,1);
        developmentCard.getProductionResults().forEach(p -> System.out.println(p.getType()));
        List<Resource> resources = new ArrayList<>();
        player.getStrongbox().addResource(resources);
        int previousPosition = player.getPositionIndex();

        Map<ResourceType, List<Resource>> previousResources = player.getStrongbox().getAll();
        player.getPlayerBoard().addDevelopmentCard(developmentCard);
        player.activateProduction(0);
        assertEquals(9, player.getStrongbox().getResource(ResourceType.SHIELD, 3).size());
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
        System.out.println(player.getVictoryPoints());
    }


    @Test
    @DisplayName("Testing the discard a Leader Card action")
    void discardLeader() {

        int previousPosition = player.getPositionIndex();
        player.discardLeader(0);
        assertEquals(previousPosition + 1, player.getPositionIndex());
        assertEquals(player.getHand().size(), 1);
        player.discardLeader(0);
        assertEquals(previousPosition + 2, player.getPositionIndex());
        assertEquals(player.getHand().size(), 0);

    }

    @Test
    @DisplayName("Testing the activate a Leader Card action")
    void activateLeaderCard() throws NonExistentCardException {

        LeaderCardType leaderCardType = player.getHand().get(0).getLeaderType();
        player.activateLeaderCard(0);
        Effects effects = player.getActiveEffects();
        switch (leaderCardType){
            case DISCOUNT:
                assertTrue(effects.isDiscount());
                break;
            case EXTRA_DEPOSIT:
                assertTrue(effects.isExtraDeposit());
                break;
            case WHITE_TO_RESOURCE:
                assertTrue(effects.isWhiteToResource());
                break;
            case EXTRA_PRODUCTION:
                assertTrue(effects.isExtraProduction());
                break;
        }
    }
}