package it.polimi.ingsw.view.client.utils;


import java.util.HashMap;
import java.util.Map;

public enum TurnActions {

    BUY_CARD("buy card"),
    BUY_RESOURCES("buy resource"),
    ACTIVATE_PRODUCTION("activate production"),
    LEADER_ACTION("leader action"),
    END_TURN("end turn");


    private static final Map<String, TurnActions> BY_LABEL = new HashMap<>();

    public final String label;

    private TurnActions(String label) {
        this.label = label;
    }

    static {
        for (TurnActions e: values()) {
            BY_LABEL.put(e.label, e);
        }
    }

    // ... fields, constructor, methods

    public static TurnActions valueOfLabel(String label) {
        return BY_LABEL.get(label);
    }
}
