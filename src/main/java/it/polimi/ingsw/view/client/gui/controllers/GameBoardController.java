package it.polimi.ingsw.view.client.gui.controllers;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;

import java.net.URL;
import java.util.ResourceBundle;

public class GameBoardController implements Initializable{

    @FXML
    private static AnchorPane gBoard;

    @FXML
    private ImageView d00, d01, d02, d03;
    @FXML
    private ImageView d10, d11, d12, d13;
    @FXML
    private ImageView d20, d21, d22, d23;
    @FXML
    private ImageView d30, d31, d32, d33;

    @FXML
    private ImageView m00, m01, m02, m03;
    @FXML
    private ImageView m10, m11, m12, m13;
    @FXML
    private ImageView m20, m21, m22, m23;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        d00.setImage(new Image("gui/Images/DevelopmentCardsFront/Masters of Renaissance_Cards_FRONT_3mmBleed_1-1-1.png"));
        d01.setImage(new Image("gui/Images/DevelopmentCardsFront/Masters of Renaissance_Cards_FRONT_3mmBleed_1-1-1.png"));
        d02.setImage(new Image("gui/Images/DevelopmentCardsFront/Masters of Renaissance_Cards_FRONT_3mmBleed_1-1-1.png"));
        d03.setImage(new Image("gui/Images/DevelopmentCardsFront/Masters of Renaissance_Cards_FRONT_3mmBleed_1-1-1.png"));
    }

    public static Node getGameBoard(){

        System.out.println(gBoard);
        return gBoard;
    }
}
