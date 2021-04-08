package it.polimi.ingsw.gameboard;

/*
 * this class represent the Resources
 */

import it.polimi.ingsw.player.Player;

public class Resource implements Producible{

    private ResourceType type;

    public Resource(ResourceType type){
        this.type = type;
    }

    /*
     * this method returns the type of the Resource
     * @return resource's type (type:ResourceType)
    */

    public ResourceType getType() {
        return type;
    }

    public void useEffect(Player p){}


}
