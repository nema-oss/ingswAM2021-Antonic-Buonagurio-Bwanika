package it.polimi.ingsw.model.cards.leadercards;

import it.polimi.ingsw.model.gameboard.Resource;
import it.polimi.ingsw.model.gameboard.ResourceType;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * this class represents the extra deposit given by leader cards
 */
public class AuxiliaryDeposit implements Serializable {

    private final List<Resource> auxiliaryDeposit;
    private final ResourceType type;
    private static final int AUXILIARY_DEPOSIT_SIZE = 2;


    public AuxiliaryDeposit(ResourceType type){
        auxiliaryDeposit = new ArrayList<>();
        this.type = type;
    }

    /**
     * this method adds a resource to the extra deposit
     * @param res the resource to add
     * @return true if the resource has been added, false otherwise
     */
    public boolean addResource(Resource res){
        if(auxiliaryDeposit.size() >= AUXILIARY_DEPOSIT_SIZE || res.getType() != type) return false;
        auxiliaryDeposit.add(res);
        return true;
    }

    /**
     * this method return the resources that are in the extra deposit
     * @param amount number of resources to get
     * @return the list of resources requested
     * @throws Exception if the action cannot be performed
     */
    public List<Resource> getResources(int amount) throws Exception{

        List<Resource> result = new ArrayList<>();

        if(amount > auxiliaryDeposit.size()) throw new Exception();
        while(amount > 0){
            result.add(auxiliaryDeposit.remove(0));
            amount--;
        }
        return result;
    }

    /**
     * @return the extra deposit's size
     */
    public int getSize(){
        return auxiliaryDeposit.size();
    }

    /**
     * @return the extra deposit's resources
     */
    public List<Resource> getAuxiliaryDeposit() {
        return auxiliaryDeposit;
    }

    /**
     * @return the type of auxiliary deposit
     */
    public ResourceType getType() {
        return type;
    }
}
