package it.polimi.ingsw.view.client.gui.controllers;

import it.polimi.ingsw.model.cards.DevelopmentCard;
import it.polimi.ingsw.model.cards.leadercards.LeaderCard;
import it.polimi.ingsw.view.client.gui.Gui;
import it.polimi.ingsw.view.client.utils.TurnActions;
import it.polimi.ingsw.view.client.gui.GuiManager;
import it.polimi.ingsw.view.client.viewComponents.ClientGameBoard;
import it.polimi.ingsw.view.client.viewComponents.ClientPlayer;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
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

    public  GameBoardController gameBoardController;
    public  PlayerTabController playerTabController;
    public  ChooseLeaderController chooseLeaderController;
    public  OthersController othersController;


    public TurnActions getTurnAction() {
        return null;
    }

    public void setInstructionLabel(String s) {
    }

    public void setLeaderCardHand(List<LeaderCard> hand) {
    }

    public void showResourceMarket() {
    }

    public void showCardMarket() {
    }

    public void setProductionDevelopmentCard(List<DevelopmentCard> developmentCards) {
    }

    public void setProductionLeaderCard(List<LeaderCard> leaderCards) {
    }

    public void showPlaceResourcesButton() {

    }
    public Gui gui;

    public void setGui(Gui gui){
        this.gui = gui;
    }

    /**
     * this method initializes the game putting the general gameboard, the players gameboards and the leader choice scene into it
     * @param location
     * @param resources
     */
    @Override
    public void initialize(URL location, ResourceBundle resources) {

        try {
            GuiManager.gameController = this;

            FXMLLoader loader1 = GuiManager.loadFXML("/gui/gameBoard");
            gameBoardPane.setCenter(loader1.load());
            gameBoardController = loader1.getController();

            FXMLLoader loader2 = GuiManager.loadFXML("/gui/playerTab");
            playerBoardPane.setCenter(loader2.load());
            playerTabController = loader2.getController();

            FXMLLoader loader3 = GuiManager.loadFXML("/gui/chooseLeaders");
            leftBorder.setCenter(loader3.load());
            chooseLeaderController = loader3.getController();

          /*  List<LeaderCard> leaders = new CardFactory().getLeaderCards();
            List<LeaderCard> chosen = new ArrayList<>();
            chosen.add(leaders.get(0));
            chosen.add(leaders.get(1));
            chosen.add(leaders.get(2));
            chosen.add(leaders.get(3));
            chooseLeaderController.initializeLeaderCards(chosen); */


            //nella gui devo chiamare initleaders

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
     * this method initializes the gameboard
     */
    public void initializeGameBoard(){
        gameBoardController.initialize(gui.getClientGameBoard());
        gameBoardController.setGui(gui);
    }


    /**
     * this method initializes each player's gameboard
     * @throws IOException
     */
    public void initializePlayerBoard() throws IOException {
        /* for(ClientPlayer p : gui.getPlayers()) {
           playerTabController.addPlayerBoard(p));
        } */

        playerTabController.setGui(gui);
        playerTabController.addPlayerBoard(gui.getClientPlayer(), true);

    }

    public void initializeActions(){

    }

    /**
     * this method shows the leader cards just chosen to the player's board
     */
    public void addLeadersToPlayer(){
        playerTabController.addLeadersChosen(gui.getClientPlayer());
    }

    public void makeCardMarketClickable(boolean bool){
        gameBoardController.setCardMarketClickable(bool);
    }

    public void makeProductionClickable(boolean bool) {
        playerTabController.setProductionClickable(gui.getClientPlayer(), bool);
    }

    public void setLeaderAction(boolean bool){
        playerTabController.setLeaderAction(gui.getClientPlayer(), bool);
    }

}
