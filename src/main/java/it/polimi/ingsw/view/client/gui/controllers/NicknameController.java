package it.polimi.ingsw.view.client.gui.controllers;

import it.polimi.ingsw.view.client.Cli;
import it.polimi.ingsw.view.client.View;
import it.polimi.ingsw.view.client.gui.Gui;
import it.polimi.ingsw.view.client.gui.GuiManager;
import it.polimi.ingsw.view.client.utils.InputValidator;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;

import java.io.IOException;

public class NicknameController {

    @FXML
    private TextField nickField;

    private Gui gui;

    @FXML
    public void sendNickname(ActionEvent event) throws IOException {

        String nickname = nickField.getText();

        if(!InputValidator.isNickname(nickname))
            notifyInvalidNickname();

        else {
            // gui.showLoginDone(nickname);
            GuiManager.changeScene("/gui/numOfPlayers");
        }

    }

    public void setGui(Gui gui){
        this.gui = gui;
    }

    private void notifyInvalidNickname(){
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setHeaderText("Invalid nickname. Please remember that your nickname must be at least 3 characters long.");
        alert.showAndWait();
    }
}
