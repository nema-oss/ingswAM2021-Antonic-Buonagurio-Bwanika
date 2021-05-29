package it.polimi.ingsw.view.client.gui.controllers;

import it.polimi.ingsw.messages.setup.client.LoginRequest;
import it.polimi.ingsw.view.client.gui.Gui;
import it.polimi.ingsw.view.client.utils.InputValidator;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;


public class NicknameController {

    @FXML
    private TextField nickField;


    private Gui gui;

    private boolean isFirstPlayer;

    @FXML
    public void sendNickname() {

        String nickname = nickField.getText();

        if(!InputValidator.isNickname(nickname))
            notifyInvalidNickname();

        else {
            LoginRequest message = new LoginRequest(nickname);

            if(isFirstPlayer){
                gui.selectNumberOfPlayers(message);
            }else{
                gui.sendMessage(message);
            }
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
