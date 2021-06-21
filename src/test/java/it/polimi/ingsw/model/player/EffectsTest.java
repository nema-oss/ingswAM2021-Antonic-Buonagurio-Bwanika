package it.polimi.ingsw.model.player;

import it.polimi.ingsw.model.Game;
import it.polimi.ingsw.model.cards.CardFactory;
import it.polimi.ingsw.model.cards.DevelopmentCard;
import it.polimi.ingsw.model.cards.leadercards.AuxiliaryDeposit;
import it.polimi.ingsw.model.cards.leadercards.LeaderCard;
import it.polimi.ingsw.model.exception.FullDepositException;
import it.polimi.ingsw.model.exception.InsufficientPaymentException;
import it.polimi.ingsw.model.exception.NonExistentCardException;
import it.polimi.ingsw.model.gameboard.GameBoard;
import it.polimi.ingsw.model.gameboard.Resource;
import it.polimi.ingsw.model.gameboard.ResourceType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Stack;
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
    void isExtraProduction() {

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

    }
}