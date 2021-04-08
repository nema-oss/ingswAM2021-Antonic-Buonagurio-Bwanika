package it.polimi.ingsw.cards;

import it.polimi.ingsw.ActionToken;

import java.util.ArrayList;
import java.util.Collections;

public class ActionTokenDeck implements Deck{

    private ArrayList<ActionToken> listOfActionToken;

    public ActionTokenDeck(ArrayList<ActionToken> tokenList){
        this.listOfActionToken = tokenList;
    }
    public ActionToken drawCard(){
        return listOfActionToken.remove(0);
    }

    public void shuffle(){
        Collections.shuffle(listOfActionToken);
    }

    public ArrayList<ActionToken> getListOfActionToken() {
        return listOfActionToken;
    }

    public ActionToken getTop(){
        return listOfActionToken.get(0);
    }
}
