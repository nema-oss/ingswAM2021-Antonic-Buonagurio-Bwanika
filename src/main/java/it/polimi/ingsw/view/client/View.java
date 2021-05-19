package it.polimi.ingsw.view.client;

import it.polimi.ingsw.messages.setup.server.DoLoginMessage;
import it.polimi.ingsw.model.cards.DevelopmentCard;
import it.polimi.ingsw.model.cards.leadercards.LeaderCard;
import it.polimi.ingsw.model.exception.NonExistentCardException;
import it.polimi.ingsw.model.gameboard.CardMarket;
import it.polimi.ingsw.model.gameboard.GameBoard;
import it.polimi.ingsw.model.player.Board;
import it.polimi.ingsw.model.player.Player;
import it.polimi.ingsw.network.client.EchoClient;
import it.polimi.ingsw.view.client.viewComponents.ClientGameBoard;
import it.polimi.ingsw.view.client.viewComponents.ClientPlayer;

import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

/**
 * The view abstract class
 * @author Nemanja Antonic
 */
public abstract class View {

    protected EchoClient clientHandler;
    protected String myIp;
    protected int myPort;

    protected ClientPlayer player;
    protected ClientGameBoard gameBoard;

    public void newMatch(String nickname){
        this.gameBoard = new ClientGameBoard();
        this.player = new ClientPlayer(nickname,this.gameBoard);
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
     * This method allows to start a match
     * @param currentPlayer  the first player to play
     */
    public abstract void startMatch(String currentPlayer);

    /**
     * Asks the users to choose its leader card
     * @param cardChoice the card pool
     */
    public abstract void setLeaderCardChoice(List<LeaderCard> cardChoice);

    /**
     * Asks the user to choose its resource
     * @param numberOfResources number of resources the user can choose
     */
    public abstract void setResourceTypeChoice(int numberOfResources);

    /**
     * Asks the user to play its turn action
     */
    public abstract void askTurnAction();

    /**
     * Asks the user what card productions it wants to use
     * @param leaderCards its leader card type production
     * @param developmentCards its development card
     * @param actionRejectedBefore true if the action was rejected before
     */
    public abstract void setProductionChoice(List<DevelopmentCard> developmentCards, List<LeaderCard> leaderCards, boolean actionRejectedBefore);

    /**
     * Asks the user what leader card it wants to use
     * @param leaderCards its leader card
     * @param actionRejectedBefore true if the action was rejected before
     */
    public abstract void setLeaderCardAction(List<LeaderCard> leaderCards, boolean actionRejectedBefore);

    /**
     * Asks the user what card it wants to buy
     * @param actionRejectedBefore true if the action was rejected before
     */
    public abstract void setBuyCardAction(boolean actionRejectedBefore);

    /**
     * Asks the user what resources it wants to buy
     * @param actionRejectedBefore true if the action was rejected before
     */
    public abstract void setBuyResourceAction(boolean actionRejectedBefore);



    /**
     * Shows the login
     *
     * @param message login message
     *
     */
    public abstract void showLogin(DoLoginMessage message);

    /**
     * Shows the player that the login has been done
     */
    public abstract void showLoginDone();

    /**
     * Shows the player that a new player has logged in
     *
     * @param username: username of the newly logged in player
     */
    public abstract void showNewUserLogged(String username);

    /**
     * Shows the player a waiting message
     *
     * @param waitFor:    reason why they're waiting
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
    public abstract void showBoard(ClientGameBoard board, ClientPlayer player);

    /**
     * Shows to the player the game board
     */
    public abstract void showGameBoard(ClientGameBoard gameBoard) throws NonExistentCardException;

    /**
     * Shows that the server has not been found
     */
    public abstract void serverNotFound();

    /**
     * Shows that another client has disconnected
     */
    public abstract void showAnotherClientDisconnection(String otherClient);

    /**
     * This method alerts the user that the server disconnected
     */
    public abstract void showServerDisconnection();

    /**
     * Shows that the player has lost
     *
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
     * This method tells the user that login has been rejected
     * @param isFirstPlayer true if it's the first player in the lobby
     */
    public abstract void showLoginFailed(boolean isFirstPlayer);

    /**
     * This method sets the client socket and output stream
     * @param socket the client
     * @param outputStream client's output
     */
    public abstract void setReceiver(Socket socket, ObjectOutputStream outputStream);

    /**
     * This method tells the user that it has to play its turn
     */
    public abstract void showPlayTurn();

    /**
     * This method shows the resource market
     */
    public abstract void showResourceMarket();

    /**
     * This method shows the user's deposit
     */
    public abstract void showDeposit();

    /**
     * This method shows the user's development cards
     */
    public abstract void showDevelopmentCards();

    /**
     * This method shows the user's active leader cards
     */
    public  abstract void showLeaderCards(List<LeaderCard> leaderCards);

    /**
     * This method shows the card market
     */
    public abstract void showCardMarket(CardMarket cardMarket);


}
