package it.polimi.ingsw.messages.setup.server;

import it.polimi.ingsw.messages.setup.SetupMessage;
import it.polimi.ingsw.view.client.View;
import it.polimi.ingsw.view.server.VirtualView;

import java.io.Serializable;
import java.util.Map;

/**
 * Message to notify the game is finished due to errors
 * @author Nemanja Antonic
 */
public class EndGameMessage implements SetupMessage, Serializable {
    private final Map<String,Integer> leaderboard;

    /**
     * Server-side constructor to create the message
     * @param leaderboard the game leaderboard
     */
    public EndGameMessage(Map<String,Integer> leaderboard) {
        this.leaderboard = leaderboard;
    }

    @Override
    public void execute(VirtualView virtualView) {

    }

    /**
     * Execute the request on the client
     * @param view: receiver view
     */
    public void execute(View view){
        view.showEndGame(leaderboard);
    }

}
