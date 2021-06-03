package it.polimi.ingsw.view.client.viewComponents;

import it.polimi.ingsw.model.gameboard.Resource;
import it.polimi.ingsw.model.gameboard.ResourceType;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ClientStrongbox implements Serializable {

    private Map<ResourceType, List<Resource>> strongbox;
    private List<Resource> temporaryResourceStorage;

    public ClientStrongbox(){
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


    public Map<ResourceType, List<Resource>> getAll() {

        return strongbox;
    }

    public void update(Map<ResourceType, List<Resource>> updatedStrongbox) {

        strongbox = updatedStrongbox;
    }
}
