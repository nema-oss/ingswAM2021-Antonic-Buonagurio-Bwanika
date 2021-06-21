package it.polimi.ingsw.model.player;

import it.polimi.ingsw.model.gameboard.Resource;
import it.polimi.ingsw.model.gameboard.ResourceType;

import javax.naming.InsufficientResourcesException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *this class represent the strongbox in the player's game board
 *@author René
 */

public class Strongbox implements Serializable {

    private final HashMap<ResourceType,List<Resource>> strongbox;
    private List<Resource> temporaryResourceStorage;

    public Strongbox(){
        strongbox = new HashMap<>();
        temporaryResourceStorage = new ArrayList<>();
    }

    /**
     *this method adds resources to the strongbox
     *@param resources the list of resources to add
     */
    public void addResource(List<Resource> resources){

        for(Resource res: resources){
            strongbox.computeIfAbsent(res.getType(), k -> new ArrayList<>());
            strongbox.get(res.getType()).add(res);
        }
    }

    /**
     * this method adds a single resource to the strongbox
     * @param resource the resource to add
     */
    public void addResource(Resource resource) {

        strongbox.computeIfAbsent(resource.getType(), k -> new ArrayList<>());
        strongbox.get(resource.getType()).add(resource);
    }

    /**
     * this method adds 50 of each resource type to the strongbox
     */
    public void addResourceCheat() {

        for(ResourceType resourceType : ResourceType.getAllResourceType()) {
            strongbox.computeIfAbsent(resourceType, k -> new ArrayList<>());

            for (int i = 0; i < 50; i++)
                strongbox.get(resourceType).add(new Resource(resourceType));
        }
    }

    /**
        * this method add production results to the temporary storage
     */

    public void addResourceTemporary(List<Resource> resources){

        temporaryResourceStorage.addAll(resources);
    }

    /**
        *this method adds a single resource to the temporary storage
     */
    public void addResourceTemporary(Resource resource){

        temporaryResourceStorage.add(resource);
    }

    /**
        * this method add resources at the end of the production action
     */

    public void moveFromTemporary(){
        addResource(temporaryResourceStorage);
        temporaryResourceStorage = new ArrayList<>();
    }

    /**
     * this method returns a given number of resources from the deposit if available
     * @param amount the amount of resource to get
     * @param type the type of resources to get
     * @return set of resources (type:ArrayList<Resource)
     */

    public List<Resource> getResource(ResourceType type, int amount) throws InsufficientResourcesException{

        if(amount > strongbox.get(type).size()) throw new InsufficientResourcesException();
        ArrayList<Resource> result = new ArrayList<>();
        while(amount > 0){
            result.add(strongbox.get(type).get(0));
            strongbox.get(type).remove(0);
            amount--;
        }
        return result;
    }


    /**
     * @return the strongbox
     */
    public Map<ResourceType, List<Resource>> getAll() {
        return strongbox;
    }
}