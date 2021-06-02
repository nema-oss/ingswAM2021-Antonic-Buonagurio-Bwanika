package it.polimi.ingsw.model.gameboard;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import it.polimi.ingsw.model.cards.DevelopmentCard;
import it.polimi.ingsw.model.cards.DevelopmentCardType;
import it.polimi.ingsw.model.cards.DevelopmentDeck;
import it.polimi.ingsw.model.exception.NonExistentCardException;


/*
* this class represents the Card Market containing Development Cards ordered by color and level
* @author Chiara Buonagurio
*/

public class CardMarket implements Serializable {

    private final DevelopmentDeck[][] cardMarket;
    private final int nRow;
    private final int nCol;

    /*
     * this method is the class constructor
     * @param deck : the set od developmentCards to put in the cardMarket
     * @param nRow, nCol : number of rows and columns of the cardMarket
     */

    public CardMarket(DevelopmentDeck deck, int nRow, int nCol){

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


    /*
     *this method returns the developmentCard chosen by the Player and removes it from the cardMarket
     * @param row, column : indexes of the cardMarket's cell which the player wants to  buy from
     * @return development card (type: DevelopmentCard)
     */
    public DevelopmentCard buyCard(int row, int column) throws NonExistentCardException{
        
        if(row>=nRow || column>=nCol || row < 0 || column<0 || cardMarket[row][column].getListOfCards().size()==0)
            throw new NonExistentCardException();

       else return cardMarket[row][column].drawCard();

        
    }

    /**
     * this method returns the developmentCard chosen by the player without removing it from the Market
     * @param row,column : indexes of the selected card in the cardMarket
     * @return development card (type: DevelopmentCard)
     */

    public DevelopmentCard getCard(int row, int column) throws NonExistentCardException{

        if(row>=nRow || column>=nCol || row < 0 || column<0 || cardMarket[row][column].getListOfCards().size()==0)
            throw new NonExistentCardException();

        else return cardMarket[row][column].getTop();
    }

    /*
     * this method returns the developmentDeck at a given position in the cardMarket
     * @param row,column : indexes of the selected deck in the cardMarket
     * @return development cards deck (type: DevelopmentDeck)
     */
    public DevelopmentDeck getMiniDeck(int row, int column) {return cardMarket[row][column];}

    /*
        *this method discard a given number of cards from the market
        * @param number of cards to discard, type of cards
     */
    public boolean discardCard(DevelopmentCardType type, int amount){

        try {
            for (int i = nRow - 1; i >= 0 && amount > 0; i--) {
                for (int j = nCol - 1; j >= 0 && amount > 0; j--) {
                    if (!cardMarket[i][j].getListOfCards().isEmpty()){
                        if (cardMarket[i][j].getTop().getType() == type) {
                            while (amount > 0 && !cardMarket[i][j].getListOfCards().isEmpty()) {
                                cardMarket[i][j].drawCard();
                                amount--;
                            }
                        }
                    }
                }
            }
            int i = 0;
            while(i < nRow){
                if(!cardMarket[i][0].getListOfCards().isEmpty())
                    return true;
                i++;
            }
        }catch (NonExistentCardException e){e.printStackTrace();}

        return false;
    }

    public DevelopmentDeck[][] getCardMarket(){
        return cardMarket;
    }
}


