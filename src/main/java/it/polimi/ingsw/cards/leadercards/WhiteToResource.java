package it.polimi.ingsw.cards.leadercards;

import it.polimi.ingsw.cards.leadercards.EffectStrategy;
import it.polimi.ingsw.gameboard.Resource;
import it.polimi.ingsw.gameboard.ResourceType;

public class WhiteToResource implements EffectStrategy {

    private ResourceType result;

    public ResourceType getResult() {
        return result;
    }

    public WhiteToResource(ResourceType result) {
        this.result = result;
    }

    public Resource useEffect() {

        //if(!toTransform.getType() == result) throw new LeaderCardException();
        return new Resource(result);
    }

}
