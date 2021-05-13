package it.polimi.ingsw.messages.actions;

import it.polimi.ingsw.view.client.View;
import it.polimi.ingsw.view.server.VirtualView;

import java.io.Serializable;

/**
 * message sent when a client chooses to activate the production
 * @author Nemanja Antonic
 */
public class ActivateProductionMessage implements Serializable, ActionMessage {
    private final ActionMessageType messageType;

    /**
     * Server-side constructor to create the message
     */
    public ActivateProductionMessage() {
        messageType = ActionMessageType.ACTIVATE_PRODUCTION;
    }
    /**
     * Execute the request client side
     * @param view: receiver view
     */
    public void execute(View view){
        //method in view to show the choice
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
