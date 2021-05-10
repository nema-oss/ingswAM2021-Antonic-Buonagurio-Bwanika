package it.polimi.ingsw.messages.actions;

import it.polimi.ingsw.model.cards.DevelopmentCard;
import it.polimi.ingsw.model.gameboard.ResourceType;
import it.polimi.ingsw.view.client.View;
import it.polimi.ingsw.view.server.VirtualView;

import java.io.Serializable;

public class ActivateBoardProductionMessage implements Serializable, ActionMessage {
    private final ActionMessageType messageType;
    private ResourceType choice;
    boolean accepted;

    public ActivateBoardProductionMessage(ResourceType choice, boolean accepted) {
        this.choice = choice;
        this.accepted=accepted;
        this.messageType = ActionMessageType.ACTIVATE_BOARD_PRODUCTION;
    }

    public void execute(VirtualView virtualView){

    }

    public void execute(View view){

    }

    public ActionMessageType getMessageType() {
        return messageType;
    }
}
