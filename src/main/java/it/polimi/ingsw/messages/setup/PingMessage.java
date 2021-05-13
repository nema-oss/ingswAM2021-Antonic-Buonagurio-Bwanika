package it.polimi.ingsw.messages.setup;

import it.polimi.ingsw.messages.Message;
import it.polimi.ingsw.messages.MessageType;
import it.polimi.ingsw.messages.actions.ActionMessageType;
import it.polimi.ingsw.view.client.View;
import it.polimi.ingsw.view.server.VirtualView;

import java.io.Serializable;

/**
 * the ping message that check if the connection between client and server is still active
 */

public class PingMessage implements SetupMessage, Serializable {
    private final MessageType messageType;

    /**
     * Server-side constructor to create the message
     */
    public PingMessage() {
        this.messageType = MessageType.PING;
    }


    /**
     * Get the message type
     * @return the message type
     */
    public MessageType getType() {
        return messageType;
    }

    @Override
    public void execute(VirtualView virtualView) {

    }

    public void execute(View view) {
    }
}
