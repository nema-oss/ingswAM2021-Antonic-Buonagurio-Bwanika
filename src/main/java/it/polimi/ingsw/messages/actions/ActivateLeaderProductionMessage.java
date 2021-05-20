package it.polimi.ingsw.messages.actions;

import it.polimi.ingsw.model.cards.leadercards.LeaderCard;
import it.polimi.ingsw.view.client.View;
import it.polimi.ingsw.view.server.VirtualView;

import java.io.Serializable;
import java.util.List;

/**
 * message sent when a client chooses to activate the production on an active leaderCard
 * @author Nemanja Antonic
 */
public class ActivateLeaderProductionMessage implements Serializable, ActionMessage {
    private final ActionMessageType messageType;
    private final String user;
    private List<LeaderCard> choice;
    boolean accepted;

    /**
     * Server-side constructor to create the message
     * @param choice: target leaderCard
     * @param accepted: the result of the request
     */
    public ActivateLeaderProductionMessage(String user,List<LeaderCard> choice, boolean accepted) {
        this.choice = choice;
        this.user = user;
        this.accepted = accepted;
        this.messageType = ActionMessageType.ACTIVATE_LEADERCARD_PRODUCTION;
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
