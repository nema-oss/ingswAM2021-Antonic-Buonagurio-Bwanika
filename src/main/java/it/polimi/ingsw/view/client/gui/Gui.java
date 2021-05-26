package it.polimi.ingsw.view.client.gui;

import it.polimi.ingsw.messages.setup.server.DoLoginMessage;
import it.polimi.ingsw.model.cards.DevelopmentCard;
import it.polimi.ingsw.model.cards.leadercards.LeaderCard;
import it.polimi.ingsw.model.gameboard.CardMarket;
import it.polimi.ingsw.model.gameboard.Resource;
import it.polimi.ingsw.model.gameboard.ResourceType;
import it.polimi.ingsw.model.player.Board;
import it.polimi.ingsw.model.player.Player;
import it.polimi.ingsw.network.client.EchoClient;
import it.polimi.ingsw.view.client.View;
import it.polimi.ingsw.view.client.gui.controllers.*;
import it.polimi.ingsw.view.client.utils.TurnActions;
import it.polimi.ingsw.view.client.viewComponents.ClientGameBoard;
import it.polimi.ingsw.view.client.viewComponents.ClientPlayer;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.stage.Stage;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.List;
import java.util.Map;

public class Gui extends View {

    private Stage primaryStage;
    private Scene startingScene;
    private Scene nicknameScene;

    private NumOfPlayersController numOfPlayersController;

    private Scene loginWaitScene;
    private LoginWaitController loginWaitController;

    private ChooseLeaderController chooseLeaderController;
    private Scene chooseLeaderScene;

    private ChooseResourcesController chooseResourcesController;
    private Scene chooseResourcesScene;

    private ConnectionController connectionController;

    private GameBoardController gameBoardController;
    private Scene gameBoardScene;

    private PlayerBoardController playerBoardController;

    private GameController gameSceneController;
    private Scene gameScene;
    private List<Player> players;


    public Gui(String ip, int port, Stage stage, Scene scene){

        ip = ip;
        port = port;
        this.primaryStage = stage;
        this.startingScene = scene;
        initLoginUsername();
        initGameScene();
        intEndGame();


    }

    private void initLoginUsername() {

        try{
            FXMLLoader loader = GuiManager.loadFXML("nickname");
            Parent root = loader.load();
            nicknameScene = new Scene(root);
            NicknameController nicknameController = loader.getController();
            nicknameController.setGui(this);
        }catch(IOException e){
            System.out.println("Could not initialize Nickname Scene");
        }
    }


    /**
     * This method loads the FXML of the lobby scene and initializes its controller
     */

    private void initLoginWait() {
        try {
            FXMLLoader loader = GuiManager.loadFXML("waiting");
            Parent root = loader.load();
            loginWaitScene = new Scene(root);
            loginWaitController = loader.getController();
            loginWaitController.setGui(this);
        } catch (IOException e) {
            System.out.println("Could not initialize loginWait Scene");
        }
    }


    private void initChooseLeadersSelection(List<LeaderCard> leaderCards){

        try {
            FXMLLoader loader = GuiManager.loadFXML("chooseLeaders");
            Parent root = loader.load();
            chooseLeaderScene = new Scene(root);
            chooseLeaderController = loader.getController();
        } catch (IOException e) {
            System.out.println("Could not initialize Choose Leaders Scene");
        }
    }

    private void initChooseResourcesSelection(List<ResourceType> resourceTypeList){

        try {
            FXMLLoader loader = GuiManager.loadFXML("chooseResources");
            Parent root = loader.load();
            chooseResourcesScene = new Scene(root);
            chooseResourcesController = loader.getController();
        } catch (IOException e) {
            System.out.println("Could not initialize Choose Resources Scene");
        }
    }


