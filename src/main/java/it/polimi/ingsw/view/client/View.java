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
 */
public abstract class View {

    /**
     * the clientHandler reference
     */
    protected EchoClient clientHandler;
    /**
     * Server ip address
     */
    protected String myIp;
    /**
     * Server port
     */
    protected int myPort;
    /**
     * Output stream
     */
    protected ObjectOutputStream outputStream;
    /**
     * Client socket
     */
    protected Socket socket;
    /**
     * Client player reference
     */
    protected ClientPlayer player;
    /**
     * Client gameboard reference
     */
    protected ClientGameBoard gameBoard;
    /**
     * Other player playerboards
     */
    protected Map<String,ClientPlayerBoard> otherPlayerBoards;
    /**
     * Local match handler
     */
    protected LocalMatchHandler localMatchHandler;
    /**
     * True if it's a local match without server connection
     */
    protected boolean isLocalMatch;


    /**
     * After starting a match, it creates the client model scheme
     * @param nickname player's nickname
     */
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
     * @param user the player's nickname
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
     * @param otherClient the client that disconnected
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
     * @param card the selected card
     * @param activate the result of the request
     */
    public abstract void showAcceptedLeaderAction(LeaderCard card, boolean activate);

    /**
     * This method tells the user that the leader card action has been accepted
     */
    public abstract void showRejectedLeaderAction();

    /**
     * This method tells the user that the buy card action has been accepted
     * @param x,y the card coordinates
     * @param user the current player
     */
    public abstract void showAcceptedBuyDevelopmentCard(String user, int x, int y);

    /**
     * This method tells the user that the activate production request has been rejected
     * @param accepted the result of the request
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

    /**
     * @return the player client side
     */
    public ClientPlayer getClientPlayer(){
        return player;
    }

    /**
     * @return the boards belonging to other players
     */
    public Map<String, ClientPlayerBoard> getOtherPlayerBoards() {
        return otherPlayerBoards;
    }

    /**
     * @return the player's board (client side)
     */
    public ClientGameBoard getClientGameBoard() {
        return gameBoard;
    }

    /**
     * this method updates player's position
     * @param position player's current position
     */
    public  abstract void updatePlayerPosition(int position);

    /**
     * this method updates one's player boards
     * @param user the nickname of the player to update
     * @param clientPlayerBoard his board
     */
    public abstract void updateOtherPlayerBoards(String user, ClientPlayerBoard clientPlayerBoard);

    /**
     * this method shows that a leader card has been activated
     * @param choice
     */
    public void showAcceptedActivateLeaderCard(LeaderCard choice){
        player.useLeaderCard(choice,true);
    }

    /**
     * this method updates the game board
     * @param cardMarket the updated card market
     * @param market the updated marble market
     * @param freeMarble the new free marble
     */
    public abstract void updateGameBoard(DevelopmentDeck[][] cardMarket, Marble[][] market, Marble freeMarble);

    /**
     * this method shows lorenzo's action
     * @param lorenzoAction the token drawn
     * @param lorenzoPosition lorenzo's position in the pope road
     */
    public abstract void showLorenzoAction(ActionToken lorenzoAction, int lorenzoPosition);

    /**
     * this method shows the result of the production
     * @param updatedStrongbox the updated strongbox
     * @param updatedWarehouse the updated warehouse
     */
    public abstract void showProductionResult(Map<ResourceType, List<Resource>> updatedStrongbox, List<List<Resource>> updatedWarehouse);

    /**
     * Return true if the player can end the turn
     * @return true when player can end its turn
     */
    public boolean checkTurnEnd() {
        return player.isStandardActionPlayed();
    }

    /**
     * Alerts the users that it's the last round of the match
     */
    public abstract void showLastRound();

    /**
     * Update the player's hand and the active leader cards after reconnection
     * @param hand player's hand
     * @param activeLeaderCards player's active leader cards
     */
    public void showLeaderCardUpdate(List<LeaderCard> hand, List<LeaderCard> activeLeaderCards){

        player.setHand(hand);
        activeLeaderCards.forEach(p->player.useLeaderCard(p,true));
    }
}
