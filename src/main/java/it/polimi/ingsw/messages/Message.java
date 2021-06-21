package it.polimi.ingsw.messages;

import it.polimi.ingsw.view.client.View;
import it.polimi.ingsw.view.server.VirtualView;

/**
 * this interface represent the message sent between client and server
 */
public interface Message {

    /**
     * Execute the request server side
     * @param virtualView: receiver view
     */
    void execute(VirtualView virtualView);

    /**
     * Execute the request client side
     * @param view: receiver view
     */
    void execute(View view);

}
