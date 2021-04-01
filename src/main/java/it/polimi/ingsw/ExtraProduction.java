package it.polimi.ingsw;

import java.util.ArrayList;

public class ExtraProduction implements EffectStrategy {

    private ArrayList<Producible> productionResult;
    private ArrayList<Producible> productionRequirement;

    public ExtraProduction(ArrayList<Producible> productionResult, ArrayList<Producible> productionRequirement) {
        this.productionResult = productionResult;
        this.productionRequirement = productionRequirement;
    }

    public ArrayList<Resource> useEffect(){
        return new ArrayList<Resource>();
    }

    /*
    STUB, see useEffect
     */
    public ArrayList<ResourceType> askForRequirements(Player p) throws LeaderCardException{
        ArrayList<ResourceType> r = new ArrayList<>();
        r.add(ResourceType.STONE);
        return r;
    }



    public ArrayList<Producible> getProductionResult() {
        return productionResult;
    }

    public ArrayList<Producible> getProductionRequirement() {
        return productionRequirement;
    }
}
