package it.polimi.ingsw.view.client.gui.controllers;

import it.polimi.ingsw.model.cards.DevelopmentCard;
import it.polimi.ingsw.model.cards.leadercards.LeaderCard;
import it.polimi.ingsw.view.client.gui.Gui;
import it.polimi.ingsw.view.client.utils.TurnActions;
import it.polimi.ingsw.view.client.gui.GuiManager;
import it.polimi.ingsw.view.client.viewComponents.ClientPlayer;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.layout.BorderPane;

import java.io.IOException;
import java.net.URL;

import java.util.List;
import java.util.ResourceBundle;

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
    private Gui gui;

    public void setGui(Gui gui){
        this.gui = gui;
    }

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


    public ChooseLeaderController getChooseLeaderController(){
        return chooseLeaderController;
    }

    public void initializeGameBoard(){
        gameBoardController.initialize(gui.getClientGameBoard());
    }

    public void initializePlayerBoard() throws IOException {
        /* for(ClientPlayer p : gui.getPlayers()) {
           playerTabController.addPlayerBoard(p));
        } */

        playerTabController.addPlayerBoard(gui.getClientPlayer(), true);
    }

    public void addLeadersToPlayer(){
        playerTabController.addLeadersChosen(gui.getClientPlayer());
    }

}
