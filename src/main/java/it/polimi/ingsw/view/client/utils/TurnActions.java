package it.polimi.ingsw.view.client.utils;


import java.util.HashMap;
import java.util.Map;

public enum TurnActions {

    BUY_CARD("buy card"),
    BUY_RESOURCES("buy resources"),
    ACTIVATE_PRODUCTION("activate production"),
    LEADER_ACTION("leader action"),
    END_TURN("end turn"),
    SHOW_GAMEBOARD("show gameboard"),
    SHOW_OTHER_PLAYERS("show other players"),
    SHOW_ACTIVE_EFFECTS("show active effects");


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

    @Override
    public  String toString() {
        return label;
    }
}
