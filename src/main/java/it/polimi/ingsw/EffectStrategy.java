package it.polimi.ingsw;

import java.util.Optional;

public interface EffectStrategy <T>{
    T useEffect() throws LeaderCardException;
}
