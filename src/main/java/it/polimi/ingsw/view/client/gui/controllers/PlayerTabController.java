package it.polimi.ingsw.view.client.gui.controllers;

import it.polimi.ingsw.view.client.gui.GuiManager;
import it.polimi.ingsw.view.client.viewComponents.ClientPlayer;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;

import java.io.IOException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;


public class PlayerTabController implements Initializable {

    @FXML
    BorderPane tabSpace;

    private TabPane tabPane;

    PlayerBoardController playerBoardController;

    Map<ClientPlayer, PlayerBoardController> controllersMap;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        tabPane = new TabPane();

        tabSpace.setTop(tabPane);
        tabPane.setMaxWidth(744.0);
        tabPane.setMaxHeight(475.0);
        tabPane.setStyle("-fx-border-color: white");

        controllersMap = new HashMap<>();

        /* List<ClientPlayer> otherPlayers = new ArrayList<>();
        otherPlayers.add(new ClientPlayer("Paolo", new ClientGameBoard()));
        otherPlayers.add(new ClientPlayer("Marco", new ClientGameBoard()));

        for (ClientPlayer other : otherPlayers) {
            try {
                FXMLLoader loader = GuiManager.loadFXML("/gui/playerBoard");
                playerBoardControllers.add(loader.getController());
                Tab tab = new Tab(other.getNickname(), loader.load());
                tabPane.getTabs().add(tab);
            } catch (IOException e) {
                e.printStackTrace();
            }
        } */
    }

    public void addPlayerBoard(ClientPlayer clientPlayer) throws IOException {

        FXMLLoader loader = GuiManager.loadFXML("/gui/playerBoard");
        Tab tab = new Tab(clientPlayer.getNickname(), loader.load());
        tab.setStyle("-fx-background-color: radial-gradient(center 50% -40%, radius 200%, #fffefe 45%, #edeff8 50%) ;-fx-text-fill: black; -fx-font-size: 14px; -fx-padding: 5 30 5 30;");
        playerBoardController = loader.getController();
        controllersMap.put(clientPlayer, loader.getController());
        tabPane.getTabs().add(tab);
    }

    public void addLeadersChosen(ClientPlayer clientPlayer){
        playerBoardController.initialize(clientPlayer);
    }

    public void updatePlayerBoard(ClientPlayer clientPlayer){

        for(Tab t : tabPane.getTabs()){
            if (t.getId().equals(clientPlayer.getNickname())) {
                controllersMap.get(clientPlayer).update(clientPlayer);
                break;
            }
        }
    }
}
