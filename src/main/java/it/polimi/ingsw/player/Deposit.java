package it.polimi.ingsw.player;

import it.polimi.ingsw.exception.FullDepositException;
import it.polimi.ingsw.exception.InsufficientPaymentException;
import it.polimi.ingsw.exception.WrongDepositSwapException;
import it.polimi.ingsw.gameboard.Producible;
import it.polimi.ingsw.gameboard.Resource;
import it.polimi.ingsw.gameboard.ResourceType;
import javax.naming.InsufficientResourcesException;
import java.util.*;


/*
    *this class represent the deposit on player's game board
    *@author René
 */

public class Deposit {

    private List<List<Resource>> warehouse;
    //private Map<ResourceType, ArrayList<Resource>> deposit;
    private boolean[] floors;
    private static final int NUMBER_OF_FLOORS = 3;

    public Deposit() {

        //deposit = new HashMap<ResourceType, ArrayList<Resource>>();
        warehouse = new ArrayList<List<Resource>>(3);
        warehouse.add(new ArrayList<Resource>());
        warehouse.add(new ArrayList<Resource>());
        warehouse.add(new ArrayList<Resource>());
    }

    /*
     * this method returns a given number of resources from the deposit
     * @return set of resources (type:ArrayList<Resource)
     */


    public ArrayList<Resource> getResources(ResourceType type, int amount) throws InsufficientResourcesException {

        ArrayList<Resource> result = new ArrayList<Resource>();

        for (List<Resource> resources : warehouse) {
            if (resources.get(0).getType() == type) {
                if (amount > resources.size()) throw new InsufficientResourcesException();
                while (amount > 0) {
                    result.add(resources.remove(0));
                    amount--;
                }
            }
        }


        /*

        if (amount > deposit.get(type).size()) throw new InsufficientResourcesException();
        result = new ArrayList<Resource>();
        while (amount > 0) {
            result.add(deposit.get(type).get(0));
            deposit.get(type).remove(0);
            amount--;
        }
        */
        return result;

    }


    /*
     * this method checks if the current deposit's setup is respecting the rules
     * @return true if rules are respected, else false
     */


    public boolean checkDepositRules() {

        /*
        int numberOfDifferentResources = 0;
        floors = new boolean[NUMBER_OF_FLOORS];
        Arrays.fill(floors, Boolean.FALSE);

        for (ArrayList<Resource> levels : deposit.values()) {
            if (levels.size() > 0) numberOfDifferentResources++;
            if (numberOfDifferentResources > NUMBER_OF_FLOORS) return Boolean.FALSE;
            if (levels.size() > floors.length) return Boolean.FALSE;
            if (floors[levels.size()]) return Boolean.FALSE;
            floors[levels.size()] = true;
        }

        return Boolean.TRUE;

         */

        for (int i = 0; i < warehouse.size(); i++) {
            if (warehouse.get(i).size() > i + 1) return false;
            if (warehouse.get(i).stream().map(Resource::getType).distinct().limit(2).count() > 1) return false;
        }
        return warehouse.stream().flatMap(List<Resource>::stream).map(Resource::getType).distinct().limit(4).count() <= 3;

    }

        /*
     * this method add resources to the deposit if possible, else raise an error
     *
     */

    /*public void addResources(ArrayList<Resource> resources) throws FullDepositException {


        for (Resource res : resources) {
            if (deposit.get(res.getType()) == null) {
                ArrayList<Resource> newResources = new ArrayList<Resource>();
                deposit.put(res.getType(), newResources);
            }
            deposit.get(res.getType()).add(res);
        }

        if (!checkDepositRules()) {
            for (Resource res : resources) {
                deposit.get(res.getType()).remove(res);
            }
            throw new FullDepositException();
        }
    }

     */

    /*public void addResources(Resource singleResource) throws Exception {


        deposit.get(singleResource.getType()).add(singleResource);

        if (!checkDepositRules()) {
            deposit.get(singleResource.getType()).remove(singleResource);
            throw new Exception();
        }

    }

     */

    /*
    public ArrayList<Resource> getAll() {

        ArrayList<Resource> allResources = new ArrayList<Resource>();
        for(ResourceType type: deposit.keySet()){
            allResources.addAll(deposit.get(type));
        }
        return allResources;
    }

     */

    /*
        * this method returns all the resources in the deposit
     */

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

        Collections.swap(warehouse,x,y);
        if(!checkDepositRules()){
            Collections.swap(warehouse,x,y);
            throw new WrongDepositSwapException();
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

        return warehouse.get(floor).remove(0);
    }
}

