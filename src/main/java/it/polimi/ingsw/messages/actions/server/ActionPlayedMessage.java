package it.polimi.ingsw.messages.actions.server;

import it.polimi.ingsw.messages.actions.ActionMessage;
import it.polimi.ingsw.view.client.View;
import it.polimi.ingsw.view.client.utils.TurnActions;
import it.polimi.ingsw.view.server.VirtualView;

import java.io.Serializable;

public class ActionPlayedMessage implements ActionMessage, Serializable {


    public ActionPlayedMessage(String user, TurnActions turnActions){

    }
    /**
     * Execute the request server side
     *
     * @param virtualView : receiver view
     */
    @Override
    public void execute(VirtualView virtualView) {

    }

    /**
     * Execute the request client side
     *
     * @param view : receiver view
     */
    @Override
    public void execute(View view) {

    }
}
