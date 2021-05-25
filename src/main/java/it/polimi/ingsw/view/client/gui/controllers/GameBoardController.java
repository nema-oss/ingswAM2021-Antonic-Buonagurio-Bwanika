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
    private static AnchorPane gBoard;

    @FXML
    private static GridPane cardMarket, marbleMarket;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        ClientGameBoard clientGameBoard = new ClientGameBoard();
        ClientCardMarket clientCardMarket = clientGameBoard.getCardMarket();
        ClientMarbleMarket clientMarbleMarket = clientGameBoard.getMarket();

        List<Node> devCards = new ArrayList<>();

        for(int i=0, j=0; i<3 && j<4; i++, j++){
            cardMarket.add(new ImageView(new Image("/gui/Images/DevelopmentCardsFront" +clientCardMarket.getCard(i,j).getId())), i,j);
            marbleMarket.add(new ImageView(new Image("/gui/Images/Marbles" + clientMarbleMarket.getMarble(i,j).getColor())), i, j); //MAIUSCOLO RICORDATELO
        }
    }

    public void updateMarbleMarket(ClientGameBoard clientGameBoard){

        for(Node n : marbleMarket.getChildren())
            n.setVisible(false);

        for(int i=0, j=0; i<3 && j<4 ; i++, j++)
            marbleMarket.add(new ImageView(new Image("/gui/Images/Marbles/" +clientGameBoard.getMarket().getMarble(i,j).getColor() + ".png")), i, j);

    }

    public void upddateCardMarket(ClientGameBoard clientGameBoard){

        for(Node n : cardMarket.getChildren())
            n.setVisible(false);

        for(int i=0, j=0; i<3 && j<4; i++, j++)
            cardMarket.add(new ImageView(new Image("/gui/Images/DevelopmentCardsFront/" + clientGameBoard.getCardMarket().getCard(i,j) + ".png")), i, j);
    }

}
