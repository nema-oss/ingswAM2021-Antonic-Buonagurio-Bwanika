package it.polimi.ingsw.view.client.gui.controllers;

import it.polimi.ingsw.messages.setup.client.LoginRequest;
import it.polimi.ingsw.view.client.gui.Gui;
import it.polimi.ingsw.view.client.utils.InputValidator;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;


/**
 * this class is the controller for the "nickname.fxml" file
 * @author chiara
 */
public class NicknameController {

    @FXML
    private TextField nickField;


    private Gui gui;

    private boolean isFirstPlayer;
    private boolean isLocalMatch;

    /**
     * this method validates the nickname's format and sends the message that a nickname has been chosen
     */
    @FXML
    public void sendNickname() {

        String nickname = nickField.getText();
        if(!InputValidator.isNickname(nickname))
            notifyInvalidNickname();

        else {
            LoginRequest message = new LoginRequest(nickname);

            if(isFirstPlayer || isLocalMatch){
                gui.selectNumberOfPlayers(message);
            }else{
                gui.sendMessage(message);
            }
        }

    }

    public void setGui(Gui gui){
        this.gui = gui;
    }

    /**
     * this method notifies the player that the nickname inserted does not have a valid format
     */
    private void notifyInvalidNickname(){
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setHeaderText("Invalid nickname. Please remember that your nickname must be at least 3 characters long.");
        alert.showAndWait();
    }


    /**
     * @param isFirstPlayer true if the player is the first one
     */
    public void setIsFirstPlayer(boolean isFirstPlayer){
        this.isFirstPlayer = isFirstPlayer;
    }

    /**
     * @param localMatch true if it is a local match
     */
    public void setLocalMatch(boolean localMatch) {
        isLocalMatch = localMatch;
    }
}
