package it.polimi.ingsw.messages.setup.server;

import it.polimi.ingsw.messages.setup.SetupMessage;
import it.polimi.ingsw.view.client.View;
import it.polimi.ingsw.view.server.VirtualView;

import java.io.Serializable;

/**
 * This message is sent to the losers
 */

public class LoseMessage implements SetupMessage, Serializable {

    private final String winner;

    public LoseMessage(String winner){
        this.winner = winner;
    }
    @Override
    public void execute(VirtualView virtualView) {

    }


    @Override
    public void execute(View view) {
        view.showYouLose(winner);
    }
}