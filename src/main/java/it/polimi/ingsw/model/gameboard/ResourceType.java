package it.polimi.ingsw.model.gameboard;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 *This class represent the possible resource's type
 */

public enum ResourceType implements ProducibleType {

    COIN("coin"),
    SERVANT("servant"),
    SHIELD("shield"),
    STONE("stone");

    private static final Map<String, ResourceType> BY_LABEL = new HashMap<>();

    public final String label;

    ResourceType(String label) {
        this.label = label;
    }

    static {
        for (ResourceType e: values()) {
            BY_LABEL.put(e.label, e);
        }
    }

    /**
     * @return the list of all resource types
     */
    public static List<ResourceType> getAllResourceType(){
       return Arrays.asList(ResourceType.values());
    }

    public static ResourceType valueOfLabel(String label) {
        return BY_LABEL.get(label);
    }
}
