package it.polimi.ingsw.network.server;

import com.sun.org.apache.xerces.internal.impl.xpath.regex.Match;
import it.polimi.ingsw.view.server.VirtualView;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;


/**
 * the server. It handles the connection of multiples client using the ClientHandler and manage multiple matches using
 * different lobbies for each match
 */

public class EchoServer {

    //attributes

    private static final int SERVER_PORT = 1234;
    private static final String SERVER_IP = "127.0.0.1";
    private ServerSocket server;
    private ExecutorService executor;

    private final List<VirtualView> lobbies;

    //constructors

    public EchoServer(){
        lobbies = new ArrayList<>();
        lobbies.add(new VirtualView());
    }

    public static void main(String[] args) {
        EchoServer echoServer = new EchoServer();
        echoServer.start();
    }


    /**
     * This method allows server to start to accept a connection from a client and create a thread which manages a
     * specific client connection
     * It allows to create a new lobby if existing lobbies are full or the match into them have already started.
     */

    public void start(){

        executor = Executors.newCachedThreadPool();
        initializeServer();

        //The server listens for a new connection and searches for a lobby where the new client can enter (if there isn't
        //server provides to create a new lobby and enters the client into this)
        while (true) {
            try {
                Socket client = server.accept();
                findAMatch(client);

            } catch(IOException e) {
                break;
            }
        }

        executor.shutdown();

    }

    /**
     * This method initializes a ServerSocket on port.
     */

    public void initializeServer(){
        try{
            server = new ServerSocket(SERVER_PORT);
        }catch (IOException e){
            System.err.println(e.getMessage());
            return;
        }

        System.out.println("Server socket ready on port: " + SERVER_PORT);
    }

    /**
     * This method searches a free lobby for the new connected client.
     * If there is a free lobby it inserts it in this lobby, otherwise it creates a new one of which this
     * client becomes its creator.
     * @param client is the new client's socket.
     */

    public void findAMatch(Socket client){

        boolean found = false;
        for (VirtualView match : lobbies){
            if(match.getLobbySize() < 4){
                executor.submit(new ClientHandler(client,match,lobbies.indexOf(match) + 1));
                found = true;
                break;
            }
        }

        //The server have to create a new lobby because there isn't a free one.
        if(!found){
            System.out.println("Server creates a new lobby...");
            VirtualView match = new VirtualView();
            lobbies.add(match);
            executor.submit(new ClientHandler(client,match,lobbies.indexOf(match) + 1));
        }
    }

    /**
     * This method is called when a client connection go down, it provides to remove the lobby where this client was
     * @param match is the virtual view represents a lobby/match
     */

    public void onClientDown(Match match) {
        System.out.println("Lobby number " + (lobbies.indexOf(match) + 1) + " deleted!");

        lobbies.remove(match);
    }

    /**
     * This method is called when the match have to finish. It provides to visualize a message on the server and to
     * delete the lobby where the match was evolving.
     * @param match is the lobby's virtualView
     */

    public void onMatchFinish(Match match){
        System.out.println("\nLobby number " + (lobbies.indexOf(match) + 1) + " deleted!");

        lobbies.remove(match);
    }
}