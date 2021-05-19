package it.polimi.ingsw.messages.setup.server;

import it.polimi.ingsw.messages.setup.SetupMessage;
import it.polimi.ingsw.view.client.View;
import it.polimi.ingsw.view.server.VirtualView;

import java.io.Serializable;

public class SelectResourcesMessage implements SetupMessage, Serializable {


    private final int numberOfResources;

    public SelectResourcesMessage(int numberOfResourcesToSelect){
        this.numberOfResources = numberOfResourcesToSelect;
    }
    @Override
    public void execute(VirtualView virtualView) {

    }

    @Override
    public void execute(View view) {
        view.setResourceTypeChoice(this.numberOfResources);
    }

    public int getNumberOfResources() {
        return numberOfResources;
    }
}
