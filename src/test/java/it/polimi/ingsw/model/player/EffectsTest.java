package it.polimi.ingsw.model.player;

import it.polimi.ingsw.model.Game;
import it.polimi.ingsw.model.cards.CardFactory;
import it.polimi.ingsw.model.cards.DevelopmentCard;
import it.polimi.ingsw.model.cards.leadercards.AuxiliaryDeposit;
import it.polimi.ingsw.model.cards.leadercards.LeaderCard;
import it.polimi.ingsw.model.cards.leadercards.WhiteToResource;
import it.polimi.ingsw.model.exception.FullDepositException;
import it.polimi.ingsw.model.exception.InsufficientPaymentException;
import it.polimi.ingsw.model.exception.NonExistentCardException;
import it.polimi.ingsw.model.exception.ProductionRequirementsException;
import it.polimi.ingsw.model.gameboard.GameBoard;
import it.polimi.ingsw.model.gameboard.Producible;
import it.polimi.ingsw.model.gameboard.Resource;
import it.polimi.ingsw.model.gameboard.ResourceType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.*;
import java.util.stream.IntStream;

import static org.junit.jupiter.api.Assertions.*;

class EffectsTest {

    private Effects effects;
    private Player player;
    private GameBoard gameBoard;
    private Game game;

    @BeforeEach
    void setUp(){

        gameBoard = new GameBoard();
        game = new Game();
        player = new Player("Testing", gameBoard, game);
        effects = player.getActiveEffects();


    }


    @Test
    @DisplayName("Testing the white to resource effect on resource buying")
    void activateWhiteToResource() throws FullDepositException {

        effects.activateWhiteToResource(ResourceType.COIN);
        List<Resource> newResources = player.buyResources(1,2);

    }

    @Test
    @DisplayName("Testing the discount effect on card buying")

    void discountEffect() throws NonExistentCardException, InsufficientPaymentException, Exception {

        DevelopmentCard developmentCard = gameBoard.getCardMarket().getCard(2,0);
        Map<ResourceType, Integer> cost = developmentCard.getCost();
        List<Resource> resources = new ArrayList<>();
        for(ResourceType type: cost.keySet()){
            IntStream.range(0, cost.get(type)).mapToObj(i -> new Resource(type)).forEach(resources::add);
        }
        Strongbox strongbox = player.getStrongbox();
        strongbox.addResource(resources);
        System.out.println(strongbox.getAll().keySet());
        for(ResourceType type: cost.keySet()){
            effects.activateDiscount(type, 1);
            break;
        }

        System.out.println(developmentCard.getCost());
        player.buyDevelopmentCard(2, 0);
        System.out.println(developmentCard.getCost());
        System.out.println(strongbox.getAll().keySet());
        List<Stack<DevelopmentCard>> cards = player.getPlayerBoard().getDevelopmentCards();
        DevelopmentCard developmentCard1 = cards.get(0).peek();
        assertEquals(developmentCard1,developmentCard);
        assertThrows(InsufficientPaymentException.class, ()-> player.buyDevelopmentCard(2,1));
    }

    @Test
    @DisplayName("Testing the extra production effect")
    void isExtraProduction(){

        Map<ResourceType,Integer> requirements = new HashMap<>();
        requirements.put(ResourceType.COIN, 1);
        List<Producible> result = new ArrayList<>();
        result.add(new Resource(ResourceType.STONE));
        player.getActiveEffects().activateExtraProduction(requirements,result);

        player.getStrongbox().addResource(new Resource(ResourceType.COIN));

        try {
            player.activateProductionLeader(0);
        } catch (InsufficientPaymentException e) {
            e.printStackTrace();
        }
        player.getStrongbox().moveFromTemporary();

        System.out.println(player.getStrongbox().getAll());
    }

    @Test
    @DisplayName("Testing the extra deposit effect")
    void useExtraDepositEffect() throws FullDepositException, Exception {


        List<Resource> newResources = new ArrayList<>();
        newResources.add(new Resource(ResourceType.STONE));
        newResources.add(new Resource(ResourceType.COIN));
        effects.activateExtraDeposit(newResources.get(0).getType());
        Resource firstToAdd = newResources.get(0);
        Resource secondToAdd = newResources.get(1);

        player.addResourceToExtraDeposit(newResources, 0);
        AuxiliaryDeposit auxiliaryDeposit = effects.getAuxiliaryDeposit(0);

        assertEquals(1 ,auxiliaryDeposit.getSize());

        assertEquals(auxiliaryDeposit.getResources(1).get(0), firstToAdd);

        List<Resource> resources = newResources.subList(0,0);
        assertEquals(resources, auxiliaryDeposit.getAuxiliaryDeposit());

        assertEquals(1, player.getActiveEffects().getAuxiliaryDeposits().size());
        assertEquals(auxiliaryDeposit, player.getActiveEffects().getAuxiliaryDeposit(0));

    }

    @Test
    @DisplayName("testing usage of extra production")
    void useExtraProduction() throws InsufficientPaymentException {
        CardFactory cardFactory = new CardFactory();
        ArrayList<LeaderCard> card = new ArrayList<>();
        card.add(cardFactory.getLeaderCards().get(14));

        player.getActiveLeaderCards().add(card.get(0));
        player.getStrongbox().addResourceCheat();

        Map<ResourceType,Integer> productionRequirements = new HashMap<>();
        List<Producible> productionResult = new ArrayList<>();

        productionRequirements.put(ResourceType.SHIELD, 1);
        productionResult.add(new Resource(ResourceType.STONE));
        effects.activateExtraProduction(productionRequirements, productionResult);
        effects.useExtraProductionEffect(player, 0);
        player.getStrongbox().moveFromTemporary();
        for(ResourceType resourceType: player.getStrongbox().getAll().keySet()){
            if(resourceType.equals(ResourceType.STONE)){
                assertEquals(52, player.getStrongbox().getAll().get(resourceType).size()+1);
            }
        }
    }
}