package it.polimi.ingsw.player;

import it.polimi.ingsw.gameboard.Resource;
import it.polimi.ingsw.gameboard.ResourceType;

import java.util.ArrayList;
import java.util.HashMap;

/*
    *this class represent the strongbox in the player's game board
    *@author René
 */

public class Strongbox {

    private HashMap<ResourceType,ArrayList<Resource>> strongbox;

    public Strongbox(){
        strongbox = new HashMap<ResourceType,ArrayList<Resource>>();
    }

    /*
        *this method add resources to the strongbox
        *@param the list of resources to add
     */
    public void addResource(ArrayList<Resource> resources){
        for(Resource res: resources){
            strongbox.get(res.getType()).add(res);
        }
    }

    public void addResource(Resource resources) {
        strongbox.get(resources.getType()).add(resources);
    }


    /*
     * this method returns a given number of resources from the deposit if available
     * @return set of resources (type:ArrayList<Resource)
    */

    public ArrayList<Resource> getResource(ResourceType type, int amount) throws Exception{

        if(amount > strongbox.get(type).size()) throw new Exception();
        ArrayList<Resource> result = new ArrayList<Resource>(strongbox.get(type).subList(0,amount));
        while(amount > 0){
            strongbox.get(type).remove(strongbox.get(type).size()-1);
            amount--;
        }
        return result;
    }


}