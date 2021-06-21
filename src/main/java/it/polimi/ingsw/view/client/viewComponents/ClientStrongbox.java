package it.polimi.ingsw.view.client.viewComponents;

import it.polimi.ingsw.model.gameboard.Resource;
import it.polimi.ingsw.model.gameboard.ResourceType;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * this class represents the strongbox client side
 */
public class ClientStrongbox implements Serializable {

    private Map<ResourceType, List<Resource>> strongbox;
    private List<Resource> temporaryResourceStorage;

    public ClientStrongbox(){
        strongbox = new HashMap<>();
        temporaryResourceStorage = new ArrayList<>();
    }

    /**
     *this method add resources to the strongbox
     *@param resources the list of resources to add
     */
    public void addResource(List<Resource> resources){

        for(Resource res: resources){
            strongbox.computeIfAbsent(res.getType(), k -> new ArrayList<>());
            strongbox.get(res.getType()).add(res);
        }
    }

    /**
     * this method adds a resource to the strongbox
     * @param resource the resource to add
     */
    public void addResource(Resource resource) {

        strongbox.computeIfAbsent(resource.getType(), k -> new ArrayList<>());
        strongbox.get(resource.getType()).add(resource);
    }


    /**
     * @return the strongbox
     */
    public Map<ResourceType, List<Resource>> getAll() {
        return strongbox;
    }

    /**
     * this method updates the strongbox
     * @param updatedStrongbox the current strongbox
     */
    public void update(Map<ResourceType, List<Resource>> updatedStrongbox) {

        strongbox = updatedStrongbox;
    }
}
