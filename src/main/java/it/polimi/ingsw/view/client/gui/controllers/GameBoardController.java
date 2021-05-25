package it.polimi.ingsw.view.client.gui.controllers;

import it.polimi.ingsw.model.cards.DevelopmentCard;
import it.polimi.ingsw.model.gameboard.CardMarket;
import it.polimi.ingsw.view.client.viewComponents.ClientCardMarket;
import it.polimi.ingsw.view.client.viewComponents.ClientGameBoard;
import it.polimi.ingsw.view.client.viewComponents.ClientMarbleMarket;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class GameBoardController implements Initializable{

    @FXML
    private AnchorPane gBoard;

    @FXML
    private GridPane cardMarket, marbleMarket;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        ClientGameBoard clientGameBoard = new ClientGameBoard();
        ClientCardMarket clientCardMarket = clientGameBoard.getCardMarket();
        ClientMarbleMarket clientMarbleMarket = clientGameBoard.getMarket();

        for(int i=0; i<3; i++)
            for(int j=0; j<4; j++) {
                ImageView card = new ImageView(new Image("/gui/Images/DevelopmentCardsFront/" + clientCardMarket.getCard(i, j).getId() + ".png"));
                card.setFitWidth(129.0);
                card.setFitHeight(174.0);
                cardMarket.add(card, j, i);

                ImageView marble = new ImageView(new Image("gui/Images/Marbles/WHITE.png" /* + clientMarbleMarket.getMarble(i,j).getColor().toString() +  ".png" */));
                marble.setPreserveRatio(true);
                marble.setFitHeight(28);
                marble.setFitWidth(28);
                marbleMarket.add(marble, j, i);
            }

    }

    public void updateMarbleMarket(ClientGameBoard clientGameBoard){

        for(Node n : marbleMarket.getChildren())
            n.setVisible(false);

        for(int i=0; i<3; i++)
            for(int j=0; j<4; j++)
                marbleMarket.add(new ImageView(new Image("/gui/Images/Marbles/" +clientGameBoard.getMarket().getMarble(i,j).getColor() + ".png")), j, i);

    }

    public void upddateCardMarket(ClientGameBoard clientGameBoard){

        for(Node n : cardMarket.getChildren())
            n.setVisible(false);

        for(int i=0; i<3; i++)
            for(int j=0; j<4; j++)
                cardMarket.add(new ImageView(new Image("/gui/Images/DevelopmentCardsFront/" + clientGameBoard.getCardMarket().getCard(i,j) + ".png")), j, i);
    }

}
