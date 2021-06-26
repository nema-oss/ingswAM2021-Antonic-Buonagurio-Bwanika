package it.polimi.ingsw.messages.actions;

import it.polimi.ingsw.model.cards.leadercards.LeaderCard;
import it.polimi.ingsw.model.gameboard.Producible;
import it.polimi.ingsw.model.gameboard.ResourceType;
import it.polimi.ingsw.view.client.View;
import it.polimi.ingsw.view.server.VirtualView;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

/**
 * message sent when a client chooses to activate the production on an active leaderCard
 * @author Nemanja Antonic
 */
public class ActivateLeaderProductionMessage implements Serializable, ActionMessage {
    private final ActionMessageType messageType;
    private final String user;
    private Map<LeaderCard, ResourceType> choice;
    boolean accepted;

    /**
     * Server-side constructor to create the message
     * @param choice: target leaderCard
     * @param accepted: the result of the request
     */
    public ActivateLeaderProductionMessage(String user, Map<LeaderCard, ResourceType> choice, boolean accepted) {
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
        virtualView.activateProductionLeaderCard(user,choice);
    }
    /**
     * Execute the request client side
     * @param view: receiver view
     */
    public void execute(View view){
        view.showProductionRequestResults(accepted);
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
