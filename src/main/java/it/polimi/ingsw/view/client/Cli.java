package it.polimi.ingsw.view.client;

import it.polimi.ingsw.messages.Message;
import it.polimi.ingsw.messages.utils.MessageSender;
import it.polimi.ingsw.messages.setup.server.DoLoginMessage;
import it.polimi.ingsw.messages.setup.client.LoginRequest;

import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.Scanner;

/**
 * this class implements the CLI view in the client
 */
public class Cli extends View {


    private final Scanner scanner;
    private Socket socket;
    private ObjectOutputStream outputStream;
    private final InputValidator inputValidator;
    
    public Cli(){
        this.scanner = new Scanner(System.in);
        this.inputValidator = new InputValidator();
    }

    public void setReceiver(Socket socket, ObjectOutputStream outputStream) {
        this.socket = socket;
        this.outputStream = outputStream;
    }

    /**
     * This method tells the user that it has to play its turn
     */
    @Override
    public void showPlayTurn() {

    }

    /**
     * This method shows the resource market
     */
    @Override
    public void showResourceMarket() {

    }

    /**
     * This method shows the user's deposit
     */
    @Override
    public void showDeposit() {

    }

    /**
     * This method shows the user's development cards
     */
    @Override
    public void showDevelopmentCards() {

    }

    /**
     * This method shows the user's leader cards
     */
    @Override
    public void showLeaderCards() {

    }

    /**
     * This method shows the card market
     */
    @Override
    public void showCardMarket() {

    }


    @Override
    public void setMyIp() {

    }

    @Override
    public void setMyPort() {

    }

    @Override
    public void setUsername() {

    }

    /**
     * This method allows to start a match
     */
    @Override
    public void startMatch() {

    }

    /**
     * This method show the login view, asks nickname and number of players to the user and checks if the input is valid
     * @param message the login message sent by the server
     */
    @Override
    public void showLogin(DoLoginMessage message) {

        boolean correct;
        String nickname;

        do {
            System.out.println("Insert your username (must be at least 3 characters long and no more than 10, valid characters: A-Z, a-z, 1-9, _)");
            nickname = scanner.nextLine();
            correct = inputValidator.isNickname(nickname);
            if (!correct)
                System.out.println("Invalid username, try again");
        }while (!correct);

        LoginRequest loginRequest = new LoginRequest(nickname);

        if(message.isFirstPlayer()){
            do{
                System.out.println("Insert number of players [1..4]");
                int numberOfPlayers = scanner.nextInt();
                correct = inputValidator.isNumberOfPlayers(numberOfPlayers);
                if(!correct){
                    System.out.println("Incorrect number of players. Try again");
                }
                else{
                    loginRequest.setNumberOfPlayers(numberOfPlayers);
                }
            }while (!correct);
        }

        new MessageSender(socket,loginRequest).sendMsg(outputStream);
    }

    /**
     * This method tells the user that login has been successful and asks to wait for other players to join
     */
    @Override
    public void showLoginDone() {
        System.out.println("Login done, waiting to start the match ...");
    }

    /**
     * This method tells the user that a new player has joined the match
     * @param username: username of the newly logged in player
     */
    @Override
    public void showNewUserLogged(String username) {

        System.out.println(username + " has joined the match");
    }

    /**
     * This methods asks the user to wait for its turn
     * @param waitFor: reason why they're waiting
     * @param nowPlaying: username of the player's turn
     */
    @Override
    public void showWaitMessage(String waitFor, String nowPlaying) {
        //We use a WAITMESSAGE that call this method on its execute
        //on the server we do:
            //Message waitMessage = new WaitMessage(waitFor,nowPlaying)
    }

    /**
     * This methods tells the user that the match has started
     */
    @Override
    public void showMatchStarted() {
        System.out.println("Match started kids");
    }

    /**
     * This method shows the player personal board
     */
    @Override
    public void showBoard() {

    }

    /**
     * This method shows the common game board
     */
    @Override
    public void showGameBoard() {

    }

    @Override
    public void serverNotFound() {

    }

    /**
     * This method tells the user that another player has disconnected
     * @param otherClient the disconnected player
     */
    @Override
    public void showAnotherClientDisconnection(String otherClient) {

    }

    /**
     * This method alerts the user that the server disconnected
     */
    @Override
    public void showServerDisconnection() {

        System.out.println("Server says Bye Bye, sayonara");

    }

    /**
     * This method alerts the user that it has lost the game and tells who is the winner
     * @param winner : username of the winner
     */
    @Override
    public void showYouLose(String winner) {

    }

    /**
     * This method tells the winner he won the match
     */
    @Override
    public void showYouWin() {

    }

    /**
     *
     */
    @Override
    public void showEndGame() {

    }

    /**
     * This method tells the user that login has been rejected
     */
    @Override
    public void showLoginFailed(boolean isFirstPlayer) {

        System.out.println("Login failed. Try again with a different username");
        DoLoginMessage doLoginMessage = new DoLoginMessage();
        doLoginMessage.setFirstPlayer(isFirstPlayer);
        showLogin(doLoginMessage);
    }


}
