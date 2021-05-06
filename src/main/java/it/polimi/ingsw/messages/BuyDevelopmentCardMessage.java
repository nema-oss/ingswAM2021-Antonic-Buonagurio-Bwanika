package it.polimi.ingsw.messages;

import it.polimi.ingsw.view.client.View;
import it.polimi.ingsw.view.server.VirtualView;

import java.io.Serializable;

public class BuyDevelopmentCardMessage implements Serializable, Message {
    private final MessageType messageType;
    int x;
    int y;

    public BuyDevelopmentCardMessage(int x, int y) {
        this.x = x;
        this.y = y;
        messageType = MessageType.BUY_DEVELOPMENT;
    }

    public void execute(View view){
        //method in view to show the change
    }

    public void execute(VirtualView virtualView){
        //method in virtualView
    }
    @Override
    public MessageType getType() {
        return messageType;
    }
}
