package it.polimi.ingsw.network.client;

import it.polimi.ingsw.messages.*;
import it.polimi.ingsw.messages.setup.BeginMessage;
import it.polimi.ingsw.messages.setup.GameModeMessage;
import it.polimi.ingsw.messages.setup.LoginMessage;
import it.polimi.ingsw.messages.setup.client.LoginRequest;
import it.polimi.ingsw.messages.setup.server.LoginDoneMessage;
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

    public static void main(String[] args) {

        EchoClient echoClient = new EchoClient("127.0.0.1", 1234, new Cli()); // this should be read from cmd line
        echoClient.start();
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
     * This method manage the disconnection of the server on client side
     */
    public void serverDisconnection() {
        try {
            server.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        //close the view
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
     * This method process messages using the Message manager
     *
     * @param message
     */
    public void processMessage(Message message) throws IOException{

        if(message instanceof PingMessage){
            //System.out.println("PING FROM SERVER");
        }
        else if(message instanceof DisconnectionMessage){
            System.out.println("Disconnection");
        }
        else if(message instanceof LoginMessage){
            System.out.println("Server asks to login");
            view.showLogin((LoginMessage) message);
        }
        else if(message instanceof GameModeMessage){
            view.showMatchStarted();
        }
        else if(message instanceof LoginDoneMessage){
            System.out.println("Waiting for other players");
        }
        else if(message instanceof BeginMessage){
            view.showMatchStarted();
        }


        //message.execute(view);
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
                    //new MessageSender(server, message).sendMsg();
                } catch (InterruptedException | IOException ignored) {
                    ignored.printStackTrace();
                    System.out.println("Closing connection");
                }
            } while (!server.isClosed());

        Thread.currentThread().interrupt();
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

}