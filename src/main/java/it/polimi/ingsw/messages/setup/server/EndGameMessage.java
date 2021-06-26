package it.polimi.ingsw.messages.setup.server;

import it.polimi.ingsw.messages.Message;
import it.polimi.ingsw.messages.MessageType;
import it.polimi.ingsw.messages.setup.SetupMessage;
import it.polimi.ingsw.view.client.View;
import it.polimi.ingsw.view.server.VirtualView;

import java.io.Serializable;
/**
 * Message to notify the game is finished due to errors
 * @author Nemanja Antonic
 */
public class EndGameMessage implements SetupMessage, Serializable {
    private final MessageType messageType;
    private final String winner;

    /**
     * Server-side constructor to create the message
     * @param winner
     */
    public EndGameMessage(String winner) {
        this.messageType = MessageType.END_GAME;
        this.winner = winner;
    }

    @Override
    public void execute(VirtualView virtualView) {

    }

    /**
     * Execute the request on the client
     * @param view: receiver view
     */
    public void execute(View view){
        view.showEndGame(winner);
    }
    /**
     * Get the message type
     * @return the message type
     */
    public MessageType getType() {
        return messageType;
    }
}
