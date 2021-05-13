package it.polimi.ingsw.network.server;

import it.polimi.ingsw.controller.MatchController;
import it.polimi.ingsw.view.client.Cli;
import it.polimi.ingsw.view.server.InGameDisconnectionHandler;
import it.polimi.ingsw.view.server.VirtualView;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;


/**
 * the server. It handles the connection of multiples client using the ClientHandler and manage multiple matches using
 * different lobbies for each match
 */

public class EchoServer {

    //attributes

    private static final int SERVER_PORT = 1234; // this should be read from command line args
    private ServerSocket server;
    private ExecutorService executor;
    private static int serverPort;


    private final List<VirtualView> lobbies;
    private final Map<String,Integer> disconnectedPlayers;


    public EchoServer(int port){

        serverPort = port;
        lobbies = new ArrayList<>();
        disconnectedPlayers = new HashMap<>();
        lobbies.add(new VirtualView(new MatchController(),lobbies.size()+1,new InGameDisconnectionHandler()));

    }

    public static void main(String[] args) {

        EchoServer echoServer = new EchoServer(SERVER_PORT);
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
            server = new ServerSocket(serverPort);
        }catch (IOException e){
            System.err.println(e.getMessage());
            return;
        }
        System.out.println("[SERVER] ready on port: " + SERVER_PORT);
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
            if(!match.isRequiredNumberOfPlayers() && !match.isActive()){
                if(match.getLobbySize() == 0) {
                    executor.submit(new ClientHandler(client, match, lobbies.indexOf(match), true));
                }
                else {
                    executor.submit(new ClientHandler(client, match, lobbies.indexOf(match), false));
                }
                found = true;
                break;
            }
        }

        //The server have to create a new lobby because there isn't a free one.
        if(!found){
            System.out.println("[SERVER] creates a new lobby...");
            VirtualView match = new VirtualView(new MatchController(),lobbies.size()+1, new InGameDisconnectionHandler());
            lobbies.add(match);
            executor.submit(new ClientHandler(client,match,lobbies.indexOf(match),true));
        }
    }


    /**
     * This method is called when the match is finished.
     * @param match is the lobby's virtualView
     */

    public void onEndOfMatch(VirtualView match){
        System.out.println("\nMatch in lobby " + (lobbies.indexOf(match) + 1) + " ended!");
        lobbies.remove(match);
    }
}