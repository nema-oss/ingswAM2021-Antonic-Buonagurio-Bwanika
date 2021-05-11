package it.polimi.ingsw.messages.setup.server;

import it.polimi.ingsw.messages.setup.SetupMessage;
import it.polimi.ingsw.messages.setup.SetupMessageType;
import it.polimi.ingsw.view.client.View;

import java.io.Serializable;

/**
 * message sent after the server checks if the login is correct
 * @author Nemanja Antonic
 */
public class LoginDoneMessage implements Serializable, SetupMessage {
    private final SetupMessageType messageType;
    boolean accepted;

    /**
     * Server-side constructor to create the message
     * @param accepted: result of the request
     */
    public LoginDoneMessage(boolean accepted) {
        this.accepted = accepted;
        this.messageType = SetupMessageType.LOGIN_DONE;
    }
    /**
     * Execute the request client side
     * @param view: receiver view
     */
    public void execute(View view){
        view.showLoginDone();
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
