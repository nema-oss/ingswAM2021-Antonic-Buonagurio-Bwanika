package it.polimi.ingsw.messages.setup;

import it.polimi.ingsw.messages.MessageType;
import it.polimi.ingsw.messages.actions.ActionMessageType;
import it.polimi.ingsw.view.client.View;

import java.io.Serializable;

public class BeginMessage implements Serializable, SetupMessage {
    private final SetupMessageType messageType;

    public BeginMessage() {
        this.messageType = SetupMessageType.START_GAME;
    }

    public void execute(View view){
        view.showMatchStarted();
    }

    public SetupMessageType getType() {
        return messageType;
    }
}
