package it.polimi.ingsw.messages.actions;

import it.polimi.ingsw.messages.MessageType;
import it.polimi.ingsw.model.gameboard.Resource;
import it.polimi.ingsw.view.client.View;
import it.polimi.ingsw.view.server.VirtualView;

import java.io.Serializable;
import java.util.ArrayList;

public class DiscardResourcesMessage implements Serializable, ActionMessage {
    private final ActionMessageType messageType;
    private ArrayList<Resource> resources;

    public DiscardResourcesMessage(ArrayList<Resource> resources) {
        this.resources = resources;
        messageType = ActionMessageType.DISCARD_RESOURCES;
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
