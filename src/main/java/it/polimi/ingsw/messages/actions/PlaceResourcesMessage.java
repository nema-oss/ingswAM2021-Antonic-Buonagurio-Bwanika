package it.polimi.ingsw.messages.actions;

import it.polimi.ingsw.model.gameboard.Resource;
import it.polimi.ingsw.view.client.View;
import it.polimi.ingsw.view.server.VirtualView;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * message sent after the client buys from the market to choose where to place the resources
 */


public class PlaceResourcesMessage implements Serializable, ActionMessage {
    private final ActionMessageType messageType;
    private ArrayList<Resource> resources;
    private ArrayList<Integer> targetShelves; //which shelves does the client want to place the resources in

    /**
     * Server-side constructor to create the message
     * @param resources: the acquired resources
     * @param targetShelves: the target shelves
     */
    public PlaceResourcesMessage(ArrayList<Resource> resources, ArrayList<Integer> targetShelves) {
        this.resources = resources;
        this.targetShelves = targetShelves;
        messageType = ActionMessageType.PLACE_RESOURCES;
    }
    /**
     * Execute the request client side
     * @param view: receiver view
     */
    public void execute(View view){
        //method in view to show the update
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
