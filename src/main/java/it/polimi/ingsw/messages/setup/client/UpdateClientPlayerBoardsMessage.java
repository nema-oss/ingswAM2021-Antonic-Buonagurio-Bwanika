package it.polimi.ingsw.messages.setup.client;

import it.polimi.ingsw.messages.setup.SetupMessage;
import it.polimi.ingsw.view.client.View;
import it.polimi.ingsw.view.client.viewComponents.ClientPlayerBoard;
import it.polimi.ingsw.view.server.VirtualView;

import java.io.Serializable;

public class UpdateClientPlayerBoardsMessage implements SetupMessage, Serializable {


    private final ClientPlayerBoard clientPlayerBoard;
    private final String user;

    public UpdateClientPlayerBoardsMessage(String user, ClientPlayerBoard clientPlayerBoard){
        this.user = user;
        this.clientPlayerBoard = clientPlayerBoard;
    }

    @Override
    public void execute(VirtualView virtualView) {
        virtualView.sendPlayerBoardUpdateToOthers(this);
    }

    @Override
    public void execute(View view) {
        view.updateOtherPlayerBoards(user,clientPlayerBoard);
    }

    public String getUser() {
        return user;
    }
}
