package it.polimi.ingsw.view.client.gui.controllers;

import it.polimi.ingsw.view.client.gui.Gui;
import javafx.fxml.FXML;
import javafx.scene.control.Label;

public class LoginWaitController {

    @FXML
    private Label waitingMessage;

    public void setGui(Gui gui) {
    }

    public void setInformationBox(String infoMessage){
        waitingMessage.setText(infoMessage);
    }

    public String getInformationBox() {
        return waitingMessage.getText();
    }
}
