package it.polimi.ingsw.model.player;

import it.polimi.ingsw.model.Game;
import it.polimi.ingsw.model.cards.CardFactory;
import it.polimi.ingsw.model.cards.DevelopmentCard;
import it.polimi.ingsw.model.cards.leadercards.*;
import it.polimi.ingsw.model.exception.*;
import it.polimi.ingsw.model.gameboard.GameBoard;
import it.polimi.ingsw.model.gameboard.Resource;
import it.polimi.ingsw.model.gameboard.ResourceType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import javax.naming.InsufficientResourcesException;

import java.util.*;
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
        leaderDeck.shuffle();
        leaderCards.add(leaderDeck.drawCard());
        leaderCards.add(leaderDeck.drawCard());
        player.setHand(leaderCards);

    }

    @Test
    @DisplayName("Testing the buy a Card action")
    void buyDevelopmentCard() throws Exception, NonExistentCardException, InsufficientPaymentException, FullDepositException {

        DevelopmentCard developmentCard = gameBoard.getCardMarket().getCard(2,0);
        Strongbox strongbox = player.getStrongbox();
        List<Resource> resources = new ArrayList<>();
        Map<ResourceType, Integer> cost = developmentCard.getCost();
        for(ResourceType type: cost.keySet()){
            IntStream.range(0, cost.get(type)).mapToObj(i -> new Resource(type)).forEach(resources::add);
        }
        Deposit deposit = player.getDeposit();
        deposit.addResource(3, new Resource(ResourceType.STONE));
        deposit.addResource(3, new Resource(ResourceType.STONE));
        deposit.addResource(3, new Resource(ResourceType.STONE));
        strongbox.addResource(resources);

        player.buyDevelopmentCard(2, 0);

        List<Stack<DevelopmentCard>> cards = player.getPlayerBoard().getDevelopmentCards();
        DevelopmentCard developmentCard1 = cards.get(0).peek();
        assertEquals(developmentCard1,developmentCard);

        assertThrows(InsufficientPaymentException.class, ()-> player.buyDevelopmentCard(0,0));
    }

    @Test
    @DisplayName("Testing the buy a Resource action")
    void buyResources() throws FullDepositException, WrongDepositSwapException {

        int previousPosition = player.getPositionIndex();
        List<Resource> newResources = player.buyResources(2,2);
        player.setStandardActionPlayed(true);
        Deposit deposit = player.getDeposit();
        deposit.addResource(1, new Resource(ResourceType.SHIELD));

        deposit.swapFloors(1,2);
        player.swapDepositFloors(1,2);

        assertEquals(player.getDeposit().getAll(), deposit.getAll());
        assertTrue(player.hasPlayedStandardAction());

        System.out.println(deposit.getAll());

        player.addResourceToDeposit(3, new Resource(ResourceType.COIN));
        assertEquals(ResourceType.COIN, player.getDeposit().get(3).getType());


    }

    @Test
    @DisplayName("Testing the activate production action")
    void activateProduction() throws Exception, NonExistentCardException, InsufficientPaymentException {

        DevelopmentCard developmentCard = gameBoard.getCardMarket().getCard(1,1);
        developmentCard.getProductionResults().forEach(p -> System.out.println(p.getType()));
        Map<ResourceType, Integer> productionRequirements = developmentCard.getProductionRequirements();
        List<Resource> resources = new ArrayList<>();
        for(ResourceType resourceType: productionRequirements.keySet()){
            int amount = productionRequirements.get(resourceType);
            while(amount > 0){
                resources.add(new Resource(resourceType));
                amount--;
            }
        }

        player.getStrongbox().addResource(resources);
        int previousPosition = player.getPositionIndex();
        Map<ResourceType, List<Resource>> previousResources = player.getStrongbox().getAll();
        player.getPlayerBoard().addDevelopmentCard(developmentCard);
        player.activateProduction(0);
        for(ResourceType resourceType: productionRequirements.keySet()) {
            assertThrows(Exception.class, () -> player.getStrongbox().getResource(resourceType, productionRequirements.get(resourceType)));
        }



    }

    @Test
    @DisplayName("Testing the movement on popeRoad")
    void moveOnPopeRoad() {

        int previousPosition = player.getPositionIndex();
        player.moveOnPopeRoad();
        assertEquals(previousPosition + 1, player.getPositionIndex());
        player.moveOnPopeRoad(5);
        assertEquals( previousPosition + 6, player.getPositionIndex());
        System.out.println(player.getVictoryPoints());
    }


    @Test
    @DisplayName("Testing the discard a Leader Card action")
    void discardLeader() throws NonExistentCardException {

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
    void activateLeaderCard() throws NonExistentCardException, InsufficientResourcesException, InsufficientDevelopmentCardsException {

        CardFactory cardFactory = new CardFactory();
        ArrayList<LeaderCard> hand = new ArrayList<>();
        hand.add(cardFactory.getLeaderCards().get(3));
        player.setHand(hand);
        LeaderCard card = player.getHand().get(0);
        System.out.println(player.getHand().get(0).getLeaderType());
        System.out.println(card.getCostResource().keySet());
        System.out.println("level == " + card.getCostDevelopment().keySet());
        System.out.println("cards == "+ card.getCostDevelopment().values());
        assertThrows(InsufficientDevelopmentCardsException.class, () -> player.activateLeaderCard(0));

        hand = new ArrayList<>();
        hand.add(cardFactory.getLeaderCards().get(7));
        player.setHand(hand);
        card = player.getHand().get(0);
        System.out.println(player.getHand().get(0).getLeaderType());
        System.out.println(card.getCostResource().keySet());
        System.out.println("type == " + card.getCostResource().keySet());
        System.out.println("amount == "+ card.getCostResource().values());
        assertThrows(InsufficientResourcesException.class, () -> player.activateLeaderCard(0));
    }

    @Test
    void test() throws NonExistentCardException {

        DevelopmentCard developmentCard = gameBoard.getCardMarket().getCard(1,1);
        player.getPlayerBoard().addDevelopmentCard(developmentCard);
        System.out.println(player.getPlayerBoard().getDevelopmentCards().get(1).size());
    }

    @Test
    void setNickname(){
        player.setNickname("prova");
        assertEquals(player.getNickname(), "prova");
    }

    @Test
    void moveOnPopeRoadDiscard(){
        player.moveOnPopeRoadDiscard(3);
        assertEquals(player.getPositionIndex(), 3);
    }

    @Test
    void setLeaderActionPlayed(){
        player.setLeaderActionPlayed(false);
        assertEquals(false, player.hasPlayedLeaderAction());
        player.setLeaderActionPlayed(true);
        assertEquals(player.hasPlayedLeaderAction(), false);
        player.setLeaderActionPlayed(true);
        assertEquals(player.hasPlayedLeaderAction(), true);

    }

    @Test
    void takeResourceForAction() throws InsufficientDevelopmentCardsException, NonExistentCardException, Exception, FullDepositException {
        game = new Game();
        gameBoard = new GameBoard();
        player = new Player("PlayerTest", gameBoard, game);
        CardFactory cardFactory = new CardFactory();
        leaderDeck = new LeaderDeck(cardFactory.getLeaderCards());
        List<LeaderCard> leaderCards = new ArrayList<>();
        leaderCards.add(leaderDeck.getListOfCards().get(5));
        leaderCards.add(leaderDeck.getListOfCards().get(6));
        player.setHand(leaderCards);
        for(int i=0; i<5; i++)
            player.getStrongbox().addResource(new Resource(ResourceType.STONE));
        for(int i=0; i<5; i++)
            player.getStrongbox().addResource(new Resource(ResourceType.COIN));
        for(int i=0; i<5; i++)
            player.getStrongbox().addResource(new Resource(ResourceType.SERVANT));
        for(int i=0; i<5; i++)
            player.getStrongbox().addResource(new Resource(ResourceType.SHIELD));
        player.activateLeaderCard(0);
        player.activateLeaderCard(1);
        System.out.println("Extra Deposits:");
        for(AuxiliaryDeposit auxiliaryDeposit: player.getActiveEffects().getAuxiliaryDeposits()){
            for(int i=0; i<2; i++)
                auxiliaryDeposit.addResource(new Resource(auxiliaryDeposit.getType()));
                System.out.println(auxiliaryDeposit.getSize() + " " + auxiliaryDeposit.getType());
        }
        System.out.println("Strongbox");
        Map<ResourceType, List<Resource>> strongbox = player.getStrongbox().getAll();
        for(ResourceType resourceType: strongbox.keySet()){
            System.out.println(strongbox.get(resourceType).size() +" "+resourceType.toString());
        }
        player.getDeposit().addResource(2, new Resource(ResourceType.SHIELD));
        System.out.println("Deposit");
        Map<ResourceType, List<Resource>> deposit = player.getDeposit().getAll();
        for(ResourceType resourceType: deposit.keySet()){
            System.out.println(deposit.get(resourceType).size() +" "+resourceType.toString());
        }
        HashMap<ResourceType, Integer> cost = new HashMap<>();
        cost.put(ResourceType.SHIELD, 4);
        cost.put(ResourceType.SERVANT, 4);
        cost.put(ResourceType.COIN, 4);
        player.takeResourceForAction(cost);
        System.out.println("\nExtra Deposits:");
        for(AuxiliaryDeposit auxiliaryDeposit: player.getActiveEffects().getAuxiliaryDeposits()){
            System.out.println(auxiliaryDeposit.getSize() + " " + auxiliaryDeposit.getType());
        }
        System.out.println("Strongbox");
        strongbox = player.getStrongbox().getAll();
        for(ResourceType resourceType: strongbox.keySet()){
            System.out.println(strongbox.get(resourceType).size() +" "+resourceType.toString());
        }
        System.out.println("Deposit");
        deposit = player.getDeposit().getAll();
        for(ResourceType resourceType: deposit.keySet()){
            System.out.println(deposit.get(resourceType).size() +" "+resourceType.toString());
        }

    }
}