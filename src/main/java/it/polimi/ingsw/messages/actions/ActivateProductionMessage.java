package it.polimi.ingsw.messages.actions;

import it.polimi.ingsw.messages.MessageType;
import it.polimi.ingsw.model.cards.DevelopmentCard;
import it.polimi.ingsw.view.client.View;
import it.polimi.ingsw.view.server.VirtualView;

import java.io.Serializable;
import java.util.ArrayList;

public class ActivateProductionMessage implements Serializable, ActionMessage {
    private final ActionMessageType messageType;

    public ActivateProductionMessage() {
        messageType = ActionMessageType.ACTIVATE_PRODUCTION;
    }

    public void execute(View view){
        //method in view to show the choice
    }

    public void execute(VirtualView virtualView){
        //method in virtualView
    }

    public ActionMessageType getType() {
        return messageType;
    }
}
