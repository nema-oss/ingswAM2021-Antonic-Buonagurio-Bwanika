package it.polimi.ingsw.view.client.gui.controllers;

import javafx.fxml.FXML;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;

public class PlayerBoardController {

    @FXML
    ImageView active1, active2, inactive1, inactive2;

    @FXML
    GridPane devCards, floor1, floor2, floor3;

    @FXML
    AnchorPane strongbox;

    @FXML
    static AnchorPane pBoard;

    public static AnchorPane getPlayerBoard(){
        return pBoard;
    }

}
