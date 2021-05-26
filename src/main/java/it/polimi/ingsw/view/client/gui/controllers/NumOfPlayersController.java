package it.polimi.ingsw.view.client.gui.controllers;

import it.polimi.ingsw.view.client.gui.Gui;
import it.polimi.ingsw.view.client.gui.GuiManager;
import it.polimi.ingsw.view.client.utils.InputValidator;
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

    private int numOfPlayers;

    private Gui gui;

    @FXML
    public void switchToLobby(ActionEvent event) throws IOException {

        numOfPlayers = comboBox.getValue();
        //Gui.tellOk()
    }


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        ObservableList<Integer> list = FXCollections.observableArrayList(1,2,3,4);
        comboBox.setItems(list) ;
    }

    public void setGui(Gui gui){
        this.gui = gui;
    }

    public int getNumOfPlayers(){
        return numOfPlayers;
    }

    public void showNumberOfPlayersButton() {
    }

    public void hideNumberOfPlayersButton() {
    }
}
