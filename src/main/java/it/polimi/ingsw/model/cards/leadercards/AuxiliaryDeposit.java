package it.polimi.ingsw.model.cards.leadercards;

import it.polimi.ingsw.model.gameboard.Resource;
import it.polimi.ingsw.model.gameboard.ResourceType;

import java.util.ArrayList;
import java.util.List;

public class AuxiliaryDeposit {

    private List<Resource> auxiliaryDeposit;
    private ResourceType type;
    private static final int AUXILIARY_DEPOSIT_SIZE = 2;


    public AuxiliaryDeposit(ResourceType type){

        auxiliaryDeposit = new ArrayList<Resource>();
        this.type = type;

    }

    public boolean addResource(Resource res){
        if(auxiliaryDeposit.size() >= AUXILIARY_DEPOSIT_SIZE || res.getType() != type) return false;
        auxiliaryDeposit.add(res);
        return true;
    }

    public List<Resource> getResources(int amount) throws Exception{

        List<Resource> result = new ArrayList<Resource>();

        if(amount > auxiliaryDeposit.size()) throw new Exception();
        while(amount > 0){
            result.add(auxiliaryDeposit.remove(0));
            amount--;
        }
        return result;
    }

    public int getSize(){
        return auxiliaryDeposit.size();
    }

    public List<Resource> getAuxiliaryDeposit() {
        return auxiliaryDeposit;
    }
}
