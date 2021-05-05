package it.polimi.ingsw.model.cards;

import it.polimi.ingsw.model.ActionToken;

import java.util.Collections;
import java.util.List;

public class ActionTokenDeck implements Deck{

    private List<ActionToken> listOfActionToken;

    public ActionTokenDeck(List<ActionToken> tokenList){
        this.listOfActionToken = tokenList;
    }
    public ActionToken drawCard(){
        return listOfActionToken.remove(0);
    }

    public void shuffle(){
        Collections.shuffle(listOfActionToken);
    }

    public List<ActionToken> getListOfActionToken() {
        return listOfActionToken;
    }

    public ActionToken getTop(){
        return listOfActionToken.get(0);
    }
}
