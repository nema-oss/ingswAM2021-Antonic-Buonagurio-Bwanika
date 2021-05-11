package it.polimi.ingsw.messages.setup;

import it.polimi.ingsw.view.client.View;
import it.polimi.ingsw.view.server.VirtualView;

import java.io.Serializable;

/**
 * message sent when a client chooses how many players to play with
 * @author Nemanja Antonic
 */
public class GameModeMessage implements Serializable, SetupMessage {
    private final SetupMessageType messageType;
    private int numPlayers;

    /**
     * Server-side constructor to create the message
     * @param numPlayers: the chosen number of players
     */
    public GameModeMessage(int numPlayers) {
        this.numPlayers = numPlayers;
        this.messageType = SetupMessageType.GAME_MODE;
    }
    /**
     * Execute the request client side
     * @param view: receiver view
     */
    public void execute(View view){
        //method in View to show the resources
    }
    /**
     * Execute the request server side
     * @param virtualView: receiver view
     */
    public void execute(VirtualView virtualView){
        //method in virtualView
    }

    /**
     * Get the message type
     * @return the message type
     */
    public SetupMessageType getType() {
        return messageType;
    }
}
