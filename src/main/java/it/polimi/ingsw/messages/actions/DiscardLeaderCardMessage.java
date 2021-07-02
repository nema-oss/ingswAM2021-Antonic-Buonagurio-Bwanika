package it.polimi.ingsw.messages.actions;

import it.polimi.ingsw.model.cards.leadercards.LeaderCard;
import it.polimi.ingsw.view.client.View;
import it.polimi.ingsw.view.server.VirtualView;

import java.io.Serializable;

/**
 * message sent when a client chooses to discard a leaderCard in their hand
 * @author Nemanja Antonic
 */
public class DiscardLeaderCardMessage implements Serializable, ActionMessage {
    private LeaderCard choice;

    /**
     * Server-side constructor to create the message
     * @param choice: target leaderCard
     */
    public DiscardLeaderCardMessage(LeaderCard choice) {
        this.choice = choice;
    }
    /**
     * Execute the request client side
     * @param view: receiver view
     */
    public void execute(View view){
        //method in view to show the choice
    }
    /**
     * Execute the request server side
     * @param virtualView: receiver view
     */
    public void execute(VirtualView virtualView){
        //method in virtualView
    }

}
