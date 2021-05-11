package it.polimi.ingsw.messages.actions;

import it.polimi.ingsw.model.cards.DevelopmentCard;
import it.polimi.ingsw.view.client.View;
import it.polimi.ingsw.view.server.VirtualView;

import java.io.Serializable;

/**
 * message sent when a client chooses to activate the production on a development card
 * @author Nemanja Antonic
 */
public class ActivateCardProductionMessage implements Serializable, ActionMessage {
    private final ActionMessageType messageType;
    private DevelopmentCard choice;
    boolean accepted;

    /**
     * Server-side constructor to create the message
     * @param choice: target development card
     * @param accepted: result of the request
     */
    public ActivateCardProductionMessage(DevelopmentCard choice, boolean accepted) {
        this.choice = choice;
        this.accepted=accepted;
        this.messageType = ActionMessageType.ACTIVATE_DEVELOPMENT_CARD_PRODUCTION;
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
