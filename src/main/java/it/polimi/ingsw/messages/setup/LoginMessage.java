package it.polimi.ingsw.messages.setup;

import it.polimi.ingsw.view.client.View;
import it.polimi.ingsw.view.server.VirtualView;

import java.io.Serializable;

/**
 * the login message the server sends to each client after establishing the connection
 * @author Nemanja Antonic
 */

public class LoginMessage implements SetupMessage, Serializable {

    private final SetupMessageType messageType;
    private boolean isFirstPlayer;

    /**
     * Server-side constructor to create the message
     */
    public LoginMessage() {
        this.messageType = SetupMessageType.LOGIN;
        isFirstPlayer = false;
    }

    /**
     * Execute the request client side
     * @param view: receiver view
     */
    public void execute(View view){
       view.showLogin(this);
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
     * Set to true if it's the first player in the lobby
     * @param firstPlayer
     */
    public void setFirstPlayer(boolean firstPlayer) {
        isFirstPlayer = firstPlayer;
    }

    /**
     * Get isFirstPlayer
     * @return isFirstPlayer
     */
    public boolean isFirstPlayer() {
        return isFirstPlayer;
    }
}
