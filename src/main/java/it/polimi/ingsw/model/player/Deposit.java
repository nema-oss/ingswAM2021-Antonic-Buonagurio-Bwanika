package it.polimi.ingsw.model.player;

import it.polimi.ingsw.model.exception.FullDepositException;
import it.polimi.ingsw.model.exception.WrongDepositSwapException;
import it.polimi.ingsw.model.gameboard.Resource;
import it.polimi.ingsw.model.gameboard.ResourceType;
import javax.naming.InsufficientResourcesException;
import java.util.*;


/*
    *this class represent the deposit on player's game board
    *@author René
 */

public class Deposit {

    private List<List<Resource>> warehouse;
    private static final int NUMBER_OF_FLOORS = 3;

    public Deposit() {

        warehouse = new ArrayList<List<Resource>>(NUMBER_OF_FLOORS);
        warehouse.add(new ArrayList<Resource>());
        warehouse.add(new ArrayList<Resource>());
        warehouse.add(new ArrayList<Resource>());
    }

    /*
     * this method returns a given number of resources from the deposit
     * @return set of resources (type:ArrayList<Resource)
     */


    public List<Resource> getResources(ResourceType type, int amount) throws InsufficientResourcesException {

        ArrayList<Resource> result = new ArrayList<Resource>();
        Map<ResourceType, List<Resource>> availableResources = getAll();
        if(!availableResources.containsKey(type) || availableResources.get(type).size() < amount) throw new InsufficientResourcesException();

        for (List<Resource> resources : warehouse) {
            if(resources.size() > 0) {
                if (resources.get(0).getType() == type){
                    while (amount > 0) {
                        result.add(resources.remove(0));
                        amount--;
                    }
                }
            }
        }

        return result;

    }


    /*
     * this method checks if the current deposit's setup is respecting the rules
     * @return true if rules are respected, else false
     */


    public boolean checkDepositRules() {

        for (int i = 0; i < warehouse.size(); i++) {
            if (warehouse.get(i).size() > i + 1) return false;
            if (warehouse.get(i).stream().map(Resource::getType).distinct().limit(2).count() > 1) return false;
        }
        return warehouse.stream().flatMap(List<Resource>::stream).map(Resource::getType).distinct().limit(4).count() <= 3;

    }

    public Map<ResourceType,List<Resource>> getAll(){

        Map<ResourceType,List<Resource>> result = new HashMap<ResourceType,List<Resource>>();
        for(List<Resource> floor: warehouse){
            if(floor.size() > 0)
                result.put(floor.get(0).getType(), new ArrayList<>(floor));
        }
        return result;
    }

    /*
        * this method swaps two floor if possible
        * @param the two floors to swap
        * @exception the movement is against the rules
     */

    public void swapFloors(int x, int y) throws WrongDepositSwapException {

        if((1 <= x && x <= 3) && (1 <= y && y <= 3)) {
            Collections.swap(warehouse, x - 1, y - 1);
            if (!checkDepositRules()) {
                Collections.swap(warehouse, x, y);
                throw new WrongDepositSwapException();
            }
        }
        
    }

    /*
        *this method adds a given resource to a given floor
        * @param the floor and the resource
        * @exception the deposit is full or the floor is not present
     */

    public void addResource(int floor, Resource resource){

        try {
            floor--; // workaround to fix
            if (floor > warehouse.size()) throw new FullDepositException();
            warehouse.get(floor).add(resource);
            if (!checkDepositRules()) {
                warehouse.get(floor).remove(resource);
                throw new FullDepositException();
            }
        }catch (FullDepositException e) {e.printStackTrace();}
    }

    /*
        * this method return a resource based on a floor
        * @param the floor
        * @return the resource in the given floor
     */

    public Resource get(int floor) {

        floor--;
        return warehouse.get(floor).get(0);
    }

    public int getNumberOfResourcesOnFloor(int floor){
        floor--;
        return warehouse.get(floor).size();
    }
}

