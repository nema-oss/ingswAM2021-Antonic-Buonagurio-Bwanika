package it.polimi.ingsw.view.server;

import java.net.Socket;

public class InGameDisconnectionHandler {
    public void onClientDown(VirtualView virtualView, String disconnectedPlayer) {
        if (virtualView.getLobbySize() > 1)
            virtualView.inGameDisconnection(disconnectedPlayer);
        else{
            virtualView.endMatch();
        }
    }

}
