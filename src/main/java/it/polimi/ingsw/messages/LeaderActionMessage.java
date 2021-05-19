package it.polimi.ingsw.messages;

import it.polimi.ingsw.messages.actions.ActionMessage;
import it.polimi.ingsw.model.cards.leadercards.LeaderCard;
import it.polimi.ingsw.view.client.View;
import it.polimi.ingsw.view.server.VirtualView;

import java.io.Serializable;
import java.util.Map;

public class LeaderActionMessage implements ActionMessage, Serializable {

    public LeaderActionMessage(Map<LeaderCard, Boolean> userChoice, boolean b) {
    }

    @Override
    public void execute(VirtualView virtualView) {

    }

    @Override
    public void execute(View view) {

    }
}
