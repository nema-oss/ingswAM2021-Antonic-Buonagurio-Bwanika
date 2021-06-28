package it.polimi.ingsw.model.CardTest;

import it.polimi.ingsw.model.cards.CardFactory;
import it.polimi.ingsw.model.cards.DevelopmentCard;
import it.polimi.ingsw.model.cards.DevelopmentCardType;
import it.polimi.ingsw.model.cards.leadercards.LeaderCard;
import it.polimi.ingsw.model.cards.leadercards.LeaderCardType;
import it.polimi.ingsw.model.gameboard.FaithType;
import it.polimi.ingsw.model.gameboard.ResourceType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


import static org.junit.jupiter.api.Assertions.assertEquals;

public class CardFactoryTest {

    private CardFactory cardFactory;
    private List<DevelopmentCard> developmentCards;
    private List<LeaderCard> leaderCards;

    @BeforeEach
    void setUp(){
        cardFactory = new CardFactory();
        developmentCards = cardFactory.getDevelopmentCards();
        leaderCards = cardFactory.getLeaderCards();
    }

    @Test
    @DisplayName("testing creation of development cards")
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
    @DisplayName("testing creation of leader cards")
    void getLeaderCardsTest(){
        assertEquals(16, leaderCards.size());
        System.out.println(leaderCards);
        for(LeaderCard c : leaderCards)
            System.out.println(c.getLeaderType()+", ");
        assertEquals(LeaderCardType.DISCOUNT, leaderCards.get(3).getLeaderType());
        assertEquals(3, leaderCards.get(4).getVictoryPoints());
    }

}
