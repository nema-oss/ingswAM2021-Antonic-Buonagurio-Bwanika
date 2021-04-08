package it.polimi.ingsw.player;

import it.polimi.ingsw.exception.FullDepositException;
import it.polimi.ingsw.exception.InsufficientPaymentException;
import it.polimi.ingsw.gameboard.Producible;
import it.polimi.ingsw.gameboard.Resource;
import it.polimi.ingsw.gameboard.ResourceType;

import javax.naming.InsufficientResourcesException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;


/*
    *this class represent the deposit on player's game board
    *@author René
 */

public class Deposit {

    private Map<ResourceType, ArrayList<Resource>> deposit;
    private boolean[] floors;
    private static final int NUMBER_OF_FLOORS = 3;

    public Deposit() {

        deposit = new HashMap<ResourceType, ArrayList<Resource>>();
    }

    /*
     * this method returns a given number of resources from the deposit
     * @return set of resources (type:ArrayList<Resource)
     */

    public ArrayList<Resource> getResources(ResourceType type, int amount) throws InsufficientResourcesException {

        ArrayList<Resource> result;

        if (amount > deposit.get(type).size()) throw new InsufficientResourcesException();
        result = new ArrayList<Resource>();
        while (amount > 0) {
            result.add(deposit.get(type).get(0));
            deposit.get(type).remove(0);
            amount--;
        }

        return result;

    }


    /*
     * this method checks if the current deposit's setup is respecting the rules
     * @return true if rules are respected, else false
     */


    public boolean checkDepositRules() {

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
    }


    /*
     * this method add resources to the deposit if possible, else raise an error
     *
     */

    public void addResources(ArrayList<Resource> resources) throws FullDepositException {


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

    public void addResources(Resource singleResource) throws Exception {


        deposit.get(singleResource.getType()).add(singleResource);

        if (!checkDepositRules()) {
            deposit.get(singleResource.getType()).remove(singleResource);
            throw new Exception();
        }

    }

    /*
    public ArrayList<Resource> getAll() {

        ArrayList<Resource> allResources = new ArrayList<Resource>();
        for(ResourceType type: deposit.keySet()){
            allResources.addAll(deposit.get(type));
        }
        return allResources;
    }

     */

    public Map<ResourceType,ArrayList<Resource>> getAll(){
        return deposit;
    }
}

