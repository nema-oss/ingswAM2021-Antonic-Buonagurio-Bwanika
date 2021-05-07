package it.polimi.ingsw.messages.setup;

import it.polimi.ingsw.messages.MessageType;
import it.polimi.ingsw.messages.actions.ActionMessageType;
import it.polimi.ingsw.view.client.View;
import it.polimi.ingsw.view.server.VirtualView;

import java.io.Serializable;

public class GameModeMessage implements Serializable, SetupMessage {
    private final SetupMessageType messageType;

    public GameModeMessage() {
        this.messageType = SetupMessageType.GAME_MODE;
    }

    public void execute(View view){
        //method in View to show the resources
    }

    public void execute(VirtualView virtualView){
        //method in virtualView
    }


    public SetupMessageType getType() {
        return messageType;
    }
}
