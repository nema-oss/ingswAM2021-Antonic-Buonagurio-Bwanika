package it.polimi.ingsw.model.gameboard;

/*
    *This class represent the possible resource's type
 */

import java.util.HashMap;
import java.util.Map;

public enum ResourceType implements ProducibleType {

    COIN("coin"),
    SERVANT("servant"),
    SHIELD("shield"),
    STONE("stone");

    private static final Map<String, ResourceType> BY_LABEL = new HashMap<>();

    public final String label;

    private ResourceType(String label) {
        this.label = label;
    }

    static {
        for (ResourceType e: values()) {
            BY_LABEL.put(e.label, e);
        }
    }

    // ... fields, constructor, methods

    public static ResourceType valueOfLabel(String label) {
        return BY_LABEL.get(label);
    }
}
