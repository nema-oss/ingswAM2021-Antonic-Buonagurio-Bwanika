package it.polimi.ingsw;

import java.util.ArrayList;
import java.util.HashMap;


/*
    *this class represent the deposit on player's game board
    *@author René
 */

public class Deposit {

    private HashMap<ResourceType,ArrayList<Resource>> deposit;
    private boolean[] floors;

    Deposit(){

        deposit = new HashMap<ResourceType,ArrayList<Resource>>();
    }

    /*
     * this method returns a given number of resources from the deposit
     * @return set of resources (type:ArrayList<Resource)
    */

    public ArrayList<Resource> getResources(ResourceType type, int amount) throws Exception{

        ArrayList<Resource> result = null;

        if(amount > deposit.get(type).size()) throw new Exception();
        result = new ArrayList<Resource>(deposit.get(type).subList(0,amount-1));
        while(amount > 0){
            deposit.get(type).remove(deposit.get(type).size()-1);
            amount--;
        }

        return result;

    }


    /*
     * this method checks if the current deposit's setup is respecting the rules
     * @return true if rules are respected, else false
     */


    public boolean checkDepositRules(){

        int numberOfDifferentResources = 0;
        floors = new boolean[]{false,false,false};

        for(ArrayList<Resource> levels: deposit.values()){
            if(levels.size() > 0) numberOfDifferentResources++;
            if(numberOfDifferentResources > 3) return Boolean.FALSE;
            if(levels.size() > floors.length) return Boolean.FALSE;
            if(floors[levels.size()]) return Boolean.FALSE;
            floors[levels.size()] = true;
        }

        return Boolean.TRUE;
    }


    /*
     * this method add resources to the deposit if possible, else raise an error
     *
    */

    public void addResources(ArrayList<Resource> resources) throws Exception{


        for(Resource res: resources){
            if(deposit.get(res.getType()) == null) {
                ArrayList<Resource> newResources = new ArrayList<Resource>();
                deposit.put(res.getType(),newResources);
            }
            deposit.get(res.getType()).add(res);
        }

        if(!checkDepositRules()){
            for(Resource res: resources){
                deposit.get(res.getType()).remove(res);
            }
            throw new Exception();
        }


    }



}
