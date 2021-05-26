package it.polimi.ingsw.view.client.gui.controllers;

import it.polimi.ingsw.model.gameboard.Resource;
import it.polimi.ingsw.model.gameboard.ResourceType;
import it.polimi.ingsw.view.client.gui.Gui;
import it.polimi.ingsw.view.client.gui.GuiManager;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Group;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;

public class ChooseResourcesController implements Initializable {


    @FXML
    private AnchorPane coin, servant, shield, stone;

    @FXML
    private Label title;

    private static int numOfResourcesToChoose, chosen;
    private boolean coinsel, servantsel, shieldsel, stonesel;
    private Gui gui;
    private List<Resource> resources;

    public void setGui(Gui gui){
        this.gui = gui;
    }

    @FXML
    private void switchOnGame() throws IOException {

        if(chosen < numOfResourcesToChoose){
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setHeaderText("Not enough resources chosen!");
        }

        else if (chosen > numOfResourcesToChoose){
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setHeaderText("Too much resources chosen!");
        }

        else {
            //Gui.tellOk();
           GuiManager.changeGameScene("/gui/others");
        }
    }

    @FXML
    private void goldChosen(){
        if(!coinsel) {
            coin.setStyle("-fx-border-color: violet; -fx-border-width: 5");
            coinsel = true;
            chosen++;
        }
        else {
            coin.setStyle("");
            coinsel = false;
            chosen--;
        }
    }

    @FXML
    private void servantChosen(){
        if(!servantsel) {
            servant.setStyle("-fx-border-color: violet; -fx-border-width: 5");
            servantsel = true;
            chosen++;
        }
        else {
            servant.setStyle("");
            servantsel = false;
            chosen--;
        }
    }

    @FXML
    private void shieldChosen(){
        if(!shieldsel) {
            shield.setStyle("-fx-border-color: violet; -fx-border-width: 5");
            shieldsel = true;
            chosen++;
        }
        else {
            shield.setStyle("");
            shieldsel = false;
            chosen--;
        }
    }

    @FXML
    private void stoneChosen(){

        if(!stonesel) {
            stone.setStyle("-fx-border-color: violet; -fx-border-width: 5");
            stonesel = true;
            chosen++;
        }
        else {
            stone.setStyle("");
            stonesel = false;
            chosen--;
        }

    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

       // numOfResourcesToChoose = gui.getNumOfPlayers;
        numOfResourcesToChoose = 2;
        title.setText(("Please choose " + numOfResourcesToChoose + " among these resources."));
        chosen = 0;
    }
}
