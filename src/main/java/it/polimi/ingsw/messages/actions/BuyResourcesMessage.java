package it.polimi.ingsw.messages.actions;

import it.polimi.ingsw.view.client.View;
import it.polimi.ingsw.view.server.VirtualView;

import java.io.Serializable;

public class BuyResourcesMessage implements Serializable, ActionMessage {
    private final ActionMessageType messageType;
    private int x;
    private int y;
    private boolean accepted;

    public BuyResourcesMessage(int x, int y, boolean accepted) {
        this.x = x;
        this.y = y;
        this.accepted = accepted;
        messageType = ActionMessageType.BUY_RESOURCES;
    }

    public void execute(View view){
        //method in view to show the update
    }

    public void execute(VirtualView virtualView){
        //method in virtualView to update
    }

    public ActionMessageType getType() {
        return messageType;
    }
}
