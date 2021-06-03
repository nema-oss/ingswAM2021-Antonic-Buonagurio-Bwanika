package it.polimi.ingsw.view.client.gui.controllers;

import it.polimi.ingsw.view.client.gui.Gui;
import it.polimi.ingsw.view.client.gui.GuiManager;
import it.polimi.ingsw.view.client.viewComponents.ClientPlayer;
import it.polimi.ingsw.view.client.viewComponents.ClientPlayerBoard;
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

/**
 * this class is the controller for the "playerTab.fxml" file
 * @author chiara
 */
public class PlayerTabController implements Initializable {

    @FXML
    BorderPane tabSpace;

    private TabPane tabPane;

    Map<String, PlayerBoardController> controllersMap;

    public Gui gui;

    public void setGui(Gui gui) {
        this.gui = gui;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        tabPane = new TabPane();

        tabSpace.setTop(tabPane);
        tabPane.setMaxWidth(744.0);
        tabPane.setMaxHeight(475.0);
        tabPane.setStyle("-fx-border-color: white");

        controllersMap = new HashMap<>();
    }

    /**
     * this method adds a new tab containing the player's board
     * @param clientPlayer the player the board belongs to
     * @param isCurrent boolean which says if the player to add corresponds to the one playing or if he is an enemy
     * @throws IOException
     */
    public void addPlayerBoard(String clientPlayer, ClientPlayerBoard clientPlayerBoard, boolean isCurrent) throws IOException {

        FXMLLoader loader = GuiManager.loadFXML("/gui/playerBoard");
        Tab tab = new Tab(clientPlayer, loader.load());
        tab.setStyle("-fx-background-color: radial-gradient(center 50% -40%, radius 200%, #fffefe 45%, #edeff8 50%) ;-fx-text-fill: black; -fx-font-size: 14px; -fx-padding: 5 30 5 30;");
        tab.setClosable(false);

        PlayerBoardController controller = loader.getController();
        controllersMap.put(clientPlayer, controller);
        controller.setGui(gui);
        gui.setPlayerBoardController(controller);

        tabPane.getTabs().add(tab);

       if(!isCurrent) {
           controllersMap.get(clientPlayer).hideInactiveLeaders();
           tab.getContent().setDisable(false);
       }
    }

    /**
     * shows the leader cards chosen
     * @param clientPlayer the player to give the cards to
     */
    public void addLeadersChosen(ClientPlayer clientPlayer){
        controllersMap.get(clientPlayer.getNickname()).initialize(clientPlayer);
    }

    /**
     * this method updates the player's board
     * @param clientPlayerBoard the player board to update
     * @param clientPlayer player name
     */
    public void updatePlayerBoard(String clientPlayer, ClientPlayerBoard clientPlayerBoard){

        for(Tab t : tabPane.getTabs()){
            if (t.getText().equals(clientPlayer)) {
                controllersMap.get(clientPlayer).update(clientPlayerBoard);
                break;
            }
        }
    }

    public void setProductionClickable(ClientPlayer clientPlayer, boolean bool){
        controllersMap.get(clientPlayer.getNickname()).setProductionClickable(bool);
    }

    public void setLeaderAction(ClientPlayer clientPlayer, boolean bool){
        controllersMap.get(clientPlayer.getNickname()).setLeaderAction(bool);
    }
}
