package it.polimi.ingsw.view.server;

import it.polimi.ingsw.messagges.Message;

import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.List;

/**
 * this class represent the virtual view in the server and abstract the network to the controller. It observer the model
 * to communicate updates to the View in the client. It uses the MessageManager to create, manage and send messages
 * between controller and client.
 */
public class VirtualView {

    private List<Socket> clients;

    public int getLobbySize() {
        return clients.size();
    }

    public void addInWaitList(Socket client) {
    }

    public void toDoLogin(Socket client, ObjectOutputStream output) {
    }

    public void processMessage(Message command, Socket client, ObjectOutputStream output) {
    }
}

