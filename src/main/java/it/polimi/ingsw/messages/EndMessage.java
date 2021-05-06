package it.polimi.ingsw.messages;

import it.polimi.ingsw.view.client.View;

import java.io.Serializable;
/**
 * Message to notify the game is finished
 * @author Nemanja Antonic
 */
public class EndMessage implements Message, Serializable {
    private final MessageType messageType;

    /**
     * Server-side constructor to create the message
     */
    public EndMessage() {
        this.messageType = MessageType.END;
    }

    /**
     * Execute the request on the client
     * @param view: receiver view
     */
    public void execute(View view){
        view.showEnd();
    }

    @Override
    public MessageType getType() {
        return messageType;
    }
}
