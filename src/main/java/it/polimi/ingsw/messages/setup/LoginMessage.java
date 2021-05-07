package it.polimi.ingsw.messages.setup;

import it.polimi.ingsw.messages.MessageType;
import it.polimi.ingsw.view.client.View;
import it.polimi.ingsw.view.server.VirtualView;

import java.io.Serializable;

/**
 * the login message the server sends to each client after establishing the connection
 */

public class LoginMessage implements SetupMessage, Serializable {
    private final MessageType messageType;

    public LoginMessage() {
        messageType = MessageType.LOGIN;
    }

    public void execute(View view){
        view.showLogin();
    }

    public void execute(VirtualView virtualView){
        //method in virtualView
    }
    @Override
    public MessageType getType() {
        return null;
    }
}
