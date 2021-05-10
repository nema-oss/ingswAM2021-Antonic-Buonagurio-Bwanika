package it.polimi.ingsw.messages.actions;

import it.polimi.ingsw.messages.MessageType;
import it.polimi.ingsw.model.gameboard.Resource;
import it.polimi.ingsw.view.client.View;
import it.polimi.ingsw.view.server.VirtualView;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * client may choose to reorganize the deposit at any time
 */

public class MoveDepositMessage implements Serializable, ActionMessage {
    private final ActionMessageType messageType;
    private int resources; //up to 3 possible resources to move (1 per type)
    private int toShelves; //where i want to move the resources to
    private boolean accepted;

    public MoveDepositMessage(int resources, int toShelves, boolean accepted) {
        this.resources = resources;
        this.toShelves = toShelves;
        this.accepted = accepted;
        messageType = ActionMessageType.MOVE_DEPOSIT;
    }

    public void execute(View view){
        //method in view to show the choice
    }

    public void execute(VirtualView virtualView){
        //method in virtualView
    }
    public ActionMessageType getType() {
        return messageType;
    }
}
