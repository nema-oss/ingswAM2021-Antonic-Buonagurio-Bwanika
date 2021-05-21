package it.polimi.ingsw.messages.actions;

import it.polimi.ingsw.model.gameboard.Resource;
import it.polimi.ingsw.view.client.View;
import it.polimi.ingsw.view.server.VirtualView;

import java.io.Serializable;
import java.util.List;

/**
 * message sent when a client chooses to buy resources
 * @author Nemanja Antonic
 */
public class BuyResourcesMessage implements Serializable, ActionMessage {

    private final ActionMessageType messageType;
    private String user;
    private int x;
    private int y;
    private boolean accepted;
    private List<Resource> resources;

    /**
     * Server-side constructor to create the message
     * @param user the current player
     * @param x: x coordinate of the target developmentCard
     * @param y: y coordinate of the target developmentCard
     * @param accepted: result of the request
     */
    public BuyResourcesMessage(String user, int x, int y, boolean accepted) {
        this.x = x;
        this.y = y;
        this.user = user;
        this.accepted = accepted;
        messageType = ActionMessageType.BUY_RESOURCES;
    }
    /**
     * This method asks the user to select where to place it's resources if the buy request has been accepted otherwise
     * it asks to repeat the request
     * @param view: receiver view
     */
    public void execute(View view){

        if(!isAccepted())
            view.setBuyResourceAction(accepted);
        else {
            view.setBoughtResources(resources);
            view.setPlaceResourcesAction();
        }
    }
    /**
     * Execute the request server side
     * @param virtualView: receiver view
     */
    public void execute(VirtualView virtualView){
        virtualView.buyResources(user,x,y);
    }
    /**
     * Get the message type
     * @return the message type
     */
    public ActionMessageType getType() {
        return messageType;
    }
    /**
     * Get the accepted attribute
     * @return accepted
     */
    public boolean isAccepted() {
        return accepted;
    }

    public void setResourceList(List<Resource> resourceList) {
        this.resources = resourceList;
    }
}
