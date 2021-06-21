package it.polimi.ingsw.messages.setup.server;

import it.polimi.ingsw.messages.setup.SetupMessage;
import it.polimi.ingsw.view.client.View;
import it.polimi.ingsw.view.server.VirtualView;

import java.io.Serializable;

/**
 * message sent to alert a user that it is his turn
 */
public class PlayTurnMessage implements SetupMessage, Serializable {


    private final String user;

    public PlayTurnMessage(String user){
        this.user = user;
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
    @Override
    public void execute(View view) {
        view.showPlayTurn(user);
    }

    /**
     * @return the player's nickname
     */
    public String getUser() {
        return user;
    }
}
