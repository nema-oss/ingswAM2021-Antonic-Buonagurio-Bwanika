package it.polimi.ingsw.messages.actions.server;

import it.polimi.ingsw.messages.MessageType;
import it.polimi.ingsw.messages.actions.ActionMessage;
import it.polimi.ingsw.messages.actions.ActionMessageType;
import it.polimi.ingsw.view.client.View;
import it.polimi.ingsw.view.server.VirtualView;

import java.io.Serializable;

public class MoveOnPopeRoadMessage implements Serializable, ActionMessage {
    private final ActionMessageType messageType;
    int moves;

    public MoveOnPopeRoadMessage(int moves) {
        this.moves = moves;
        messageType = ActionMessageType.MOVE_ON_POPEROAD;
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
