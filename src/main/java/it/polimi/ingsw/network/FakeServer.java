package it.polimi.ingsw.network;

import it.polimi.ingsw.network.server.ClientHandler;
import it.polimi.ingsw.view.server.InGameReconnectionHandler;
import it.polimi.ingsw.view.server.VirtualView;

import java.io.ObjectOutputStream;
import java.net.Socket;

public class FakeServer implements InGameReconnectionHandler {

    @Override
    public void onClientDown(VirtualView virtualView, String disconnectedPlayer) {

    }

    @Override
    public void playerReconnection(String disconnectedPlayer, Socket socket, ObjectOutputStream outputStream) {

    }

    @Override
    public boolean hasDisconnectedBefore(String nickname) {
        return false;
    }

    @Override
    public void addClientHandler(String user, ClientHandler clientHandler) {

    }

    @Override
    public void endMatch(int lobbyID) {

    }
}