    private void initGameScene() {

        try {
            FXMLLoader loader = GuiManager.loadFXML("game");
            Parent root = loader.load();
            gameScene = new Scene(root);
            gameSceneController = loader.getController();
            gameSceneController.setGui(this);
        } catch (IOException e) {
            System.out.println("Could not initialize Game Scene");
        }
    }


    private void intEndGame() {
    }

    /**
     * This method throws an alert to the user and it is called when something goes wrong
     * @param title alert's title
     * @param text alert's text
     */

    public void alertUser(String title, String text, Alert.AlertType type){
        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setHeaderText(text);
        alert.showAndWait();
    }

    /**
     * Allows to set the server IP address
     */
    @Override
    public void setMyIp() {

    }

    /**
     * Allows to set the port
     */
    @Override
    public void setMyPort() {

    }

    /**
     * Asks the users to choose its leader card
     *
     * @param cardChoice the card pool
     */
    @Override
    public void setLeaderCardChoice(List<LeaderCard> cardChoice) {

        Platform.runLater( () -> {
            String infoMessage = "Select two leader cards. ";
            try {
                FXMLLoader loader = GuiManager.loadFXML("chooseLeaders");
                Parent root = loader.load();
                chooseLeaderScene = new Scene(root);
                chooseLeaderController = loader.getController();
                chooseLeaderController.setGui(this);
                chooseLeaderController.initializeLeaderCards(cardChoice);
                chooseLeaderController.hideFinalConfirmButton();
            } catch (IOException e) {
                System.out.println("Could not initialize Choose Leaders Scene");
            }
            primaryStage.setScene(chooseLeaderScene);
            primaryStage.show();
            chooseLeaderController.setInstructionLabel(infoMessage);
        });
    }

    /**
     * Asks the user to choose its resource
     *
     * @param numberOfResources number of resources the user can choose
     */
    @Override
    public void setResourceTypeChoice(int numberOfResources) {

        Platform.runLater( () -> {
            String infoMessage = "Select " + numberOfResources + " resource type. ";
            List<ResourceType> resourceTypes = ResourceType.getAllResourceType();
            try {
                FXMLLoader loader = GuiManager.loadFXML("chooseResources");
                Parent root = loader.load();
                chooseResourcesScene = new Scene(root);
                chooseResourcesController = loader.getController();
                chooseResourcesController.setGui(this);
                chooseResourcesController.initializeResourceTypes(resourceTypes);
                chooseLeaderController.hideFinalConfirmButton();
            } catch (IOException e) {
                System.out.println("Could not initialize Choose Resources Scene");
            }
            primaryStage.setScene(chooseLeaderScene);
            primaryStage.show();
            chooseLeaderController.setInstructionLabel(infoMessage);
        });
    }

    /**
     * Asks the user to play its turn action
     */
    @Override
    public void askTurnAction() {

        TurnActions action = gameSceneController.getTurnAction();
        switch (action){
            case BUY_CARD:
                setBuyCardAction(false);
                break;
            case BUY_RESOURCES:
                setBuyResourceAction(false);
                break;
            case ACTIVATE_PRODUCTION:
                setProductionChoice(player.getDevelopmentCards(), player.getProductionLeaderCards(),false);
                break;
            case LEADER_ACTION:
                setLeaderCardAction(player.getHand(),false);
                break;
            case END_TURN:

        }
    }

    /**
     * Asks the user what card productions it wants to use
     *
     * @param developmentCards     its development card
     * @param leaderCards          its leader card type production
     * @param actionRejectedBefore true if the action was rejected before
     */
    @Override
    public void setProductionChoice(List<DevelopmentCard> developmentCards, List<LeaderCard> leaderCards, boolean actionRejectedBefore) {

        Platform.runLater(()->{
            if(!actionRejectedBefore){
                gameSceneController.setInstructionLabel("Select which production power you want to activate.");
                gameSceneController.setProductionDevelopmentCard(developmentCards);
                gameSceneController.setProductionLeaderCard(leaderCards);
            }else{
                //game.restore();
                alertUser("Warning", "Try again.", Alert.AlertType.WARNING);
            }
        });
    }

