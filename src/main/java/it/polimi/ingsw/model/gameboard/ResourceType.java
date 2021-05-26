package it.polimi.ingsw.model.gameboard;

/*
    *This class represent the possible resource's type
 */

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static it.polimi.ingsw.view.client.utils.Formatting.ColorCode.ANSI_RESET;
import static it.polimi.ingsw.view.client.utils.Formatting.Unicode.RESOURCE;

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

    public static List<ResourceType> getAllResourceType(){
       return Arrays.asList(ResourceType.values());
    }
    // ... fields, constructor, methods

    public static ResourceType valueOfLabel(String label) {
        return BY_LABEL.get(label);
    }
}
