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
import javafx.scene.control.Button;
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

    @FXML
    private AnchorPane coinView, servantView, shieldView, stoneView;

    @FXML
    private Button button;

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

    /**
     * this method hides the resources and the buttons and sets the title to "waiting"
     */
    public void hide(){
        coinView.setVisible(false);
        servantView.setVisible(false);
        servantView.setVisible(false);
        stoneView.setVisible(false);
        servant.setVisible(false);
        stone.setVisible(false);
        shield.setVisible(false);
        coin.setVisible(false);
        button.setVisible(false);
        title.setText("Waiting for other players to setup their board");
    }
}
