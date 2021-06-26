package it.polimi.ingsw.model.cards;

import it.polimi.ingsw.model.ActionToken;

import java.util.Collections;
import java.util.List;

/**
 * this class represents the Deck of action tokens
 */
public class ActionTokenDeck implements Deck<ActionToken>{

    private final List<ActionToken> listOfActionToken;

    public ActionTokenDeck(List<ActionToken> tokenList){
        this.listOfActionToken = tokenList;
    }

    @Override
    public ActionToken drawCard(){
        ActionToken actionToken = listOfActionToken.remove(0);
        listOfActionToken.add(actionToken);
        return actionToken;
    }

    @Override
    public void shuffle(){
        Collections.shuffle(listOfActionToken);
    }

    @Override
    public ActionToken getTop(){
        return listOfActionToken.get(0);
    }
}
