package it.polimi.ingsw.messages.actions.server;

import it.polimi.ingsw.messages.actions.ActionMessage;
import it.polimi.ingsw.model.ActionToken;
import it.polimi.ingsw.view.client.View;
import it.polimi.ingsw.view.server.VirtualView;

import java.io.Serializable;

public class LorenzoTurnMessage implements ActionMessage, Serializable {


    private final ActionToken lorenzoAction;

    public LorenzoTurnMessage(ActionToken lorenzoAction){
        this.lorenzoAction = lorenzoAction;

    }

    @Override
    public void execute(VirtualView virtualView) {

    }

    @Override
    public void execute(View view) {

        view.showLorenzoAction(lorenzoAction);
    }
}
