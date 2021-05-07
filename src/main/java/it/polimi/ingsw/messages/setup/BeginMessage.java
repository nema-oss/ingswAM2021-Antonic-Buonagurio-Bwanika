package it.polimi.ingsw.messages.setup;

import it.polimi.ingsw.messages.MessageType;
import it.polimi.ingsw.view.client.View;

import java.io.Serializable;

public class BeginMessage implements Serializable, SetupMessage {
    private final MessageType messageType;

    public BeginMessage() {
        this.messageType = MessageType.START_GAME;
    }

    public void execute(View view){
        view.showMatchStarted();
    }
    @Override
    public MessageType getType() {
        return null;
    }
}
