package it.polimi.ingsw.messages.setup;

import it.polimi.ingsw.model.gameboard.Resource;
import it.polimi.ingsw.view.client.View;
import it.polimi.ingsw.view.server.VirtualView;


import java.io.Serializable;

/**
 * message sent when a client has to choose the starting resource(s)
 * @author Nemanja Antonic
 */
public class ChooseResourcesMessage implements Serializable, SetupMessage {
    private final SetupMessageType messageType;
    private Resource resource;
    private boolean accepted;

    /**
     * Server-side constructor to create the message
     * @param resource: target resource
     * @param accepted: result of the request
     */
    public ChooseResourcesMessage(Resource resource, boolean accepted) {
        this.resource = resource;
        this.accepted = accepted;
        messageType = SetupMessageType.CHOOSE_RESOURCES;
    }
    /**
     * Execute the request client side
     * @param view: receiver view
     */
    public void execute(View view){
        //method in View to show the resources
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
    public SetupMessageType getType() {
        return messageType;
    }
    /**
     * Get the accepted attribute
     * @return accepted
     */
    public boolean isAccepted() {
        return accepted;
    }
}
