package it.polimi.ingsw.messages.actions;

import it.polimi.ingsw.messages.MessageType;
import it.polimi.ingsw.model.cards.leadercards.LeaderCard;
import it.polimi.ingsw.view.client.View;
import it.polimi.ingsw.view.server.VirtualView;

import java.io.Serializable;
import java.util.ArrayList;

public class ActivateLeaderCardMessage implements Serializable, ActionMessage{

    private final ActionMessageType messageType;
    private LeaderCard choice;
    boolean accepted;

    public ActivateLeaderCardMessage(LeaderCard choice, boolean accepted) {
        this.choice = choice;
        this.accepted = accepted;
        messageType = ActionMessageType.ACTIVATE_LEADERCARD;
    }

    public void execute(View view) {
        //method in view to show the choice
    }

    public void execute(VirtualView virtualView) {
        //method in virtualView
    }

    public ActionMessageType getType() {
        return messageType;
    }
}
