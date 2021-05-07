package it.polimi.ingsw.messages.setup.client;

import it.polimi.ingsw.messages.MessageType;
import it.polimi.ingsw.messages.setup.SetupMessage;

import java.io.Serializable;

/**
 * the login request a client sends to a server after receive a LoginMessage from it. It should contain the nickname
 *
 */
public class LoginRequest implements SetupMessage, Serializable {

    /*
        this is just an example to test the connection, change it if needed
     */
    private String nickname;
    private final MessageType messageType;

    public LoginRequest(String nickname, MessageType messageType){
        this.nickname = nickname;
        this.messageType = messageType;
    }

    public String getNickname() {
        return nickname;
    }

    @Override
    public MessageType getType() {
        return messageType;
    }
}
