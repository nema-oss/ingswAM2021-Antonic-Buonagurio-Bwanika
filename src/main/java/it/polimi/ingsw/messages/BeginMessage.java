package it.polimi.ingsw.messages;

import it.polimi.ingsw.view.client.View;

import java.io.Serializable;

public class BeginMessage implements Serializable, Message {
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
