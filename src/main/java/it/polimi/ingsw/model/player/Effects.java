package it.polimi.ingsw.model.player;

import it.polimi.ingsw.model.cards.leadercards.AuxiliaryDeposit;
import it.polimi.ingsw.model.exception.InsufficientPaymentException;
import it.polimi.ingsw.model.gameboard.Producible;
import it.polimi.ingsw.model.gameboard.Resource;
import it.polimi.ingsw.model.gameboard.ResourceType;
import java.util.*;

public class Effects{


    private boolean isWhiteToResource;
    private final List<Resource> toResources;

    private boolean isExtraDeposit;
    private final List<AuxiliaryDeposit> auxiliaryDeposits;

    private boolean isDiscount;
    private final Map<ResourceType, Integer> discountAmounts;

    private boolean isExtraProduction;
    private final List<List<Producible>> productionResultList;
    private final List<Map<ResourceType, Integer>> productionRequirementsList;

    public Effects(){

        isDiscount = false;
        discountAmounts = new HashMap<>();
        isWhiteToResource = false;
        toResources = new ArrayList<>();
        isExtraProduction = false;
        productionResultList = new ArrayList<>();
        productionRequirementsList = new ArrayList<>();
        isExtraDeposit = false;
        auxiliaryDeposits = new ArrayList<>();

    }

    /**
        * this method activate the White to Resource effect
        * @param resourcetype the resource type that will match the white marble
     */
    public void activateWhiteToResource(ResourceType resourcetype){
        isWhiteToResource = true;
        toResources.add(new Resource(resourcetype));
    }

    /**
     * this method activate the Discount effect
     * @param discountType the resource type to discount
     * @param discountAmount the discount amount
     */
    public void activateDiscount(ResourceType discountType, int discountAmount){
        isDiscount = true;
        discountAmounts.put(discountType, discountAmount);
    }

    /**
     * this method activate the Extra Deposit effect
     * @param resourceType the storage type
     */
    public void activateExtraDeposit(ResourceType resourceType){
        isExtraDeposit = true;
        AuxiliaryDeposit auxiliaryDeposit = new AuxiliaryDeposit(resourceType);
        auxiliaryDeposits.add(auxiliaryDeposit);
    }

    /**
     * this method activate the ExtraProduction effect
     * @param productionRequirements the production requirements
     * @param productionResult and the results of production
     */
    public void activateExtraProduction(Map<ResourceType,Integer> productionRequirements, List<Producible> productionResult){
        isExtraProduction = true;
        productionResultList.add(productionResult);
        productionRequirementsList.add(productionRequirements);
    }


    /**
     * @return true if the effect is "white to resource"
     */
    public boolean isWhiteToResource() {
        return isWhiteToResource;
    }

    /**
     * @return true if the effect is extra deposit
     */
    public boolean isExtraDeposit() {
        return isExtraDeposit;
    }

    /**
     * @return true if the effect is a discount
     */
    public boolean isDiscount() {
        return isDiscount;
    }

    /**
     * @return true if the effect is extra production
     */
    public boolean isExtraProduction() {
        return isExtraProduction;
    }

    /**
     * this method use the White to Resource effect
     * @param position the index of the selected effect
     */
    public Resource useWhiteToResourceEffect(int position){
        return toResources.get(position);
    }

    /**
     * this method use the ExtraProduction effect
     * @param player the current player
     * @param positionIndex the index of the selected effect
     */
    public void useExtraProductionEffect(Player player, int positionIndex) throws InsufficientPaymentException {

        player.checkCardRequirements(productionRequirementsList.get(positionIndex));
        List<Resource> result = new ArrayList<>();
        for(Producible producible: productionResultList.get(positionIndex)){
            if(!producible.useEffect(player.getPopeRoad())) {
                result.add(new Resource((ResourceType) producible.getType()));
            }
        }
        player.getStrongbox().addResourceTemporary(result);

    }

    /**
     * this method use the Discount effect
     * @param cost the cost to discount
     */
    public void useDiscountEffect(Map<ResourceType,Integer> cost){
        for(ResourceType discountType: discountAmounts.keySet())
            if(cost.containsKey(discountType)){
                cost.put(discountType, cost.get(discountType) - discountAmounts.get(discountType));
            }
    }

    /**
     * this method use the ExtraDeposit effect
     * @param resources the resources obtained
     * @param positionIndex the index of the selected effect
     */
    public void useExtraDepositEffect(List<Resource> resources, int positionIndex){
        resources.removeIf(resource -> auxiliaryDeposits.get(positionIndex).addResource(resource));
    }

    /**
        * this method returns the chosen auxiliary deposit
        * @param positionIndex the index of the selected auxiliary deposit
     */
    public AuxiliaryDeposit getAuxiliaryDeposit(int positionIndex){
        if(positionIndex >= 0 && positionIndex <= auxiliaryDeposits.size() - 1)
            return auxiliaryDeposits.get(positionIndex);
        return null;
    }

}
