package it.polimi.ingsw.network.server;

import it.polimi.ingsw.messages.*;
import it.polimi.ingsw.messages.setup.PingMessage;
import it.polimi.ingsw.messages.setup.client.LoginRequest;
import it.polimi.ingsw.messages.setup.server.LoginDoneMessage;
import it.polimi.ingsw.messages.utils.MessageSender;
import it.polimi.ingsw.view.server.InGameReconnectionHandler;
import it.polimi.ingsw.view.server.VirtualView;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.SocketException;

/**
 * this class manage the connection between one single client and the server
 */

public class ClientHandler implements Runnable{

    private final Socket client;
    private VirtualView virtualView;
    private final int lobbyNumber;
    private ObjectOutputStream outputStream;
    private ObjectInputStream inputStream;
    private final boolean isFirstPLayer;
    private boolean isLogged;
    private String nickname;
    private InGameReconnectionHandler inGameReconnectionHandler;


    public ClientHandler(Socket socket, VirtualView virtualView, int lobbyNumber, boolean isFirstPLayer, InGameReconnectionHandler inGameReconnectionHandler) {
        this.client = socket;
        this.virtualView = virtualView;
        this.lobbyNumber = lobbyNumber;
        this.isFirstPLayer = isFirstPLayer;
        this.isLogged = false;
        this.inGameReconnectionHandler = inGameReconnectionHandler;
    }

    /**
     * This method allows to receive messages from the socket and process them.
     */

    @Override
    public void run() {

        try {
            outputStream = new ObjectOutputStream(client.getOutputStream());
            inputStream = new ObjectInputStream(client.getInputStream());
        } catch (IOException e) {
            System.out.println("could not open connection to " + client.getInetAddress());
            return;
        }

        //This is the ping received timeout.
        //If in 3 seconds server doesn't receive a ping message, the connection is declared dropped.
        try {
            client.setSoTimeout(3000);
        } catch (SocketException e) {
            e.printStackTrace();
        }


        new Thread(this::pingClient).start();

        virtualView.addInWaitList(client,outputStream);
        virtualView.toDoLogin(client, isFirstPLayer, outputStream); // asks the client to login

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

                Object next = inputStream.readObject();
                Message message = (Message) next;
                if(!isPing(message)) {
                    System.out.println(message);
                    processMessage(message);
                }
            }

        } catch (ClassNotFoundException | ClassCastException | IOException e) {
            System.out.println("[SERVER] invalid stream from client");
            inputStream.close();
            clientDisconnection();

        }

    }

    /**
     * This method return the outputStream of the client
     * @return output stream
     */
    public ObjectOutputStream getOutputStream() {
        return outputStream;
    }

    /**
     * This method process messages using the Message manager
     * @param message the message to parse
     */

    public void processMessage(Message message) throws IOException{


        if(message instanceof LoginRequest){
            LoginRequest loginRequest = (LoginRequest) message;
            this.nickname = loginRequest.getNickname();
            virtualView.loginRequest(loginRequest.getNickname(), loginRequest.getNumberOfPlayers(), client);
        }
        else {
            message.execute(virtualView);
        }

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


    public boolean isPing(Message message){
        return message instanceof PingMessage;
    }


    /**
     * This method send a ping message to the client to check if the connection is still active
     */
    private void pingClient() {

        do{
            try {
                Thread.sleep(1500);
                Message message = new PingMessage();
                new MessageSender(client, message).sendMsg(outputStream);
            } catch (InterruptedException  ignored) {
            }
        }while(!client.isClosed());

        Thread.currentThread().interrupt();
    }

    public void setVirtualView(VirtualView virtualView) {
        this.virtualView = virtualView;
    }

    public String getNickname() {
        return nickname;
    }
}


