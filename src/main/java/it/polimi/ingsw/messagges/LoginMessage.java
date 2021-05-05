package it.polimi.ingsw.messagges;

import java.io.Serializable;

/**
 * the login message the server sends to each client after establishing the connection
 */

public class LoginMessage implements Message, Serializable {


    @Override
    public MessageType getType() {
        return null;
    }
}
