package it.polimi.ingsw.view.client.viewComponents;

import it.polimi.ingsw.model.cards.leadercards.AuxiliaryDeposit;
import it.polimi.ingsw.model.gameboard.ResourceType;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Manages the player active effects on client side
 */
public class ClientActiveEffects {

    private boolean isWhiteToResource;
    private List<ResourceType> whiteToResourceList;

    private boolean isDiscount;
    private Map<ResourceType, Integer> discountAmounts;

    private boolean isExtraDeposit;
    private List<AuxiliaryDeposit> auxiliaryDeposits;

    public ClientActiveEffects(){
        isWhiteToResource = false;
        whiteToResourceList = new ArrayList<>();

    }

    /**
     * Check if white To Resource effect is active
     * @return true if the effect is active
     */
    public boolean isWhiteToResource() {
        return isWhiteToResource;
    }

    /**
     * Activates the white to Resource effect
     * @param resourceType the new white marble-resource association
     */
    public void setWhiteToResource(ResourceType resourceType) {
        isWhiteToResource = true;
        whiteToResourceList.add(resourceType);
    }

    /**
     * Returns the list of available resource types for the white To Resource effect
     * @return the available resource types
     */
    public List<ResourceType> getWhiteToResourceList() {
        return whiteToResourceList;
    }

    /**
     * Check if discount effect is active
     * @return true if the effect is active
     */
    public boolean isDiscount() {
        return isDiscount;
    }

    /**
     * @return the amount discounted by the effect
     */
    public Map<ResourceType, Integer> getDiscountAmounts() {
        return discountAmounts;
    }

    /**
     * this method adds a discount effect
     * @param resourceType the type od resource to discount
     * @param amount the amount to discount
     */
    public void addDiscountEffect(ResourceType resourceType, int amount) {
        isDiscount = true;
        discountAmounts.put(resourceType,amount);
    }

    /**
     * Check if extra deposit effect is active
     * @return true if the effect is active
     */
    public boolean isExtraDeposit() {
        return isExtraDeposit;
    }

    /**
     * this method adds an extra deposit
     * @param auxiliaryDepositType the type of the resources that can be put in the extra deposit
     */
    public void addAuxiliaryDeposit(ResourceType auxiliaryDepositType) {
        auxiliaryDeposits.add(new AuxiliaryDeposit(auxiliaryDepositType));
    }

    /**
     * @return the list of all extra deposits active
     */
    public List<AuxiliaryDeposit> getAuxiliaryDeposits() {
        return auxiliaryDeposits;
    }
}
