package it.polimi.ingsw.view.client.gui.controllers;

import it.polimi.ingsw.model.cards.CardFactory;
import it.polimi.ingsw.model.cards.leadercards.LeaderCard;
import it.polimi.ingsw.model.cards.leadercards.LeaderCardType;
import it.polimi.ingsw.model.player.Effects;
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
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class GameController implements Initializable{

    @FXML
    public BorderPane gameBoardPane, myPlayerPane, leftBorder;

    public static GameBoardController gameBoardController;
    public static PlayerBoardController playerBoardController;
    public static ChooseLeaderController chooseLeaderController;

    private Gui gui;

    public void setGui(Gui gui){
        this.gui = gui;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        try {
            FXMLLoader loader1 = GuiManager.loadFXML("/gui/gameBoard");
            gameBoardController = loader1.getController();
            gameBoardPane.setLeft(loader1.load());

            FXMLLoader loader2 = GuiManager.loadFXML("/gui/playerBoard");
            playerBoardController = loader2.getController();
            gameBoardPane.setRight(loader2.load());

            FXMLLoader loader3 = GuiManager.loadFXML("/gui/chooseLeaders");
            leftBorder.setCenter(loader3.load());
            chooseLeaderController = loader3.getController();

            List<LeaderCard> leaders = new CardFactory().getLeaderCards();
            List<LeaderCard> chosen = new ArrayList<>();
            chosen.add(leaders.get(0));
            chosen.add(leaders.get(1));
            chosen.add(leaders.get(2));
            chosen.add(leaders.get(3));
            chooseLeaderController.initialize(chosen);

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
