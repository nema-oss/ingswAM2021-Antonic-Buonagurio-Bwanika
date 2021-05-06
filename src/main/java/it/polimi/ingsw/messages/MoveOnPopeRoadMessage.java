package it.polimi.ingsw.messages;

import it.polimi.ingsw.view.client.View;
import it.polimi.ingsw.view.server.VirtualView;

import java.io.Serializable;

public class MoveOnPopeRoadMessage implements Serializable, Message {
    private final MessageType messageType;
    int moves;

    public MoveOnPopeRoadMessage(int moves) {
        this.moves = moves;
        messageType = MessageType.MOVE_ON_POPEROAD;
    }

    public void execute(View view){
        //method in view to show the choice
    }

    public void execute(VirtualView virtualView){
        //method in virtualView
    }
    @Override
    public MessageType getType() {
        return messageType;
    }
}
