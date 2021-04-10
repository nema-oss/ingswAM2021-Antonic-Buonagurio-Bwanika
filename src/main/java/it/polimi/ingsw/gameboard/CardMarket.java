package it.polimi.ingsw.gameboard;

import java.util.ArrayList;
import java.util.HashMap;
import it.polimi.ingsw.exception.NonexistentCardException;
import it.polimi.ingsw.cards.DevelopmentCard;
import it.polimi.ingsw.cards.DevelopmentCardType;
import it.polimi.ingsw.cards.DevelopmentDeck;


/*
* this class represents the Card Market belonging to the GameBoard and containing Development Cards.
*/

public class CardMarket {

    private final DevelopmentDeck[][] cardMarket;
    private final int nRow;
    private final int nCol;

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
     *this method returns the developmentCard chosen by the Player
     *@return development card (type: DevelopmentCard)
     */
    public DevelopmentCard buyCard(int row, int column) throws NonexistentCardException{
        
        if(row>=nRow || column>=nCol || row < 0 || column<0 || cardMarket[row][column].getListOfCards().size()==0)
            throw new NonexistentCardException();

       else return cardMarket[row][column].drawCard();

        
    }

    public DevelopmentCard getCard(int row, int column) throws NonexistentCardException{

        if(row>=nRow || column>=nCol || row < 0 || column<0 || cardMarket[row][column].getListOfCards().size()==0)
            throw new NonexistentCardException();

        else return cardMarket[row][column].getTop();
    }

    public DevelopmentDeck getMiniDeck(int row, int column) {return cardMarket[row][column];}
}


