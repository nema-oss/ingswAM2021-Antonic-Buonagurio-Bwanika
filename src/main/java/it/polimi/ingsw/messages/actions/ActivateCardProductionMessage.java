package it.polimi.ingsw.messages.actions;

import it.polimi.ingsw.model.cards.DevelopmentCard;
import it.polimi.ingsw.view.client.View;
import it.polimi.ingsw.view.server.VirtualView;

import java.io.Serializable;
import java.util.List;

/**
 * message sent when a client chooses to activate the production on a development card
 * @author Nemanja Antonic
 */
public class ActivateCardProductionMessage implements Serializable, ActionMessage {
    private final String user;
    private List<DevelopmentCard> choice;
    boolean accepted;

    /**
     * Server-side constructor to create the message
     * @param choice: target development card
     * @param accepted: result of the request
     */
    public ActivateCardProductionMessage(String user, List<DevelopmentCard> choice, boolean accepted) {
        this.choice = choice;
        this.user = user;
        this.accepted=accepted;
    }
    /**
     * Execute the request server side
     * @param virtualView: receiver view
     */
    public void execute(VirtualView virtualView){
        virtualView.activateProductionDevelopmentCard(user,choice);
    }
    /**
     * Execute the request client side
     * @param view: receiver view
     */
    public void execute(View view){
        view.showProductionRequestResults(user, accepted);
    }

    /**
     * Get the accepted attribute
     * @return accepted
     */
    public boolean isAccepted() {
        return accepted;
    }

}
