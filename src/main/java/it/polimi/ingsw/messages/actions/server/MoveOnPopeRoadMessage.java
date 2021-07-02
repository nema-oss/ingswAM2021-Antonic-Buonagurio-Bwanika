package it.polimi.ingsw.messages.actions.server;

import it.polimi.ingsw.messages.actions.ActionMessage;
import it.polimi.ingsw.view.client.View;
import it.polimi.ingsw.view.server.VirtualView;

import java.io.Serializable;

/**
 * Message sent from server to clients to display the updated PopeRoad of a player after he has advanced
 * @author Nemanja Antonic
 */
public class MoveOnPopeRoadMessage implements Serializable, ActionMessage {
    int position;

    /**
     * Server-side constructor to create the message
     * @param position: current player position
     */
    public MoveOnPopeRoadMessage(int position) {
        this.position = position;
    }
    /**
     * Execute the request client side
     * @param view: receiver view
     */
    public void execute(View view){
        view.updatePlayerPosition(position);
    }
    /**
     * Execute the request server side
     * @param virtualView: receiver view
     */
    public void execute(VirtualView virtualView){
        //method in virtualView
    }

}
