package it.polimi.ingsw.view.server;

import it.polimi.ingsw.controller.ControllerInterface;
import it.polimi.ingsw.messages.*;
import it.polimi.ingsw.model.cards.DevelopmentCard;
import it.polimi.ingsw.model.cards.leadercards.LeaderCard;
import it.polimi.ingsw.model.gameboard.ResourceType;

import java.io.IOException;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

/**
 * this class represent the virtual view in the server and abstract the network to the controller. It observer the model
 * to communicate updates to the View in the client. It uses the MessageManager to create, manage and send messages
 * between controller and client.
 */
public class VirtualView implements VirtualViewInterface{

    private final int lobbyID;
    private final ControllerInterface matchController;
    private List<Socket> clients;
    private List<Socket> waitList;
    private boolean isActive;
    private InGameDisconnectionHandler inGameDisconnectionHandler;

    public VirtualView(ControllerInterface matchController, int lobbyID, InGameDisconnectionHandler inGameDisconnectionHandler) {
        this.lobbyID = lobbyID;
        this.matchController = matchController;
        this.clients = new ArrayList<>();
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
        if(clients.contains(disconnectedSocket)){
            for(Socket socket: clients){
                sendMessage(socket, new MessageWriter(MessageType.CLIENT_DISCONNECTION).getMessage());
            }
        }
        if(isActive) inGameDisconnectionHandler.onClientDown(this);
    }


    /**
     * This method send a login request to a client
     * @param client the client that must login
     */
    public void toDoLogin(Socket client) {
        Message message = new MessageWriter(MessageType.LOGIN).getMessage();
        sendMessage(client, message);
    }

    /**
     * This method ask a client to play its turn and send a wait message to the others
     * @param username the nickname of the player that has to play
     */
    public void playTurn(String username){
    }
    /**
     * This method manage the choose leader request from client
     * @param leaderCard the chosen card
     */
    public void chooseLeaderCard(LeaderCard leaderCard){
        List<Error> errors = matchController.onChooseLeaderCard(leaderCard);
        if(isActive){
            if(errors.isEmpty())
                onAcceptedChooseLeaderCard(leaderCard);
            else
                onRejectedChooseLeaderCard(leaderCard);
        }
    }

    private void onRejectedChooseLeaderCard(LeaderCard leaderCard) {
    }

    private void onAcceptedChooseLeaderCard(LeaderCard leaderCard) {
    }

    /**
     * This method manage the choose resource type request from client
     * @param resourceType the chosen resource type
     */
    public void chooseResourceType(ResourceType resourceType){
        List<Error> errors = matchController.onChooseResourceType(resourceType);
        if(isActive){
            if(errors.isEmpty())
                onAcceptedChooseResourceType(resourceType);
            else
                onRejectedChooseResourceType(resourceType);
        }
    }

    private void onAcceptedChooseResourceType(ResourceType resourceType) {
    }

    private void onRejectedChooseResourceType(ResourceType resourceType) {
    }

    /**
     * This method manage the swap deposit request from client
     * @param a,b deposit's floor to swap
     */
    public void moveDeposit(int a, int b){
        List<Error> errors = matchController.onMoveDeposit(a,b);
        if(isActive){
            if(errors.isEmpty())
                onAcceptedMoveDeposit(a,b);
            else
                onRejectedMoveDeposit(a,b);
        }
    }

    private void onAcceptedMoveDeposit(int a, int b) {
    }

    private void onRejectedMoveDeposit(int a, int b) {
    }


    /**
     * This method manage a buy resource requests from client
     * @param x, y are the coordinates of the card
     */
    public void buyDevelopmentCards(int x, int y){
        List<Error> errors = matchController.onBuyDevelopmentCards(x,y);
        if(isActive){
            if(errors.isEmpty())
                onAcceptedBuyDevelopmentCards(x,y);
            else
                onRejectedBuyDevelopmentCards(x,y);
        }
    }

    private void onAcceptedBuyDevelopmentCards(int x, int y) {
    }

    private void onRejectedBuyDevelopmentCards(int x, int y) {
    }

    /**
     * This method manage a buy resources requests from client
     * @param x, y are the coordinates of the card
     */
    public void buyResources(int x, int y){
        List<Error> errors = matchController.onBuyResources(x,y);
        if(isActive){
            if(errors.isEmpty()) {
                onAcceptedBuyResources(x,y);
            } else
                onRejectedBuyResources(x,y);
        }
    }

    private void onAcceptedBuyResources(int x, int y) {
    }

    private void onRejectedBuyResources(int x, int y) {
    }

    /**
     * This method manage the activate production development card request from client
     * @param card the card to use
     */
    public void activateProductionDevelopmentCard(DevelopmentCard card){
        List<Error> errors = matchController.onActivateProductionDevelopmentCard(card);
        if(isActive){
            if(errors.isEmpty())
                onAcceptedActivateProductionDevelopmentCard(card);
            else
                onRejectedActivateProductionDevelopmentCard(card);
        }
    }

    private void onAcceptedActivateProductionDevelopmentCard(DevelopmentCard card) {
    }

    private void onRejectedActivateProductionDevelopmentCard(DevelopmentCard card) {
    }

    /**
     * This method manage the activate production board request from client
     */
    public void activateProductionBoard(ResourceType resourceType){
        List<Error> errors = matchController.onActivateProductionBoard(resourceType);
        if(isActive){
            if(errors.isEmpty())
                onAcceptedActivateProductionBoard(resourceType);
            else
                onRejectedActivateProductionBoard(resourceType);

        }
    }

    private void onRejectedActivateProductionBoard(ResourceType resourceType) {
    }

    private void onAcceptedActivateProductionBoard(ResourceType resourceType) {
    }

    /**
     * This method manage the activate production leader card request from client
     * @param card the card to use
     */
    public void activateProductionLeaderCard(LeaderCard card){
        List<Error> errors = matchController.onActivateProductionLeaderCard(card);
        if(isActive){
            if(errors.isEmpty())
                onAcceptedActivateProductionLeaderCard(card);
            else
                onRejectedActivateProductionLeaderCard(card);

        }
    }

    private void onAcceptedActivateProductionLeaderCard(LeaderCard card){

    }

    private void onRejectedActivateProductionLeaderCard(LeaderCard card){

    }


    /**
     * This method manage the activation of a leader card
     * @param card the card to use
     */
    public void activateLeaderCard(LeaderCard card){
        List<Error> errors = matchController.onActivateLeaderCard(card);
        if(isActive){
            if(errors.isEmpty())
                onAcceptedActivateLeaderCard(card);
            else
                onRejectedActivateLeaderCard(card);

        }
    }

    private void onAcceptedActivateLeaderCard(LeaderCard card) {
    }

    private void onRejectedActivateLeaderCard(LeaderCard card) {
    }

    /**
     * This method manage the discard of a leader card
     * @param card the card to use
     */
    public void discardLeaderCard(LeaderCard card){
        List<Error> errors = matchController.onDiscardLeaderCard(card);
        if(isActive){
            if(errors.isEmpty())
                onAcceptedDiscardLeaderCard(card);
            else
                onRejectedDiscardLeaderCard(card);

        }
    }


    private void onAcceptedDiscardLeaderCard(LeaderCard card){

    }

    private void onRejectedDiscardLeaderCard(LeaderCard card) {
    }

    public void EndMatch(){
        for(Socket socket: clients){
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


}

