package it.polimi.ingsw.messages;

import it.polimi.ingsw.view.client.View;
import it.polimi.ingsw.view.server.VirtualView;

import java.io.Serializable;

public class BuyResourcesMessage implements Serializable, Message {
    private final MessageType messageType;
    private int x;
    private int y;

    public BuyResourcesMessage(int x, int y) {
        this.x = x;
        this.y = y;
        messageType = MessageType.BUY_RESOURCES;
    }

    public void execute(View view){
        //method in view to show the update
    }

    public void execute(VirtualView virtualView){
        //method in virtualView to update
    }

    @Override
    public MessageType getType() {
        return messageType;
    }
}
