package it.polimi.ingsw.view.server;

import it.polimi.ingsw.controller.ControllerInterface;
import it.polimi.ingsw.messages.*;
import it.polimi.ingsw.messages.setup.SetupMessageType;
import it.polimi.ingsw.model.cards.DevelopmentCard;
import it.polimi.ingsw.model.cards.leadercards.LeaderCard;
import it.polimi.ingsw.model.gameboard.Resource;
import it.polimi.ingsw.model.gameboard.ResourceType;
import it.polimi.ingsw.controller.Error;

import java.io.IOException;
import java.net.Socket;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.ErrorManager;

/**
 * this class represent the virtual view in the server and abstract the network to the controller. It observer the model
 * to communicate updates to the View in the client. It uses the MessageManager to create, manage and send messages
 * between controller and client.
 */
public class VirtualView implements VirtualViewInterface{

    private static final int MAXIMUM_LOBBY_SIZE = 4;
    private final int lobbyID;
    private final ControllerInterface matchController;
    private Map<String,Socket> clients;
    private List<Socket> waitList;
    private boolean isActive;
    private InGameDisconnectionHandler inGameDisconnectionHandler;

    public VirtualView(ControllerInterface matchController, int lobbyID, InGameDisconnectionHandler inGameDisconnectionHandler) {
        this.lobbyID = lobbyID;
        this.matchController = matchController;
        this.clients = new HashMap<>();
        this.inGameDisconnectionHandler = inGameDisconnectionHandler;
    }


    public synchronized int getLobbySize() {
        return clients.size();
    }

    public synchronized void addInWaitList(Socket client) {
        waitList.add(client);
    }

    public synchronized boolean isActive() {
        return isActive;
    }

    /**
     * This method send a message on the socket
     * @param socket the receiver
     * @param message the message to send
     */
    public void sendMessage(Socket socket, Message message){
        boolean success = new MessageSender(socket,message).sendMsg();
        if(!success) clientDown(socket);
    }

    /**
     * This method manage the client disconnection
     * @param disconnectedSocket the client unavailable
     */

    public void clientDown(Socket disconnectedSocket) {

        if(clients.containsValue(disconnectedSocket)) {
            String nickname = clients.keySet()
                    .stream()
                    .filter(key -> disconnectedSocket.equals(clients.get(key)))
                    .findAny().get();
            Message message = new UpdateWriter().playerDisconnection(nickname);
            for (Socket socket : clients.values()) {
                if (!socket.isClosed())
                    sendMessage(socket, message);
            }
            if (isActive) inGameDisconnectionHandler.onClientDown(this, nickname);
        }
    }


    /**
     * This method send a login request to a client
     * @param client the client that must login
     */
    public void toDoLogin(Socket client) {
        Message message = new MessageWriter(SetupMessageType.LOGIN).getMessage();
        sendMessage(client, message);
    }

    /**
     * This method handles a login request, turns it over to the controller and, based on the server response, directs
     * the response. It also assigns a random color to the player.
     * @param nickname is the player who want to log in username
     * @param socket is the socket associated with the new player
     */

    public synchronized void loginRequest(String nickname, Socket socket) {

        List<Error> errors = matchController.onNewPlayer(nickname);

        if(isActive) {
            if (errors.isEmpty()) {
                onLoginAcceptedRequest(nickname, socket);
            } else
                onLoginRejectedRequest(nickname, errors, socket);
        }
    }

    /**
     * This method manage the successful login of a client, sends an update to the other players and a positive reply to
     * the new logged client
     * @param nickname the nickname of the new logged client
     * @param socket the new client socket
     */
    private void onLoginAcceptedRequest(String nickname, Socket socket){
        waitList.remove(socket);

        clients.put(nickname,socket);
        System.out.println(nickname + " logged in lobby number " + lobbyID + "\n");

        Message loginUpdate = new UpdateWriter().loginUpdate(nickname);
        for(String user: clients.keySet()){
            if(!user.equals(nickname)){
                sendMessage(clients.get(user), loginUpdate);
            }
        }
        sendMessage(socket, new MessageWriter(SetupMessageType.LOGIN_DONE).getMessage());

        if(getLobbySize() == MAXIMUM_LOBBY_SIZE) toStartMatch();
    }

    private void toStartMatch() {
    }

    /**
     * This method manage the unsuccessful login of a client and alerts the client that it must login again
     * @param nickname the proposed nickname of the new client
     * @param socket the new client socket
     */
    private void onLoginRejectedRequest(String nickname, List<Error> errors, Socket socket){
        sendMessage(socket, new ErrorWriter().rejectedLogin());
    }



    /**
     * This method ask a client to play its turn and send a wait message to the others
     * @param username the nickname of the player that has to play
     */
    public void playTurn(String username){
    }
    /**
     * This method manage the choose leaderCard request from client
     * @param leaderCard the chosen card
     * @param user the user that selects the card
     */
    public void chooseLeaderCard(String user, LeaderCard leaderCard){
        List<Error> errors = matchController.onLeaderCardsChosen(leaderCard);
        if(isActive){
            if(errors.isEmpty())
                onAcceptedChooseLeaderCard(user,leaderCard);
            else
                onRejectedChooseLeaderCard(user, leaderCard);
        }
    }


