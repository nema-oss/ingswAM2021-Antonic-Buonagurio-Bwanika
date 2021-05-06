package it.polimi.ingsw.view.server;

import it.polimi.ingsw.controller.ControllerInterface;
import it.polimi.ingsw.messages.*;

import java.net.Socket;
import java.util.List;

/**
 * this class represent the virtual view in the server and abstract the network to the controller. It observer the model
 * to communicate updates to the View in the client. It uses the MessageManager to create, manage and send messages
 * between controller and client.
 */
public class VirtualView {

    private final int lobbyID;
    private final ControllerInterface matchController;
    private List<Socket> clients;
    private boolean isStarted;

    public VirtualView(ControllerInterface matchController, int lobbyID) {
        this.lobbyID = lobbyID;
        this.matchController = matchController;
    }


    public synchronized int getLobbySize() {
        return clients.size();
    }

    public void addInWaitList(Socket client) {
    }

    public void toDoLogin(Socket client) {

        LoginMessage loginMessage = new LoginMessage();
        sendMessage(client, loginMessage.getType());
    }



    public synchronized boolean isActive() {
        return isStarted;
    }

    public void sendMessage(Socket socket, MessageType messageType){
        boolean success = new MessageSender(socket,messageType).sendMsg();
        if(!success) clientDown(socket);
    }

    public void clientDown(Socket socket) {
    }
}

