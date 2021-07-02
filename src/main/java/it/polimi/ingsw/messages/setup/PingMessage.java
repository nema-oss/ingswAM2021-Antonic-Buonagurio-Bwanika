package it.polimi.ingsw.messages.setup;

import it.polimi.ingsw.view.client.View;
import it.polimi.ingsw.view.server.VirtualView;

import java.io.Serializable;

/**
 * the ping message that check if the connection between client and server is still active
 */

public class PingMessage implements SetupMessage, Serializable {


    /**
     * Server-side constructor to create the message
     */
    public PingMessage() {

    }

    /**
     * Execute the request server side
     * @param virtualView: receiver view
     */
    @Override
    public void execute(VirtualView virtualView) {

    }

    /**
     * Execute the request client side
     * @param view: receiver view
     */
    public void execute(View view) {
    }
}
