package it.polimi.ingsw.messages.actions;

import it.polimi.ingsw.view.client.View;
import it.polimi.ingsw.view.server.VirtualView;

import java.io.Serializable;

/**
 * message sent when a client chooses to buy resources
 * @author Nemanja Antonic
 */
public class BuyResourcesMessage implements Serializable, ActionMessage {
    private final ActionMessageType messageType;
    private int x;
    private int y;
    private boolean accepted;

    /**
     * Server-side constructor to create the message
     * @param x: x coordinate of the target developmentCard
     * @param y: y coordinate of the target developmentCard
     * @param accepted: result of the request
     */
    public BuyResourcesMessage(int x, int y, boolean accepted) {
        this.x = x;
        this.y = y;
        this.accepted = accepted;
        messageType = ActionMessageType.BUY_RESOURCES;
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
        //method in virtualView to update
    }
    /**
     * Get the message type
     * @return the message type
     */
    public ActionMessageType getType() {
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
