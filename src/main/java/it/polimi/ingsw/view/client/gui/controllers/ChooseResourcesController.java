package it.polimi.ingsw.view.client.gui.controllers;

import it.polimi.ingsw.messages.Message;
import it.polimi.ingsw.messages.setup.server.ChooseResourcesMessage;
import it.polimi.ingsw.view.client.gui.Gui;
import it.polimi.ingsw.model.gameboard.ResourceType;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import java.util.*;

/**
 * this class is the controller for the "chooseResources.fxml" file
 * @author chiara
 */
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


    /**
     * this method sends the message with the resources chosen by the player
     */
    @FXML
    private void switchOnGame() {

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

    /**
     * this method changes the images's style if it is clicked
     */
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

    /**
     * this method updates the label, writing how many resources the client can choose
     * @param text
     */
    public void setInstructionalLabel(String text){
        title.setText(text);
    }

}
