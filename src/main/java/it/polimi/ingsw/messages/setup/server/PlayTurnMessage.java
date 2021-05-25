package it.polimi.ingsw.messages.setup.server;

import it.polimi.ingsw.messages.setup.SetupMessage;
import it.polimi.ingsw.view.client.View;
import it.polimi.ingsw.view.server.VirtualView;

import java.io.Serializable;

public class PlayTurnMessage implements SetupMessage, Serializable {


    private final String user;

    public PlayTurnMessage(String user){
        this.user = user;
    }

    @Override
    public void execute(VirtualView virtualView) {

    }

    @Override
    public void execute(View view) {
        view.showPlayTurn(user);
    }

    public String getUser() {
        return user;
    }
}
