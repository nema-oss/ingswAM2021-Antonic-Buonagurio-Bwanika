package it.polimi.ingsw.view.client.gui.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Label;

public class EndGameController {

    @FXML
    Label message;

    private String winner;

    /**
     * this method sets the message in the final scene
     * @param text the final message
     */
    public void setMessage(String text){
        message.setText(text);
    }

    /**
     * Set the game winner
     * @param winner game winner
     */
    public void setWinner(String winner) {
        this.winner = winner;
    }
}
