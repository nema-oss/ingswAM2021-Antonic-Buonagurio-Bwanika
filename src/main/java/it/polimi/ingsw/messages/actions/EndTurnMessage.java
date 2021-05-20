package it.polimi.ingsw.messages.actions;

import it.polimi.ingsw.view.client.View;
import it.polimi.ingsw.view.server.VirtualView;

import java.io.Serializable;

public class EndTurnMessage implements ActionMessage, Serializable {
    @Override
    public void execute(VirtualView virtualView) {

    }

    @Override
    public void execute(View view) {

    }
}
