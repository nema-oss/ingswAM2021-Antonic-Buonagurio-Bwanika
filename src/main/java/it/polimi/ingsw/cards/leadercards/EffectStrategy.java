package it.polimi.ingsw.cards.leadercards;

import it.polimi.ingsw.exception.LeaderCardException;

public interface EffectStrategy <T>{
    T useEffect() throws LeaderCardException;
}
