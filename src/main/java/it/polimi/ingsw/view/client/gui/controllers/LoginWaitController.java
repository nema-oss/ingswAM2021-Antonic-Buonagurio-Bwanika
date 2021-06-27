package it.polimi.ingsw.view.client.gui.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Label;

/**
 * this class is the controller for the "loginWait.fxml" file
 * @author chiara
 */
public class LoginWaitController {

    @FXML
    private Label waitingMessage;

    /**
     * this method sets the message to be put on screen during the waiting scene
     * @param infoMessage the message to show
     */
    public void setInformationBox(String infoMessage){
        waitingMessage.setText(infoMessage);
    }

    /**
     * @return the message of the waiting scene
     */
    public String getInformationBox() {
        return waitingMessage.getText();
    }
}
