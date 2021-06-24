package it.polimi.ingsw.view.client.gui.controllers;

import it.polimi.ingsw.messages.Message;
import it.polimi.ingsw.messages.actions.server.LeaderActionAccepted;
import it.polimi.ingsw.messages.setup.server.ChooseLeadersMessage;
import it.polimi.ingsw.model.cards.leadercards.LeaderCard;
import it.polimi.ingsw.view.client.gui.Gui;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


/**
 * this class represents the javafx scene "chooseLeaders" controller
 * @author chiara
 */
public class ChooseLeaderController {

   @FXML
   ImageView l1, l2, l3, l4;

   @FXML
    Label text;

   @FXML
   Button leadersOk;

   private boolean l1selected, l2selected, l3selected, l4selected;
   private List<LeaderCard> givenCards;
   private Gui gui;


    /**
     * this method sets the gui
     * @param gui the gui to assign
     */
    public void setGui(Gui gui){
        this.gui = gui;
    }

    /**
     * this method sends the message with the chosen leader cards
     */
    @FXML
    private void switchOnChooseResources() {

        List<LeaderCard> selected = new ArrayList<>();
        if(l1selected){
            selected.add(givenCards.get(0));
        }
        if(l2selected){
            selected.add(givenCards.get(1));
        }
        if(l3selected){
            selected.add(givenCards.get(2));
        }
        if(l4selected){
            selected.add(givenCards.get(3));
        }

        if(selected.size() < 2){
            selected.clear();
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setHeaderText("Not enough cards selected! You need to choose exactly two leader cards.");
            alert.showAndWait();
            return;
        }
        else if(selected.size() > 2){
            selected.clear();
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setHeaderText("Too much leader cards selected! You need to choose exactly two leader cards.");
            alert.showAndWait();
            return;
        }
        Message message = new ChooseLeadersMessage(gui.getPlayerNickname(),selected,true);
        gui.sendMessage(message);

    }

    /**
     * this method changes the card's style if it is selected, adding a green border to it
     */
    @FXML
    private void l1Clicked(){

        if(!l1selected) {
            l1.getParent().setStyle("-fx-border-width: 5; -fx-border-color: #51db51");
            l1selected = true;
        }

        else{
            l1.getParent().setStyle("");
            l1selected = false;
        }
    }

    /**
     * this method changes the card's style if it is selected, adding a green border to it
     */
    @FXML
    private void l2Clicked(){

        if(!l2selected) {
            l2.getParent().setStyle("-fx-border-width: 5; -fx-border-color: #51db51");
            l2selected = true;
        }

        else{
            l2.getParent().setStyle("");
            l2selected = false;
        }
    }

    /**
     * this method changes the card's style if it is selected, adding a green border to it
     */
    @FXML
    private void l3Clicked(){

        if(!l3selected) {
            l3.getParent().setStyle("-fx-border-width: 5; -fx-border-color: #51db51");
            l3selected = true;
        }

        else{
            l3.getParent().setStyle("");
            l3selected = false;
        }
    }

    /**
     * this method changes the card's style if it is selected, adding a green border to it
     */
    @FXML
    private void l4Clicked(){

        if(!l4selected) {
            l4.getParent().setStyle("-fx-border-width: 5; -fx-border-color: #51db51");
            l4selected = true;
        }

        else{
            l4.getParent().setStyle("");
            l4selected = false;
        }

    }


    /**
     * this method initializes the images representing the leader cards to choose
     * @param leaderCards to choose from
     */
    public void initializeLeaderCards(List<LeaderCard> leaderCards){

        givenCards = leaderCards;

        l1.setImage(new Image("/gui/Images/LeaderCardsFront/" + leaderCards.get(0).getId() + ".png"));
        l1.setPreserveRatio(false);
        l2.setImage(new Image("/gui/Images/LeaderCardsFront/" + leaderCards.get(1).getId() + ".png"));
        l2.setPreserveRatio(false);
        l3.setImage(new Image("/gui/Images/LeaderCardsFront/" + leaderCards.get(2).getId() + ".png"));
        l3.setPreserveRatio(false);
        l4.setImage(new Image("/gui/Images/LeaderCardsFront/" + leaderCards.get(3).getId() + ".png"));
        l4.setPreserveRatio(false);


    }


    public void setInstructionLabel(String infoMessage) {
    }

    /**
     * this method hides the cards and the button and changes the label to "waiting"
     */
    public void hide(){
        l1.getParent().setVisible(false);
        l2.getParent().setVisible(false);
        l3.getParent().setVisible(false);
        l4.getParent().setVisible(false);
        leadersOk.setVisible(false);
        text.setText("Waiting for other players to setup their board");
    }


}
