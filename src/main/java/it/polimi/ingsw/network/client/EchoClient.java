package it.polimi.ingsw.network.client;

import it.polimi.ingsw.messagges.*;
import it.polimi.ingsw.view.client.Cli;
import it.polimi.ingsw.view.client.View;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.SocketException;
import java.util.Scanner;


/**
 * the client. It handle the connection on the client side
 *
 */

public class EchoClient {

    //attributes

    private String ip;
    private int serverPort;
    private Socket server;
    private View view;
    private ObjectInputStream input;
    private ObjectOutputStream outputStream;

    public EchoClient(String ip, int port, View view){
        this.serverPort = port;
        this.ip = ip;
        this.view = view;

    }

    public static void main(String[] args) {

        EchoClient echoClient = new EchoClient("127.0.0.1",1234, new Cli()); // this should be read from cmd line
        echoClient.start();
    }

    //methods

    /**
     * This method allows to receive a message from connection with server and to start the message process.
     * The communication go down when server send a disconnection message.
     */

    public void start(){

        initializeClientConnection();
        System.out.println("Client connected to" + server.getInetAddress());

        // if there's no ping response from server after 3 seconds, connection is ended
        try{
            server.setSoTimeout(3000);
        }catch (SocketException e){
            e.printStackTrace();
        }

        new Thread(this::pingServer).start();

        if(server != null) {
            try {
                input = new ObjectInputStream(server.getInputStream());
            } catch (IOException e) {
                // System.out.println("could not open connection to " + client.getInetAddress());
                return;
            }

            try {
                handleServerConnection();
            } catch (IOException e) {
                System.out.println("client " + server.getInetAddress() + " connection dropped");
            }

            try {
                server.close();
            } catch (IOException e) {
                serverDisconnection();
            }

        }

    }


    public void serverDisconnection(){
        try{
            server.close();
        }catch (IOException e){
            e.printStackTrace();
        }
        //close the view
    }

    /**
     * this method handle the sending, parsing and creation of messages between client and server
     * @throws IOException ...
     */
    public void handleServerConnection() throws IOException{

        try{
            while (true) {

                Object next = input.readObject();
                Message message = (Message) next;
                if(message instanceof DisconnectionMessage) break;
                if(!(message instanceof PingMessage))
                    processMessage(message); // from this method we also send the message back to the server
            }
            input.close();
        } catch (ClassNotFoundException | ClassCastException e) {
            if(!server.isClosed()) serverDisconnection();
        }
    }

    /**
     * This method process messages using the Message manager
     * @param message
     */
    public synchronized void processMessage(Message message) {
        new MessageManager().parseMessageFromServer(server,message,view);
    }

    /**
     * This method send a ping message to the server
     */
    public void pingServer() {
        do{
            try {
                Thread.sleep(1500);
                new MessageSender(server, MessageType.PING).sendMsg();
            } catch (InterruptedException ignored) {
            }
        }while(!server.isClosed());

        Thread.currentThread().interrupt();
    }

    /**
     * This method initializes a socket connection on ip-serverPort.
     */

    public void initializeClientConnection(){
        try{
            server = new Socket(ip, serverPort);
        }catch (IOException e){
            System.out.println("Can't connect to server on port: " + serverPort);
        }
    }

}