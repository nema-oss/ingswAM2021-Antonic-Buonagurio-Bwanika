package it.polimi.ingsw.messages.setup.client;

import it.polimi.ingsw.messages.setup.SetupMessage;
import it.polimi.ingsw.view.client.View;
import it.polimi.ingsw.view.client.viewComponents.ClientPlayerBoard;
import it.polimi.ingsw.view.server.VirtualView;

import java.io.Serializable;

/**
 * the message to update the layer's board
 */
public class UpdateClientPlayerBoardsMessage implements SetupMessage, Serializable {


    private final ClientPlayerBoard clientPlayerBoard;
    private final String user;

    public UpdateClientPlayerBoardsMessage(String user, ClientPlayerBoard clientPlayerBoard){
        this.user = user;
        this.clientPlayerBoard = clientPlayerBoard;
    }

    /**
     * Execute the request server side
     * @param virtualView: receiver view
     */
    @Override
    public void execute(VirtualView virtualView) {
        virtualView.sendPlayerBoardUpdateToOthers(this);
    }

    /**
     * Execute the request client side
     * @param view: receiver view
     */
    @Override
    public void execute(View view) {
        view.updateOtherPlayerBoards(user,clientPlayerBoard);
    }

    /**
     * @return the player's nickname
     */
    public String getUser() {
        return user;
    }
}
