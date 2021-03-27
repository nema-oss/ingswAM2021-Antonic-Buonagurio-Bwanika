package it.polimi.ingsw;

/*
 * this class represent the Resources
 */

public class Resource implements Producible{

    private ResourceType type;

    Resource(ResourceType type){
        this.type = type;
    }

    /*
     * this method returns the type of the Resource
     * @return resource's type (type:ResourceType)
    */

    public ResourceType getType() {
        return type;
    }


}
