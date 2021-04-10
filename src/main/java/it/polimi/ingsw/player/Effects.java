package it.polimi.ingsw.player;

import it.polimi.ingsw.cards.leadercards.AuxiliaryDeposit;
import it.polimi.ingsw.gameboard.Producible;
import it.polimi.ingsw.gameboard.Resource;
import it.polimi.ingsw.gameboard.ResourceType;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class Effects {


    private boolean isWhiteToResource;
    private List<Resource> toResources;

    private boolean isExtraDeposit;
    private ResourceType depositType;
    private AuxiliaryDeposit auxiliaryDeposit;

    private boolean isDiscount;
    private Map<ResourceType, Integer> discountAmounts;

    private boolean isExtraProduction;
    private List<Producible> productionResult;

    public Effects(){

        isDiscount = false;
        isWhiteToResource = false;
        isExtraProduction = false;
        isExtraDeposit = false;
    }

    public void activateWhiteToResource(ResourceType resourcetype){
        isWhiteToResource = true;
        toResources.add(new Resource(resourcetype));
    }

    public void activateDiscount(ResourceType discountType, int discountAmount){
        isDiscount = true;
        discountAmounts.put(discountType, discountAmount);
    }

    public void activateExtraDeposit(ResourceType resourceType){
        isExtraDeposit = true;
        auxiliaryDeposit = new AuxiliaryDeposit(resourceType);
    }

    public void activateExtraProduction(List<Producible> productionResult){
        isExtraProduction = true;
        this.productionResult = productionResult;
    }


    public boolean isWhiteToResource() {
        return isWhiteToResource;
    }

    public boolean isExtraDeposit() {
        return isExtraDeposit;
    }

    public boolean isDiscount() {
        return isDiscount;
    }

    public boolean isExtraProduction() {
        return isExtraProduction;
    }

    public Resource useWhiteToResourceEffect(int position){
        return toResources.get(position);
    }

    public void useExtraProductionEffect(Player player){

        List<Resource> result = new ArrayList<>();
        for(Producible producible: productionResult){
            if(!producible.useEffect(player.getPopeRoad())) return;
                //result.add(new Resource(producibl()));
        }

        player.getStrongbox().addResource(result);



    }

    public void useDiscountEffect(Map<ResourceType,Integer> cost){
        for(ResourceType discountType: discountAmounts.keySet())
            if(cost.containsKey(discountType)){
                cost.put(discountType, cost.get(discountType) - discountAmounts.get(discountType));
            }
    }

    public void useExtraDepositEffect(List<Resource> resources){
        resources.removeIf(resource -> auxiliaryDeposit.addResource(resource));
    }

}
