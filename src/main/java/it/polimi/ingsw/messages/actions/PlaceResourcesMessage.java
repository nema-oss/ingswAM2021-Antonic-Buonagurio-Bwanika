package it.polimi.ingsw.messages.actions;

import it.polimi.ingsw.messages.MessageType;
import it.polimi.ingsw.model.gameboard.Resource;
import it.polimi.ingsw.view.client.View;
import it.polimi.ingsw.view.server.VirtualView;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * after the client buys from the market, he chooses where to place the resources
 */


public class PlaceResourcesMessage implements Serializable, ActionMessage {
    private final ActionMessageType messageType;
    private ArrayList<Resource> resources;
    private ArrayList<Integer> targetShelves; //which shelves does the client want to place the resources in

    public PlaceResourcesMessage(ArrayList<Resource> resources, ArrayList<Integer> targetShelves) {
        this.resources = resources;
        this.targetShelves = targetShelves;
        messageType = ActionMessageType.PLACE_RESOURCES;
    }

    public void execute(View view){
        //method in view to show the update
    }

    public void execute(VirtualView virtualView){
        //method in virtualView
    }

    public ActionMessageType getType() {
        return messageType;
    }
}
