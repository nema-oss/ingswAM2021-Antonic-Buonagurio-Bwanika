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
import javafx.scene.layout.BorderPane;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class OthersController implements Initializable {

    @FXML
    private BorderPane leftPane;

    private Gui gui;

    public void setGui(Gui gui){
        this.gui = gui;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        //tabpane degli altri giocatori sulla sinistra
        TabPane tabPane = new TabPane();
        leftPane.setLeft(tabPane);
        tabPane.setTabMaxWidth(700);
        tabPane.setStyle("-fx-border-color: white; -fx-border-width: 5");

        //provvisorio: ovviamente dovrò prenderli da qualche parte

        List<ClientPlayer> otherPlayers = new ArrayList<>();    // = gui.getPlayers() e nella gui chiamo l'update di tutte le playerBoard
        otherPlayers.add(new ClientPlayer("Paolo", new ClientGameBoard()));
        otherPlayers.add(new ClientPlayer("Marco", new ClientGameBoard()));

        for(ClientPlayer other : otherPlayers) {
            try {
                FXMLLoader loader = GuiManager.loadFXML("/gui/playerBoard");
                Tab tab = new Tab(other.getNickname(), loader.load());
                tabPane.getTabs().add(tab);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        //combobox per la scelta delle azioni possibili



    }
}
