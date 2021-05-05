package it.polimi.ingsw.network.client;

import it.polimi.ingsw.messagges.LoginMessage;
import it.polimi.ingsw.messagges.LoginRequest;
import it.polimi.ingsw.messagges.Message;
import it.polimi.ingsw.view.client.View;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.Scanner;


/**
 * the client. It handle the connection on the client side
 *
 */

public class EchoClient {

    //attributes

    private static final String SERVER_IP = "127.0.0.1";
    private static final int SERVER_PORT = 1234;
    private Socket server;
    private View view;
    private ObjectInputStream inputStream;
    private ObjectOutputStream outputStream;

    public EchoClient(){

    }

    public static void main(String[] args) {

        EchoClient echoClient = new EchoClient();
        echoClient.start();
    }

    //methods

    /**
     * This method allows to receive an XML from connection with server and to start the message process.
     * The communication go down when server send a "disconnection" message.
     */

    public void start(){

        initializeClientConnection();
        System.out.println("Client connected to" + server.getInetAddress());

        if(server != null) {
            try {


                inputStream = new ObjectInputStream(server.getInputStream());
                outputStream = new ObjectOutputStream(server.getOutputStream());

                while (!server.isClosed()) {

                    Message message = (Message) inputStream.readObject();
                    processMsg(message);
                }

            } catch (IOException | ClassNotFoundException e) {
                //Server disconnection manager
                e.printStackTrace();
            }
            try{
                server.close();
            }catch (IOException e){
                e.printStackTrace();
            }
        }


    }

    /**
     * This method initializes a Socket connection on hostName-port.
     */

    public void initializeClientConnection(){
        try{
            server = new Socket(SERVER_IP, SERVER_PORT);
        }catch (IOException e){
            System.out.println("Can't connect to server on port: " + SERVER_PORT);
        }
    }


    private synchronized void processMsg(Message message) throws IOException{

        if(message instanceof LoginMessage) {
            String output = "";
            output += "Insert your username (must be at least 3 characters long and no more than 10, valid characters: A-Z, a-z, 1-9, _)";
            System.out.println(output);
            Scanner scanner = new Scanner(System.in);
            String nickname = scanner.nextLine();
            outputStream.writeObject(new LoginRequest(nickname));
        }
        else if(message instanceof OkMsg){
            System.out.println("Nickname is okay");
        }

    }


}