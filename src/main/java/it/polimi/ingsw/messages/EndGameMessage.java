package it.polimi.ingsw.messages;

import it.polimi.ingsw.messages.actions.ActionMessageType;
import it.polimi.ingsw.view.client.View;
import it.polimi.ingsw.view.server.VirtualView;

import java.io.Serializable;
/**
 * Message to notify the game is finished
 * @author Nemanja Antonic
 */
public class EndGameMessage implements Message, Serializable {
    private final MessageType messageType;

    /**
     * Server-side constructor to create the message
     */
    public EndGameMessage() {
        this.messageType = MessageType.END_GAME;
    }

    @Override
    public void execute(VirtualView virtualView) {

    }

    /**
     * Execute the request on the client
     * @param view: receiver view
     */
    public void execute(View view){
        view.showEnd();
    }
    /**
     * Get the message type
     * @return the message type
     */
    public MessageType getType() {
        return messageType;
    }
}
