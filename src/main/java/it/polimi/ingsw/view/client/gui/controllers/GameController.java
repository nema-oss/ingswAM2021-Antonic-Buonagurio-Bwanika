package it.polimi.ingsw.view.client.gui.controllers;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.layout.BorderPane;

import java.net.URL;
import java.util.ResourceBundle;

public class GameController implements Initializable {

    @FXML
    BorderPane gameBoardPane, myPlayerPane, otherPlayersPane;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        gameBoardPane.setCenter(GameBoardController.getGameBoard());
        myPlayerPane.setCenter(PlayerBoardController.getPlayerBoard());
        otherPlayersPane.setTop(PlayerBoardController.getPlayerBoard());
        otherPlayersPane.setCenter(PlayerBoardController.getPlayerBoard());
        otherPlayersPane.setBottom(PlayerBoardController.getPlayerBoard());
    }
}
