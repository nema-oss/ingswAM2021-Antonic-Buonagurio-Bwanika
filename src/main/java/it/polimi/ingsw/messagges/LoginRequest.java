package it.polimi.ingsw.messagges;

import java.io.Serializable;

/**
 * the login request a client sends to a server after receive a LoginMessage from it. It should contain the nickname
 *
 */
public class LoginRequest implements Message, Serializable {

    /*
        this is just an example to test the connection, change it if needed
     */
    private String nickname;

    public LoginRequest(String nickname){
        this.nickname = nickname;
    }

    public String getNickname() {
        return nickname;
    }

}