    /**
     * Asks the user what leader card it wants to use
     *
     * @param leaderCards          its leader card
     * @param actionRejectedBefore true if the action was rejected before
     */
    @Override
    public void setLeaderCardAction(List<LeaderCard> leaderCards, boolean actionRejectedBefore) {

        Platform.runLater(()->{
            if(!actionRejectedBefore){
                gameSceneController.setInstructionLabel("Select which cards you want to discard/activate. ");
                gameSceneController.setLeaderCardHand(player.getHand());
            }else{
                //gameSceneController.restore();
                String informationMessage = "Try again. ";
                alertUser("Warning!", informationMessage, Alert.AlertType.WARNING);

            }
        });
    }

    /**
     * Asks the user what card it wants to buy
     *
     * @param actionRejectedBefore true if the action was rejected before
     */
    @Override
    public void setBuyCardAction(boolean actionRejectedBefore) {

        Platform.runLater(()->{
            if(!actionRejectedBefore){
                gameSceneController.setInstructionLabel("Select which card you want to buy");
                gameSceneController.showCardMarket();
            }else{
                //gameSceneController.restore();
                alertUser("Warning!", "Try again", Alert.AlertType.WARNING);
            }
        });
    }

    /**
     * Asks the user what resources it wants to buy
     *
     * @param actionRejectedBefore true if the action was rejected before
     */
    @Override
    public void setBuyResourceAction(boolean actionRejectedBefore) {
        Platform.runLater(()->{
            if(!actionRejectedBefore){
                gameSceneController.setInstructionLabel("Select which row/column you want to buy");
                gameSceneController.showResourceMarket();
            }else{
                //gameSceneController.restore();
                alertUser("Warning!", "Try again", Alert.AlertType.WARNING);
            }
        });
    }

    /**
     * Shows the login
     *
     * @param message login message
     */
    @Override
    public void showLogin(DoLoginMessage message) {

        boolean isFirstPlayer = message.isFirstPlayer();

        if(isFirstPlayer){
            Platform.runLater(
                    () -> {
                        primaryStage.setScene(nicknameScene);
                        numOfPlayersController.showNumberOfPlayersButton();
                        primaryStage.show();
                    });
        }else{
            primaryStage.setScene(nicknameScene);
            numOfPlayersController.hideNumberOfPlayersButton();
            primaryStage.show();
        }

    }

    /**
     * Shows the player that the login has been done
     *
     * @param user
     */
    @Override
    public void showLoginDone(String user) {

        newMatch(user);

        Platform.runLater(
                () -> {
                    initLoginWait();
                    String infoMessage = "Login done, matchmaking... ";
                    loginWaitController.setInformationBox(infoMessage);
                    primaryStage.setScene(loginWaitScene);
                    primaryStage.show();
                });

    }

    /**
     * Shows the player that a new player has logged in
     *
     * @param username : username of the newly logged in player
     */
    @Override
    public void showNewUserLogged(String username) {

        String infoMessage = loginWaitController.getInformationBox() + "\n" + username + " is a new player!";
        Platform.runLater(()->{
                    loginWaitController.setInformationBox(infoMessage);
                });
    }


    /**
     * Shows that the server has not been found
     */
    @Override
    public void serverNotFound() {

    }

    /**
     * Shows that another client has disconnected
     *
     * @param otherClient
     */
    @Override
    public void showAnotherClientDisconnection(String otherClient) {

    }

    /**
     * This method alerts the user that the server disconnected
     */
    @Override
    public void showServerDisconnection() {

    }

    /**
     * Shows that the player has lost
     *
     * @param winner : username of the winner
     */
    @Override
    public void showYouLose(String winner) {

    }

    /**
     * Shows that the player has won
     */
    @Override
    public void showYouWin() {

    }

