package it.polimi.ingsw.view.client.gui.controllers;

import it.polimi.ingsw.view.client.gui.Gui;
import it.polimi.ingsw.view.client.gui.GuiManager;
import it.polimi.ingsw.view.client.viewComponents.ClientGameBoard;
import it.polimi.ingsw.view.client.viewComponents.ClientPlayer;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class GameController implements Initializable{

    @FXML
    public BorderPane gameBoardPane, myPlayerPane, leftBorder;

    public GameBoardController gameBoardController;
    public PlayerBoardController playerBoardController;

    private Gui gui;

    public void setGui(Gui gui){
        this.gui = gui;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        try {
            FXMLLoader loader1 = GuiManager.loadFXML("/gui/gameBoard");
            gameBoardController = loader1.getController();
            gameBoardPane.setCenter(loader1.load());

            FXMLLoader loader2 = GuiManager.loadFXML("/gui/playerBoard");
            playerBoardController = loader2.getController();
            myPlayerPane.setCenter(loader2.load());

            leftBorder.setCenter(GuiManager.loadFXML("/gui/chooseLeaders").load());

            //nella gui devo chiamare initleaders

        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public GameBoardController getGameBoardController() {
        return gameBoardController;
    }

    public PlayerBoardController getPlayerBoardController() {
        return playerBoardController;
    }

    public void leftPanelMultiplayer(List<ClientPlayer> otherPlayers) throws IOException {

        TabPane tabPane = new TabPane();
        leftBorder.setTop(tabPane);

        for(ClientPlayer other : otherPlayers) {
            FXMLLoader loader = GuiManager.loadFXML("/gui/playerBoard");
            Tab tab = new Tab(other.getNickname(), loader.load());
            tabPane.getTabs().add(tab);
        }

    }
}
