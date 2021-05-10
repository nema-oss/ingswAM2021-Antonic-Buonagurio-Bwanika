package it.polimi.ingsw.messages.actions;

import it.polimi.ingsw.messages.MessageType;
import it.polimi.ingsw.view.client.View;
import it.polimi.ingsw.view.server.VirtualView;

import java.io.Serializable;

public class BuyDevelopmentCardMessage implements Serializable, ActionMessage {
    private final ActionMessageType messageType;
    int x;
    int y;
    private boolean accepted;

    public BuyDevelopmentCardMessage(int x, int y, boolean accepted) {
        this.x = x;
        this.y = y;
        this.accepted = accepted;
        messageType = ActionMessageType.BUY_DEVELOPMENT;
    }

    public void execute(View view){
        //method in view to show the change
    }

    public void execute(VirtualView virtualView){
        //method in virtualView
    }

    public ActionMessageType getType() {
        return messageType;
    }
}
