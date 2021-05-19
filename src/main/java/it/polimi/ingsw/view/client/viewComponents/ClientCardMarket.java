package it.polimi.ingsw.view.client.viewComponents;

import it.polimi.ingsw.model.cards.Deck;
import it.polimi.ingsw.model.cards.DevelopmentCard;
import it.polimi.ingsw.model.cards.DevelopmentCardType;
import it.polimi.ingsw.model.cards.DevelopmentDeck;
import it.polimi.ingsw.model.exception.NonExistentCardException;

import java.util.ArrayList;
import java.util.HashMap;

public class ClientCardMarket {

    private final DevelopmentDeck[][] cardMarket;
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

    public DevelopmentCard getCard(int row, int column) {

        if(row>=nRow || column>=nCol || row < 0 || column<0 || cardMarket[row][column].getListOfCards().size()==0)
            //throw new NonExistentCardException();
            ;

        return cardMarket[row][column].getTop();
    }


    public DevelopmentDeck getStack(int i, int j){
        return cardMarket[i][j];
    }
}
