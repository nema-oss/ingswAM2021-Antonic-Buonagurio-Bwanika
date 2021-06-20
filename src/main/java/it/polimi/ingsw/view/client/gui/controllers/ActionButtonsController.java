package it.polimi.ingsw.view.client.gui.controllers;

import it.polimi.ingsw.messages.Message;
import it.polimi.ingsw.messages.actions.*;
import it.polimi.ingsw.messages.setup.client.UpdateClientPlayerBoardsMessage;
import it.polimi.ingsw.model.ActionToken;
import it.polimi.ingsw.model.gameboard.Resource;
import it.polimi.ingsw.model.gameboard.ResourceType;
import it.polimi.ingsw.view.client.gui.Gui;
import it.polimi.ingsw.view.client.gui.GuiManager;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Border;
import javafx.scene.layout.BorderPane;

import java.io.IOException;
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
    private AnchorPane resourcePane, swapPane, lorenzoPane;

    @FXML
    BorderPane generalPane;

    @FXML
    private ProgressIndicator wait;

     @FXML
     private Button standardAction, leaderAction, buyResource, buyCard, startProd, rowOrColumnOk, rowOk, columnOk, endProd, placeResourcesOk, swapOk, endTurnButton, actionTokenOk, backButton, cheat;

     @FXML
     private CheckBox discard1, discard2, discard3, discard4;
     @FXML
     private ComboBox<String> rowOrColumn;

     @FXML
     private ComboBox<Integer> rowIndex, columnIndex, floorComboBox1, floorComboBox2, floorComboBox3, floorComboBox4, firstSwap, secondSwap;

     @FXML
     private Label waitingMessage, devMessage, prodMessage, leaderMessage, placeResourcesMessage, lorenzoLabel;

     @FXML
     private ImageView firstRes, secondRes, thirdRes, fourthRes, actionToken;


     private ResourceType toPut1, toPut2, toPut3, toPut4;
     private GameController gameController;
     private Gui gui;
     private List<Resource> boughtResources;
     public LorenzoController lorenzoController;

    public void setGui(Gui gui){
        this.gui = gui;
    }

     public void setGameController(GameController gameController){
         this.gameController = gameController;
     }

    /**
     * this method creates all the buttons for the actions and sets them invisible
     */
    @Override
    public void initialize(URL location, ResourceBundle resources) {


        wait.setStyle("-fx-progress-color: white");
        wait.setPrefWidth(80);
        setWaitVisible(false);


        standardAction.setOnAction(event -> {
            setChooseActionTypeVisible(false);
            setChooseStandardActionVisible(true);
            setBackButtonVisible(true);
        });
        leaderAction.setOnAction(event -> {
            gameController.setLeaderAction(true);
            setChooseActionTypeVisible(false);
            setChooseLeaderActionVisible(true);
            setBackButtonVisible(true);
        });
        setChooseActionTypeVisible(true);


        buyResource.setOnAction(event -> {
            setChooseStandardActionVisible(false);
            setRowOrColumnVisible(true);

        });
        cheat.setVisible(false);
        cheat.setOnAction(event -> {
            Message msg = new CheatMessage(gui.getPlayerNickname());
            gui.sendMessage(msg);
        });
        buyCard.setOnAction(event -> {
            gameController.makeCardMarketClickable(true);
            setChooseStandardActionVisible(false);
            setBuyCardVisible(true);
        });
        startProd.setOnAction(event -> {

            setChooseStandardActionVisible(false);
            setChooseActionTypeVisible(false);
            setEndTurnVisible(false);
            setActivateProductionVisible(true);

            Message msg = new ActivateProductionMessage(gui.getPlayerNickname());
            gui.sendMessage(msg);

            gameController.makeProductionClickable(true);
        });
        endProd.setOnAction(event -> {
            setActivateProductionVisible(false);
            gameController.makeProductionClickable(false);
            setEndTurnVisible(true);
            EndProductionMessage endProductionMessage = new EndProductionMessage(gui.getPlayerNickname());
            gui.sendMessage(endProductionMessage);
        });
        setChooseStandardActionVisible(false);
        endProd.setVisible(false);

        rowOrColumn.setItems(FXCollections.observableArrayList("row", "column"));
        setRowOrColumnVisible(false);
        rowOrColumnOk.setOnAction(event -> {
            if(rowOrColumn.getValue()!= null && rowOrColumn.getValue().equals("row"))
                setRowIndexVisible(true);
            else if(rowOrColumn.getValue()!= null && rowOrColumn.getValue().equals("column"))
                setColumnIndexVisible(true);
            setRowOrColumnVisible(false);
        });
        rowIndex.setItems(FXCollections.observableArrayList(1,2,3));
        setRowIndexVisible(false);
        rowOk.setOnAction(event -> {
            if(rowIndex.getValue()!= null) {
                setRowIndexVisible(false);
                Message msg = new BuyResourcesMessage(gui.getPlayerNickname(), rowIndex.getValue() - 1, -1, false);
                gui.sendMessage(msg);
            }
        });
        columnIndex.setItems(FXCollections.observableArrayList(1,2,3,4));
        setColumnIndexVisible(false);
        columnOk.setOnAction(event -> {
            if(columnIndex.getValue()!= null) {
                setColumnIndexVisible(false);
                Message msg = new BuyResourcesMessage(gui.getPlayerNickname(), -1, columnIndex.getValue() - 1, false);
                gui.sendMessage(msg);
            }
        });

        floorComboBox1.setItems(FXCollections.observableArrayList(1,2,3));
        floorComboBox2.setItems(FXCollections.observableArrayList(1,2,3));
        floorComboBox3.setItems(FXCollections.observableArrayList(1,2,3));
        floorComboBox4.setItems(FXCollections.observableArrayList(1,2,3));
        placeResourcesOk.setOnAction(event -> sendPlaceResources());
        setResourcePaneVisible(false);

        swapOk.setOnAction(event -> sendSwapFloors());
        firstSwap.setItems(FXCollections.observableArrayList(1,2,3));
        secondSwap.setItems(FXCollections.observableArrayList(1,2,3));
        setSwapPaneVisible(false);

        devMessage.setVisible(false);

        prodMessage.setVisible(false);

        leaderMessage .setVisible(false);

        endTurnButton.setOnAction(event -> {

            if(!gui.checkTurnEnd())
                gui.alertUser("Warning", "You can't end your turn without playing a standard action", Alert.AlertType.WARNING);
            else {
                Message msg = new EndTurnMessage(gui.getPlayerNickname());
                UpdateClientPlayerBoardsMessage updateClientPlayerBoardsMessage = new UpdateClientPlayerBoardsMessage(gui.getPlayerNickname(), gui.getClientPlayer().getPlayerBoard());
                gui.sendMessage(updateClientPlayerBoardsMessage);
                gui.sendMessage(msg);
            }
        });
        setEndTurnVisible(false);

        setLorenzoVisible(false);
        actionTokenOk.setOnAction(event -> {
            setLorenzoVisible(false);
            setChooseActionTypeVisible(true);
            setEndTurnVisible(true);
        });

        backButton.setVisible(false);
        backButton.setOnAction(event -> {
            setStandardActionVisible(false);
            setLorenzoVisible(false);
            setChooseStandardActionVisible(false);
            setChooseLeaderActionVisible(false);
            setLorenzoVisible(false);
            setRowOrColumnVisible(false);
            setRowIndexVisible(false);
            setColumnIndexVisible(false);
            setSwapPaneVisible(false);
            setResourcePaneVisible(false);
            setActivateProductionVisible(false);
            setBuyCardVisible(false);
            if(!leaderAction.isVisible()) {
                setChooseActionTypeVisible(true);
                setBackButtonVisible(false);
            }
            else{
                setEndTurnVisible(true);
            }
        });

    }

    /**
     * these methods show or hide the action buttons
     * @param value: true to set object visible, false otherwise
     */
    public void setBackButtonVisible(boolean value){
        backButton.setVisible(value);
    }

    public void setWaitVisible(boolean value){
        wait.setVisible(value);
        waitingMessage.setVisible(value);
        endTurnButton.setVisible(false);
        backButton.setVisible(false);
    }

    public void setChooseActionTypeVisible(boolean value){
        if(!lorenzoPane.isVisible()) {
            standardAction.setVisible(value);
            leaderAction.setVisible(value);
        }
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
        cheat.setVisible(value);
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
        placeResourcesMessage.setVisible(value);
        placeResourcesOk.setVisible(value);
        resourcePane.setVisible(value);
        endTurnButton.setVisible(false);

    }

    public void setSwapPaneVisible(boolean value){
        swapPane.setVisible(value);
    }

    public void setEndTurnVisible(boolean value){
        endTurnButton.setVisible(value);
    }

    /**
     * this method prepares the pane to place the resources
     * @param resources to place
     */
    public void setPlaceResources(List<Resource> resources){

        int size = resources.size();
        this.boughtResources = resources;

        if(size!=0) {

            resourcePane.setVisible(true);
            placeResourcesOk.setVisible(true);
            placeResourcesMessage.setVisible(true);
            setEndTurnVisible(false);

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

        if(floorComboBox1.isVisible() && !discard1.isSelected() && floorComboBox1.getValue()!=null) {
            map.put(new Resource(toPut1), floorComboBox1.getValue());
        }
        if(floorComboBox2.isVisible() && !discard2.isSelected() && floorComboBox2.getValue()!=null) {
            map.put(new Resource(toPut2), floorComboBox2.getValue());
        }
        if(floorComboBox3.isVisible() && !discard3.isSelected() && floorComboBox3.getValue()!=null) {
            map.put(new Resource(toPut3), floorComboBox3.getValue());
        }
        if(floorComboBox4.isVisible() && !discard4.isSelected() && floorComboBox4.getValue()!=null) {
            map.put(new Resource(toPut4), floorComboBox4.getValue());
        }

        gui.alertUser("Information", "The other resources will be discarded.", Alert.AlertType.INFORMATION);
        PlaceResourcesMessage msg = new PlaceResourcesMessage(gui.getPlayerNickname(), map);
        msg.setDiscardedResources(Math.abs(boughtResources.size() - map.size()));
        gui.sendMessage(msg);
    }

    /**
     * this method sens the message to swap the deposit's floors
     */
    public void sendSwapFloors(){

        if(firstSwap.getValue()!=null && secondSwap.getValue()!=null) {
            Message msg = new MoveDepositMessage(gui.getPlayerNickname(), firstSwap.getValue(), secondSwap.getValue(), false);
            gui.sendMessage(msg);
        }
    }


    /**
     * this method shows or hides lorenzo's turn
     * @param value true to set visible, false otherwise
     */
    public void setLorenzoVisible(boolean value){
        lorenzoLabel.setVisible(value);
        actionToken.setVisible(value);
        lorenzoPane.setVisible(value);
        actionTokenOk.setVisible(value);
    }

    /**
     * this method shows the action token drawn by the player and explains the corresponding action
     * @param tokenDrawn the action token drawn
     * @param text string of explanation
     */
    public void showLorenzoTurn(ActionToken tokenDrawn, String text){

        actionToken.setImage(new Image("/gui/Images/ActionTokens/cerchio" + tokenDrawn.getId() + ".png"));
        lorenzoLabel.setText(text);

        setLorenzoVisible(true);

        standardAction.setVisible(false);
        leaderAction.setVisible(false);
        endTurnButton.setVisible(false);
        backButton.setVisible(false);

    }

    public void showLorenzoPosition() throws IOException {

        FXMLLoader loader = GuiManager.loadFXML("/gui/lorenzoPosition");
        generalPane.setRight(loader.load());
        lorenzoController = loader.getController();

    }

    public void updateLorenzoPosition(int lorenzoPosition){
        lorenzoController.update(lorenzoPosition);
    }
}
