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
    private ArrayList<Resource> resources; //up to 3 possible resources to move (1 per type)
    private ArrayList<Integer> toShelves; //where i want to move the resources to

    public MoveDepositMessage(ArrayList<Resource> resources, ArrayList<Integer> toShelves) {
        this.resources = resources;
        this.toShelves = toShelves;
        messageType = ActionMessageType.MOVE_DEPOSIT;
    }

    public void execute(View view){
        //method in view to show the choice
    }

    public void execute(VirtualView virtualView){
        //method in virtualView
    }
    @Override
    public ActionMessageType getType() {
        return messageType;
    }
}