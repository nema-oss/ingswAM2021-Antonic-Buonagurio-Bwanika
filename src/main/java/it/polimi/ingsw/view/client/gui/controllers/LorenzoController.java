package it.polimi.ingsw.view.client.gui.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Label;

public class LorenzoController {

    @FXML
    Label lorenzoPos;

    public void update(int index){
        int value = Integer.parseInt(lorenzoPos.getText()) + index;
        lorenzoPos.setText(String.valueOf(value));
    }
}
