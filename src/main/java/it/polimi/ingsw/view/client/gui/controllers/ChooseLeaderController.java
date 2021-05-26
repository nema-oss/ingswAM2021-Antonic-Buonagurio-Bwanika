package it.polimi.ingsw.view.client.gui.controllers;

import it.polimi.ingsw.model.cards.leadercards.LeaderCard;

import it.polimi.ingsw.view.client.gui.Gui;
import it.polimi.ingsw.view.client.gui.GuiManager;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class ChooseLeaderController{

   @FXML
   BorderPane mainPane;

   @FXML
   ImageView l1, l2, l3, l4;

   @FXML
   Button leadersOk;

   private boolean l1selected, l2selected, l3selected, l4selected;

   private List<LeaderCard> givenCards;


    @FXML
    private List<LeaderCard> switchOnChooseResources(ActionEvent event) throws IOException {

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

        GuiManager.changeGameScene("/gui/chooseResources");
        return selected;


    }

    @FXML
    private void l1Clicked(MouseEvent event){

        if(!l1selected) {
            l1.getParent().setStyle("-fx-border-width: 5; -fx-border-color: #9c78d5");
            l1selected = true;
        }

        else{
            l1.getParent().setStyle("");
            l1selected = false;
        }
    }

    @FXML
    private void l2Clicked(MouseEvent event){

        if(!l1selected) {
            l2.getParent().setStyle("-fx-border-width: 5; -fx-border-color: #9c78d5");
            l2selected = true;
        }

        else{
            l2.getParent().setStyle("");
            l2selected = false;
        }
    }

    @FXML
    private void l3Clicked(MouseEvent event){

        if(!l3selected) {
            l3.getParent().setStyle("-fx-border-width: 5; -fx-border-color: #9c78d5");
            l3selected = true;
        }

        else{
            l3.getParent().setStyle("");
            l3selected = false;
        }
    }

    @FXML
    private void l4Clicked(MouseEvent event){

        if(!l4selected) {
            l4.getParent().setStyle("-fx-border-width: 5; -fx-border-color: #9c78d5");
            l4selected = true;
        }

        else{
            l4.getParent().setStyle("");
            l4selected = false;
        }

    }

    public void initialize(List<LeaderCard> leaderCards){

        givenCards = leaderCards;

        l1.setImage(new Image("/gui/Images/LeaderCardsFront/" + leaderCards.get(0).getId() + ".png"));
        l2.setImage(new Image("/gui/Images/LeaderCardsFront/" + leaderCards.get(1).getId() + ".png"));
        l3.setImage(new Image("/gui/Images/LeaderCardsFront/" + leaderCards.get(2).getId() + ".png"));
        l4.setImage(new Image("/gui/Images/LeaderCardsFront/" + leaderCards.get(3).getId() + ".png"));

    }

    public void hideFinalConfirmButton() {
    }

    public void setInstructionLabel(String infoMessage) {
    }

    public void setGui(Gui gui) {
    }

    public void initializeLeaderCards(List<LeaderCard> cardChoice) {
    }
}
