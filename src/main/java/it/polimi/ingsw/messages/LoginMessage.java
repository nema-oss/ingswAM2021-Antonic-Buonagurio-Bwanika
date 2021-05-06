package it.polimi.ingsw.messages;

import it.polimi.ingsw.view.client.View;

import java.io.Serializable;

/**
 * the login message the server sends to each client after establishing the connection
 */

public class LoginMessage implements Message, Serializable {
    private final MessageType messageType;

    public LoginMessage() {
        messageType = MessageType.LOGIN;
    }

    public void execute(View view){
        view.showLogin();
    }
    @Override
    public MessageType getType() {
        return null;
    }
}
