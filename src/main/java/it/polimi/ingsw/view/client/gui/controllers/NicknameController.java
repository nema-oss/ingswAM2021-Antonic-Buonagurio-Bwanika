package it.polimi.ingsw.view.client.gui.controllers;

import it.polimi.ingsw.messages.Message;
import it.polimi.ingsw.messages.setup.client.LoginRequest;
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

    private boolean isFirstPlayer;

    @FXML
    public void sendNickname(ActionEvent event) throws IOException {

        String nickname = nickField.getText();

        if(!InputValidator.isNickname(nickname))
            notifyInvalidNickname();

        else {
            LoginRequest message = new LoginRequest(nickname);
            // big trouble with an entire scene just for number of players box, using 3 for testing
            message.setNumberOfPlayers(2);
            gui.sendMessage(message);
            //GuiManager.changeScene("/gui/numOfPlayers");
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

    public void setIsFirstPlayer(boolean isFirstPlayer){
        this.isFirstPlayer = isFirstPlayer;
    }
}
