package it.polimi.ingsw.model.gameboard;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import it.polimi.ingsw.model.cards.*;
import static org.junit.jupiter.api.Assertions.*;
import it.polimi.ingsw.model.exception.NonExistentCardException;


class CardMarketTest {

    private CardMarket cardMarket;

    @BeforeEach
    void setUp(){
        CardFactory cardFactory = new CardFactory();
        ArrayList<DevelopmentCard> cards = cardFactory.getDevelopmentCards();
        DevelopmentDeck developmentDeck = new DevelopmentDeck(cards);
        cardMarket = new CardMarket(developmentDeck,3,4);
    }
    @Test
    @DisplayName("construction of the  cardMarket through cardFactory")
    void testCardMarket(){


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
                }catch(NonExistentCardException e){e.printStackTrace();}
            }

        //controls that all the cards in a certain row are of the correct level
        for(int i=0; i<3; i++)
            for(int j=0; j<4; j++) {
                    try {
                        assertEquals(3 - i, cardMarket.getCard(i, j).getLevel());
                    }
                    catch(NonExistentCardException e){e.printStackTrace();}
            }

        //controls that all the cards in a certain column are of the same color.
        for (int i=1; i<3; i++)
            for(int j=0; j<4; j++) {
                    try{assertEquals(cardMarket.getCard(i-1,j).getType(), cardMarket.getCard(i,j).getType());}
                    catch(NonExistentCardException e){e.printStackTrace();}
            }

    }

    @Test
    @DisplayName("tests purchase of a card at a given index")
    void buyCardTest(){

        CardFactory cardFactory = new CardFactory();
        ArrayList<DevelopmentCard> cards = cardFactory.getDevelopmentCards();
        DevelopmentDeck developmentDeck = new DevelopmentDeck(cards);
        cardMarket = new CardMarket(developmentDeck,3,4);

        for(int i=0; i<3; i++)
            for(int j=0; j<4; j++){
                DevelopmentDeck miniDeck = cardMarket.getMiniDeck(i,j);
                try{assertEquals(miniDeck.getTop(), cardMarket.buyCard(i,j));}
                catch(NonExistentCardException e){e.printStackTrace();}
            }

        int size = cardMarket.getMiniDeck(2,3).getListOfCards().size();
            for(int i=0; i<size; i++) {
                try {
                    DevelopmentCard bought = cardMarket.buyCard(2, 3);
                } catch(NonExistentCardException e){e.printStackTrace();}
            }

            assertEquals(0, cardMarket.getMiniDeck(2,3).getListOfCards().size());

            try{cardMarket.buyCard(2,3);}
            catch(NonExistentCardException e){e.printStackTrace(); System.out.println("errore!");}

    }


    @Test
    @DisplayName("tests return of miniDeck at a given index")
    public void getMiniDeckTest(){

        CardFactory cardFactory1 = new CardFactory();
        ArrayList<DevelopmentCard> cards1 = cardFactory1.getDevelopmentCards();
        CardFactory cardFactory2 = new CardFactory();
        ArrayList<DevelopmentCard> cards2 = cardFactory2.getDevelopmentCards();

        DevelopmentDeck developmentDeck = new DevelopmentDeck(cards1);
        cardMarket = new CardMarket(developmentDeck,3,4);

        for(int j=0; j<4; j++){
            try {
                DevelopmentCardType color = cardMarket.getCard(0, j).getType();

                ArrayList<DevelopmentCard> oneColor = new ArrayList<>();
                ArrayList<DevelopmentCard> oneType =  new ArrayList<>();

                for(DevelopmentCard card : cards2)
                    if(card.getType().equals(color))
                        oneColor.add(card);


                for(int i=0; i<3; i++){
                    for(DevelopmentCard c : oneColor) {
                        if (c.getLevel() == 3 - i)
                            oneType.add(c);
                    }

                    assertEquals(cardMarket.getMiniDeck(i, j).getListOfCards().size(), oneType.size());
                    for(DevelopmentCard x : oneType){
                        assertEquals(x.getType(),cardMarket.getMiniDeck(i,j).getTop().getType());
                        assertEquals(x.getLevel(),cardMarket.getMiniDeck(i,j).getTop().getLevel());
                    }
                    oneType.clear();
                }

            }catch(NonExistentCardException e){e.printStackTrace();}
        }
    }

    @Test
    @DisplayName("Testing discard card market")
    void discardCard(){

        int previousSize = cardMarket.getMiniDeck(2,3).getListOfCards().size();
        int previousSizeUpper = cardMarket.getMiniDeck(1,3).getListOfCards().size();
        DevelopmentCardType developmentCardType = cardMarket.getMiniDeck(2,3).getListOfCards().get(0).getType();
        cardMarket.discardCard(developmentCardType, previousSize+1);
        int actualSize = cardMarket.getMiniDeck(2,3).getListOfCards().size();
        assertEquals(1, actualSize + 1);
        int actualSizeUpper = cardMarket.getMiniDeck(1,3).getListOfCards().size();
        assertEquals(actualSizeUpper, previousSizeUpper - 1);

    }

    @Test
    void getCardTest(){
        try {
            cardMarket.getCard(5,5);
        } catch (NonExistentCardException ignored) {
        }
    }
}
