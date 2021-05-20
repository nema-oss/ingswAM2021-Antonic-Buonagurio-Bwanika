package it.polimi.ingsw.messages.actions.server;

import it.polimi.ingsw.messages.actions.ActionMessage;
import it.polimi.ingsw.view.client.View;
import it.polimi.ingsw.view.server.VirtualView;

import java.io.Serializable;

public class LeaderActionAccepted implements ActionMessage, Serializable {
    @Override
    public void execute(VirtualView virtualView) {
    }

    @Override
    public void execute(View view) {
        view.showAcceptedLeaderAction();
    }
}
