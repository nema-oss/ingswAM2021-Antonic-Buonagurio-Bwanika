package it.polimi.ingsw.view.client.gui.controllers;

import it.polimi.ingsw.model.gameboard.ResourceType;
import it.polimi.ingsw.view.client.gui.Gui;
import it.polimi.ingsw.view.client.gui.GuiManager;
import javafx.fxml.FXML;
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

public class ChooseResourcesController {


    private ResourceType resource;

    @FXML
    private BorderPane gb;

    @FXML
    private AnchorPane coin, servant, shield, stone;

    private boolean coinsel, servantsel, shieldsel, stonesel;



    @FXML
    private void switchOnGame() throws IOException {

        if(!coinsel && !servantsel && !shieldsel && !stonesel)
            notifySelectResource();

        else {
            //Gui.tellOk();
            GuiManager.changeGameScene("/gui/others");
        }
    }

    @FXML
    private void goldChosen(){
        if(!coinsel) {
            coin.setStyle("-fx-border-color: violet; -fx-border-width: 5");
            resource = ResourceType.COIN;
            coinsel = true;
        }
        else {
            coin.setStyle("");
            coinsel = false;
        }
    }

    @FXML
    private void servantChosen(){
        if(!servantsel) {
            servant.setStyle("-fx-border-color: violet; -fx-border-width: 5");
            resource = ResourceType.SERVANT;
            servantsel = true;
        }
        else {
            servant.setStyle("");
            servantsel = false;
        }
    }

    @FXML
    private void shieldChosen(){
        if(!shieldsel) {
            shield.setStyle("-fx-border-color: violet; -fx-border-width: 5");
            resource = ResourceType.SHIELD;
            shieldsel = true;
        }
        else {
            shield.setStyle("");
            shieldsel = false;
        }
    }

    @FXML
    private void stoneChosen(){

        if(!stonesel) {
            stone.setStyle("-fx-border-color: violet; -fx-border-width: 5");
            resource = ResourceType.STONE;
            stonesel = true;
        }
        else {
            stone.setStyle("");
            stonesel = false;
        }

    }

    public void initialize(AnchorPane gameBoard) {
        gb.setCenter(gameBoard);
    }

    public ResourceType getResource(){

        return resource;
    }

    public void notifySelectResource(){
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setHeaderText("You need to choose a resource before proceeding!");
        alert.showAndWait();
    }

    public void setGui(Gui gui) {
    }

    public void initializeResourceTypes(List<ResourceType> resourceTypes) {
    }
}
