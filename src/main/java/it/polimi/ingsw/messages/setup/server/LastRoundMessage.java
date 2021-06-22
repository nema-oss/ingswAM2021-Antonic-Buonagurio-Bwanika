package it.polimi.ingsw.messages.setup.server;

import it.polimi.ingsw.messages.setup.SetupMessage;
import it.polimi.ingsw.view.client.View;
import it.polimi.ingsw.view.server.VirtualView;

import java.io.Serializable;

public class LastRoundMessage implements SetupMessage, Serializable {


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
        view.showLastRound();
    }
}
