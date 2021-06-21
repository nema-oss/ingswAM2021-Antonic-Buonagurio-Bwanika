package it.polimi.ingsw.view.client.viewComponents;

import it.polimi.ingsw.model.cards.Deck;
import it.polimi.ingsw.model.cards.DevelopmentCard;
import it.polimi.ingsw.model.cards.DevelopmentCardType;
import it.polimi.ingsw.model.cards.DevelopmentDeck;
import it.polimi.ingsw.model.exception.NonExistentCardException;
import it.polimi.ingsw.model.gameboard.CardMarket;
import it.polimi.ingsw.model.gameboard.MarbleMarket;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * this class represents the crad market client side
 */
public class ClientCardMarket implements Serializable {

    private DevelopmentDeck[][] cardMarket;
    private static final int CARD_MARKET_ROW = 3;
    private static final int CARD_MARKET_COLUMNS = 4;
    private final int nRow;
    private final int nCol;

    public ClientCardMarket(DevelopmentDeck deck, int nRow, int nCol){

        this.nCol = nCol;
        this.nRow = nRow;

        cardMarket = new DevelopmentDeck[nRow][nCol];
        ArrayList<DevelopmentCard> cards = deck.getListOfCards();

        HashMap<DevelopmentCardType, ArrayList<DevelopmentCard>> mapByColor = new HashMap<>();


        for(DevelopmentCard card: cards){
            if (!mapByColor.containsKey(card.getType())) {
                mapByColor.put(card.getType(), new ArrayList<>());
            }
            mapByColor.get(card.getType()).add(card);
        }

        int col =0;

        for(DevelopmentCardType color : mapByColor.keySet()){
            ArrayList<DevelopmentCard> column = mapByColor.get(color);
            column.sort((o1, o2) -> -1 * Integer.compare(o1.getLevel(), o2.getLevel()));

            int i,row;
            int length = column.size();

            for(row=0; row<nRow; row++) {
                ArrayList<DevelopmentCard> miniDeck = new ArrayList<>();

                for(i=0; i < length/nRow; i++)
                    miniDeck.add(column.remove(0));

                DevelopmentDeck cell = new DevelopmentDeck(miniDeck);
                cardMarket[row][col] = cell;
                cardMarket[row][col].shuffle();

            }

            col++;
        }
    }

    /**
     * this method returns the card in a given position
     * @param row the row index in the card market
     * @param column the column index in the card market
     * @return the development card requested
     */
    public DevelopmentCard getCard(int row, int column) {

        if(row>=nRow || column>=nCol || row < 0 || column<0 || cardMarket[row][column].getListOfCards().size()==0)
            //throw new NonExistentCardException();
            ;

        return cardMarket[row][column].getTop();
    }


    /**
     * this method returns the stack of development cards in a given position of the card market
     * @param i the row index in the card market
     * @param j the column index in the card market
     * @return the stack of development cards requested
     */
    public DevelopmentDeck getStack(int i, int j){
        return cardMarket[i][j];
    }

    /**
     * this method updates the card market
     * @param market the modified market
     */
    public void update(DevelopmentDeck[][] market) {
        cardMarket = market;
    }

}
