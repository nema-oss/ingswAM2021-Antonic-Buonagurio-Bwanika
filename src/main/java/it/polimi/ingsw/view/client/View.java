package it.polimi.ingsw.view.client;

import it.polimi.ingsw.messages.Message;
import it.polimi.ingsw.messages.setup.client.UpdateClientPlayerBoardsMessage;
import it.polimi.ingsw.messages.setup.server.DoLoginMessage;
import it.polimi.ingsw.messages.utils.MessageSender;
import it.polimi.ingsw.model.ActionToken;
import it.polimi.ingsw.model.cards.DevelopmentCard;
import it.polimi.ingsw.model.cards.DevelopmentDeck;
import it.polimi.ingsw.model.cards.leadercards.LeaderCard;
import it.polimi.ingsw.model.gameboard.*;
import it.polimi.ingsw.network.LocalMatchHandler;
import it.polimi.ingsw.network.client.EchoClient;
import it.polimi.ingsw.view.client.viewComponents.ClientDeposit;
import it.polimi.ingsw.view.client.viewComponents.ClientGameBoard;
import it.polimi.ingsw.view.client.viewComponents.ClientPlayer;
import it.polimi.ingsw.view.client.viewComponents.ClientPlayerBoard;

import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * The view abstract class
 * @author Nemanja Antonic
 */
public abstract class View {

    protected EchoClient clientHandler;
    protected String myIp;
    protected int myPort;
    protected ObjectOutputStream outputStream;
    protected Socket socket;

    protected ClientPlayer player;
    protected ClientGameBoard gameBoard;
    protected Map<String,ClientPlayerBoard> otherPlayerBoards;

    protected LocalMatchHandler localMatchHandler;
    protected boolean isLocalMatch;

    public void newMatch(String nickname){
        this.gameBoard = new ClientGameBoard();
        this.player = new ClientPlayer(nickname,this.gameBoard);
        otherPlayerBoards = new HashMap<>();

        Message message = new UpdateClientPlayerBoardsMessage(nickname, player.getPlayerBoard());
        sendMessage(socket,message);
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
    public abstract void showLoginDone(String user);

    /**
     * Shows the player that a new player has logged in
     *
     * @param username: username of the newly logged in player
     */
    public abstract void showNewUserLogged(String username);

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
    public void setReceiver(Socket socket, ObjectOutputStream outputStream) {
        this.socket = socket;
        this.outputStream = outputStream;
    }

    /**
     * This method tells the user that it has to play its turn
     * @param currentPlayer the player that it's playing now
     */
    public abstract void showPlayTurn(String currentPlayer);


    /**
     * This method set the phase to choose where to place the resources after a buy resource action
     */
    public abstract void setPlaceResourcesAction();

    /**
     * This method tells the user that the leader card action has been accepted
     * @param card
     * @param activate
     */
    public abstract void showAcceptedLeaderAction(LeaderCard card, boolean activate);

    /**
     * This method tells the user that the leader card action has been accepted
     */
    public abstract void showRejectedLeaderAction();

    /**
     * This method tells the user that the buy card action has been accepted
     */
    public abstract void showAcceptedBuyDevelopmentCard(String user, int x, int y);

    /**
     * This method tells the user that the activate production request has been rejected
     * @param accepted
     */
    public abstract void showProductionRequestResults(boolean accepted);

    /**
     * This method add the leader cards to the user's hand
     *
     * @param choice user choice
     */
    public abstract void showLeaderCardsSelectionAccepted(List<LeaderCard> choice);

    /**
     * Shows the results of the move deposit request.
     * @param x,y the floors to swap
     * @param accepted true if the request has been accepted, false if rejected
     */
    public abstract void showMoveDepositResult(int x, int y, boolean accepted);

    /**
     * Shows the result of the place resources request.
     * @param accepted true if the request has been accepted
     * @param userChoice the user choice
     */
    public abstract void showPlaceResourcesResult(boolean accepted, Map<Resource, Integer> userChoice);

    /**
     * Sets the recently bought resources from market
     * @param resources the bought resources
     */
    public void setBoughtResources(List<Resource> resources) {
        player.setBoughtResources(resources);
    }
    /**
     * Show the results of the selection the initial resources
     * @param resourceChoice the user choice
     */
    public abstract void showResourceSelectionAccepted(Map<ResourceType, Integer> resourceChoice);

    public abstract void showReconnectionToMatch();

    /**
     * This method send a message on the socket
     * @param socket the receiver
     * @param message the message to send
     */
    public void sendMessage(Socket socket, Message message){
        if(isLocalMatch)
            localMatchHandler.executeMessageFromClient(message);
        else {
            new MessageSender(socket, message).sendMsg(outputStream);
        }
    }


    public ClientPlayer getClientPlayer(){
        return player;
    }

    public Map<String, ClientPlayerBoard> getOtherPlayerBoards() {
        return otherPlayerBoards;
    }

    public ClientGameBoard getClientGameBoard() {
        return gameBoard;
    }

    public  abstract void updatePlayerPosition(int position);

    public abstract void updateOtherPlayerBoards(String user, ClientPlayerBoard clientPlayerBoard);

    public void showAcceptedActivateLeaderCard(LeaderCard choice){
        player.useLeaderCard(choice,true);
    }

    public abstract void updateGameBoard(DevelopmentDeck[][] cardMarket, Marble[][] market, Marble freeMarble);

    public abstract void showLorenzoAction(ActionToken lorenzoAction, int lorenzoPosition);

    public abstract void showProductionResult(Map<ResourceType, List<Resource>> updatedStrongbox, List<List<Resource>> updatedWarehouse);

    /**
     * Return true if the player can end the turn
     * @return true when player can end its turn
     */
    public boolean checkTurnEnd() {
        return player.isStandardActionPlayed();
    }

}
