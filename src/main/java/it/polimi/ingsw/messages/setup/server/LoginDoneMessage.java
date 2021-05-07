package it.polimi.ingsw.messages.setup.server;

import it.polimi.ingsw.messages.MessageType;
import it.polimi.ingsw.messages.actions.ActionMessageType;
import it.polimi.ingsw.messages.setup.SetupMessage;
import it.polimi.ingsw.messages.setup.SetupMessageType;
import it.polimi.ingsw.view.client.View;

import java.io.Serializable;

public class LoginDoneMessage implements Serializable, SetupMessage {
    private final SetupMessageType messageType;

    public LoginDoneMessage() {
        this.messageType = SetupMessageType.LOGIN_DONE;
    }

    public void execute(View view){
        view.showLoginDone();
    }



    public SetupMessageType getType() {
        return messageType;
    }
}
