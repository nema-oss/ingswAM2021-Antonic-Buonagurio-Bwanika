package it.polimi.ingsw.view.server;

import it.polimi.ingsw.controller.ControllerInterface;
import it.polimi.ingsw.messages.*;
import it.polimi.ingsw.messages.actions.BuyResourcesMessage;
import it.polimi.ingsw.messages.actions.EndProductionMessage;
import it.polimi.ingsw.messages.actions.server.LorenzoTurnMessage;
import it.polimi.ingsw.messages.actions.server.MoveOnPopeRoadMessage;
import it.polimi.ingsw.messages.actions.server.UpdatePlayerBoardMessage;
import it.polimi.ingsw.messages.setup.client.UpdateClientPlayerBoardsMessage;
import it.polimi.ingsw.messages.setup.server.*;
import it.polimi.ingsw.messages.utils.ErrorWriter;
import it.polimi.ingsw.messages.utils.MessageSender;
import it.polimi.ingsw.messages.utils.UpdateWriter;
import it.polimi.ingsw.model.ActionToken;
import it.polimi.ingsw.model.cards.DevelopmentCard;
import it.polimi.ingsw.model.cards.DevelopmentDeck;
import it.polimi.ingsw.model.cards.leadercards.LeaderCard;
import it.polimi.ingsw.model.gameboard.*;
import it.polimi.ingsw.controller.Error;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
    private Map<Socket,ObjectOutputStream> socketObjectOutputStreamMap;
    private final List<Socket> waitList;
    private boolean isActive;
    private int requiredNumberOfPlayers;
    private List<Resource> boughtResources;
    private InGameReconnectionHandler inGameReconnectionHandler;

    public VirtualView(ControllerInterface matchController, int lobbyID, InGameReconnectionHandler inGameReconnectionHandler) {
        this.lobbyID = lobbyID;
        this.matchController = matchController;
        this.clients = new HashMap<>();
        this.waitList = new ArrayList<>();
        this.requiredNumberOfPlayers = -1;
        this.socketObjectOutputStreamMap = new HashMap<>();
        this.inGameReconnectionHandler = inGameReconnectionHandler;

        matchController.setVirtualView(this);
    }

    /**
     * This method returns the current number of players in the match
     * @return number of players
     */
    public synchronized int getLobbySize() {
        return clients.size();
    }

    /**
     * Returns the ID of the current match
     * @return match id
     */
    public int getLobbyID() {
        return lobbyID;
    }

    /**
     * This method adds a client to a wait list before asking to login, and saves its output stream t
     * @param client the client socket
     * @param outputStream the client output stream
     */
    public synchronized void addInWaitList(Socket client,ObjectOutputStream outputStream) {
        waitList.add(client);
        socketObjectOutputStreamMap.put(client,outputStream);
    }

    /**
     * This method check if the match has started
     * @return true if match has started
     */
    public synchronized boolean isActive() {
        return isActive;
    }

    /**
     * This method send a message on the socket
     * @param socket the receiver
     * @param message the message to send
     */
    public void sendMessage(Socket socket, Message message){

        ObjectOutputStream outputStream = socketObjectOutputStreamMap.get(socket);
        boolean success = new MessageSender(socket,message).sendMsg(outputStream);
        if(!success) clientDown(socket);
        else System.out.println("sent from server === " + message + " \nsocket == " + socket);
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
            clients.remove(nickname);
            socketObjectOutputStreamMap.remove(disconnectedSocket);
            Message message = new UpdateWriter().playerDisconnection(nickname);
            for (Socket socket : clients.values()) {
                if (!socket.isClosed())
                    sendMessage(socket, message);
            }
            if (isActive()) inGameReconnectionHandler.onClientDown(this, nickname);
        }
    }

    /**
     * This method send a login request to a client
     * @param client the client that must login
     */
    public void toDoLogin(Socket client, boolean isFirstPlayer, ObjectOutputStream outputStream) {

        DoLoginMessage message = new DoLoginMessage();
        message.setFirstPlayer(isFirstPlayer);
        boolean success = new MessageSender(client,message).sendMsg(outputStream);
        if(!success) clientDown(client);
    }

    /**
     * This method handles a login request, turns it over to the controller and, based on the server response, directs
     * the response.
     * @param nickname is the player who want to log in username
     * @param socket is the socket associated with the new player
     */
    public synchronized void loginRequest(String nickname, int requiredNumberOfPlayers, Socket socket) {

        if(inGameReconnectionHandler.hasDisconnectedBefore(nickname)){
            ObjectOutputStream outputStream = socketObjectOutputStreamMap.get(socket);
            socketObjectOutputStreamMap.remove(socket);
            inGameReconnectionHandler.playerReconnection(nickname,socket,outputStream);
        }
        else {

            List<Error> errors = matchController.onNewPlayer(nickname);

            boolean isFirstPlayer = false;
            if (errors.isEmpty()) {
                if (requiredNumberOfPlayers != -1) {
                    setRequiredNumberOfPlayers(requiredNumberOfPlayers);
                }
                onLoginAcceptedRequest(nickname, socket);
            } else {
                if (requiredNumberOfPlayers != -1) isFirstPlayer = true;
                onLoginRejectedRequest(nickname, errors, socket, isFirstPlayer);
            }
        }
    }

    /**
     * This method manage the successful login of a client, sends an update to the other players and a positive reply to
     * the new logged client
     * @param nickname the nickname of the new logged client
     * @param socket the new client socket
     */
    private void onLoginAcceptedRequest(String nickname, Socket socket){


        clients.put(nickname,socket);
        System.out.println(nickname + " logged in lobby number " + lobbyID );

        Message newUserMessage = new UpdateWriter().loginUpdate(nickname);
        for(String user: clients.keySet()){
            if(!user.equals(nickname)){
                sendMessage(clients.get(user), newUserMessage);
            }
        }

        sendMessage(socket, new UpdateWriter().loginDone(nickname));

        if(getLobbySize() == MAXIMUM_LOBBY_SIZE || isRequiredNumberOfPlayers()) toStartMatch();



    }

    /**
     * This method send a message to the clients that the match has started and call the on
     * to start the game on controller
     */
    private synchronized void toStartMatch() {

        isActive = true;
        matchController.onStartGame();

    }

    /**
     * This method manage the unsuccessful login of a client and alerts the client that it must login again
     * @param nickname the proposed nickname of the new client
     * @param socket the new client socket
     */
    private void onLoginRejectedRequest(String nickname, List<Error> errors, Socket socket, boolean isFirstPlayer){
        sendMessage(socket, new ErrorWriter().rejectedLogin(isFirstPlayer));
    }

    /**
     * This method ask a client to play its turn and send a wait message to the others
     * @param username the nickname of the player that has to play
     */
    public void playTurn(String username){

        SetGameBoardMessage message = new SetGameBoardMessage(matchController.getCardMarket(), matchController.getMarbleMarket(), matchController.getFreeMarble());

        for(Socket socket: clients.values()){
            sendMessage(socket,message);
            sendMessage(socket, new PlayTurnMessage(username));
        }
    }

    /**
     * This method manage the choose leaderCard request from client
     * @param leaderCards the chosen card
     * @param user the user that selects the card
     */
    public void chooseLeaderCards(String user, List<LeaderCard> leaderCards){
        List<Error> errors = matchController.onLeaderCardsChosen(user,leaderCards);

        if(isActive){
            if(errors.isEmpty())
                onAcceptedChooseLeaderCard(user,leaderCards);
            else
                onRejectedChooseLeaderCard(user, leaderCards);
        }


    }



    /**
     * This method send alerts the client that its leaderCard selection has been accepted
     * @param user the user requesting the card
     * @param leaderCards the card selected
     */
    private void onAcceptedChooseLeaderCard(String user, List<LeaderCard> leaderCards) {
        Message requestAccepted = new UpdateWriter().cardSelectionAccepted(user,leaderCards);
        sendMessage(clients.get(user), requestAccepted);
    }

    /**
     * This method send alerts the client that its leaderCard selection has been rejected
     * @param user the user requesting the card
     * @param leaderCard the card selected
     */
    private void onRejectedChooseLeaderCard(String user, List<LeaderCard> leaderCard) {
        Message requestRejected = new ErrorWriter().cardSelectionRejected(user,leaderCard);
        sendMessage(clients.get(user), requestRejected);
    }


    /**
     * This method manage the choose resource type request from client
     * @param resourceType the chosen resource type
     */
    public void chooseResourceType(String username, Map<ResourceType,Integer> resourceType){
        List<Error> errors = matchController.onResourcesChosen(username,resourceType);
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
    private void onAcceptedChooseResourceType(String user, Map<ResourceType, Integer> resourceType) {
        Message resourceTypeSelectionAccepted = new UpdateWriter().resourceTypeSelectionAccepted(user, resourceType);
        sendMessage(clients.get(user), resourceTypeSelectionAccepted);

        Message boardUpdate = new UpdatePlayerBoardMessage(matchController.sendBoardUpdate(user));
        sendMessage(clients.get(user), boardUpdate);
    }
    /**
     * This method send alerts the client that its resourceType selection has been rejected
     * @param user the user requesting the card
     * @param resourceType the type selected
     */
    private void onRejectedChooseResourceType(String user, Map<ResourceType, Integer> resourceType) {
        Message resourceTypeSelectionRejected = new ErrorWriter().resourceTypeSelectionRejected(user,resourceType);
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
                onAcceptedMoveDeposit(user, a, b);
            else
                onRejectedMoveDeposit(user, a, b);
        }
    }

    /**
     * This method alerts the client that its move deposit request has been accepted
     * @param user the user
     */
    private void onAcceptedMoveDeposit(String user, int a, int b) {
        Message message = new UpdateWriter().moveDepositRequestAccepted(user,a, b);
        sendMessage(clients.get(user), message);

        Message boardUpdate = new UpdatePlayerBoardMessage(matchController.sendBoardUpdate(user));
        sendMessage(clients.get(user), boardUpdate);
    }

    /**
     * This method alerts the client that its move deposit request has been rejected
     * @param user the user
     */
    private void onRejectedMoveDeposit(String user, int a, int b) {
        Message message = new ErrorWriter().moveDepositRequestRejected(user,a, b);
        sendMessage(clients.get(user), message);
    }


    /**
     * This method manage a buy resource requests from client
     * @param x, y are the coordinates of the card
     */
    public void buyDevelopmentCards(String user, int x, int y){
        List<Error> errors = matchController.onBuyDevelopmentCards(user, x,y);
        errors.forEach(System.out::println);
        if(isActive){
            if(errors.isEmpty())
                onAcceptedBuyDevelopmentCards(user,x,y);
            else
                onRejectedBuyDevelopmentCards(user,x,y);
        }
    }


    private void onAcceptedBuyDevelopmentCards(String user, int x, int y) {
        Message message = new UpdateWriter().buyCardAccepted(user, x, y);
      //  for(Socket socket: clients.values())
        //    sendMessage(socket, message);
        sendMessage(clients.get(user), message);
    }

    private void onRejectedBuyDevelopmentCards(String user, int x, int y) {

        Message message = new ErrorWriter().buyCardRejected(user,x, y);
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

        Message message = new UpdateWriter().buyResourceAccepted(user,x,y);
        ((BuyResourcesMessage) message).setResourceList(boughtResources);
        updatePlayerPosition(user);

        /*
        Message boardUpdate = new UpdatePlayerBoardMessage(matchController.sendBoardUpdate(user));
        sendMessage(clients.get(user), boardUpdate);

         */

        sendMessage(clients.get(user), message);

        sendGameBoard(matchController.getCardMarket(), matchController.getMarbleMarket(), matchController.getFreeMarble());


    }

    private void onRejectedBuyResources(String user, int x, int y) {
        Message message = new ErrorWriter().buyResourceRejected(user,x,y);
        sendMessage(clients.get(user), message);

    }

    /**
     * This method manage the activate production development card request from client
     * @param developmentCards the cards  to use
     * @param user the current user
     */
    public void activateProductionDevelopmentCard(String user, List<DevelopmentCard> developmentCards) {
        List<Error> errors = matchController.onActivateDevelopmentProduction(user, developmentCards);
        if (isActive) {
            if (errors.isEmpty())
                onAcceptedActivateProductionDevelopmentCard(user, developmentCards);
            else
                onRejectedActivateProductionDevelopmentCard(user, developmentCards);
        }
    }

    private void onAcceptedActivateProductionDevelopmentCard(String user, List<DevelopmentCard> cards) {

        Message message = new UpdateWriter().productionCardAccepted(user,cards);
        sendMessage(clients.get(user), message);
        updatePlayerPosition(user);

        //Message boardUpdate = new UpdatePlayerBoardMessage(matchController.sendBoardUpdate(user));
        //sendMessage(clients.get(user), boardUpdate);
    }

    private void onRejectedActivateProductionDevelopmentCard(String user, List<DevelopmentCard> cards) {
        Message message = new ErrorWriter().productionCardRejected(user,cards);
        sendMessage(clients.get(user), message);
    }

    /**
     * This method manage the activate production board request from client
     */
    public void activateProductionBoard(String user, Map<Resource,List<ResourceType>> userChoice){

        List<Error> errors = matchController.onActivateBoardProduction(user, userChoice);
        if(isActive){
            if(errors.isEmpty())
                onAcceptedActivateProductionBoard(user, userChoice);
            else
                onRejectedActivateProductionBoard(user, userChoice);

        }

    }

    private void onAcceptedActivateProductionBoard(String user, Map<Resource,List<ResourceType>> userChoice) {
        Message message = new UpdateWriter().productionBoardAccepted(user,userChoice);
        sendMessage(clients.get(user), message);
        updatePlayerPosition(user);
        //Message boardUpdate = new UpdatePlayerBoardMessage(matchController.sendBoardUpdate(user));
        //sendMessage(clients.get(user), boardUpdate);
    }


    private void onRejectedActivateProductionBoard(String user,  Map<Resource,List<ResourceType>> userChoice) {
        Message message = new ErrorWriter().productionBoardRejected(user,userChoice);
        sendMessage(clients.get(user), message);
    }


    /**
     * This method manage the activate production leader card request from client
     * @param leaderCards the selected cards
     * @param user the current user
     */
    public void activateProductionLeaderCard(String user, List<LeaderCard> leaderCards){
        List<Error> errors = matchController.onActivateLeaderProduction(user,leaderCards);
        if(isActive){
            if(errors.isEmpty())
                onAcceptedActivateProductionLeaderCard(user,leaderCards);
            else
                onRejectedActivateProductionLeaderCard(user,leaderCards);
        }
    }

    private void onAcceptedActivateProductionLeaderCard(String user, List<LeaderCard> card){
        Message message = new UpdateWriter().productionLeaderAccepted(user,card);
        sendMessage(clients.get(user), message);
        updatePlayerPosition(user);

    }

    private void onRejectedActivateProductionLeaderCard(String user, List<LeaderCard> card){
        Message message = new ErrorWriter().productionLeaderRejected(user,card);
        sendMessage(clients.get(user), message);

    }

    public void endProduction(String user){

        List<Error> errors = matchController.onEndProduction(user);
        if(!errors.isEmpty()) {
            System.out.println("End production not successful");
            errors.forEach(System.out::println);
        }
    }


    /**
     * This method manage the activation of a leader card
     * @param card the card to use
     */
    public void activateLeaderCard(String user, LeaderCard card){
        List<Error> errors = matchController.onActivateLeader(user,card);
        if(isActive()) {
            if (errors.isEmpty())
                onAcceptedActivateLeaderCard(user, card);
            else
                onRejectedActivateLeaderCard(user, card);
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
            else {
                errors.forEach(System.out::println);
                onRejectedDiscardLeaderCard(user, card);
            }

        }
    }


    private void onAcceptedDiscardLeaderCard(String user, LeaderCard card){
        Message message = new UpdateWriter().discardLeaderAccepted(card);
        sendMessage(clients.get(user), message);
        updatePlayerPosition(user);
    }

    private void onRejectedDiscardLeaderCard(String user, LeaderCard card) {
        Message message = new ErrorWriter().discardLeaderRejected(card);
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

    /**
     * This method alerts a user that its turn is finished
     * @param nickname the user's nickname
     */
    public void endTurn(String nickname){
        //sendMessage(clients.get(nickname, new MoveOnPopeRoadMessage());
        matchController.onEndTurn(nickname);
    }

    /**
     * This method alerts the clients that it is the last turn to play
     */
    public void lastRound(){

    }

    public void toDoChooseLeaderCards(String user, List<LeaderCard> leaderCards){
        sendMessage(clients.get(user), new ChooseLeadersMessage(user,leaderCards,false));
    }


    /**
     * Asks the user to choose its resources
     * @param nickname the user's nickname
     * @param numberOfResources the amount of resources to select
     */
    public void toDoChooseResources(String nickname, int numberOfResources){
        sendMessage(clients.get(nickname), new SelectResourcesMessage(numberOfResources));
    }

    /**
     * This method check if the match has the required number of player
     * @return true if number of player is enough
     */
    public synchronized boolean isRequiredNumberOfPlayers() {
        return this.requiredNumberOfPlayers == clients.size();
    }

    /**
     * This method set the required number of players for this match
     * @param numberOfPlayers the selected number of players
     */
    public synchronized void setRequiredNumberOfPlayers(int numberOfPlayers) {
        this.requiredNumberOfPlayers = numberOfPlayers;
    }

    /**
     * This method handle a place resource request from the client

     */
    public void placeResource(String user, Map<Resource,Integer> userChoice) {
        List<Error> errors = matchController.onPlaceResources(user,userChoice);
        if(isActive()){
            if(errors.isEmpty())
                onAcceptedPlaceResource(user, userChoice);
            else
                onRejectedPlaceResource(user, userChoice);
        }
    }

    private void onRejectedPlaceResource(String user, Map<Resource, Integer> userChoice) {
        Message message = new ErrorWriter().placeResourceRejected(user,userChoice);
        sendMessage(clients.get(user), message);
    }

    private void onAcceptedPlaceResource(String user, Map<Resource, Integer> userChoice) {
        Message message = new UpdateWriter().placeResourceAccepted(user, userChoice);
        sendMessage(clients.get(user), message);

        Message boardUpdate = new UpdatePlayerBoardMessage(matchController.sendBoardUpdate(user));
        sendMessage(clients.get(user), boardUpdate);
    }


    /**
     * This method manage a discard resources request from client
     * @param user the player
     * @param numberOfResourcesToDiscard the number of resources to discard
     */
    public void discardResources(String user, int numberOfResourcesToDiscard) {
        matchController.onDiscardResource(user, numberOfResourcesToDiscard);
    }
    /**
     * This method manage an activate production request from the client
     * @param user the current player
     */
    public void activateProduction(String user) {

        List<Error> errors = matchController.onActivateProduction(user);
    }

    // I temporary save the resource and send them after the buy resources action is accepted
    public synchronized void sendResourcesBought(List<Resource> resources){
        boughtResources = resources;
    }

    @Override
    public void sendGameBoard(DevelopmentDeck[][] cardMarket, Marble[][] market, Marble freeMarble) {

        SetGameBoardMessage message = new SetGameBoardMessage(cardMarket,market,freeMarble);
        clients.values().forEach(p->sendMessage(p,message));
    }


    public synchronized void reconnectPlayer(String disconnectedPlayer, Socket socket, ObjectOutputStream outputStream) {

        clients.put(disconnectedPlayer,socket);
        socketObjectOutputStreamMap.put(socket,outputStream);
        System.out.println(disconnectedPlayer + " is back!");
        //sendMessage(socket, new UpdateWriter().loginDone(disconnectedPlayer));
        sendMessage(clients.get(disconnectedPlayer), new ReconnectedMessage(disconnectedPlayer));
        matchController.onPlayerReconnection(disconnectedPlayer);
    }

    public ObjectOutputStream getOutputStream(Socket socket) {
        return socketObjectOutputStreamMap.get(socket);
    }


    public void updatePlayerPosition(String nickname){

        Message message = new MoveOnPopeRoadMessage(matchController.getPlayerCurrentPosition(nickname));
        sendMessage(clients.get(nickname), message);
    }

    @Override
    public void sendLorenzoTurn(ActionToken lorenzoAction, int lorenzoPosition) {
        Message message = new LorenzoTurnMessage(lorenzoAction, lorenzoPosition);
        clients.values().forEach(p->sendMessage(p,message));
    }

    @Override
    public void setProductionResult(String user, Map<ResourceType, List<Resource>> updateStrongbox, List<List<Resource>> updatedWarehouse) {

        EndProductionMessage message = new EndProductionMessage(user);
        message.setProductionResult(updateStrongbox, updatedWarehouse);
        sendMessage(clients.get(user), message);
        updatePlayerPosition(user);
    }

    public void sendPlayerBoardUpdateToOthers(UpdateClientPlayerBoardsMessage updateClientPlayerBoardsMessage) {

        String sender = updateClientPlayerBoardsMessage.getUser();
        for(String user: clients.keySet()){
            if(!user.equals(sender))
                sendMessage(clients.get(user), updateClientPlayerBoardsMessage);
        }

    }
}