    /**
     * This method send alerts the client that its leaderCard selection has been accepted
     * @param user the user requesting the card
     * @param leaderCard the card selected
     */
    private void onAcceptedChooseLeaderCard(String user, LeaderCard leaderCard) {
        Message requestAccepted = new UpdateWriter().cardSelectionAccepted(leaderCard);
        sendMessage(clients.get(user), requestAccepted);
    }

    /**
     * This method send alerts the client that its leaderCard selection has been rejected
     * @param user the user requesting the card
     * @param leaderCard the card selected
     */
    private void onRejectedChooseLeaderCard(String user, LeaderCard leaderCard) {
        Message requestRejected = new ErrorWriter().cardSelectionRejected(leaderCard);
        sendMessage(clients.get(user), requestRejected);
    }


    /**
     * This method manage the choose resource type request from client
     * @param resourceType the chosen resource type
     */
    public void chooseResourceType(String username, ResourceType resourceType){
        List<Error> errors = matchController.onResourcesChosen(resourceType);
        if(isActive){
            if(errors.isEmpty())
                onAcceptedChooseResourceType(username, resourceType);
            else
                onRejectedChooseResourceType(username, resourceType);
        }
    }

    /**
     * This method send alerts the client that its resourceType selection has been accepted
     * @param user the user requesting the card
     * @param resourceType the type selected
     */
    private void onAcceptedChooseResourceType(String user,ResourceType resourceType) {
        Message resourceTypeSelectionAccepted = new UpdateWriter().resourceTypeSelectionAccepted(resourceType);
        sendMessage(clients.get(user), resourceTypeSelectionAccepted);
    }
    /**
     * This method send alerts the client that its resourceType selection has been rejected
     * @param user the user requesting the card
     * @param resourceType the type selected
     */
    private void onRejectedChooseResourceType(String user, ResourceType resourceType) {
        Message resourceTypeSelectionRejected = new ErrorWriter().resourceTypeSelectionRejected(resourceType);
        sendMessage(clients.get(user), resourceTypeSelectionRejected);
    }

    /**
     * This method manage the swap deposit request from client
     * @param a,b deposit's floor to swap
     */
    public void moveDeposit(String user, int a, int b){
        List<Error> errors = matchController.onMoveDeposit(user, a,b);
        if(isActive){
            if(errors.isEmpty())
                onAcceptedMoveDeposit(user);
            else
                onRejectedMoveDeposit(user);
        }
    }

    /**
     * This method alerts the client that its move deposit request has been accepted
     * @param user the user
     */
    private void onAcceptedMoveDeposit(String user) {
        Message message = new UpdateWriter().moveDepositRequestAccepted();
        sendMessage(clients.get(user), message);
    }

    /**
     * This method alerts the client that its move deposit request has been rejected
     * @param user the user
     */
    private void onRejectedMoveDeposit(String user) {
        Message message = new UpdateWriter().moveDepositRequestRejected();
        sendMessage(clients.get(user), message);
    }


    /**
     * This method manage a buy resource requests from client
     * @param x, y are the coordinates of the card
     */
    public void buyDevelopmentCards(String user, int x, int y){
        List<Error> errors = matchController.onBuyDevelopmentCards(user, x,y);
        if(isActive){
            if(errors.isEmpty())
                onAcceptedBuyDevelopmentCards(user,x,y);
            else
                onRejectedBuyDevelopmentCards(user,x,y);
        }
    }


    private void onAcceptedBuyDevelopmentCards(String user, int x, int y) {
        Message message = new UpdateWriter().buyCardAccepted();
        for(Socket socket: clients.values())
            sendMessage(socket, message);
    }

    private void onRejectedBuyDevelopmentCards(String user, int x, int y) {
        Message message = new ErrorWriter().buyCardRejected();
        sendMessage(clients.get(user), message);
    }

    /**
     * This method manage a buy resources requests from client
     * @param x, y are the coordinates of the card
     */
    public void buyResources(String user, int x, int y){
        List<Error> errors = matchController.onBuyResources(user, x,y);
        if(isActive){
            if(errors.isEmpty()) {
                onAcceptedBuyResources(user,x,y);
            } else
                onRejectedBuyResources(user,x,y);
        }
    }

    private void onAcceptedBuyResources(String user, int x, int y) {
        Message message = new UpdateWriter().buyResourceAccepted(x,y);
        sendMessage(clients.get(user), message);
    }

    private void onRejectedBuyResources(String user, int x, int y) {
        Message message = new ErrorWriter().buyResourceRejected(x,y);
        sendMessage(clients.get(user), message);

    }

