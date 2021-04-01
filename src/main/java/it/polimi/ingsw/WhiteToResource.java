package it.polimi.ingsw;

import java.util.ArrayList;

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
