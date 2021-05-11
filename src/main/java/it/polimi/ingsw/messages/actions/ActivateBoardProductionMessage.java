package it.polimi.ingsw.messages.actions;

import it.polimi.ingsw.model.cards.DevelopmentCard;
import it.polimi.ingsw.model.gameboard.ResourceType;
import it.polimi.ingsw.view.client.View;
import it.polimi.ingsw.view.server.VirtualView;

import java.io.Serializable;

/**
 * message sent when a client chooses to activate the default board production
 * @author Nemanja Antonic
 */
public class ActivateBoardProductionMessage implements Serializable, ActionMessage {
    private final ActionMessageType messageType;
    private ResourceType choice;
    boolean accepted;

    /**
     * Server-side constructor to create the message
     * @param choice: resource to get
     * @param accepted: result of request
     */
    public ActivateBoardProductionMessage(ResourceType choice, boolean accepted) {
        this.choice = choice;
        this.accepted=accepted;
        this.messageType = ActionMessageType.ACTIVATE_BOARD_PRODUCTION;
    }
    /**
     * Execute the request server side
     * @param virtualView: receiver view
     */
    public void execute(VirtualView virtualView){

    }
    /**
     * Execute the request client side
     * @param view: receiver view
     */
    public void execute(View view){

    }
    /**
     * Get the message type
     * @return the message type
     */
    public ActionMessageType getMessageType() {
        return messageType;
    }

    /**
     * Get the accepted attribute
     * @return accepted
     */
    public boolean isAccepted() {
        return accepted;
    }
}
