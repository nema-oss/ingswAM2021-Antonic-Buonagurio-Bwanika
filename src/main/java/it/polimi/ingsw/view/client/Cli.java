package it.polimi.ingsw.view.client;

import it.polimi.ingsw.messages.Message;
import it.polimi.ingsw.messages.MessageSender;
import it.polimi.ingsw.messages.setup.LoginMessage;
import it.polimi.ingsw.messages.setup.client.LoginRequest;

import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.Scanner;

/**
 * this class implements the CLI view in the client
 */
public class Cli extends View {


    private Scanner scanner;
    private Socket socket;
    private ObjectOutputStream outputStream;
    
    public Cli(){
        this.scanner = new Scanner(System.in);
    }

    public void setReceiver(Socket socket, ObjectOutputStream outputStream) {
        this.socket = socket;
        this.outputStream = outputStream;
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

    @Override
    public void startMatch() {

    }

    @Override
    public void turn(String firstOperation) {

    }

    @Override
    public void showLogin(LoginMessage message) {
        System.out.println("Insert your nickname and your lobbyID");
        String nickname = scanner.nextLine();
        LoginRequest loginRequest = new LoginRequest(nickname);
        if(message.isFirstPlayer()){
            System.out.println("Insert number of players");
            int numberOfPlayers = scanner.nextInt();
            loginRequest.setNumberOfPlayers(numberOfPlayers);
        }
        else{
            loginRequest.setNumberOfPlayers(-1);
        }
        //int lobbyID = scanner.nextInt();
        new MessageSender(socket,loginRequest).sendMsg(outputStream);
    }

    @Override
    public void showLoginDone() {

    }

    @Override
    public void showNewUserLogged(String username) {

    }

    @Override
    public void showWaitMessage(String waitFor, String nowPlaying) {

    }

    @Override
    public void showMatchStarted() {
        System.out.println("Match started assholes");

    }

    @Override
    public void showBoard() {

    }

    @Override
    public void showGameBoard() {

    }

    @Override
    public void serverNotFound() {

    }

    @Override
    public void showAnotherClientDisconnection(String otherClient) {

    }

    @Override
    public void showDisconnectionForLobbyNoLongerAvailable() {

    }

    @Override
    public void showServerDisconnection() {

    }

    @Override
    public void showDisconnectionForInputExpiredTimeout() {

    }

    @Override
    public void showYouLose(int points, String winner) {

    }

    @Override
    public void showYouWin(int points) {

    }

    @Override
    public void showEnd() {

    }

    @Override
    public void showLoginFailed() {

    }

    @Override
    public void showNumberOfPlayers() {


    }


}
