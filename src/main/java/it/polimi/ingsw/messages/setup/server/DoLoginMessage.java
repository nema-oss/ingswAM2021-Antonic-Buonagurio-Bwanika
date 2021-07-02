package it.polimi.ingsw.messages.setup.server;

import it.polimi.ingsw.messages.setup.SetupMessage;
import it.polimi.ingsw.view.client.View;
import it.polimi.ingsw.view.server.VirtualView;

import java.io.Serializable;

/**
 * the login message the server sends to each client after establishing the connection
 * @author Nemanja Antonic
 */

public class DoLoginMessage implements SetupMessage, Serializable {

    private boolean isFirstPlayer;

    /**
     * Server-side constructor to create the message
     */
    public DoLoginMessage() {
        isFirstPlayer = false;
    }

    /**
     * Execute the request client side
     * @param view: receiver view
     */
    public void execute(View view){
       view.showLogin(this);
    }

    /**
     * Execute the request server side
     * @param virtualView: receiver view
     */
    public void execute(VirtualView virtualView){
        //method in virtualView
    }


    /**
     * Set to true if it's the first player in the lobby
     * @param firstPlayer
     */
    public void setFirstPlayer(boolean firstPlayer) {
        isFirstPlayer = firstPlayer;
    }

    /**
     * Get isFirstPlayer
     * @return isFirstPlayer
     */
    public boolean isFirstPlayer() {
        return isFirstPlayer;
    }
}
