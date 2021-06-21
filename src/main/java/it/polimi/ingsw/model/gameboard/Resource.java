package it.polimi.ingsw.model.gameboard;

import it.polimi.ingsw.model.player.PopeRoad;

import java.io.Serializable;

/**
 * this class represents the Resources
 */
public class Resource implements Producible, Serializable {

    private final ResourceType type;
    private final String className;

    public Resource(ResourceType type){
        this.className = getClass().getName();
        this.type = type;
    }

    /**
     * this method returns the type of the Resource
     * @return resource's type (type:ResourceType)
    */

    @Override
    public ResourceType getType() {
        return type ;
    }

    @Override
    public String getClassName() {
        return className;
    }

    public boolean useEffect(PopeRoad popeRoad){
        return false;
    }

}
