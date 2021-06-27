package it.polimi.ingsw.view.client.gui.controllers;

import it.polimi.ingsw.model.cards.DevelopmentCard;
import it.polimi.ingsw.model.cards.leadercards.LeaderCard;
import it.polimi.ingsw.view.client.gui.Gui;
import it.polimi.ingsw.view.client.utils.TurnActions;
import it.polimi.ingsw.view.client.gui.GuiManager;
import it.polimi.ingsw.view.client.viewComponents.ClientGameBoard;
import it.polimi.ingsw.view.client.viewComponents.ClientPlayer;
import it.polimi.ingsw.view.client.viewComponents.ClientPlayerBoard;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;

import java.io.IOException;
import java.net.URL;

import java.util.List;
import java.util.ResourceBundle;

/**
 * this class is the controller for the "game.fxml" file
 * @author chiara
 */
public class GameController implements Initializable{

    @FXML
    public BorderPane gameBoardPane, playerBoardPane, leftBorder;

    @FXML
    private Label instructionalLabel;


    public  GameBoardController gameBoardController;
    public  PlayerTabController playerTabController;
    public  ChooseLeaderController chooseLeaderController;
    public ActionButtonsController actionButtonsController;


    public TurnActions getTurnAction() {
        return null;
    }

    public void setInstructionLabel(String s) {
        instructionalLabel.setText(s);
    }

    public void setLeaderCardHand(List<LeaderCard> hand) {
    }

    public void showResourceMarket() {
    }


    public void setProductionDevelopmentCard(List<DevelopmentCard> developmentCards) {
    }

    public void setProductionLeaderCard(List<LeaderCard> leaderCards) {
    }

    public Gui gui;

    public void setGui(Gui gui){
        this.gui = gui;
    }

    /**
     * this method initializes the game putting the general game board, the players' boards and the leader choice scene into it
     * @param location
     * @param resources
     */
    @Override
    public void initialize(URL location, ResourceBundle resources) {

        try {
            GuiManager.gameController = this;
            instructionalLabel.setVisible(false);

            FXMLLoader loader1 = GuiManager.loadFXML("/gui/gameBoard");
            gameBoardPane.setCenter(loader1.load());
            gameBoardController = loader1.getController();

            FXMLLoader loader2 = GuiManager.loadFXML("/gui/playerTab");
            playerBoardPane.setCenter(loader2.load());
            playerTabController = loader2.getController();

            FXMLLoader loader3 = GuiManager.loadFXML("/gui/chooseLeaders");
            leftBorder.setCenter(loader3.load());
            chooseLeaderController = loader3.getController();

        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    /**
     * this method returns the controller for the "choose leaders scene"
     */
    public ChooseLeaderController getChooseLeaderController(){
        return chooseLeaderController;
    }

    /**
     * this method initializes the game board
     */
    public void initializeGameBoard(){
        gameBoardController.initialize(gui.getClientGameBoard());
        gameBoardController.setGui(gui);
    }


    /**
     * this method initializes each player's game  board
     * @throws IOException if unable to add the player board
     */
    public void initializePlayerBoard() throws IOException {

        playerTabController.setGui(gui);
        for(String playerNickname : gui.getOtherPlayerBoards().keySet()) {
           playerTabController.addPlayerBoard(playerNickname,gui.getOtherPlayerBoards().get(playerNickname),false);
        }
        playerTabController.addPlayerBoard(gui.getPlayerNickname(),gui.getClientPlayer().getPlayerBoard(),true);
    }

    /**
     * this method adds a new player board
     * @param user the player
     * @param clientPlayerBoard his board
     */
    public void addPlayerBoard(String user, ClientPlayerBoard clientPlayerBoard){
        try {
            playerTabController.addPlayerBoard(user, clientPlayerBoard, false);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void initializeActions(){
    }

    /**
     * this method sets up thew player's board
     * @param gameBoard his board
     */
    public void initializeGameBoard(ClientGameBoard gameBoard) {

        gameBoardController.updateCardMarket(gameBoard);
        gameBoardController.updateMarbleMarket(gameBoard);
    }

    /**
     * this method shows the leader cards just chosen to the player's board
     */
    public void addLeadersToPlayer(){
        playerTabController.addLeadersChosen(gui.getClientPlayer());
    }

    /**
     * this method allows or denies click on crad Market
     * @param bool true to allow, false to deny
     */
    public void makeCardMarketClickable(boolean bool){
        gameBoardController.setCardMarketClickable(bool);
    }

    /**
     * this method allows or denies clicks on production elements
     * @param bool true to allow, false to deny
     */
    public void makeProductionClickable(boolean bool) {
        playerTabController.setProductionClickable(gui.getClientPlayer(), bool);
    }

    /**
     * this method updates one's board
     * @param clientPlayer the player
     * @param clientPlayerBoard the board to be updated
     */
    public void updatePlayerBoard(String clientPlayer, ClientPlayerBoard clientPlayerBoard) {
        playerTabController.updatePlayerBoard(clientPlayer,clientPlayerBoard);
    }

    /**
     * this method allows or denies clicks on leader cards
     * @param bool true to allow, false to deny
     */
    public void setLeaderAction(boolean bool){
        playerTabController.setLeaderAction(gui.getClientPlayer(), bool);
    }

    /**
     * this method hides initial leader cards
     */
    public void hideLeaders(){
        chooseLeaderController.hide();
    }

    /**
     * Remove a disconnected player's playerboard from the pane
     * @param otherClient the player that disconnected
     */
    public void removePlayerBoard(String otherClient) {
        playerTabController.removePlayerBoard(otherClient);
    }


    public void addLeaderToOtherPlayer(String user, LeaderCard card){
        playerTabController.controllersMap.get(user).addLeaderOnOtherClient(card);
    }
}
