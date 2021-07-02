package it.polimi.ingsw.view.client.gui.controllers;

import it.polimi.ingsw.messages.Message;
import it.polimi.ingsw.messages.actions.BuyDevelopmentCardMessage;
import it.polimi.ingsw.view.client.gui.Gui;
import it.polimi.ingsw.view.client.viewComponents.ClientCardMarket;
import it.polimi.ingsw.view.client.viewComponents.ClientGameBoard;
import it.polimi.ingsw.view.client.viewComponents.ClientMarbleMarket;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
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
    @FXML
    private Label infoBox;

    private Gui gui;

    public void setGui(Gui gui){
        this.gui = gui;
    }


    /**
     * this method shows a message in the info box
     * @param text the  message to show
     */
    public void setInformationalBox(String text){
        infoBox.setWrapText(true);
        infoBox.setText(text);
    }

    /**
     * this method adds a cart to the card market
     */
    public void setCard(ImageView card, int i, int j){
        card.setFitWidth(140.0);
        card.setFitHeight(200.0);
        cardMarket.add(card, j, i);

        card.setOnMouseClicked(event -> {
            card.setStyle("-fx-border-width: 5; -fx-border-color: #51db51");
            buyDevelopmentCard(i, j);
            card.setStyle("");
        });
    }

    /**
     * this method initializes the general game board
     * @param clientGameBoard the game's game board
     */
    public void initialize(ClientGameBoard clientGameBoard) {

        ClientCardMarket clientCardMarket = clientGameBoard.getCardMarket();
        ClientMarbleMarket clientMarbleMarket = clientGameBoard.getMarket();

        for(int i=0; i<3; i++) {
            for (int j = 0; j < 4; j++) {
                if (clientCardMarket.getStack(i, j).getListOfCards().size() != 0) {
                    ImageView card = new ImageView(new Image("/gui/Images/DevelopmentCardsFront/" + clientCardMarket.getCard(i, j).getId() + ".png"));
                    card.setId(clientCardMarket.getCard(i, j).getId());
                    setCard(card, i,  j);
                    card.setDisable(true);
                }

                ImageView marble = new ImageView(new Image("gui/Images/Marbles/" + clientMarbleMarket.getMarble(i, j).getColor().toString() + ".png"));
                marble.setPreserveRatio(true);
                marble.setFitHeight(40);
                marble.setFitWidth(40);
                marbleMarket.add(marble, j, i);
            }
        }

        freeMarble.setImage(new Image("/gui/Images/Marbles/" + clientMarbleMarket.getFreeMarble().getColor().toString() + ".png"));
        freeMarble.setFitWidth(40);
        freeMarble.setFitHeight(40);

        setCardMarketClickable(false);


    }

    /**
     * this method updates the game board's marble market
     * @param clientGameBoard the game board
     */
    public void updateMarbleMarket(ClientGameBoard clientGameBoard){

        for(int i=0; i<3; i++)
            for(int j=0; j<4; j++){

                removeNodeByRowColumnIndex(i,j, marbleMarket);

                ImageView marble = new ImageView(new Image("gui/Images/Marbles/" +  clientGameBoard.getMarket().getMarble(i,j).getColor().toString() +  ".png" ));
                marble.setPreserveRatio(true);
                marble.setFitHeight(40);
                marble.setFitWidth(40);
                marbleMarket.add(marble, j, i);
                marble.setVisible(true);

            }
        freeMarble.setImage(new Image("/gui/Images/Marbles/" + clientGameBoard.getMarket().getFreeMarble().getColor().toString() + ".png"));
        freeMarble.setFitWidth(40);
        freeMarble.setFitHeight(40);



    }

    /**
     * this method removes an imageview from a gridPane
     * @param row the imageView's row index
     * @param column the imageView's column index
     * @param gridPane the grid to remove it from
     */
    public void removeNodeByRowColumnIndex(final int row,final int column,GridPane gridPane) {

        ObservableList<Node> children = gridPane.getChildren();
        for(Node node : children) {
            if(node instanceof ImageView && GridPane.getRowIndex(node) == row && GridPane.getColumnIndex(node) == column) {
                ImageView imageView= (ImageView) node;
                gridPane.getChildren().remove(imageView);
                break;
            }
        }
    }


    /**
     * this method updates the game board's card market
     * @param clientGameBoard the board to update
     */
    public void updateCardMarket(ClientGameBoard clientGameBoard){

        for(int i=0; i<3; i++)
            for(int j=0; j<4; j++) {

                removeNodeByRowColumnIndex(i,j, cardMarket);

                if(clientGameBoard.getCardMarket().getStack(i,j).getListOfCards().size()!=0) {
                    ImageView card = new ImageView(new Image("/gui/Images/DevelopmentCardsFront/" + clientGameBoard.getCardMarket().getCard(i, j).getId() + ".png"));
                    card.setId(clientGameBoard.getCardMarket().getCard(i, j).getId());
                    setCard(card, i, j);
                    card.setDisable(true);
                }
            }
    }

    /**
     * this method sends the message that the layer wants to buy a developmentCard
     */
    public void buyDevelopmentCard(int i, int j){

        gui.alertUser("Information", "Buy", Alert.AlertType.INFORMATION);
        Message msg = new BuyDevelopmentCardMessage(gui.getPlayerNickname(), i, j, true);
        gui.sendMessage(msg);

    }

    /**
     * this method allows or forbids the player to click the card market
     * @param bool true to allow, false to forbid
     */
    public void setCardMarketClickable(boolean bool){

        for(Node img : cardMarket.getChildren()) {
            img.setDisable(!bool);
        }

    }


}