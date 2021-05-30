package it.polimi.ingsw.view.client.gui.controllers;

import it.polimi.ingsw.messages.Message;
import it.polimi.ingsw.messages.actions.*;
import it.polimi.ingsw.model.gameboard.Resource;
import it.polimi.ingsw.model.gameboard.ResourceType;
import it.polimi.ingsw.view.client.gui.Gui;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;

import java.net.URL;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;


/**
 * this class is the controller for the "actions.fxml" file
 * @author chiara
 */

public class ActionButtonsController implements Initializable {

    @FXML
    private BorderPane leftPane;

    @FXML
    private AnchorPane resourcePane, swapPane;

    @FXML
    private ProgressIndicator wait;

     @FXML
     private Button standardAction, leaderAction, buyResource, buyCard, startProd, rowOrColumnOk, rowOk, columnOk, endProd, placeResourcesOk, swapOk, endTurnButton;

     @FXML
     private CheckBox discard1, discard2, discard3, discard4;
     @FXML
     private ComboBox<String> rowOrColumn;

     @FXML
     private ComboBox<Integer> rowIndex, columnIndex, floorComboBox1, floorComboBox2, floorComboBox3, floorComboBox4, firstSwap, secondSwap;

     @FXML
     private Label waitingMessage, devMessage, prodMessage, leaderMessage, placeResourcesMessage;

     @FXML
     private ImageView firstRes, secondRes, thirdRes, fourthRes;


     private ResourceType toPut1, toPut2, toPut3, toPut4;
     private GameController gameController;
     private Gui gui;
    private List<Resource> boughtResources;

    public void setGui(Gui gui){
        this.gui = gui;
    }

     public void setGameController(GameController gameController){
         this.gameController = gameController;
     }

    /**
     * this method creates all the buttons for the actions and sets them invisible
     * @param location
     * @param resources
     */
    @Override
    public void initialize(URL location, ResourceBundle resources) {

        //scelta delle azioni da fare

        //progressindicator per l'attesa con scritta "tizio sta giocando il suo turno"
        wait.setStyle("-fx-progress-color: white");
        wait.setPrefWidth(80);
        setWaitVisible(false);

        //wait.setVisible(true);
       // waitingMessage.setVisible(true);

        //bottoni per decidere se standard action o leader action con scritta di spiegazione
        standardAction.setOnAction(event -> {
            setChooseActionTypeVisible(false);
            setChooseStandardActionVisible(true);
        });
        leaderAction.setOnAction(event -> {
            gameController.setLeaderAction(true);
            setChooseActionTypeVisible(false);
            setChooseLeaderActionVisible(true);
        });
        setChooseActionTypeVisible(true);


        //se standard action tre bottoni per i tre tipi di standard action
        buyResource.setOnAction(event -> {
            setChooseStandardActionVisible(false);
            setRowOrColumnVisible(true);

        });
        buyCard.setOnAction(event -> {
            gameController.makeCardMarketClickable(true);
            setChooseStandardActionVisible(false);
            setBuyCardVisible(true);
        });
        startProd.setOnAction(event -> {

            setChooseStandardActionVisible(false);
            setActivateProductionVisible(true);

            Message msg = new ActivateProductionMessage(gui.getPlayerNickname());
            gui.sendMessage(msg);

            //TODO: da mettere altrove
            gameController.makeProductionClickable(true);
        });
        endProd.setOnAction(event -> {
            setActivateProductionVisible(false);

            //TODO: e il messaggio per finire dov'è?
        });
        setChooseStandardActionVisible(false);
        endProd.setVisible(false);

        //se buyResource combo con riga/colonna + combo con numero riga/colonna
        rowOrColumn.setItems(FXCollections.observableArrayList("row", "column"));
        setRowOrColumnVisible(false);
        rowOrColumnOk.setOnAction(event -> {
            if(rowOrColumn.getValue().equals("row"))
                setRowIndexVisible(true);
            else if(rowOrColumn.getValue().equals("column"))
                setColumnIndexVisible(true);
            setRowOrColumnVisible(false);
        });
        rowIndex.setItems(FXCollections.observableArrayList(1,2,3));
        setRowIndexVisible(false);
        rowOk.setOnAction(event -> {
            setRowIndexVisible(false);
            Message msg = new BuyResourcesMessage(gui.getPlayerNickname(),(Integer) rowIndex.getValue(), -1, false);
            gui.sendMessage(msg);
        });
        columnIndex.setItems(FXCollections.observableArrayList(1,2,3,4));
        setColumnIndexVisible(false);
        columnOk.setOnAction(event -> {
            setColumnIndexVisible(false);
            Message msg = new BuyResourcesMessage(gui.getPlayerNickname(),-1, (Integer) columnIndex.getValue(), false);
            gui.sendMessage(msg);
        });

        //placing resources
        floorComboBox1.setItems(FXCollections.observableArrayList(1,2,3));
        floorComboBox2.setItems(FXCollections.observableArrayList(1,2,3));
        floorComboBox3.setItems(FXCollections.observableArrayList(1,2,3));
        floorComboBox4.setItems(FXCollections.observableArrayList(1,2,3));
        placeResourcesOk.setOnAction(event -> {
            sendPlaceResources();
        });
        setResourcePaneVisible(false);

        swapOk.setOnAction(event -> sendSwapFloors());
        firstSwap.setItems(FXCollections.observableArrayList(1,2,3));
        secondSwap.setItems(FXCollections.observableArrayList(1,2,3));
        setSwapPaneVisible(false);

        //se buyDevelopmentCard messaggio con clicca sulla carta che vuoi
        devMessage.setVisible(false);

        //se attiva produzione clicca sulle carteDev / plancia / carteLeader
        prodMessage.setVisible(false);

        //se scarta clicca su quale scartare
        //se attiva clicca su quale attivare
        leaderMessage .setVisible(false);

        endTurnButton.setOnAction(event -> {
            Message msg = new EndTurnMessage(gui.getPlayerNickname());
            gui.sendMessage(msg);
        });
        setEndTurnVisible(false);
    }

