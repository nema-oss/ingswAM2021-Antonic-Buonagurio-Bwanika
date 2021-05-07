package it.polimi.ingsw.messages.setup;

import it.polimi.ingsw.messages.MessageType;
import it.polimi.ingsw.messages.actions.ActionMessageType;
import it.polimi.ingsw.view.client.View;
import it.polimi.ingsw.view.server.VirtualView;

import java.io.Serializable;

/**
 * the login message the server sends to each client after establishing the connection
 */

public class LoginMessage implements SetupMessage, Serializable {
    private final SetupMessageType messageType;

    public LoginMessage() {
        messageType = SetupMessageType.LOGIN;
    }

    public void execute(View view){
        view.showLogin();
    }

    public void execute(VirtualView virtualView){
        //method in virtualView
    }

    public SetupMessageType getType() {
        return null;
    }
}
