package it.polimi.ingsw.player;

import it.polimi.ingsw.cards.leadercards.AuxiliaryDeposit;
import it.polimi.ingsw.exception.ProductionRequirementsException;
import it.polimi.ingsw.gameboard.Producible;
import it.polimi.ingsw.gameboard.Resource;
import it.polimi.ingsw.gameboard.ResourceType;

import java.util.*;

public class Effects {


    private boolean isWhiteToResource;
    private List<Resource> toResources;

    private boolean isExtraDeposit;
    private ResourceType depositType;
    private List<AuxiliaryDeposit> auxiliaryDeposits;

    private boolean isDiscount;
    private Map<ResourceType, Integer> discountAmounts;

    private boolean isExtraProduction;
    private List<List<Producible>> productionResultList;
    private List<Map<ResourceType, Integer>> productionRequirementsList;

    public Effects(){

        isDiscount = false;
        discountAmounts = new HashMap<>();
        isWhiteToResource = false;
        toResources = new ArrayList<>();
        isExtraProduction = false;
        productionResultList = new ArrayList<>();
        productionRequirementsList = new ArrayList<>();
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
        AuxiliaryDeposit auxiliaryDeposit = new AuxiliaryDeposit(resourceType);
        auxiliaryDeposits.add(auxiliaryDeposit);
    }

    public void activateExtraProduction(Map<ResourceType,Integer> productionRequirements, List<Producible> productionResult){
        isExtraProduction = true;
        productionResultList.add(productionResult);
        productionRequirementsList.add(productionRequirements);
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

    public void useExtraProductionEffect(Player player, int positionIndex){

        player.checkCardRequirementsProduction(productionRequirementsList.get(positionIndex));
        List<Resource> result = new ArrayList<>();
        for(Producible producible: productionResultList.get(positionIndex)){
            if(!producible.useEffect(player.getPopeRoad())) {
                result.add(new Resource((ResourceType) producible.getType()));
            }
        }

        player.getStrongbox().addResourceTemporary(result);

    }

    public void useDiscountEffect(Map<ResourceType,Integer> cost){
        for(ResourceType discountType: discountAmounts.keySet())
            if(cost.containsKey(discountType)){
                cost.put(discountType, cost.get(discountType) - discountAmounts.get(discountType));
            }
    }

    public void useExtraDepositEffect(List<Resource> resources, int positionIndex){
        resources.removeIf(resource -> auxiliaryDeposits.get(positionIndex).addResource(resource));
    }

    public AuxiliaryDeposit getAuxiliaryDeposit(int positionIndex){
        return auxiliaryDeposits.get(positionIndex);
    }

}
