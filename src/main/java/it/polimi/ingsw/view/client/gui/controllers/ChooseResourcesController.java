package it.polimi.ingsw.view.client.gui.controllers;

import it.polimi.ingsw.messages.Message;
import it.polimi.ingsw.messages.setup.server.ChooseResourcesMessage;
import it.polimi.ingsw.view.client.gui.Gui;
import it.polimi.ingsw.model.gameboard.ResourceType;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
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

    @FXML
    private AnchorPane coinView, servantView, stoneView, shieldView;

    @FXML
    private Button button;

    private Gui gui;
    private int numOfResourcesToChoose;

    public void setGui(Gui gui){
        this.gui = gui;
    }


    /**
     * this method sends the message with the resources chosen by the player
     */
    @FXML
    private void switchOnGame() {

        Map<ResourceType,Integer> selectedResourceTypes = new HashMap<>();

        if(coin.getValue()!=0)
            selectedResourceTypes.put(ResourceType.COIN, coin.getValue());
        if(shield.getValue()!=0)
            selectedResourceTypes.put(ResourceType.SHIELD, shield.getValue());
        if(servant.getValue()!=0)
            selectedResourceTypes.put(ResourceType.SERVANT, servant.getValue());
        if(stone.getValue()!=0)
            selectedResourceTypes.put(ResourceType.STONE, stone.getValue());

        int amountRequested = 0;
        for(ResourceType resourceType : selectedResourceTypes.keySet()){
            amountRequested = amountRequested + selectedResourceTypes.get(resourceType);
        }

        if(amountRequested!=numOfResourcesToChoose) {
            gui.alertUser("Warning", "Please choose the correct amount of resources!", Alert.AlertType.WARNING);
            return;
        }

        Message message = new ChooseResourcesMessage(gui.getClientPlayer().getNickname(),selectedResourceTypes,false);
        gui.sendMessage(message);

        hide();

    }

    /**
     * this method updates the label, writing how many resources the client can choose
     * @param text the text to set
     */
    public void setInstructionalLabel(String text){
        title.setText(text);
    }

    /**
     * this method sets the number of resources that the player can choose
     * @param numberOfResources the amount
     */
    public void setNumberOfResources(int numberOfResources){
        numOfResourcesToChoose = numberOfResources;
    }


    @Override
    public void initialize(URL location, ResourceBundle resources) {

        ObservableList<Integer> numbers = FXCollections.observableArrayList(0,1,2);
        SpinnerValueFactory<Integer> coinValueFactory = new SpinnerValueFactory.ListSpinnerValueFactory<>(numbers);
        SpinnerValueFactory<Integer> servantValueFactory = new SpinnerValueFactory.ListSpinnerValueFactory<>(numbers);
        SpinnerValueFactory<Integer> shieldValueFactory = new SpinnerValueFactory.ListSpinnerValueFactory<>(numbers);
        SpinnerValueFactory<Integer> stoneValueFactory = new SpinnerValueFactory.ListSpinnerValueFactory<>(numbers);

        coin.setValueFactory(coinValueFactory);
        coinValueFactory.setValue(0);
        servant.setValueFactory(servantValueFactory);
        servantValueFactory.setValue(0);
        shield.setValueFactory(shieldValueFactory);
        shieldValueFactory.setValue(0);
        stone.setValueFactory(stoneValueFactory);
        stoneValueFactory.setValue(0);
    }

    /**
     * this method hides the resources and the buttons and sets the title to "waiting"
     */
    public void hide(){
        coinView.setVisible(false);
        servantView.setVisible(false);
        shieldView.setVisible(false);
        stoneView.setVisible(false);
        servant.setVisible(false);
        stone.setVisible(false);
        shield.setVisible(false);
        coin.setVisible(false);
        button.setVisible(false);
        title.setText("Waiting for other players to setup  their board");
    }
}
