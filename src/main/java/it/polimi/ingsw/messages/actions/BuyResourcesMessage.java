package it.polimi.ingsw.messages.actions;

import it.polimi.ingsw.view.client.View;
import it.polimi.ingsw.view.server.VirtualView;

import java.io.Serializable;

public class BuyResourcesMessage implements Serializable, ActionMessage {
    private final ActionMessageType messageType;
    private int x;
    private int y;

    public BuyResourcesMessage(int x, int y) {
        this.x = x;
        this.y = y;
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
