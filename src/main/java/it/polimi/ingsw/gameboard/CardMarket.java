package it.polimi.ingsw.gameboard;

import it.polimi.ingsw.cards.DevelopmentCard;
import it.polimi.ingsw.cards.DevelopmentCardType;
import it.polimi.ingsw.cards.DevelopmentDeck;
import it.polimi.ingsw.exception.InsufficientPaymentException;
import it.polimi.ingsw.exception.NonexistentCardException;

import java.util.*;

/*
* this class represents the Card Market belonging to the GameBoard and containing Development Cards.
*/

public class CardMarket {

    private DevelopmentDeck[][] cardMarket;
    private int nRow, nCol;

    public CardMarket(DevelopmentDeck deck, int nCol, int nRow){

        this.nCol = nCol;
        this.nRow = nRow;

        ArrayList<DevelopmentCard> cards = deck.getListOfCards();
        HashMap<DevelopmentCardType, ArrayList<DevelopmentCard>> mapByColor = new HashMap<>();


        for(DevelopmentCard card: cards){
            if (!mapByColor.containsKey(card.getType())) {
                mapByColor.put(card.getType(), new ArrayList<>());
            }
            mapByColor.get(card.getType()).add(card);
        }


        int col =0;

        for(DevelopmentCardType color: mapByColor.keySet()){
            ArrayList<DevelopmentCard> column = mapByColor.get(color);
            column.sort((o1, o2) -> -1 * Integer.compare(o1.getLevel(), o2.getLevel()));

            int i,row;
            ArrayList<DevelopmentCard> miniDeck = new ArrayList<>();
            for(row=0; row<nRow; row++) {

                for (i = 0; i < column.size() - 1 && column.get(i).getLevel() == column.get(i + 1).getLevel(); i++)
                    miniDeck.add(column.get(i));
                if (i < column.size())
                    miniDeck.add(column.get(i));

                cardMarket[row][col] = new DevelopmentDeck(miniDeck);
                cardMarket[row][col].shuffle();
            }

            col++;
        }
    }


    /*
     *this method returns the developmentCard chosen by the Player
     *@return development card (type: DevelopmentCard)
     */
    public DevelopmentCard buyCard(int row, int column, Map<ResourceType,Integer> payment) throws NonexistentCardException, InsufficientPaymentException {
        DevelopmentCard developmentCard;

        if(row>=nRow || column>=nCol || row < 0 || column<0)
            throw new NonexistentCardException();

        developmentCard = cardMarket[row][column].drawCard();

        if(!(developmentCard.getCost().equals(payment)))
            throw new InsufficientPaymentException();

        return developmentCard;
    }
}
