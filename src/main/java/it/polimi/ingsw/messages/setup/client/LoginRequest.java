package it.polimi.ingsw.messages.setup.client;

import it.polimi.ingsw.messages.setup.SetupMessage;
import it.polimi.ingsw.messages.setup.SetupMessageType;

import java.io.Serializable;

/**
 * the login request a client sends to a server after receiving a LoginMessage from it. It should contain the nickname
 *
 */
public class LoginRequest implements SetupMessage, Serializable {

    private String nickname;
    private final SetupMessageType messageType;

    /**
     * Server-side constructor to create the message
     * @param nickname: client's nickname
     */
    public LoginRequest(String nickname){
        this.nickname = nickname;
        this.messageType = SetupMessageType.LOGIN_REQUEST;
    }

    /**
     * get the nickname of the client
     * @return the nickname
     */
    public String getNickname() {
        return nickname;
    }

    /**
     * Get the message type
     * @return the message type
     */
    public SetupMessageType getType() {
        return messageType;
    }
}
