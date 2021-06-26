package it.polimi.ingsw.messages.actions;

import it.polimi.ingsw.messages.actions.ActionMessage;
import it.polimi.ingsw.model.cards.leadercards.LeaderCard;
import it.polimi.ingsw.view.client.View;
import it.polimi.ingsw.view.server.VirtualView;

import java.io.Serializable;
import java.util.Map;

public class LeaderActionMessage implements ActionMessage, Serializable {

    private final boolean accepted;
    private final Map<LeaderCard,Boolean> userChoice;
    private final String user;

    public LeaderActionMessage(String user,Map<LeaderCard, Boolean> userChoice, boolean accepted) {
        this.userChoice = userChoice;
        this.accepted = accepted;
        this.user = user;
    }

    /**
     * Execute the request server side
     * @param virtualView: receiver view
     */
    @Override
    public void execute(VirtualView virtualView) {

        for(LeaderCard leaderCard: userChoice.keySet()) {
            if(userChoice.get(leaderCard)) {
                virtualView.activateLeaderCard(user, leaderCard);
            }
            else {
                virtualView.discardLeaderCard(user, leaderCard);
            }
        }
    }

    /**
     * Execute the request client side
     * @param view: receiver view
     */
    @Override
    public void execute(View view) {
    }
}
