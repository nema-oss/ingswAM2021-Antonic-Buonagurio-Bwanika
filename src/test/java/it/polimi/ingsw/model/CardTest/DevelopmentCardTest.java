package it.polimi.ingsw.model.CardTest;

import it.polimi.ingsw.model.cards.CardFactory;
import it.polimi.ingsw.model.cards.DevelopmentCard;
import it.polimi.ingsw.model.gameboard.Resource;
import it.polimi.ingsw.model.gameboard.ResourceType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class DevelopmentCardTest {

    private CardFactory cardFactory;
    private List<DevelopmentCard> developmentCards;

    @BeforeEach
    void setUp() {
        cardFactory = new CardFactory();
        developmentCards = cardFactory.getDevelopmentCards();
    }

    @Test
    void developmentTest() throws Exception {

        assertEquals("d6", developmentCards.get(5).getId());
        assertEquals(2, developmentCards.get(6).getVictoryPoints());

        DevelopmentCard card = developmentCards.get(0);
        DevelopmentCard developmentCard = new DevelopmentCard(card.getId(), card.getLevel(), card.getType(), card.getCost(), card.getProductionRequirements(), new ArrayList<>(card.getProductionResults()), card.getVictoryPoints());

        List<Resource> payment = new ArrayList<>();
        for(ResourceType resourceType : card.getProductionRequirements().keySet())
            for (int amount =0; amount<card.getProductionRequirements().get(resourceType); amount++)
                payment.add(new Resource(resourceType));

        assertEquals(card.getProductionResults(), developmentCard.activateProduction(payment));
    }

}
