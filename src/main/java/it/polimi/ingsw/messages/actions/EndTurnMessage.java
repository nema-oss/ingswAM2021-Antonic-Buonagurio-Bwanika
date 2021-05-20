package it.polimi.ingsw.messages.actions;

import it.polimi.ingsw.view.client.View;
import it.polimi.ingsw.view.server.VirtualView;

import java.io.Serializable;

public class EndTurnMessage implements ActionMessage, Serializable {

    private final String user;

    public EndTurnMessage(String user){
        this.user = user;
    }
    @Override
    public void execute(VirtualView virtualView) {
        virtualView.endTurn(user);
    }

    @Override
    public void execute(View view) {

    }
}
