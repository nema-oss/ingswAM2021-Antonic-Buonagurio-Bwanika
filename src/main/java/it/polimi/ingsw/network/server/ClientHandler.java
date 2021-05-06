package it.polimi.ingsw.network.server;

import it.polimi.ingsw.messages.*;
import it.polimi.ingsw.view.server.VirtualView;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

/**
 * this class manage the connection between one single client and the server
 */

public class ClientHandler implements Runnable{

    private final Socket client;
    private final VirtualView virtualView;
    private final int lobbyNumber;
    private ObjectOutputStream output;
    private ObjectInputStream input;
    private boolean isConnect;


    public ClientHandler(Socket socket, VirtualView virtualView, int lobbyNumber) {
        this.client = socket;
        this.virtualView = virtualView;
        this.lobbyNumber = lobbyNumber;
        isConnect = false;
    }

    /**
     * This method allows to receive messages from the socket and process them.
     */

    @Override
    public void run() {

        System.out.println("[CLIENT] " + client + " has connected in lobby number " + lobbyNumber + "!");

        new Thread(this::pingClient).start();

        try {
            input = new ObjectInputStream(client.getInputStream());
        } catch (IOException e) {
            System.out.println("could not open connection to " + client.getInetAddress());
            return;
        }

        virtualView.addInWaitList(client);
        virtualView.toDoLogin(client); // asks the client to login

        try {
            handleClientConnection();
        } catch (IOException e) {
            System.out.println("client " + client.getInetAddress() + " connection dropped");
        }

        try {
            client.close();
        } catch (IOException e) {
            clientDisconnection();
        }

    }

    /**
     * this method handle the sending, parsing and creation of messages between client and server
     * @throws IOException ...
     */

    private void handleClientConnection() throws IOException{

        try {
            while (true) {

                Object next = input.readObject();
                Message message = (Message) next;
                if(message instanceof EndMessage) break;
                if(!(message instanceof PingMessage))
                    processMessage(message); // from this method we also send the message back to the client
            }
            input.close();
            System.out.println("[SERVER] connection closed with" + client);
            client.close();
        } catch (ClassNotFoundException | ClassCastException e) {
            System.out.println("[SERVER] invalid stream from client");
            clientDisconnection();
        }


    }

    /**
     * This method process messages using the Message manager
     * @param message the message to parse
     */

    public void processMessage(Message message){
        new MessageManager().parseMessage(virtualView,message);
    }


    /**
     * This method handles the involuntary disconnection of a client
     */
    private void clientDisconnection(){
        System.err.println("Client " + client + " has disconnected!");
        try {
            client.close();
        } catch (IOException ioException) {
            ioException.printStackTrace();
        }
        // this should manage the disconnection in game from the logic side ---> advanced functionality
        if(virtualView.isActive())
            virtualView.clientDown(client);
    }


    /**
     * This method send a ping message to the client to check if the connection is still active
     */
    private void pingClient() {
        do{
            try {
                Thread.sleep(1500);
                new MessageSender(client, MessageType.PING).sendMsg();
            } catch (InterruptedException ignored) {
            }
        }while(!client.isClosed());

        Thread.currentThread().interrupt();
    }
}


