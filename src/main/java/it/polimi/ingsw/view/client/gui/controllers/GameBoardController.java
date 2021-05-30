package it.polimi.ingsw.view.client.gui.controllers;

import it.polimi.ingsw.messages.Message;
import it.polimi.ingsw.messages.actions.BuyDevelopmentCardMessage;
import it.polimi.ingsw.view.client.gui.Gui;
import it.polimi.ingsw.view.client.viewComponents.ClientCardMarket;
import it.polimi.ingsw.view.client.viewComponents.ClientGameBoard;
import it.polimi.ingsw.view.client.viewComponents.ClientMarbleMarket;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;

/**
 * this class is the controller for the "gameBoard.fxml" file
 * @author chiara
 */
public class GameBoardController{

    @FXML
    private GridPane cardMarket, marbleMarket;
    @FXML
    private ImageView freeMarble;

    private Gui gui;

    public void setGui(Gui gui){
        this.gui = gui;
    }

    /**
     * this method initializes the general gameboard
     * @param clientGameBoard the game's gameboard
     */
    public void initialize(ClientGameBoard clientGameBoard) {

        ClientCardMarket clientCardMarket = clientGameBoard.getCardMarket();
        ClientMarbleMarket clientMarbleMarket = clientGameBoard.getMarket();

        for(int i=0; i<3; i++)
            for(int j=0; j<4; j++) {
                if(clientCardMarket.getStack(i,j).getListOfCards().size()!=0) {
                    ImageView card = new ImageView(new Image("/gui/Images/DevelopmentCardsFront/" + clientCardMarket.getCard(i, j).getId() + ".png"));
                    card.setId(clientCardMarket.getCard(i, j).getId());
                    card.setFitWidth(140.0);
                    card.setFitHeight(200.0);
                    cardMarket.add(card, j, i);

                    int finalJ = j;
                    int finalI = i;
                    card.setOnMouseClicked(event -> {
                        buyDevelopmentCard(finalJ, finalI);
                    });
                    card.setDisable(true);
                }

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

    /**
     * this method updates the gameboard's marble market
     * @param clientGameBoard
     */
    public void updateMarbleMarket(ClientGameBoard clientGameBoard){
        for(Node n : marbleMarket.getChildren())
            n.setVisible(false);

        for(int i=0; i<3; i++)
            for(int j=0; j<4; j++){
                ImageView marble = new ImageView(new Image("gui/Images/Marbles/" + clientGameBoard.getMarket().getMarble(i,j).getColor().toString() +  ".png" ));
                marble.setPreserveRatio(true);
                marble.setFitHeight(40);
                marble.setFitWidth(40);
                marbleMarket.add(marble, j, i);
            }
        freeMarble.setImage(new Image("/gui/Images/Marbles/" + clientGameBoard.getMarket().getFreeMarble().getColor().toString() + ".png"));
        freeMarble.setFitWidth(40);
        freeMarble.setFitHeight(40);


    }

    /**
     * this method updates the gameboard's card market
     * @param clientGameBoard
     */
    public void updateCardMarket(ClientGameBoard clientGameBoard){

        for(Node n : cardMarket.getChildren())
            n.setVisible(false);

        for(int i=0; i<3; i++)
            for(int j=0; j<4; j++) {
                if(clientGameBoard.getCardMarket().getStack(i,j).getListOfCards().size()!=0) {
                    ImageView card = new ImageView(new Image("/gui/Images/DevelopmentCardsFront/" + clientGameBoard.getCardMarket().getCard(i, j).getId() + ".png"));
                    card.setId(clientGameBoard.getCardMarket().getCard(i, j).getId());
                    card.setFitWidth(140.0);
                    card.setFitHeight(200.0);
                    cardMarket.add(card, j, i);

                    int finalI = i;
                    int finalJ = j;
                    card.setOnMouseClicked(event -> {
                        buyDevelopmentCard(finalJ, finalI);
                    });
                }
            }
    }

    /**
     * this method sends the message that the layer wants to buy a developmentCard
     */
    public void buyDevelopmentCard(int i, int j){

        Message msg = new BuyDevelopmentCardMessage(gui.getPlayerNickname(), i, j, true);
        gui.sendMessage(msg);

    }

    public void setCardMarketClickable(boolean bool){

        for(Node img : cardMarket.getChildren())
            img.setDisable(bool);

    }


}