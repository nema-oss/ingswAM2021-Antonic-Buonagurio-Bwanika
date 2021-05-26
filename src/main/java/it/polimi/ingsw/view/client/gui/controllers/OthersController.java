package it.polimi.ingsw.view.client.gui.controllers;

import it.polimi.ingsw.view.client.gui.Gui;
import it.polimi.ingsw.view.client.gui.GuiManager;
import it.polimi.ingsw.view.client.viewComponents.ClientGameBoard;
import it.polimi.ingsw.view.client.viewComponents.ClientPlayer;
import javafx.collections.FXCollections;
import javafx.collections.ObservableArray;
import javafx.collections.ObservableList;
import javafx.collections.ObservableListBase;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class OthersController implements Initializable {

    @FXML
    private BorderPane leftPane;

    @FXML
    private ProgressIndicator wait;

     @FXML
     private Button standardAction, leaderAction, buyResource, buyCard, startProd, activateLeader, discardLeader, rowOrColumnOk, rowOk, columnOk;
     @FXML
     private ComboBox rowOrColumn, rowIndex, columnIndex;

     @FXML
     private Label waitingMessage, devMessage, prodMessage, leaderMessage;

    private Gui gui;

    public void setGui(Gui gui){
        this.gui = gui;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        //tabpane degli altri giocatori sulla sinistra
        TabPane tabPane = new TabPane();
        leftPane.setLeft(tabPane);
        tabPane.setTabMaxWidth(700);
        tabPane.setStyle("-fx-border-color: white; -fx-border-width: 5");

        //provvisorio: ovviamente dovrò prenderli da qualche parte

        List<ClientPlayer> otherPlayers = new ArrayList<>();    // = gui.getPlayers() e nella gui chiamo l'update di tutte le playerBoard
        otherPlayers.add(new ClientPlayer("Paolo", new ClientGameBoard()));
        otherPlayers.add(new ClientPlayer("Marco", new ClientGameBoard()));

        for(ClientPlayer other : otherPlayers) {
            try {
                FXMLLoader loader = GuiManager.loadFXML("/gui/playerBoard");
                Tab tab = new Tab(other.getNickname(), loader.load());
                tabPane.getTabs().add(tab);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        //scelta delle azioni da fare

        //progressindicator per l'attesa con scritta "tizio sta giocando il suo turno"
        wait.setStyle("-fx-progress-color: white");
        wait.setPrefWidth(80);
        wait.setVisible(false);
        waitingMessage.setVisible(false);

        //bottoni per decidere se standard action o leader action con scritta di spiegazione
        standardAction.setOnAction(event -> {
            setChooseActionTypeVisible(false);
            setChooseStandardActionVisible(true);
        });
        leaderAction.setOnAction(event -> {
            setChooseActionTypeVisible(false);
            setChooseLeaderActionVisible(true);
        });
        standardAction.setVisible(false);
        leaderAction.setVisible(false);


        //se standard action tre bottoni per i tre tipi di standard action
        buyResource.setOnAction(event -> {
            setChooseStandardActionVisible(false);
            setRowOrColumnVisible(false);
        });
        buyCard.setOnAction(event -> {
            setChooseStandardActionVisible(false);
            setBuyCardVisible(true);
        });
        startProd.setOnAction(event -> {
            setChooseStandardActionVisible(false);
            setActivateProductionVisible(true);
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
                rowIndex.setVisible(true);
            else if(rowOrColumn.getValue()=="column")
                columnIndex.setVisible(true);
            rowOrColumn.setVisible(false);
        });
        rowIndex.setItems(FXCollections.observableArrayList(1,2,3));
        rowIndex.setVisible(false);
        rowOk.setVisible(false);
        rowOk.setOnAction(event -> {
            setRowIndexVisible(false);
            //TODO: comunica alla gui
        });
        columnIndex.setItems(FXCollections.observableArrayList(1,2,3,4));
        columnOk.setVisible(false);
        rowIndex.setVisible(false);
        columnOk.setOnAction(event -> {
            setColumnIndexVisible(false);
            //TODO: comunica alla gui
        });

        //se buyDevelopmentCard messaggio con clicca culla carta che vuoi
        devMessage.setVisible(false);

        //se attiva produzione clicca sulle carteDev / plancia / carteLeader
        prodMessage.setVisible(false);

        //se leader action bottoni per decidere se scartare o attivare
        activateLeader.setOnAction(event -> {
            setDiscardOrActivateLeaderVisible(true);
            setChooseLeaderActionVisible(false);
        });
        discardLeader.setOnAction(event -> {
            setDiscardOrActivateLeaderVisible(true);
            setChooseLeaderActionVisible(false);
        });
        activateLeader.setVisible(false);
        discardLeader.setVisible(false);

        //se scarta clicca su quale scartare
        //se attiva clicca su quale attivare
        leaderMessage = new Label("choose the leader card you want to discard/activate");
    }


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
        activateLeader.setVisible(value);
        discardLeader.setVisible(value);
    }

    public void setBuyCardVisible(boolean value){
        devMessage.setVisible(value);
    }

    public void setRowOrColumnVisible(boolean value){
        rowOrColumn.setVisible(value);
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

    public void setDiscardOrActivateLeaderVisible(boolean value){
        leaderMessage.setVisible(value);
    }


}
