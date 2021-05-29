package it.polimi.ingsw.view.client.gui.controllers;

import it.polimi.ingsw.messages.Message;
import it.polimi.ingsw.messages.actions.ActivateProductionMessage;
import it.polimi.ingsw.messages.actions.BuyResourcesMessage;
import it.polimi.ingsw.view.client.gui.Gui;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * this class is the controller for the "actions.fxml" file
 * @author chiara
 */
public class OthersController implements Initializable {

    @FXML
    private BorderPane leftPane;

    @FXML
    private ProgressIndicator wait;

     @FXML
     private Button standardAction, leaderAction, buyResource, buyCard, startProd, rowOrColumnOk, rowOk, columnOk;
     @FXML
     private ComboBox rowOrColumn, rowIndex, columnIndex;

     @FXML
     private Label waitingMessage, devMessage, prodMessage, leaderMessage;

     private GameController gameController;
     private Gui gui;

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
        waitingMessage.setVisible(false);
        wait.setVisible(false);

        //wait.setVisible(true);
       // waitingMessage.setVisible(true);

        //bottoni per decidere se standard action o leader action con scritta di spiegazione
        standardAction.setOnAction(event -> {
            setChooseActionTypeVisible(false);
            setChooseStandardActionVisible(true);
        });
        leaderAction.setOnAction(event -> {
            setChooseActionTypeVisible(false);
            setChooseLeaderActionVisible(true);
        });
        standardAction.setVisible(true);
        leaderAction.setVisible(true);


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
        buyResource.setVisible(false);
        buyCard.setVisible(false);
        startProd.setVisible(false);

        //se buyResource combo con riga/colonna + combo con numero riga/colonna
        rowOrColumn.setItems(FXCollections.observableArrayList("row", "column"));
        rowOrColumn.setVisible(false);
        rowOrColumnOk.setVisible(false);
        rowOrColumnOk.setOnAction(event -> {
            if(rowOrColumn.getValue() == "row")
                setRowIndexVisible(true);
            else if(rowOrColumn.getValue()=="column")
                setColumnIndexVisible(true);
            setRowOrColumnVisible(false);
        });
        rowIndex.setItems(FXCollections.observableArrayList(1,2,3));
        rowIndex.setVisible(false);
        rowOk.setVisible(false);
        rowOk.setOnAction(event -> {
            setRowIndexVisible(false);
            Message msg = new BuyResourcesMessage(gui.getPlayerNickname(),(Integer) rowIndex.getValue(), -1, false);
            gui.sendMessage(msg);
        });
        columnIndex.setItems(FXCollections.observableArrayList(1,2,3,4));
        columnOk.setVisible(false);
        columnIndex.setVisible(false);
        columnOk.setOnAction(event -> {
            setColumnIndexVisible(false);
            Message msg = new BuyResourcesMessage(gui.getPlayerNickname(),-1, (Integer) columnIndex.getValue(), false);
            gui.sendMessage(msg);
        });

        //se buyDevelopmentCard messaggio con clicca sulla carta che vuoi
        devMessage.setVisible(false);

        //se attiva produzione clicca sulle carteDev / plancia / carteLeader
        prodMessage.setVisible(false);

        //se scarta clicca su quale scartare
        //se attiva clicca su quale attivare
        leaderMessage .setVisible(false);
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
        startProd.setVisible(value);
    }



}
