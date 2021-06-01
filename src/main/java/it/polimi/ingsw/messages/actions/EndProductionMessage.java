package it.polimi.ingsw.messages.actions;

import it.polimi.ingsw.messages.actions.ActionMessage;
import it.polimi.ingsw.view.client.View;
import it.polimi.ingsw.view.server.VirtualView;

import java.io.Serializable;

public class EndProductionMessage implements ActionMessage, Serializable {

    private final String user;

    public EndProductionMessage(String user){
        this.user = user;
    }
    @Override
    public void execute(VirtualView virtualView) {
        virtualView.endProduction(user);
    }

    @Override
    public void execute(View view) {
    }
}
