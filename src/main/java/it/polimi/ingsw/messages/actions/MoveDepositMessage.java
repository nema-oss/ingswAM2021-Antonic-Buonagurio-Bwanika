package it.polimi.ingsw.messages.actions;

import it.polimi.ingsw.view.client.View;
import it.polimi.ingsw.view.server.VirtualView;

import java.io.Serializable;

/**
 * message sent when a client chooses to reorganize their deposit
 * @author Nemanja Antonic
 */

public class MoveDepositMessage implements Serializable, ActionMessage {
    private final ActionMessageType messageType;
    private int resources;
    private int toShelves;
    private boolean accepted;

    /**
     * Server-side constructor to create the message
     * @param resources: index of the source level
     * @param toShelves: index of the target level
     * @param accepted: result of the request
     */
    public MoveDepositMessage(int resources, int toShelves, boolean accepted) {
        this.resources = resources;
        this.toShelves = toShelves;
        this.accepted = accepted;
        messageType = ActionMessageType.MOVE_DEPOSIT;
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
    /**
     * Get the accepted attribute
     * @return accepted
     */
    public boolean isAccepted() {
        return accepted;
    }
}
