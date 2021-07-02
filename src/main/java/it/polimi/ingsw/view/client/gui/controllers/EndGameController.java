package it.polimi.ingsw.view.client.gui.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Label;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class EndGameController {

    @FXML
    Label message;


    /**
     * this method sets the message in the final scene
     * @param text the final message
     */
    public void setMessage(String text){
        message.setText(text);
    }
}
