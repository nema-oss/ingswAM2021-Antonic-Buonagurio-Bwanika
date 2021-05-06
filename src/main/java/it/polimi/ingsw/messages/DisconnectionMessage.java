package it.polimi.ingsw.messages;

import it.polimi.ingsw.view.client.View;

import java.io.Serializable;

/**
 * Message to notify the player that another player has disconnected.
 * @author Nemanja Antonic
 */

public class DisconnectionMessage implements Serializable, Message {
    private final MessageType messageType;
    private final String disconnectedNickname;

    /**
     * Server-side constructor to create the message
     * @param disconnectedNickname: nickname of the disconnected user
     */

    public DisconnectionMessage(String disconnectedNickname) {
        this.messageType = MessageType.DISCONNECTION;
        this.disconnectedNickname = disconnectedNickname;
    }

    /**
     * Execute the request on the client
     * @param view: receiver view
     */
    public void execute(View view) {
        view.showAnotherClientDisconnection(disconnectedNickname);
    }

    /**
     * Get the message type
     * @return the message type
     */
    @Override
    public MessageType getType() {
        return messageType;
    }
}
