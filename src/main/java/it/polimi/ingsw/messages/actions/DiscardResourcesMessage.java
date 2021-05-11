package it.polimi.ingsw.messages.actions;

import it.polimi.ingsw.model.gameboard.Resource;
import it.polimi.ingsw.view.client.View;
import it.polimi.ingsw.view.server.VirtualView;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * message sent when a client discards extra resources
 * @author Nemanja Antonic
 */
public class DiscardResourcesMessage implements Serializable, ActionMessage {
    private final ActionMessageType messageType;
    private ArrayList<Resource> resources;

    /**
     * Server-side constructor to create the message
     * @param resources: the chosen extra resources to discard
     */
    public DiscardResourcesMessage(ArrayList<Resource> resources) {
        this.resources = resources;
        messageType = ActionMessageType.DISCARD_RESOURCES;
    }
    /**
     * Execute the request client side
     * @param view: receiver view
     */
    public void execute(View view){
        //method in view to show the change
    }
    /**
     * Execute the request server side
     * @param virtualView: receiver view
     */
    public void execute(VirtualView virtualView){
        //method in virtualView
    }
    /**
     * Get the message type
     * @return the message type
     */
    public ActionMessageType getType() {
        return messageType;
    }
}
