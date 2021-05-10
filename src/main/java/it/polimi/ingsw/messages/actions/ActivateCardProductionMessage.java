package it.polimi.ingsw.messages.actions;

import it.polimi.ingsw.model.cards.DevelopmentCard;
import it.polimi.ingsw.view.client.View;
import it.polimi.ingsw.view.server.VirtualView;

import java.io.Serializable;

public class ActivateCardProductionMessage implements Serializable, ActionMessage {
    private final ActionMessageType messageType;
    private DevelopmentCard choice;
    boolean accepted;

    public ActivateCardProductionMessage(DevelopmentCard choice, boolean accepted) {
        this.choice = choice;
        this.accepted=accepted;
        this.messageType = ActionMessageType.ACTIVATE_DEVELOPMENT_CARD_PRODUCTION;
    }

    public void execute(VirtualView virtualView){

    }

    public void execute(View view){

    }

    public ActionMessageType getMessageType() {
        return messageType;
    }
}
