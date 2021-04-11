package it.polimi.ingsw.CardTest;

import it.polimi.ingsw.cards.CardFactory;
import it.polimi.ingsw.cards.DevelopmentCard;
import it.polimi.ingsw.cards.DevelopmentCardType;
import it.polimi.ingsw.cards.leadercards.LeaderCard;
import it.polimi.ingsw.cards.leadercards.LeaderCardType;
import it.polimi.ingsw.gameboard.FaithPoint;
import it.polimi.ingsw.gameboard.FaithType;
import it.polimi.ingsw.gameboard.Producible;
import it.polimi.ingsw.gameboard.ResourceType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;


import static org.junit.jupiter.api.Assertions.assertEquals;

public class CardFactoryTest {

    private CardFactory cardFactory;
    private ArrayList<DevelopmentCard> developmentCards;
    private ArrayList<LeaderCard> leaderCards;

    @BeforeEach
    void setUp(){
        cardFactory = new CardFactory();
        developmentCards = cardFactory.getDevelopmentCards();
        leaderCards = cardFactory.getLeaderCards();
    }

    @Test
    void getDevelopmentCardsTest(){
        assertEquals(48, developmentCards.size());

        assertEquals(DevelopmentCardType.YELLOW, developmentCards.get(3).getType());
        assertEquals(1, developmentCards.get(3).getLevel());

        Map<ResourceType,Integer> requirements = new HashMap<>();
        requirements.put(ResourceType.SERVANT,1);

        Map<ResourceType,Integer> cost = new HashMap<>();
        cost.put(ResourceType.STONE,2);

        assertEquals(requirements, developmentCards.get(3).getProductionRequirements());
        assertEquals(FaithType.FAITH, developmentCards.get(3).getProductionResults().get(0).getType());
        assertEquals(cost, developmentCards.get(3).getCost());

    }

    @Test
    void getLeaderCardsTest(){
        assertEquals(16, leaderCards.size());
        System.out.println(leaderCards);
        for(LeaderCard c : leaderCards)
            System.out.println(c.getLeaderType()+", ");
        assertEquals(LeaderCardType.DISCOUNT, leaderCards.get(3).getLeaderType());
        assertEquals(3, leaderCards.get(4).getVictoryPoints());
    }

}
