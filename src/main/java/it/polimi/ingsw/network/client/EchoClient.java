package it.polimi.ingsw.network.client;

import it.polimi.ingsw.messages.*;
import it.polimi.ingsw.messages.setup.PingMessage;
import it.polimi.ingsw.messages.setup.server.EndGameMessage;
import it.polimi.ingsw.messages.utils.MessageWriter;
import it.polimi.ingsw.view.client.Cli;
import it.polimi.ingsw.view.client.View;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.SocketException;


/**
 * the client. It handle the connection on the client side
 */

public class EchoClient {

    //attributes

    private String ip;
    private int serverPort;
    private Socket server;
    private View view;
    private ObjectInputStream input;
    private ObjectOutputStream outputStream;

    public EchoClient(String ip, int port, View view) {
        this.serverPort = port;
        this.ip = ip;
        this.view = view;

    }



    /**
     * This method allows to receive a message from connection with server and to start the message process.
     * The communication go down when server send a disconnection message.
     */

    public void start() {

        initializeClientConnection();
        System.out.println("[CLIENT] connected to " + server.getInetAddress() + "...");

        try {
            outputStream = new ObjectOutputStream(server.getOutputStream());
            input = new ObjectInputStream(server.getInputStream());
        } catch (IOException e) {
            System.out.println("could not open connection to " + server.getInetAddress());
            return;
        }

        view.setReceiver(server,outputStream);

        // if there's no ping response from server after 3 seconds, connection is ended
        try {
            server.setSoTimeout(3000);
        } catch (SocketException e) {
            e.printStackTrace();
        }

        new Thread(this::pingServer).start();

        if (server != null) {

            try {
                handleServerConnection();
            } catch (IOException e) {
                System.out.println("server " + server.getInetAddress() + " connection dropped");
            }

            try {
                server.close();
            } catch (IOException e) {
                serverDisconnection();
            }

        }


    }

    /**
     * This method initializes a socket connection on ip-serverPort.
     */
    public void initializeClientConnection() {
        try {
            server = new Socket(ip, serverPort);
        } catch (IOException e) {
            System.out.println("Can't connect to server on port: " + serverPort);
        }
    }

    /**
     * This method manage the disconnection of the server on client side
     */
    public void serverDisconnection() {
        try {
            server.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        view.showServerDisconnection();
    }

    /**
     * this method handle the sending, parsing and creation of messages between client and server
     *
     * @throws IOException ...
     */
    public void handleServerConnection() throws IOException {

        try {
            while (true) {

                Object next = input.readObject();
                Message message = (Message) next;
                if(!isPing(message)) {
                    if (isEnd(message)) break;
                    processMessage(message);
                }

            }
           input.close();
        } catch (ClassNotFoundException | ClassCastException | IOException e) {
            if (!server.isClosed()) serverDisconnection();
        }
    }


    /**
     * This method process messages using the execute method in the message
     * @param message
     */
    public void processMessage(Message message) throws IOException{
        message.execute(view);
    }

    /**
     * This method check if the received message is a ping
     * @param message the received message
     * @return true if ping message
     */
    public boolean isPing(Message message){
        return message instanceof PingMessage;
    }

    /**
     * This method check if the received message is end game message
     * @param message the received message
     * @return true if end game message message
     */
    public boolean isEnd(Message message){
        return message instanceof EndGameMessage;
    }


    /**
     * This method send a ping message to the server
     */
    public void pingServer() {

            do {
                try {
                    Thread.sleep(1500);
                    Message message = new MessageWriter(MessageType.PING).getMessage();
                    outputStream.writeObject( message);
                } catch (InterruptedException | IOException ignored) {
                }
            } while (!server.isClosed());

        Thread.currentThread().interrupt();
    }



}