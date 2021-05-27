package it.polimi.ingsw.view.client.gui.controllers;

import it.polimi.ingsw.messages.Message;
import it.polimi.ingsw.messages.setup.server.ChooseResourcesMessage;
import it.polimi.ingsw.model.gameboard.Resource;
import it.polimi.ingsw.view.client.gui.Gui;
import it.polimi.ingsw.model.gameboard.ResourceType;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import java.io.IOException;
import java.net.URL;
import java.util.*;

public class ChooseResourcesController {


    @FXML
    private AnchorPane coin, servant, shield, stone;

    @FXML
    private Label title;


    private boolean coinSelected, servantSelected, shieldSelected, stoneSelected;
    private Gui gui;

    public void setGui(Gui gui){
        this.gui = gui;
    }

    @FXML
    private void switchOnGame() throws IOException {

        /*
        if(chosen < 1){
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setHeaderText("You must choose a resource first!");
            alert.showAndWait();
        }

        else if (chosen > 1){
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setHeaderText("Too much resources chosen! You can choose one resource at a time");
            alert.showAndWait();
        }

         */

        Map<ResourceType,Integer> selectedResourceTypes = new HashMap<>();

        if(coinSelected){
            selectedResourceTypes.put(ResourceType.COIN,1);
        }
        if(servantSelected){
            selectedResourceTypes.put(ResourceType.SERVANT,1);
        }
        if(stoneSelected) {
            selectedResourceTypes.put(ResourceType.STONE, 1);
        }
        if(shieldSelected){
            selectedResourceTypes.put(ResourceType.SHIELD,1);
        }

        Message message = new ChooseResourcesMessage(gui.getPlayerNickname(),selectedResourceTypes,true);
        gui.sendMessage(message);

    }

    @FXML
    private void goldChosen(){
        if(!coinSelected) {
            coin.setStyle("-fx-border-color: violet; -fx-border-width: 5");
            coinSelected = true;
        }
        else {
            coin.setStyle("");
            coinSelected = false;
        }
    }

    @FXML
    private void servantChosen(){
        if(!servantSelected) {
            servant.setStyle("-fx-border-color: violet; -fx-border-width: 5");
            servantSelected = true;
        }
        else {
            servant.setStyle("");
            servantSelected = false;
        }
    }

    @FXML
    private void shieldChosen(){
        if(!shieldSelected) {
            shield.setStyle("-fx-border-color: violet; -fx-border-width: 5");
            shieldSelected = true;
        }
        else {
            shield.setStyle("");
            shieldSelected = false;
        }
    }

    @FXML
    private void stoneChosen(){

        if(!stoneSelected) {
            stone.setStyle("-fx-border-color: violet; -fx-border-width: 5");
            stoneSelected = true;
        }
        else {
            stone.setStyle("");
            stoneSelected = false;
        }

    }

    public void setInstructionalLabel(String text){
        title.setText(text);
    }

}
