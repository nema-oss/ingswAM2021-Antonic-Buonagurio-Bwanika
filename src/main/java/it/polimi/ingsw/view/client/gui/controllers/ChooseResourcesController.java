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

public class ChooseResourcesController implements Initializable {


    @FXML
    private AnchorPane coin, servant, shield, stone;

    @FXML
    private Label title;

    private boolean coinSelected, servantSelected, shieldSelected, stoneSelected;
    private Gui gui;
    private Resource chosenResource;
    private int chosen;
    private int numberOfResources;

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

        /*
        else {
            //Gui.tellOk();
           GuiManager.changeGameScene("/gui/others");
        }

         */
    }

    @FXML
    private void goldChosen(){
        if(!coinSelected) {
            coin.setStyle("-fx-border-color: violet; -fx-border-width: 5");
            coinSelected = true;
            //chosen++;
        }
        else {
            coin.setStyle("");
            coinSelected = false;
            //chosen--;
        }
    }

    @FXML
    private void servantChosen(){
        if(!servantSelected) {
            servant.setStyle("-fx-border-color: violet; -fx-border-width: 5");
            servantSelected = true;
            //chosen++;
        }
        else {
            servant.setStyle("");
            servantSelected = false;
            //chosen--;
        }
    }

    @FXML
    private void shieldChosen(){
        if(!shieldSelected) {
            shield.setStyle("-fx-border-color: violet; -fx-border-width: 5");
            shieldSelected = true;
            //chosen++;
        }
        else {
            shield.setStyle("");
            shieldSelected = false;
            //chosen--;
        }
    }

    @FXML
    private void stoneChosen(){

        if(!stoneSelected) {
            stone.setStyle("-fx-border-color: violet; -fx-border-width: 5");
            stoneSelected = true;
            //chosen++;
        }
        else {
            stone.setStyle("");
            stoneSelected = false;
            //chosen--;
        }

    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        title.setText(("Choose a resource among these"));
        chosen = 0;
;    }

    public void initializeResourceTypes(List<ResourceType> resourceTypes){

    }

    public void initializeNumberOfResources(int numberOfResources){
        this.numberOfResources = numberOfResources;
    }
}