    /**
     * This method manage the activate production development card request from client
     * @param card the card to use
     */
    public void activateProductionDevelopmentCard(String user, DevelopmentCard card){
        List<Error> errors = matchController.onActivateDevelopmentProduction(user, card);
        if(isActive){
            if(errors.isEmpty())
                onAcceptedActivateProductionDevelopmentCard(user,card);
            else
                onRejectedActivateProductionDevelopmentCard(user,card);
        }
    }

    private void onAcceptedActivateProductionDevelopmentCard(String user, DevelopmentCard card) {
        Message message = new UpdateWriter().productionCardAccepted(card);
        sendMessage(clients.get(user), message);
    }

    private void onRejectedActivateProductionDevelopmentCard(String user, DevelopmentCard card) {
        Message message = new ErrorWriter().productionCardRejected(card);
        sendMessage(clients.get(user), message);
    }

    /**
     * This method manage the activate production board request from client
     */
    public void activateProductionBoard(String user, ArrayList<Resource> toGive, ResourceType resourceType){
        List<Error> errors = matchController.onActivateBoardProduction(user, toGive, resourceType);
        if(isActive){
            if(errors.isEmpty())
                onAcceptedActivateProductionBoard(user,resourceType);
            else
                onRejectedActivateProductionBoard(user,resourceType);

        }
    }

    private void onAcceptedActivateProductionBoard(String user, ResourceType resourceType) {
        Message message = new UpdateWriter().productionBoardAccepted(resourceType);
        sendMessage(clients.get(user), message);
    }


    private void onRejectedActivateProductionBoard(String user, ResourceType resourceType) {
        Message message = new ErrorWriter().productionBoardRejected(resourceType);
        sendMessage(clients.get(user), message);
    }


    /**
     * This method manage the activate production leader card request from client
     * @param card the card to use
     */
    public void activateProductionLeaderCard(String user, LeaderCard card){
        List<Error> errors = matchController.onActivateLeaderProduction(user,card);
        if(isActive){
            if(errors.isEmpty())
                onAcceptedActivateProductionLeaderCard(user,card);
            else
                onRejectedActivateProductionLeaderCard(user,card);

        }
    }

    private void onAcceptedActivateProductionLeaderCard(String user, LeaderCard card){
        Message message = new UpdateWriter().productionLeaderAccepted(card);
        sendMessage(clients.get(user), message);

    }

    private void onRejectedActivateProductionLeaderCard(String user, LeaderCard card){
        Message message = new ErrorWriter().productionLeaderRejected(card);
        sendMessage(clients.get(user), message);

    }


    /**
     * This method manage the activation of a leader card
     * @param card the card to use
     */
    public void activateLeaderCard(String user, LeaderCard card){
        List<Error> errors = matchController.onActivateLeader(user,card);
        if(isActive){
            if(errors.isEmpty())
                onAcceptedActivateLeaderCard(user,card);
            else
                onRejectedActivateLeaderCard(user,card);

        }
    }

    private void onAcceptedActivateLeaderCard(String user, LeaderCard card) {
        Message message = new UpdateWriter().activateLeaderAccepted(card);
        sendMessage(clients.get(user), message);

    }

    private void onRejectedActivateLeaderCard(String user, LeaderCard card) {
        Message message = new ErrorWriter().activateLeaderRejected(card);
        sendMessage(clients.get(user), message);

    }

    /**
     * This method manage the discard of a leader card
     * @param card the card to use
     */
    public void discardLeaderCard(String user, LeaderCard card){
        List<Error> errors = matchController.onDiscardLeader(user,card);
        if(isActive){
            if(errors.isEmpty())
                onAcceptedDiscardLeaderCard(user,card);
            else
                onRejectedDiscardLeaderCard(user,card);

        }
    }


    private void onAcceptedDiscardLeaderCard(String user, LeaderCard card){
        Message message = new UpdateWriter().discardLeaderAccepted(card);
        sendMessage(clients.get(user), message);
    }

    private void onRejectedDiscardLeaderCard(String user, LeaderCard card) {
        Message message = new UpdateWriter().activateLeaderRejected(card);
        sendMessage(clients.get(user), message);
    }

    /**
     * This method close all the connections after a match is finished
     */
    public void endMatch(){
        for(Socket socket: clients.values()){
            if(!socket.isClosed()){
                try {
                    socket.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        System.out.println("[SERVER] Disconnection! Match in lobby number = " + lobbyID + "finished");
    }


    public void inGameDisconnection(String disconnectedPlayer) {
        matchController.onPlayerDisconnection(disconnectedPlayer);

    }

    public void endTurn(String user){} //dice a un giocatore che il suo turno è finito (si può anche evitare)
    public void lastRound(){} //dice a tutti che si sta giocando l'ultimo round
    public void toDoChooseLeaderCards(String user, ArrayList<LeaderCard> leaderCards ){} //manda le leaderCards tra cui scegliere
    public void toDoChooseResources(String user, int numOfResourcesToChoose){} //manda il numero di risorse tra cui possono scegleire
}

