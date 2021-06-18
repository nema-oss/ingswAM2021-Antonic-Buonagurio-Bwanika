package it.polimi.ingsw.view.server;

import it.polimi.ingsw.network.server.ClientHandler;

import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.net.Socket;

public interface InGameReconnectionHandler {

    void onClientDown(VirtualView virtualView, String disconnectedPlayer);

    void playerReconnection(String disconnectedPlayer, Socket socket, ObjectOutputStream outputStream);

    boolean hasDisconnectedBefore(String nickname);

    void addClientHandler(String user, ClientHandler clientHandler);
}
