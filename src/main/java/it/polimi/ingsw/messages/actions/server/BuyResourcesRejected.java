package it.polimi.ingsw.messages.actions.server;

import it.polimi.ingsw.messages.actions.ActionMessage;
import it.polimi.ingsw.view.client.View;
import it.polimi.ingsw.view.server.VirtualView;

import java.io.Serializable;

public class BuyResourcesRejected implements ActionMessage, Serializable {
    public BuyResourcesRejected(String user, int x, int y) {

    }

    @Override
    public void execute(VirtualView virtualView) {

    }

    @Override
    public void execute(View view) {

    }
}
