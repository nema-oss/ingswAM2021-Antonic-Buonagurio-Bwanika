package it.polimi.ingsw.messages.setup.client;

import it.polimi.ingsw.messages.setup.SetupMessage;
import it.polimi.ingsw.messages.setup.SetupMessageType;
import it.polimi.ingsw.view.client.View;
import it.polimi.ingsw.view.server.VirtualView;

import java.io.Serializable;

/**
 * the login request a client sends to a server after receiving a LoginMessage from it. It should contain the nickname
 */
public class LoginRequest implements SetupMessage, Serializable {

    private String nickname;
    private int numberOfPlayers;
    private final SetupMessageType messageType;

    /**
     * Server-side constructor to create the message
     * @param nickname: client's nickname
     */
    public LoginRequest(String nickname){
        this.nickname = nickname;
        this.messageType = SetupMessageType.LOGIN_REQUEST;
        this.numberOfPlayers = -1;
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

    /**
     * Get the number of players requested
     * @return the number of players
     */
    public int getNumberOfPlayers() {
        return numberOfPlayers;
    }

    /**
     * Set the number of players requested
     * @param numberOfPlayers the number of players requested
     */
    public void setNumberOfPlayers(int numberOfPlayers) {
        this.numberOfPlayers = numberOfPlayers;
    }

    @Override
    public void execute(VirtualView virtualView) {
        virtualView.loginRequest(nickname,numberOfPlayers,null);
    }

    @Override
    public void execute(View view) {

    }
}
