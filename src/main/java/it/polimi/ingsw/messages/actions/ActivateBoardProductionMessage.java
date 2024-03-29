package it.polimi.ingsw.messages.actions;

import it.polimi.ingsw.model.gameboard.Resource;
import it.polimi.ingsw.model.gameboard.ResourceType;
import it.polimi.ingsw.view.client.View;
import it.polimi.ingsw.view.server.VirtualView;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

/**
 * message sent when a client chooses to activate the default board production
 * @author Nemanja Antonic
 */
public class ActivateBoardProductionMessage implements Serializable, ActionMessage {
    private final String user;
    private Map<Resource, List<ResourceType>> choice;
    boolean accepted;

    /**
     * Server-side constructor to create the message
     * @param choice : resource to get
     * @param accepted : result of request
     */
    public ActivateBoardProductionMessage(String user, Map<Resource, List<ResourceType>> choice, boolean accepted) {
        this.choice = choice;
        this.user = user;
        this.accepted=accepted;
    }
    /**
     * Execute the request server side
     * @param virtualView: receiver view
     */
    public void execute(VirtualView virtualView){
        virtualView.activateProductionBoard(user,choice);
    }
    /**
     * Execute the request client side
     * @param view: receiver view
     */
    public void execute(View view){
       view.showProductionRequestResults(user,accepted);
    }

    /**
     * Get the accepted attribute
     * @return accepted
     */
    public boolean isAccepted() {
        return accepted;
    }
}
