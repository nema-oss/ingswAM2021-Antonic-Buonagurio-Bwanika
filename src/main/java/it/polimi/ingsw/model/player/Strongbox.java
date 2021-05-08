package it.polimi.ingsw.model.player;

import it.polimi.ingsw.model.gameboard.Resource;
import it.polimi.ingsw.model.gameboard.ResourceType;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/*
 *this class represent the strongbox in the player's game board
 *@author René
 */

public class Strongbox {

    private HashMap<ResourceType,List<Resource>> strongbox;
    private List<Resource> temporaryResourceStorage;

    public Strongbox(){
        strongbox = new HashMap<ResourceType,List<Resource>>();
        temporaryResourceStorage = new ArrayList<>();
    }

    /*
     *this method add resources to the strongbox
     *@param the list of resources to add
     */
    public void addResource(List<Resource> resources){

        for(Resource res: resources){
            strongbox.computeIfAbsent(res.getType(), k -> new ArrayList<Resource>());
            strongbox.get(res.getType()).add(res);
        }
    }

    public void addResource(Resource resources) {

        strongbox.computeIfAbsent(resources.getType(), k -> new ArrayList<Resource>());
        strongbox.get(resources.getType()).add(resources);
    }

    /*
        * this method add production results to the temporary storage
     */

    public void addResourceTemporary(List<Resource> resources){

        temporaryResourceStorage.addAll(resources);
    }

    /*
        *this method adds a single resource to the temporary storage
     */
    public void addResourceTemporary(Resource resource){

        temporaryResourceStorage.add(resource);
    }

    /*
        * this method add resources at the end of the production action
     */

    public void moveFromTemporary(){
        addResource(temporaryResourceStorage);
        temporaryResourceStorage = new ArrayList<>();
    }

    /*
     * this method returns a given number of resources from the deposit if available
     * @return set of resources (type:ArrayList<Resource)
     */

    public List<Resource> getResource(ResourceType type, int amount) throws Exception{

        if(amount > strongbox.get(type).size()) throw new Exception();
        ArrayList<Resource> result = new ArrayList<Resource>();
        while(amount > 0){
            result.add(strongbox.get(type).get(0));
            strongbox.get(type).remove(0);
            amount--;
        }
        return result;
    }


    public Map<ResourceType, List<Resource>> getAll() {

        return strongbox;
    }
}