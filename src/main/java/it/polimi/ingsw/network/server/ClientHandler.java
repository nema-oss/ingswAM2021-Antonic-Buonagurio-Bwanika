package it.polimi.ingsw.network.server;

import it.polimi.ingsw.messagges.Message;
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
    private final VirtualView currentMatch;
    private final int lobbyNumber;
    private ObjectOutputStream output;
    private ObjectInputStream input;
    private boolean isConnect;


    public ClientHandler(Socket socket, VirtualView match, int lobbyNumber) {
        this.client = socket;
        this.currentMatch = match;
        this.lobbyNumber = lobbyNumber;
        isConnect = false;
    }


    @Override
    public void run() {

        System.out.println("Client " + client + " has connected in lobby number " + lobbyNumber + "!");

        //new Thread(this::pingClient).start();

        try {
            output = new ObjectOutputStream(client.getOutputStream());
            input = new ObjectInputStream(client.getInputStream());
        } catch (IOException e) {
            System.out.println("could not open connection to " + client.getInetAddress());
            return;
        }

        currentMatch.addInWaitList(client);
        currentMatch.toDoLogin(client,output);

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

    private void handleClientConnection() throws IOException{

        try {
            //currentMatch.toDoLogin(client);
            while (true) {
                /* read commands from the client, process them, and send replies */
                Object next = input.readObject();
                Message command = (Message) next;
                currentMatch.processMessage(command, client, output);
            }
        } catch (ClassNotFoundException | ClassCastException e) {
            System.out.println("invalid stream from client");
        }

    }


    private void clientDisconnection(){
        System.err.println("Client " + client + " has disconnected!");
        try {
            client.close();
        } catch (IOException ioException) {
            ioException.printStackTrace();
        }
        //useless now
        if(currentMatch.isOn())
            currentMatch.clientDown(client);
    }

    private void pingClient() {

    }
}


