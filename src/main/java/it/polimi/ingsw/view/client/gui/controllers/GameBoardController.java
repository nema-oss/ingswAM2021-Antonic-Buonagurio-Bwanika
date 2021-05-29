package it.polimi.ingsw.view.client.gui.controllers;

import it.polimi.ingsw.view.client.viewComponents.ClientCardMarket;
import it.polimi.ingsw.view.client.viewComponents.ClientGameBoard;
import it.polimi.ingsw.view.client.viewComponents.ClientMarbleMarket;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
public class GameBoardController{

    @FXML
    private GridPane cardMarket, marbleMarket;
    @FXML
    private ImageView freeMarble;

    public void initialize(ClientGameBoard clientGameBoard) {

        ClientCardMarket clientCardMarket = clientGameBoard.getCardMarket();
        ClientMarbleMarket clientMarbleMarket = clientGameBoard.getMarket();

        for(int i=0; i<3; i++)
            for(int j=0; j<4; j++) {
                ImageView card = new ImageView(new Image("/gui/Images/DevelopmentCardsFront/" + clientCardMarket.getCard(i, j).getId() + ".png"));
                card.setId(clientCardMarket.getCard(i,j).getId());
                card.setFitWidth(140.0);
                card.setFitHeight(200.0);
                cardMarket.add(card, j, i);

                card.setOnMouseClicked(this::buyDevelopmentCard);

                ImageView marble = new ImageView(new Image("gui/Images/Marbles/" + clientMarbleMarket.getMarble(i,j).getColor().toString() +  ".png" ));
                marble.setPreserveRatio(true);
                marble.setFitHeight(40);
                marble.setFitWidth(40);
                marbleMarket.add(marble, j, i);
            }

        freeMarble.setImage(new Image("/gui/Images/Marbles/" + clientMarbleMarket.getFreeMarble().getColor().toString() + ".png"));
        freeMarble.setFitWidth(40);
        freeMarble.setFitHeight(40);


    }

    public void updateMarbleMarket(ClientGameBoard clientGameBoard){
        for(Node n : marbleMarket.getChildren())
            n.setVisible(false);

        for(int i=0; i<3; i++)
            for(int j=0; j<4; j++)
                marbleMarket.add(new ImageView(new Image("/gui/Images/Marbles/" +clientGameBoard.getMarket().getMarble(i,j).getColor() + ".png")), j, i);

    }

    public void updateCardMarket(ClientGameBoard clientGameBoard){

        for(Node n : cardMarket.getChildren())
            n.setVisible(false);

        for(int i=0; i<3; i++)
            for(int j=0; j<4; j++) {
                ImageView img =new ImageView(new Image("/gui/Images/DevelopmentCardsFront/" + clientGameBoard.getCardMarket().getCard(i,j).getId() + ".png"));
                img.setId(clientGameBoard.getCardMarket().getCard(i,j).getId());
                cardMarket.add(img, j, i);
            }
    }

    public void buyDevelopmentCard(MouseEvent mouseEvent){

        ImageView img = (ImageView) mouseEvent.getSource();
        // gui.buyCard( img.getId() );
    }


}