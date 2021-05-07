package it.polimi.ingsw.messages.setup.server;

import it.polimi.ingsw.messages.MessageType;
import it.polimi.ingsw.messages.setup.SetupMessage;
import it.polimi.ingsw.view.client.View;

import java.io.Serializable;

public class LoginDoneMessage implements Serializable, SetupMessage {
    private final MessageType messageType;

    public LoginDoneMessage() {
        this.messageType = MessageType.LOGIN_DONE;
    }

    public void execute(View view){
        view.showLoginDone();
    }


    @Override
    public MessageType getType() {
        return messageType;
    }
}
