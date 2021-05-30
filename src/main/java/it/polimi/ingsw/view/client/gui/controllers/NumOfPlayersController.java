package it.polimi.ingsw.view.client.gui.controllers;

import it.polimi.ingsw.messages.setup.client.LoginRequest;
import it.polimi.ingsw.view.client.gui.Gui;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import java.net.URL;
import java.util.ResourceBundle;

/**
 * this class is the controller for the "numOfPlayers.fxml" file
 * @author chiara
 */
public class NumOfPlayersController implements Initializable {

    @FXML
    ComboBox<Integer> comboBox;

    private int numOfPlayers;

    private Gui gui;

    private LoginRequest message;

    /**
     * this method sens the message with the number of players chosen
     */
    @FXML
    public void switchToLobby() {

        numOfPlayers = comboBox.getValue();
        message.setNumberOfPlayers(numOfPlayers);
        gui.sendMessage(message);
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        ObservableList<Integer> list = FXCollections.observableArrayList(1,2,3,4);
        comboBox.setItems(list) ;
    }

    public void setGui(Gui gui){
        this.gui = gui;
    }


    public void setNicknameMessage(LoginRequest message) {
        this.message = message;
    }
}
