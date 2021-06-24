package it.polimi.ingsw.messages.actions.server;

import it.polimi.ingsw.messages.actions.ActionMessage;
import it.polimi.ingsw.model.cards.leadercards.LeaderCard;
import it.polimi.ingsw.view.client.View;
import it.polimi.ingsw.view.server.VirtualView;

import java.io.Serializable;

public class LeaderActionAccepted implements ActionMessage, Serializable {

    private final String user;
    private final LeaderCard card;
    private final boolean activate;

    public LeaderActionAccepted(String user, LeaderCard card, boolean activate){
        this.card = card;
        this.user = user;
        this.activate = activate;
    }

    /**
     * Execute the request server side
     * @param virtualView: receiver view
     */
    @Override
    public void execute(VirtualView virtualView) {
    }

    /**
     * Execute the request client side
     * @param view: receiver view
     */
    @Override
    public void execute(View view) {
        view.showAcceptedLeaderAction(user,card,activate);
    }
}
