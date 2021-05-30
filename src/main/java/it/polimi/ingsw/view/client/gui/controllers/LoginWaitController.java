package it.polimi.ingsw.view.client.gui.controllers;

import it.polimi.ingsw.view.client.gui.Gui;
import javafx.fxml.FXML;
import javafx.scene.control.Label;

/**
 * this class is the controller for the "loginWait.fxml" file
 * @author chiara
 */
public class LoginWaitController {

    @FXML
    private Label waitingMessage;

    public void setGui(Gui gui) {
    }

    /**
     * this method sets the message to be put on screen during the waiting scene
     * @param infoMessage
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
