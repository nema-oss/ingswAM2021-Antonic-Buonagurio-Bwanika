package it.polimi.ingsw.messages.setup.server;

import it.polimi.ingsw.messages.setup.SetupMessage;
import it.polimi.ingsw.model.gameboard.ResourceType;
import it.polimi.ingsw.view.client.View;
import it.polimi.ingsw.view.server.VirtualView;


import java.io.Serializable;
import java.util.Map;

/**
 * message sent when a client has to choose the starting resource(s)
 * @author Nemanja Antonic
 */
public class ChooseResourcesMessage implements Serializable, SetupMessage {

    private String user;
    private Map<ResourceType,Integer> resourceChoice;
    private boolean accepted;

    /**
     * Server-side constructor to create the message
     * @param resourceChoice: target resource
     * @param accepted: result of the request
     */
    public ChooseResourcesMessage(String user, Map<ResourceType,Integer> resourceChoice, boolean accepted) {
        this.resourceChoice = resourceChoice;
        this.accepted = accepted;
        this.user = user;
    }


    /**
     * Execute the request client side
     * @param view: receiver view
     */
    public void execute(View view){
        view.showResourceSelectionAccepted(resourceChoice);
    }
    /**
     * Execute the request server side
     * @param virtualView: receiver view
     */
    public void execute(VirtualView virtualView){
        virtualView.chooseResourceType(user,resourceChoice);
    }


    /**
     * Get the accepted attribute
     * @return accepted
     */
    public boolean isAccepted() {
        return accepted;
    }
}
