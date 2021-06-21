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
    private int x;
    private int y;
    private String user;
    private boolean accepted;

    /**
     * Server-side constructor to create the message
     * @param user the user nickname
     * @param x,y the selected floors
     * @param accepted: result of the request
     */
    public MoveDepositMessage(String user, int x, int y, boolean accepted) {
        this.user = user;
        this.x = x;
        this.y = y;
        this.accepted = accepted;
        messageType = ActionMessageType.MOVE_DEPOSIT;
    }
    /**
     * Execute the request client side
     * @param view: receiver view
     */
    public void execute(View view){
        view.showMoveDepositResult(x,y,accepted);
    }
    /**
     * Execute the request server side
     * @param virtualView: receiver view
     */
    public void execute(VirtualView virtualView){
        virtualView.moveDeposit(user,x,y);
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
