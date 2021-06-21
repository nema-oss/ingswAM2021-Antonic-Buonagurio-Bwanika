package it.polimi.ingsw.messages.setup.server;

import it.polimi.ingsw.messages.setup.SetupMessage;
import it.polimi.ingsw.view.client.View;
import it.polimi.ingsw.view.server.VirtualView;

import java.io.Serializable;

/**
 * message sent to tell the player to choose his initial resources
 */
public class SelectResourcesMessage implements SetupMessage, Serializable {


    private final int numberOfResources;

    public SelectResourcesMessage(int numberOfResourcesToSelect){
        this.numberOfResources = numberOfResourcesToSelect;
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
        view.setResourceTypeChoice(this.numberOfResources);
    }

}
