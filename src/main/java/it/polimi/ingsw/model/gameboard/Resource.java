package it.polimi.ingsw.model.gameboard;

/*
 * this class represents the Resources
 */

import it.polimi.ingsw.model.player.PopeRoad;

public class Resource implements Producible{

    private final ResourceType type;
    private final String className;

    public Resource(ResourceType type){
        this.className = getClass().getName();
        this.type = type;
    }

    /*
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
