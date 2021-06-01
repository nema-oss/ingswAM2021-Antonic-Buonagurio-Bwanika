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
    private boolean accepted;
    private int discardResources;

    /**
     * Server-side constructor to create the message
     */
    public PlaceResourcesMessage(String user, Map<Resource, Integer> userChoice) {
        this.user = user;
        this.userChoice = userChoice;
        messageType = ActionMessageType.PLACE_RESOURCES;
        accepted = true;
    }
    /**
     * Execute the request client side
     * @param view: receiver view
     */
    public void execute(View view){
        view.showPlaceResourcesResult(accepted,userChoice);
    }
    /**
     * Execute the request server side
     * @param virtualView: receiver view
     */
    public void execute(VirtualView virtualView){
        virtualView.placeResource(user,userChoice);
        virtualView.discardResources(user,discardResources);
    }
    /**
     * Get the message type
     * @return the message type
     */
    public ActionMessageType getType() {
        return messageType;
    }

    public void setAccepted(boolean accepted) {
        this.accepted = accepted;
    }

    public void setDiscardedResources(int discardResources){
        this.discardResources = discardResources;
    }
}