    /**
     * these methods show or hide the action buttons
     * @param value
     */
    public void setWaitVisible(boolean value){
        wait.setVisible(value);
        waitingMessage.setVisible(value);
    }

    public void setChooseActionTypeVisible(boolean value){
        standardAction.setVisible(value);
        leaderAction.setVisible(value);
    }

    public void setStandardActionVisible(boolean value){
        standardAction.setVisible(value);
    }

    public void setLeaderActionVisible(boolean value){
        leaderAction.setVisible(value);
    }

    public void setChooseStandardActionVisible(boolean value){
        buyCard.setVisible(value);
        buyResource.setVisible(value);
        startProd.setVisible(value);
    }

    public void setChooseLeaderActionVisible(boolean value){
        leaderMessage.setVisible(value);
    }

    public void setBuyCardVisible(boolean value){
        devMessage.setVisible(value);
    }

    public void setRowOrColumnVisible(boolean value){
        rowOrColumn.setVisible(value);
        rowOrColumnOk.setVisible(value);
    }

    public void setColumnIndexVisible(boolean value) {
        columnIndex.setVisible(value);
        columnOk.setVisible(value);
    }

    public void setRowIndexVisible(boolean value){
        rowIndex.setVisible(value);
        rowOk.setVisible(value);
    }

    public void setActivateProductionVisible(boolean value){

        prodMessage.setVisible(value);
        endProd.setVisible(value);

    }

    public void setResourcePaneVisible(boolean value){

        firstRes.setVisible(value);
        secondRes.setVisible(value);
        thirdRes.setVisible(value);
        fourthRes.setVisible(value);
        floorComboBox1.setVisible(value);
        floorComboBox2.setVisible(value);
        floorComboBox3.setVisible(value);
        floorComboBox4.setVisible(value);
        discard1.setVisible(value);
        discard2.setVisible(value);
        discard3.setVisible(value);
        discard4.setVisible(value);
        placeResourcesMessage.setVisible(false);
        placeResourcesOk.setVisible(false);
        resourcePane.setVisible(false);

    }

    public void setSwapPaneVisible(boolean value){
        swapPane.setVisible(value);
    }

    public void setEndTurnVisible(boolean value){
        endTurnButton.setVisible(value);
    }

    /**
     * this method prepares the pane to place the resources
     * @param resources
     */
    public void setPlaceResources(List<Resource> resources){

        int size = resources.size();
        this.boughtResources = resources;

        if(size!=0) {

            resourcePane.setVisible(true);
            placeResourcesOk.setVisible(true);
            placeResourcesMessage.setVisible(true);

            firstRes.setImage(new Image("/gui/Images/Resources/" +resources.get(0).getType().label + ".png"));
            firstRes.setVisible(true);
            floorComboBox1.setVisible(true);
            discard1.setVisible(true);
            toPut1 = resources.get(0).getType();

            if (size >= 2) {
                secondRes.setImage(new Image("/gui/Images/Resources/" +resources.get(1).getType().label + ".png"));
                secondRes.setVisible(true);
                floorComboBox2.setVisible(true);
                discard2.setVisible(true);
                toPut2 = resources.get(1).getType();
            }

            if (size >= 3) {
                thirdRes.setImage(new Image("/gui/Images/Resources/" +resources.get(2).getType().label + ".png"));
                thirdRes.setVisible(true);
                floorComboBox3.setVisible(true);
                discard3.setVisible(true);
                toPut3 = resources.get(2).getType();
            }

            if (size == 4) {
                fourthRes.setImage(new Image("/gui/Images/Resources/" +resources.get(3).getType().label + ".png"));
                fourthRes.setVisible(true);
                floorComboBox4.setVisible(true);
                discard4.setVisible(true);
                toPut4 = resources.get(3).getType();
            }
        }
    }


    /**
     * this method sends the message with the resources chosen and where to put them
     */
    public void sendPlaceResources(){

        Map<Resource, Integer> map = new HashMap<>();

        if(floorComboBox1.isVisible() && !discard1.isSelected()) {
            map.put(new Resource(toPut1), floorComboBox1.getValue());
        }
        if(floorComboBox2.isVisible() && !discard2.isSelected()) {
            map.put(new Resource(toPut2), floorComboBox2.getValue());
        }
        if(floorComboBox3.isVisible() && !discard3.isSelected()) {
            map.put(new Resource(toPut3), floorComboBox3.getValue());
        }
        if(floorComboBox4.isVisible() && !discard4.isSelected()) {
            map.put(new Resource(toPut4), floorComboBox4.getValue());
        }

        gui.alertUser("Information", "The other resources will be discarded.", Alert.AlertType.INFORMATION);
        PlaceResourcesMessage msg = new PlaceResourcesMessage(gui.getPlayerNickname(), map);
        System.out.println(map);
        msg.setDiscardedResources(Math.abs(boughtResources.size() - map.size()));
        gui.sendMessage(msg);
    }

    public void sendSwapFloors(){

        Message msg = new MoveDepositMessage(gui.getPlayerNickname(), firstSwap.getValue(), secondSwap.getValue(), false);
        gui.sendMessage(msg);
    }
}
