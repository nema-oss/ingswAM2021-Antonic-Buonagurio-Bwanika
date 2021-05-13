package it.polimi.ingsw.messages;

import it.polimi.ingsw.view.client.View;
import it.polimi.ingsw.view.server.VirtualView;

import java.io.Serializable;

public class SpecialLoginResponse implements Message, Serializable {

    private String nickname;
    private int lobbyID;

    public SpecialLoginResponse(String nickname, int lobbyID){
        this.lobbyID = lobbyID;
        this.nickname = nickname;
    }
    public String getNickname() {
        return nickname;
    }
    public int getLobbyID() {
        return lobbyID;
    }

    @Override
    public void execute(VirtualView virtualView) {

    }

    @Override
    public void execute(View view) {

    }
}
