package it.polimi.ingsw.view.client.gui.controllers;

import it.polimi.ingsw.view.client.gui.GuiManager;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class NumOfPlayersController implements Initializable {

    @FXML
    ComboBox<Integer> comboBox;

    @FXML
    public void switchToLobby(ActionEvent event) throws IOException {

        int numOfPlayers = comboBox.getValue();

        GuiManager.changeScene("/gui/chooseLeaders");

        //setta il numero di giocatori

        //se è da solo allora inizia il game
        //altrimenti va nella schermata in attesa di altri giocatori
    }


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        ObservableList<Integer> list = FXCollections.observableArrayList(1,2,3,4);
        comboBox.setItems(list) ;
    }
}
