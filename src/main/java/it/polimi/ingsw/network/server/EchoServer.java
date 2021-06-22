package it.polimi.ingsw.network.server;

import it.polimi.ingsw.controller.MatchController;
import it.polimi.ingsw.view.server.InGameReconnectionHandler;
import it.polimi.ingsw.view.server.VirtualView;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;


/**
 * The server. It handles the connection of multiples client using the ClientHandler and manage multiple matches using
 * different lobbies for each match
 */

public class EchoServer implements InGameReconnectionHandler {

    private ServerSocket server;
    private ExecutorService executor;
    private static int serverPort;
    private Map<String, Integer> disconnectedPlayers;
    private Map<Integer,VirtualView> matchesWithDisconnectedPlayers;
    private Map<String,ClientHandler> playersClientHandlers;
    private List<ClientHandler> clientHandlers;
    private final List<VirtualView> lobbies;



    public EchoServer(int port){

        serverPort = port;
        lobbies = new ArrayList<>();
        disconnectedPlayers = new HashMap<>();
        matchesWithDisconnectedPlayers = new HashMap<>();
        lobbies.add(new VirtualView(new MatchController(),lobbies.size()+1, this));
        clientHandlers = new ArrayList<>();

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
        System.out.println("[SERVER] ready on port: " + serverPort);
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
                    ClientHandler c = new ClientHandler(client, match, lobbies.indexOf(match), true,this);
                    clientHandlers.add(c);
                    executor.submit(c);
                }
                else {
                    ClientHandler c = new ClientHandler(client, match, lobbies.indexOf(match), false,this);
                    clientHandlers.add(c);
                    executor.submit(c);
                }
                found = true;
                break;
            }
        }

        if(!found){
            System.out.println("[SERVER] creates a new lobby...");
            VirtualView match = new VirtualView(new MatchController(),lobbies.size()+1,this);
            lobbies.add(match);
            ClientHandler c = new ClientHandler(client, match, lobbies.indexOf(match), true,this);
            clientHandlers.add(c);
            executor.submit(c);
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

    @Override
    public void onClientDown(VirtualView virtualView, String disconnectedPlayer) {

        if (virtualView.getLobbySize() > 1) {
            virtualView.inGameDisconnection(disconnectedPlayer);
            clientHandlers.removeIf(p->p.getNickname().equals(disconnectedPlayer));
            disconnectedPlayers.put(disconnectedPlayer, virtualView.getLobbyID());
            matchesWithDisconnectedPlayers.put(virtualView.getLobbyID(),virtualView);
        }
        else{
            virtualView.endMatch();
        }
    }

    @Override
    public void playerReconnection(String player, Socket socket, ObjectOutputStream outputStream){

        if(disconnectedPlayers.containsKey(player)){
            int lobbyID = disconnectedPlayers.get(player);
            VirtualView previousMatch = matchesWithDisconnectedPlayers.get(lobbyID);
            ClientHandler clientHandler = clientHandlers.stream().filter(p->p.getNickname().equals(player)).findFirst().get();
            clientHandler.setVirtualView(previousMatch);
            previousMatch.reconnectPlayer(player,socket,outputStream);

        }

    }

    @Override
    public boolean hasDisconnectedBefore(String nickname) {
        return disconnectedPlayers.containsKey(nickname);
    }

    @Override
    public void addClientHandler(String user, ClientHandler clientHandler) {
        playersClientHandlers.put(user,clientHandler);
    }
}