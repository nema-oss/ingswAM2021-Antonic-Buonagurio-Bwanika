package it.polimi.ingsw.view.client.gui.controllers;

import it.polimi.ingsw.view.client.gui.Gui;
import it.polimi.ingsw.view.client.gui.GuiManager;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class GameController implements Initializable{

    @FXML
    public BorderPane gameBoardPane, myPlayerPane, leftBorder;

    private Gui gui;

    public void setGui(Gui gui){
        this.gui = gui;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        try {
            gameBoardPane.setCenter(GuiManager.loadFXML("/gui/gameBoard").load());
            myPlayerPane.setCenter(GuiManager.loadFXML("/gui/playerBoard").load());
            leftBorder.setCenter(GuiManager.loadFXML("/gui/chooseLeaders").load());

            //nella gui devo chiamare initleaders

        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
