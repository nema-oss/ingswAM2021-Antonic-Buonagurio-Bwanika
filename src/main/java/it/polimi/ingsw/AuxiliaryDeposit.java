package it.polimi.ingsw;

import java.util.ArrayList;

public class AuxiliaryDeposit {

    private ArrayList<Resource> auxiliaryDeposit;
    private ResourceType type;

    AuxiliaryDeposit(ResourceType type){

        auxiliaryDeposit = new ArrayList<>();
        this.type = type;

    }

    public void addResource(Resource res) throws Exception {
        if(auxiliaryDeposit.size() >= 2) throw new Exception();
        auxiliaryDeposit.add(res);
    }

    public ArrayList<Resource> getResources(int amount) throws Exception{

        ArrayList<Resource> result = new ArrayList<Resource>();

        if(amount > auxiliaryDeposit.size()) throw new Exception();
        while(amount > 0){
            result.add(auxiliaryDeposit.remove(0));
            amount--;
        }
        return result;
    }

}
