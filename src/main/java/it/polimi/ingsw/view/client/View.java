package it.polimi.ingsw.view.client;

import it.polimi.ingsw.messages.setup.server.DoLoginMessage;
import it.polimi.ingsw.model.player.Board;
import it.polimi.ingsw.model.player.Player;
import it.polimi.ingsw.network.client.EchoClient;

import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

/**
 * this interface represent the view in the client
 * @author Nemanja Antonic
 */
public abstract class View {

    protected Player myPlayer;
    protected List<Player> players;
    public Board gameBoard;

    protected EchoClient clientHandler;
    protected String myIp;
    protected int myPort;

    /**
     * Constructor called upon connection
     */

    public View(){
        myPlayer = null;
        players = new ArrayList<>();
        gameBoard = new Board();
    }

    /**
     * Constructor called upon connection
     * @param ip: ip of the server
     * @param port: port the connection is established through
     */
    public View(String ip, int port) {
        myPlayer = null;
        players = new ArrayList<>();
        gameBoard = new Board();
        myIp = ip;
        myPort = port;
    }

    /**
     * Allows the client to establish a connection
     */
    public void start(){
        clientHandler = new EchoClient(myIp,myPort,this);
        clientHandler.start();
    }

    /**
     * Initializes a new game
     */
    public void newGame() {
        myPlayer = null;
        players = new ArrayList<>();
        gameBoard = new Board();
    }

    /**
     * Allows to set the server IP address
     */
    public abstract void setMyIp();

    /**
     * Allows to set the port
     */
    public abstract void setMyPort();

    /**
     * Allows the player to set his username
     */
    public abstract void setUsername();

    /**
     * Allows to start a match
     */
    public abstract void startMatch();

    /**
     * Allows to begin a turn
     * @param firstOperation: the first action a player can perform
     */
    public abstract void turn(String firstOperation);

    /**
     * Updates the local information available to the player
     * @param otherPlayers: list of the other connected players
     * @param myUsername: username of the client player
     */
    public void updateLoginDone(ArrayList<String> otherPlayers, String myUsername){
        /*myPlayer = new Player(myUsername, new Game());
        for(String username : otherPlayers){
            players.add(new Player(username, new Game()));
        }
        gameBoard = myPlayer.getPlayerBoard();
        showLoginDone();*/
    }

    /**
     * Updates the local information available to the player when a new user logs in
     * @param username: username of the newly connected player
     */
    public void updateNewUserLogged(String username){
        /*players.add(new Player(username, new Game()));

        showNewUserLogged(username);*/
    }

    //various disconnection methods missing

    /**
     * Calls a clientHandler method to handle the disconnection of another player
     */
    public void anotherClientDisconnection(){}

    /**
     * Shows the login
     * @param message
     */
    public abstract void showLogin(DoLoginMessage message);

    /**
     * Shows the player that the login has been done
     */
    public abstract void showLoginDone();

    /**
     * Shows the player that a new player has logged in
     * @param username: username of the newly logged in player
     */
    public abstract void showNewUserLogged(String username);

    /**
     * Shows the player a waiting message
     * @param waitFor: reason why they're waiting
     * @param nowPlaying: username of the player's turn
     */
    public abstract void showWaitMessage(String waitFor, String nowPlaying);

    /**
     * Shows that the match has started
     */
    public abstract void showMatchStarted();

    /**
     * Shows the player's board
     */
    public abstract void showBoard();

    /**
     * Shows to the player the game board
     */
    public abstract void showGameBoard();

    /**
     * Shows that the server has not been found
     */
    public abstract void serverNotFound();

    /**
     * Shows that another client has disconnected
     */
    public abstract void showAnotherClientDisconnection(String otherClient);

    /**
     * Shows to the player that they have been disconnected because the lobby is not available anymore
     */
    public abstract void showDisconnectionForLobbyNoLongerAvailable();

    /**
     * Shows to the player that they have been disconnected because the server has been disconnected
     */
    public abstract void showServerDisconnection();

    /**
     * Shows to the player that they have been disconnected because the input timer has expired
     */
    public abstract void showDisconnectionForInputExpiredTimeout();

    /**
     * Shows that the player has lost
     * @param winner : username of the winner
     */
    public abstract void showYouLose(String winner);

    /**
     * Shows that the player has won
     */
    public abstract void showYouWin();

    /**
     * Shows the game is finished
     */
    public abstract void showEndGame();

    /**
     * Sends a login request to the server
     * @param username: username of the client
     */
    public void sendLoginRequest(String username){

    }

    /**
     * Sends a start game request to the server
     */
    public void sendStartGameRequest(){

    }

    /**
     * Identifies the next action to be performed and starts it
     * @param nextOperation: the action to perform
     */
    public void nextOperation(String nextOperation){

    }

    /**
     * Gets the player that has a certain username from the list of players
     * @param username: username of the player to find
     * @return player that corresponds to username or null if the username is unavailable
     */
    public Player getPlayerByUsername(String username){
        for(Player p : players){
            if(p.getNickname().equals(username)) return p;
        }

        return null;
    }

    /**
     * Gets the number of players in the match
     * @return number of players in the match
     */
    public int getPlayerNumber(){
        return players.size() + 1;
    }

    public abstract void showLoginFailed();

    public void pingMessage() {
        System.out.println("Receiving ping message");
    }

    public abstract void showNumberOfPlayers();

    public abstract void setReceiver(Socket socket, ObjectOutputStream outputStream);
}
