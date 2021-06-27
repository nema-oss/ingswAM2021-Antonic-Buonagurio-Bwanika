package it.polimi.ingsw.view.client.utils;


import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * this class enumerates all the possible actions to play in a turn
 */
public enum TurnActions {

    BUY_CARD("buy card"),
    RESET("reset"),
    BUY_RESOURCES("buy resources"),
    ACTIVATE_PRODUCTION("activate production"),
    LEADER_ACTION("leader action"),
    END_TURN("end turn"),
    SHOW_GAMEBOARD("show gameboard"),
    SHOW_OTHER_PLAYERS("show other players"),
    SHOW_ACTIVE_EFFECTS("show active effects"),
    CHEAT("cheat");


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

    public static List<TurnActions> getLocalMatchTurnAction(){
        List<TurnActions> turnActions = Arrays.asList(TurnActions.values());
        turnActions.remove(TurnActions.SHOW_OTHER_PLAYERS);
        return turnActions;
    }
}
