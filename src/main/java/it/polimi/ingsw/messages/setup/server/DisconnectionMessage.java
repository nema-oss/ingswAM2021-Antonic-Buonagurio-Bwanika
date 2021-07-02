package it.polimi.ingsw.messages.setup.server;

import it.polimi.ingsw.messages.Message;
import it.polimi.ingsw.view.client.View;
import it.polimi.ingsw.view.server.VirtualView;

import java.io.Serializable;

/**
 * Message to notify the player that another player has disconnected.
 * @author Nemanja Antonic
 */

public class DisconnectionMessage implements Serializable, Message {
    private final String disconnectedNickname;

    /**
     * Server-side constructor to create the message
     * @param disconnectedNickname: nickname of the disconnected user
     */

    public DisconnectionMessage(String disconnectedNickname) {
        this.disconnectedNickname = disconnectedNickname;
    }

    @Override
    public void execute(VirtualView virtualView) {

    }

    /**
     * Execute the request on the client
     * @param view: receiver view
     */
    public void execute(View view) {
        view.showAnotherClientDisconnection(disconnectedNickname);
    }




    public String getDisconnectedNickname(){
        return disconnectedNickname;
    }
}
