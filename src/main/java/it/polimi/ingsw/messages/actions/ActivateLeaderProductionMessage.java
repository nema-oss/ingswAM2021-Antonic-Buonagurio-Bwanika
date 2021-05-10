package it.polimi.ingsw.messages.actions;

import it.polimi.ingsw.model.cards.DevelopmentCard;
import it.polimi.ingsw.model.cards.leadercards.LeaderCard;
import it.polimi.ingsw.view.client.View;
import it.polimi.ingsw.view.server.VirtualView;

import java.io.Serializable;

public class ActivateLeaderProductionMessage implements Serializable, ActionMessage {
    private final ActionMessageType messageType;
    private LeaderCard choice;
    boolean accepted;

    public ActivateLeaderProductionMessage(LeaderCard choice, boolean accepted) {
        this.choice = choice;
        this.accepted = accepted;
        this.messageType = ActionMessageType.ACTIVATE_LEADERCARD_PRODUCTION;
    }

    public void execute(VirtualView virtualView){

    }

    public void execute(View view){

    }

    public ActionMessageType getMessageType() {
        return messageType;
    }
}