    /**
     * Shows the game is finished
     */
    @Override
    public void showEndGame() {

    }

    /**
     * This method tells the user that login has been rejected
     *
     * @param isFirstPlayer true if it's the first player in the lobby
     */
    @Override
    public void showLoginFailed(boolean isFirstPlayer) {

        Platform.runLater(()->{
            alertUser("Error","Login failed. Try again with a different username", Alert.AlertType.ERROR);
            /*
            DoLoginMessage doLoginMessage = new DoLoginMessage();
            doLoginMessage.setFirstPlayer(isFirstPlayer);
            showLogin(doLoginMessage);
             */
        });

    }


    /**
     * This method tells the user that it has to play its turn
     *
     * @param currentPlayer the player that it's playing now
     */
    @Override
    public void showPlayTurn(String currentPlayer) {

        Platform.runLater(()->{
            if(currentPlayer.equals(player.getNickname())){
                gameSceneController.showActionButtons();
                gameSceneController.showYourTurnMessage();
            }else{
                gameSceneController.hideActionButtons();
                gameSceneController.showOtherTurnMessage(currentPlayer);
            }
        });
    }


    /**
     * This method set the phase to choose where to place the resources after a buy resource action
     */
    @Override
    public void setPlaceResourcesAction() {

        Platform.runLater(()->{
            gameSceneController.showPlaceResourcesButton();
        });
    }

    /**
     * This method tells the user that the leader card action has been accepted
     */
    @Override
    public void showAcceptedLeaderAction() {
        Platform.runLater(()->{
            alertUser("Information", "Leader card action accepted.", Alert.AlertType.CONFIRMATION);
        });
    }

    /**
     * This method tells the user that the leader card action has been accepted
     */
    @Override
    public void showRejectedLeaderAction() {
        Platform.runLater(()->{
            alertUser("Warning", "Leader card action rejected.", Alert.AlertType.WARNING);
        });
    }

    /**
     * This method tells the user that the buy card action has been accepted
     *
     * @param x
     * @param y
     */
    @Override
    public void showAcceptedBuyDevelopmentCard(int x, int y) {
        /*
        Platform.runLater(()->{
            alertUser("Warning", "Leader card action rejected.", Alert.AlertType.WARNING);
        });

         */
    }

    /**
     * This method tells the user that the activate production request has been rejected
     */
    @Override
    public void showProductionError() {
        Platform.runLater(()->{
            alertUser("Warning", "Production request rejected.Try again", Alert.AlertType.WARNING);
        });
        setProductionChoice(player.getDevelopmentCards(),player.getProductionLeaderCards(),true);

    }


    /**
     * Shows the results of the move deposit request.
     * @param x,y the floors to swap
     * @param accepted true if the request has been accepted, false if rejected
     */
    @Override
    public void showMoveDepositResult(int x, int y, boolean accepted) {

        if(accepted) {
            player.getDeposit().swapFloors(x, y);
        }
        else {
            Platform.runLater(()->{
                alertUser("Warning","Move deposit request rejected. Try again", Alert.AlertType.WARNING);
            });
            setPlaceResourcesAction();
        }
    }

    /**
     * Shows the result of the place resources request.
     *
     * @param accepted   true if the request has been accepted
     * @param userChoice the user choice
     */
    @Override
    public void showPlaceResourcesResult(boolean accepted, Map<Resource, Integer> userChoice) {
        if(accepted){

            Platform.runLater(()->{
                gameSceneController.setInstructionLabel("The other resources will be discarded.");
                //gameSceneController.nextTurnAction();
            });
            player.addResource(userChoice);
            //askTurnAction();
        }
        else{
            alertUser("Warning","Incorrect place resources. Try again.", Alert.AlertType.WARNING);
            setPlaceResourcesAction();
        }
    }

    @Override
    public void showReconnectionToMatch() {

    }

    public void start() {
        new EchoClient(myIp,myPort,this).start();
    }
}
