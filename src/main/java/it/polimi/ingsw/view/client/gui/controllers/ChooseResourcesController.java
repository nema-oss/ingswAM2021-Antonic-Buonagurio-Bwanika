package it.polimi.ingsw.view.client.gui.controllers;

import it.polimi.ingsw.messages.Message;
import it.polimi.ingsw.messages.setup.server.ChooseResourcesMessage;
import it.polimi.ingsw.view.client.gui.Gui;
import it.polimi.ingsw.model.gameboard.ResourceType;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.css.CssMetaData;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;
import javafx.scene.layout.AnchorPane;

import java.net.URL;
import java.util.*;

/**
 * this class is the controller for the "chooseResources.fxml" file
 * @author chiara
 */
public class ChooseResourcesController implements Initializable {


    @FXML
    private Spinner<Integer> coin, servant, shield, stone;

    @FXML
    private Label title;


    private boolean coinSelected1, servantSelected1, shieldSelected1, stoneSelected1, coinSelected2, servantSelected2, shieldSelected2, stoneSelected2;
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

     /*   if(coinSelected1){
            if(coinSelected2)
                selectedResourceTypes.put(ResourceType.COIN,2);
            else
                selectedResourceTypes.put(ResourceType.COIN,1);
        }
        if(servantSelected1){
            if(servantSelected2)
                selectedResourceTypes.put(ResourceType.SERVANT,2);
            else
                selectedResourceTypes.put(ResourceType.SERVANT,1);
        }
        if(stoneSelected1) {
            if(stoneSelected2)
                selectedResourceTypes.put(ResourceType.STONE,2);
            else
                selectedResourceTypes.put(ResourceType.STONE,1);
        }
        if(shieldSelected1){
            if(shieldSelected2)
                selectedResourceTypes.put(ResourceType.SHIELD,2);
            else
                selectedResourceTypes.put(ResourceType.SHIELD,1);
        } */

        if(coin.getValue()!=0)
            selectedResourceTypes.put(ResourceType.COIN, coin.getValue());
        if(shield.getValue()!=0)
            selectedResourceTypes.put(ResourceType.SHIELD, shield.getValue());
        if(servant.getValue()!=0)
            selectedResourceTypes.put(ResourceType.SERVANT, servant.getValue());
        if(stone.getValue()!=0)
            selectedResourceTypes.put(ResourceType.STONE, stone.getValue());

        Message message = new ChooseResourcesMessage(gui.getPlayerNickname(),selectedResourceTypes,false);
        gui.sendMessage(message);

    }

    /**
     * this method changes the images's style if it is clicked
     */
    /* @FXML
    private void goldChosen(){
        if(!coinSelected1) {
            coin.setStyle("-fx-border-color: violet; -fx-border-width: 5");
            coinSelected1 = true;
        }
        else if(!coinSelected2) {
            coin.setStyle(" -fx-border-color: purple; -fx-border-width: 5");
            coinSelected2 = true;
        }
        else {
            coin.setStyle("");
            coinSelected1 = false;
            coinSelected2 = false;
        }
    }

    @FXML
    private void servantChosen(){
        if(!servantSelected1) {
            servant.setStyle("-fx-border-color: violet; -fx-border-width: 5");
            servantSelected1 = true;
        }
        else if(!servantSelected2) {
            servant.;

            servant.setStyle("");
            servantSelected2 = true;
        }
        else {
            servant.setStyle("");
            servantSelected1 = false;
            servantSelected2 = false;
        }
    }

    @FXML
    private void shieldChosen(){
        if(!shieldSelected1) {
            shield.setStyle("-fx-border-color: violet; -fx-border-width: 5");
            shieldSelected1 = true;
        }
        else if(!shieldSelected2) {
            shield.setStyle("");
            shieldSelected2 = true;
        }
        else {
            shield.setStyle("");
            shieldSelected1 = false;
            shieldSelected2 = false;
        }
    }

    @FXML
    private void stoneChosen(){

        if(!stoneSelected1) {
            stone.setStyle("");
            stoneSelected1 = true;
        }
        else if(!stoneSelected2) {
            stone.setStyle("-fx-border-color: purple; -fx-border-width: 5");
            stoneSelected2 = true;
        }
        else {
            stone.setStyle("");
            stoneSelected1 = false;
            stoneSelected2 = false;
        }

    } */

    /**
     * this method updates the label, writing how many resources the client can choose
     * @param text
     */
    public void setInstructionalLabel(String text){
        title.setText(text);
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        ObservableList<Integer> numbers = FXCollections.observableArrayList(0,1,2);
        SpinnerValueFactory<Integer> coinValueFactory = new SpinnerValueFactory.ListSpinnerValueFactory<Integer>(numbers);
        SpinnerValueFactory<Integer> servantValueFactory = new SpinnerValueFactory.ListSpinnerValueFactory<Integer>(numbers);
        SpinnerValueFactory<Integer> shieldValueFactory = new SpinnerValueFactory.ListSpinnerValueFactory<Integer>(numbers);
        SpinnerValueFactory<Integer> stoneValueFactory = new SpinnerValueFactory.ListSpinnerValueFactory<Integer>(numbers);

        coin.setValueFactory(coinValueFactory);
        coinValueFactory.setValue(0);
        servant.setValueFactory(servantValueFactory);
        servantValueFactory.setValue(0);
        shield.setValueFactory(shieldValueFactory);
        shieldValueFactory.setValue(0);
        stone.setValueFactory(stoneValueFactory);
        stoneValueFactory.setValue(0);
    }
}
