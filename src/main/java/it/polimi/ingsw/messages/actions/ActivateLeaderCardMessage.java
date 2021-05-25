package it.polimi.ingsw.messages.actions;

import it.polimi.ingsw.model.cards.leadercards.LeaderCard;
import it.polimi.ingsw.view.client.View;
import it.polimi.ingsw.view.server.VirtualView;

import java.io.Serializable;

/**
 * message sent from the client to activate a leaderCard
 * @author Nemanja Antonic
 */
public class ActivateLeaderCardMessage implements Serializable, ActionMessage{

    private final ActionMessageType messageType;
    private LeaderCard choice;
    boolean accepted;

    /**
     * Server-side constructor to create the message
     * @param choice: target leaderCard
     * @param accepted: result of the request
     */
    public ActivateLeaderCardMessage(LeaderCard choice, boolean accepted) {
        this.choice = choice;
        this.accepted = accepted;
        messageType = ActionMessageType.ACTIVATE_LEADERCARD;
    }
    /**
     * Execute the request client side
     * @param view: receiver view
     */
    public void execute(View view) {
        System.out.println(accepted);

    }
    /**
     * Execute the request server side
     * @param virtualView: receiver view
     */
    public void execute(VirtualView virtualView) {
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
}
