package it.polimi.ingsw.messages.setup;

import it.polimi.ingsw.view.client.View;
import it.polimi.ingsw.view.server.VirtualView;

import java.io.Serializable;

/**
 * message sent when the game begins
 * @author Nemanja Antonic
 */
public class BeginMessage implements Serializable, SetupMessage {
    private final SetupMessageType messageType;

    /**
     * Server-side constructor to create the message
     */
    public BeginMessage() {
        this.messageType = SetupMessageType.START_GAME;
    }

    @Override
    public void execute(VirtualView virtualView) {

    }

    /**
     * Execute the request client side
     * @param view: receiver view
     */
    public void execute(View view){
        view.showMatchStarted();
    }
    /**
     * Get the message type
     * @return the message type
     */
    public SetupMessageType getType() {
        return messageType;
    }
}
