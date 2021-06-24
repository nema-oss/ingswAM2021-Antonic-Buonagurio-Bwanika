package it.polimi.ingsw.model.CardTest;

import it.polimi.ingsw.model.cards.CardFactory;
import it.polimi.ingsw.model.cards.DevelopmentCardType;
import it.polimi.ingsw.model.cards.leadercards.*;
import it.polimi.ingsw.model.gameboard.Producible;
import it.polimi.ingsw.model.gameboard.ResourceType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

public class LeaderCardsTest {

    private CardFactory cardFactory;
    private List<LeaderCard> leaderCards;

    @BeforeEach
    void setUp(){
        cardFactory = new CardFactory();
        leaderCards = cardFactory.getLeaderCards();
    }

    @Test
    void leaderTest(){

        int i=1;
        for(LeaderCard card : leaderCards){

            assertEquals("l"+i , card.getId());
            i++;
        }
        assertEquals(2,leaderCards.get(3).getVictoryPoints());

        Map<DevelopmentCardType, Integer> cardMap1= new HashMap<>();
        cardMap1.put(DevelopmentCardType.YELLOW,1);
        cardMap1.put(DevelopmentCardType.PURPLE,1);

        Map<Integer, Map<DevelopmentCardType,Integer>> map = new HashMap<>();
        map.put(0, cardMap1);
        assertEquals(map, leaderCards.get(3).getCostDevelopment());

        Map<ResourceType, Integer> resourceCost = new HashMap<>();
        resourceCost.put(ResourceType.COIN,5);
        assertEquals(resourceCost, leaderCards.get(4).getCostResource());
    }

    @Test
    void discountTest(){

        Discount leaderCard = (Discount) leaderCards.get(0);
        Discount card = new Discount("l1", leaderCard.getCostResource(), leaderCard.getCostDevelopment(), leaderCard.getVictoryPoints(), leaderCard.getDiscountType(), leaderCard.getDiscountAmount(), leaderCard.getLeaderType());

        assertEquals(LeaderCardType.DISCOUNT, leaderCard.getLeaderType());

        assertEquals(ResourceType.SERVANT, leaderCard.getDiscountType());
        assertEquals(1, leaderCard.getDiscountAmount());

    }

    @Test
    void extraDepositTest(){

        ExtraDeposit leaderCard = (ExtraDeposit) leaderCards.get(4);
        ExtraDeposit card = new ExtraDeposit(leaderCard.getId(), leaderCard.getCostResource(), leaderCard.getCostDevelopment(), leaderCard.getVictoryPoints(), leaderCard.getStorageType(), leaderCard.getLeaderType());

        assertEquals(LeaderCardType.EXTRA_DEPOSIT, card.getLeaderType());

        assertEquals(ResourceType.STONE, card.getStorageType());

    }

    @Test
    void extraProductionTest(){

        ExtraProduction leaderCard = (ExtraProduction) leaderCards.get(12);
        ExtraProduction card = new ExtraProduction(leaderCard.getId(), leaderCard.getCostResource(), leaderCard.getCostDevelopment(), leaderCard.getVictoryPoints(),new ArrayList<Producible>(leaderCard.getProductionResult()), leaderCard.getProductionRequirement(), leaderCard.getLeaderType());

        assertEquals(LeaderCardType.EXTRA_PRODUCTION, card.getLeaderType());

        assertTrue(card.getProductionRequirement().containsKey(ResourceType.SHIELD));
        assertEquals(1, (int) card.getProductionRequirement().get(ResourceType.SHIELD));

        assertNull(card.getProductionResult().get(0).getType());
        assertEquals( "it.polimi.ingsw.model.gameboard.FaithPoint", card.getProductionResult().get(1).getClassName());
    }

    @Test
    void whiteToResourceTest(){

        WhiteToResource leaderCard = (WhiteToResource) leaderCards.get(8);
        WhiteToResource card = new WhiteToResource(leaderCard.getId(), leaderCard.getCostResource(), leaderCard.getCostDevelopment(), leaderCard.getVictoryPoints(), leaderCard.getResult(), leaderCard.getLeaderType());

        assertEquals(LeaderCardType.WHITE_TO_RESOURCE, card.getLeaderType());
        assertEquals(ResourceType.SERVANT, card.getResult());


    }

    @Test
    void leaderDeckTest(){
        LeaderDeck deck = new LeaderDeck(leaderCards);
        assertEquals(leaderCards.get(0), deck.getTop());
        assertEquals(leaderCards, deck.getListOfCards());
    }
}
