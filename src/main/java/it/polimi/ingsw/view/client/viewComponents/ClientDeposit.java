package it.polimi.ingsw.view.client.viewComponents;

import it.polimi.ingsw.model.cards.DevelopmentCard;
import it.polimi.ingsw.model.exception.FullDepositException;
import it.polimi.ingsw.model.exception.WrongDepositSwapException;
import it.polimi.ingsw.model.gameboard.Resource;
import it.polimi.ingsw.model.gameboard.ResourceType;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

/**
 * this class represents the player's deposit client side
 */
public class ClientDeposit implements Serializable {

    private List<List<Resource>> warehouse;
    private static final int NUMBER_OF_FLOORS = 3;

    public ClientDeposit() {

        warehouse = new ArrayList<List<Resource>>(NUMBER_OF_FLOORS);
        warehouse.add(new ArrayList<Resource>());
        warehouse.add(new ArrayList<Resource>());
        warehouse.add(new ArrayList<Resource>());
    }

    /**
     * this method returns a resource based on a floor
     * @param floor the floor to get the resource from
     * @return the resource in the given floor
     */

    public Resource get(int floor) {

        floor--;
        return warehouse.get(floor).get(0);
    }

    /**
     * this method returns the number of resources on a given floor
     * @param floor the floor
     * @return the number of resources on it
     */
    public int getNumberOfResourcesOnFloor(int floor){
        floor--;
        return warehouse.get(floor).size();
    }

    /**
     *this method adds a given resource to a given floor
     * @param floor the floor where to put the resource
     * @param resource the resource to add
     */
    public void addResource(int floor, Resource resource){
            floor--;
            warehouse.get(floor).add(resource);
    }

    /**
     * this method swaps two floor if possible
     * @param x the first floor to swap
     * @param y the second floor to swap
     */
    public void swapFloors(int x, int y){
        if((1 <= x && x <= 3) && (1 <= y && y <= 3)) {
            Collections.swap(warehouse, x - 1, y - 1);

        }
    }

    /**
     * this method removes the resources from the deposit given a development card's requirements
     * @param developmentCard the card
     */
    public void removeResourcesFromDeposit(DevelopmentCard developmentCard) {

        Map<ResourceType, Integer> cost = developmentCard.getCost();

        for (ResourceType resourceType : cost.keySet()) {
            for (List<Resource> resources : warehouse) {
                if (resources.size() > 0) {
                    if (resources.get(0).getType() == resourceType) {
                        int amount = cost.get(resourceType);
                        while (amount > 0) {
                            resources.remove(0);
                            amount--;
                        }
                    }
                }
            }
        }

    }

    /**
     * this method updates the warehouse
     * @param updatedWarehouse the modified warehouse
     */
    public void update(List<List<Resource>> updatedWarehouse){
        warehouse = updatedWarehouse;
    }
}
