package it.polimi.ingsw.view.client.viewComponents;

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


}
