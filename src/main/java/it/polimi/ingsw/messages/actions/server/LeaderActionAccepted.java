package it.polimi.ingsw.messages.actions.server;

import it.polimi.ingsw.messages.actions.ActionMessage;
import it.polimi.ingsw.model.cards.leadercards.LeaderCard;
import it.polimi.ingsw.view.client.View;
import it.polimi.ingsw.view.server.VirtualView;

import java.io.Serializable;

public class LeaderActionAccepted implements ActionMessage, Serializable {

    private final LeaderCard card;
    private final boolean activate;

    public LeaderActionAccepted(LeaderCard card, boolean activate){
        this.card = card;
        this.activate = activate;
    }

    @Override
    public void execute(VirtualView virtualView) {
    }

    @Override
    public void execute(View view) {
        view.showAcceptedLeaderAction(card,activate);
    }
}
