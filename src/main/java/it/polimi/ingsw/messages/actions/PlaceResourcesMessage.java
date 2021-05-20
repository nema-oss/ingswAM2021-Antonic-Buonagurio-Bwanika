package it.polimi.ingsw.messages.actions;

import it.polimi.ingsw.model.gameboard.Resource;
import it.polimi.ingsw.view.client.View;
import it.polimi.ingsw.view.server.VirtualView;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * message sent after the client buys from the market to choose where to place the resources
 */
public class PlaceResourcesMessage implements Serializable, ActionMessage {

    private final ActionMessageType messageType;
    private final String user;
    private final Map<Resource,Integer> userChoice;

    /**
     * Server-side constructor to create the message
     */
    public PlaceResourcesMessage(String user, Map<Resource, Integer> userChoice) {
        this.user = user;
        this.userChoice = userChoice;
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
        virtualView.placeResource(user,userChoice);
    }
    /**
     * Get the message type
     * @return the message type
     */
    public ActionMessageType getType() {
        return messageType;
    }
}
