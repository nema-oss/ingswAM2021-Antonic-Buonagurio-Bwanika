package it.polimi.ingsw.view.client.gui;

import it.polimi.ingsw.messages.setup.server.DoLoginMessage;
import it.polimi.ingsw.model.cards.DevelopmentCard;
import it.polimi.ingsw.model.cards.leadercards.LeaderCard;
import it.polimi.ingsw.model.gameboard.CardMarket;
import it.polimi.ingsw.model.gameboard.Resource;
import it.polimi.ingsw.model.gameboard.ResourceType;
import it.polimi.ingsw.model.player.Board;
import it.polimi.ingsw.view.client.View;
import it.polimi.ingsw.view.client.viewComponents.ClientGameBoard;
import it.polimi.ingsw.view.client.viewComponents.ClientPlayer;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.List;
import java.util.Map;

public class Gui extends View {

    private Stage primaryStage;
    private Scene startingScene;
    private Scene nicknameScene;

    public Gui(String ip, int port, Stage stage, Scene scene){
        //super(ip,port)
        this.primaryStage = stage;
        this.startingScene = scene;
        //inizializza prima scena

    }


    public void start(){}

    @Override
    public void setMyIp() {

    }

    @Override
    public void setMyPort() {

    }


    @Override
    public void setLeaderCardChoice(List<LeaderCard> cardChoice) {

    }

    @Override
    public void setResourceTypeChoice(int numberOfResources) {

    }

    @Override
    public void askTurnAction() {

    }

    @Override
    public void setProductionChoice(List<DevelopmentCard> developmentCards, List<LeaderCard> leaderCards, boolean actionRejectedBefore) {

    }

    @Override
    public void setLeaderCardAction(List<LeaderCard> leaderCards, boolean actionRejectedBefore) {

    }

    @Override
    public void setBuyCardAction(boolean actionRejectedBefore) {

    }

    @Override
    public void setBuyResourceAction(boolean actionRejectedBefore) {

    }

    @Override
    public void showLogin(DoLoginMessage message) {

    }

    @Override
    public void showLoginDone(String user) {

    }

    @Override
    public void showNewUserLogged(String username) {

    }


    @Override
    public void showBoard(ClientGameBoard board, ClientPlayer player) {

    }

    @Override
    public void showGameBoard(ClientGameBoard gameBoard) {

    }

    @Override
    public void serverNotFound() {

    }

    @Override
    public void showAnotherClientDisconnection(String otherClient) {

    }

    @Override
    public void showServerDisconnection() {

    }

    @Override
    public void showYouLose(String winner) {

    }

    @Override
    public void showYouWin() {

    }

    @Override
    public void showEndGame() {

    }

    @Override
    public void showLoginFailed(boolean isFirstPlayer) {

    }

    @Override
    public void setReceiver(Socket socket, ObjectOutputStream outputStream) {

    }

    @Override
    public void showPlayTurn(String currentPlayer) {

    }


    @Override
    public void showLeaderCards(Map<LeaderCard, Boolean> status) {

    }


    @Override
    public void showAllAvailableResources() {

    }

    @Override
    public void setPlaceResourcesAction() {

    }

    @Override
    public void showAcceptedLeaderAction() {

    }

    @Override
    public void showAcceptedBuyDevelopmentCard(int x, int y) {

    }

    @Override
    public void showProductionError() {

    }

    @Override
    public void showLeaderCardsSelectionAccepted(List<LeaderCard> choice) {

    }

    @Override
    public void showRejectedLeaderAction() {

    }

    @Override
    public void showMoveDepositResult(int x, int y, boolean accepted) {

    }

    /**
     * Shows the result of the place resources request.
     *
     * @param accepted   true if the request has been accepted
     * @param userChoice the user choice
     */
    @Override
    public void showPlaceResourcesResult(boolean accepted, Map<Resource, Integer> userChoice) {

    }

    @Override
    public void setBoughtResources(List<Resource> resources) {

    }

    /**
     * Show the results of the selection the initial resources
     *
     * @param resourceChoice the user choice
     */
    @Override
    public void showResourceSelectionAccepted(Map<ResourceType, Integer> resourceChoice) {

    }

    @Override
    public void showReconnectionToMatch() {

    }

}
