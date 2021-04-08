package it.polimi.ingsw.GameBoardTest;

import it.polimi.ingsw.cards.*;

import static org.junit.jupiter.api.Assertions.*;

import it.polimi.ingsw.exception.NonexistentCardException;
import it.polimi.ingsw.gameboard.CardMarket;
import org.junit.Test;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;

import java.util.ArrayList;
import java.util.Collections;
import java.util.stream.Stream;


public class CardMarketTest {

    private CardMarket cardMarket;

    @BeforeEach
    void testCardMarket(){

        CardFactory cardFactory = new CardFactory();

        ArrayList<DevelopmentCard> cards = cardFactory.getDevelopmentCards();

        DevelopmentDeck developmentDeck = new DevelopmentDeck(cards);

        cardMarket = new CardMarket(developmentDeck,4,3);

        //controls that every miniDeck of the cardMarket contains cards of  the same color and level
        for (int i=0; i<3; i++)
            for(int j=0; j<4; j++){
                DevelopmentDeck miniDeck = cardMarket.getMiniDeck(i,j);
                try {
                    DevelopmentCardType color = cardMarket.buyCard(i, j).getType();
                    int level = cardMarket.buyCard(i, j).getLevel();
                    for (DevelopmentCard card : miniDeck.getListOfCards()) {
                        assertEquals(color, card.getType());
                        assertEquals(level, card.getLevel());
                    }
                }catch(NonexistentCardException e){e.printStackTrace();}
            }

        //controls that all the cards in a certain row are of the correct level
        for(int i=0; i<3; i++)
            for(int j=0; j<4; j++){
                try{assertEquals(i+1, cardMarket.buyCard(i,j).getLevel() );}
                catch(NonexistentCardException e){e.printStackTrace();}
            }

        //controls that all the cards in a certain column are of the same color.
        for (int i=1; i<3; i++)
            for(int j=0; j<4; j++){
                try{assertEquals(cardMarket.buyCard(i-1,j).getType(), cardMarket.buyCard(i,j).getType());}
                catch(NonexistentCardException e){e.printStackTrace();}
            }

    }

    @Test
    @DisplayName("tests purchase of a card at a given index")
    public void buyCardTest(){

        for(int i=0; i<3; i++)
            for(int j=0; j<4; j++){
                DevelopmentDeck miniDeck = cardMarket.getMiniDeck(i,j);
                try{assertEquals(miniDeck.drawCard(), cardMarket.buyCard(i,j));}
                catch(NonexistentCardException e){e.printStackTrace();}
            }
    }


    @Test
    @DisplayName("tests return of miniDeck at a given index")
    public void getMiniDeckTest(){

        CardFactory cardFactory = new CardFactory();
        ArrayList<DevelopmentCard> cards = cardFactory.getDevelopmentCards();

        for(int j=0; j<4; j++){
            try {
                DevelopmentCardType color = cardMarket.buyCard(0, j).getType();

                ArrayList<DevelopmentCard> oneColor = new ArrayList<>();
                ArrayList<DevelopmentCard> oneType =  new ArrayList<>();

                for(DevelopmentCard card : cards)
                    if(card.getType().equals(color))
                        oneColor.add(card);

                for(int i=0; i<3; i++){
                    for(DevelopmentCard card : oneColor)
                        if(card.getLevel()==i+1)
                            oneType.add(card);
                    assertTrue(oneType.size()==cardMarket.getMiniDeck(i,j).getListOfCards().size() && oneType.containsAll(cardMarket.getMiniDeck(i,j).getListOfCards()));
                }

            }catch(NonexistentCardException e){e.printStackTrace();}
        }
    }
}
