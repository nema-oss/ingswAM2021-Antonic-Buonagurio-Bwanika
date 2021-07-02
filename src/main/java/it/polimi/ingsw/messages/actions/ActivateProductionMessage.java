package it.polimi.ingsw.messages.actions;

import it.polimi.ingsw.view.client.View;
import it.polimi.ingsw.view.server.VirtualView;

import java.io.Serializable;

/**
 * message sent when a client chooses to activate the production
 * @author Nemanja Antonic
 */
public class ActivateProductionMessage implements Serializable, ActionMessage {

    private final String user;

    /**
     * Server-side constructor to create the message
     */
    public ActivateProductionMessage(String user) {
        this.user = user;
    }
    /**
     * Execute the request client side
     * @param view: receiver view
     */
    public void execute(View view){
        //method in view to show the choice
    }
    /**
     * Execute the request server side
     * @param virtualView: receiver view
     */
    public void execute(VirtualView virtualView){
        virtualView.activateProduction(user);
    }

}
