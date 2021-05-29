package it.polimi.ingsw.view.client.gui.controllers;

import com.sun.javafx.scene.control.ContextMenuContent;
import it.polimi.ingsw.messages.Message;
import it.polimi.ingsw.messages.actions.*;
import it.polimi.ingsw.model.cards.DevelopmentCard;
import it.polimi.ingsw.model.cards.leadercards.LeaderCard;
import it.polimi.ingsw.model.cards.leadercards.LeaderCardType;
import it.polimi.ingsw.model.gameboard.Resource;
import it.polimi.ingsw.model.gameboard.ResourceType;
import it.polimi.ingsw.view.client.Cli;
import it.polimi.ingsw.view.client.gui.Gui;
import it.polimi.ingsw.view.client.viewComponents.*;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;

import java.net.URL;
import java.util.*;

/**
 * this class is the controller for the "playerBoard.fxml" file
 * @author chiara
 */
public class PlayerBoardController {

    @FXML
    ImageView leader1, leader2, res1, res2, result;

    @FXML
    Button boardProdButton, cardProdButton, leaderProdButton;

    @FXML
    GridPane devCards, floor1, floor2, floor3, popeRoad;

    @FXML
    AnchorPane strongbox;

    @FXML
    AnchorPane pBoard;

    @FXML
    Label strongboxCoinCount, strongboxShieldCount, strongboxServantCount, strongboxStoneCount;

    @FXML
    ImageView p0, p1, p2, p3, p4, p5, p6, p7, p8, p9, p10, p11, p12, p13, p14, p15, p16, p17, p18, p19, p20, p21, p22, p23, p24;

    public Gui gui;
    private LeaderCard l1, l2;
    private List<DevelopmentCard> prodCardsList;
    private List <LeaderCard> leaderCardsList;

    private boolean is1active, is2active, isLeaderAction;
    private List<Node> popeSpaces;


    /**
     * this method hides the inactive leader cards
     */
    public void hideInactiveLeaders(){
        if(!is1active)
            leader1.setVisible(false);
        if(!is2active)
            leader2.setVisible(false);
    }

    /**
     * this method shows tha actions which can be performed on a inactive leader
     * @param event
     */
    @FXML
    private void actionsOnLeader1(MouseEvent event){

        if(isLeaderAction) {
            if (!is1active) {
                ContextMenu inactiveMenu1 = new ContextMenu();

                MenuItem activate = new MenuItem("Activate leader card");
                activate.setOnAction(event1 -> {
                    is1active = true;
                    leader1.getParent().setStyle("-fx-border-width: 5; -fx-border-color: #51db51");

                    Message msg = new ActivateLeaderCardMessage(l1, false);
                    gui.sendMessage(msg);

                });

                MenuItem discard = new MenuItem("Discard leader card");
                discard.setOnAction(event2 -> {
                    Message msg = new DiscardLeaderCardMessage(l1);
                    gui.sendMessage(msg);
                });

                inactiveMenu1.getItems().addAll(activate, discard);

                inactiveMenu1.show(leader1, event.getSceneX(), event.getSceneY());
            } else {
                leaderCardsList.add(l1);
            }
        }
    }

    @FXML
    private void actionsOnLeader2(MouseEvent event){

        if(isLeaderAction) {
            if (!is2active) {
                ContextMenu inactiveMenu2 = new ContextMenu();

                MenuItem activate = new MenuItem("Activate leader card");
                activate.setOnAction(event1 -> {
                    is2active = true;
                    leader2.getParent().setStyle("-fx-border-width: 5; -fx-border-color: #51db51");

                    Message msg = new ActivateLeaderCardMessage(l2, false);
                    gui.sendMessage(msg);
                });

                MenuItem discard = new MenuItem("Discard leader card");
                discard.setOnAction(event2 -> {
                    Message msg = new DiscardLeaderCardMessage(l2);
                    gui.sendMessage(msg);
                });

                inactiveMenu2.getItems().addAll(activate, discard);

                inactiveMenu2.show(leader2, event.getSceneX(), event.getSceneY());
            } else {
                leaderCardsList.add(l2);
            }
        }
    }


    /**
     * this method updates the pope road
     * @param clientPlayer
     */
    public void updatePopeRoad(ClientPlayer clientPlayer){
        int index = clientPlayer.getPositionIndex();
        moveOnPopeRoad(index-(25-popeSpaces.size()));
    }

    /**
     * this method moves the player on the poperoad
     * @param steps
     */
    private void moveOnPopeRoad(Integer steps){
        popeSpaces.get(0).setStyle("");
        if (steps > 0) {
            popeSpaces.subList(0, steps).clear();
        }
        popeSpaces.get(0).setStyle("-fx-background-color:  #51db51");
    }

    /**
     * this method activates board production
     */
    @FXML
    public void activateBoardProduction(){
        Map<ResourceType, List<ResourceType>> map = new HashMap<>();
        List<ResourceType> toGive = new ArrayList<>();

        String url = res1.getImage().getUrl();
        ResourceType key = ResourceType.COIN;

        if(url.contains("coin"))
            toGive.add(ResourceType.COIN);
        else if(url.contains("shield"))
            toGive.add(ResourceType.SHIELD);
        else if(url.contains("servant"))
            toGive.add(ResourceType.SERVANT);
        else if(url.contains("stone"))
            toGive.add(ResourceType.STONE);

        url = res2.getImage().getUrl();

        if(url.contains("coin"))
            toGive.add(ResourceType.COIN);
        else if(url.contains("shield"))
            toGive.add(ResourceType.SHIELD);
        else if(url.contains("servant"))
            toGive.add(ResourceType.SERVANT);
        else if(url.contains("stone"))
            toGive.add(ResourceType.STONE);

        url = result.getImage().getUrl();

        if(url.contains("coin"))
            key = ResourceType.COIN;
        else if(url.contains("shield"))
            key = ResourceType.SHIELD;
        else if(url.contains("servant"))
            key = ResourceType.SERVANT;
        else if(url.contains("stone"))
            key = ResourceType.STONE;

        map.put(key, toGive);

        Message msg = new ActivateBoardProductionMessage(gui.getPlayerNickname(), map, false);
        gui.sendMessage(msg);
    }

    @FXML
    public void activateCardsProduction (){
        Message msg = new ActivateCardProductionMessage(gui.getPlayerNickname(), prodCardsList, false);
        gui.sendMessage(msg);
    }

    @FXML
    public void activateLeaderProduction(){
        Message msg = new ActivateLeaderProductionMessage(gui.getPlayerNickname(), leaderCardsList, false);
        gui.sendMessage(msg);
    }

    /**
     * this method changes the resources on the board's production panel
     * @param event
     */
    @FXML
    public void switchOnNextResource(MouseEvent event){
        if( event.getSource().equals(res1) )
            setNextResource(res1, res1.getImage().getUrl());
        if( event.getSource().equals(res2) )
            setNextResource(res2, res2.getImage().getUrl());
        if( event.getSource().equals(result) )
            setNextResource(result, result.getImage().getUrl());

    }

    //coin -> servant -> shield -> stone
    public void setNextResource(ImageView view, String url){
        if(url.contains("coin")) {
            view.setImage(new Image("/gui/Images/Resources/servant.png"));
        }
        else if (url.contains("servant")) {
            view.setImage(new Image("/gui/Images/Resources/shield.png"));
        }
        else if (url.contains("shield")) {
            view.setImage(new Image("/gui/Images/Resources/stone.png"));
        }
        else if (url.contains("stone")) {
            view.setImage(new Image("/gui/Images/Resources/coin.png"));
        }
    }


    /**
     * this method updates the player's developmentCards
     * @param clientPlayer the player to update
     */
    public void updateDevelopmentCards(ClientPlayer clientPlayer){

        devCards.getChildren().removeAll();
        for(int i=0; i<3; i++){
            ImageView card =new ImageView(new Image("/gui/Images/DevelopmentCardsFront/" + clientPlayer.getPlayerBoard().getDevelopmentCard(i).getId() + ".png"));
            card.setId(clientPlayer.getPlayerBoard().getDevelopmentCard(i).getId());

            int finalI = i;
            card.setOnMouseClicked(event -> {
               prodCardsList.add(clientPlayer.getPlayerBoard().getDevelopmentCard(finalI));
            });
            devCards.add(card,0,i);
        }
    }

    /**
     * this method updates the player's strongbox content
     * @param clientPlayer the player to update
     */
    public void updateStrongBox(ClientPlayer clientPlayer){

        for(ResourceType resourceType : clientPlayer.getStrongbox().getAll().keySet()){
            if(resourceType.equals(ResourceType.COIN)){
                strongboxCoinCount.setText(String.valueOf(clientPlayer.getStrongbox().getAll().get(resourceType).size()));
            }
            else if(resourceType.equals(ResourceType.SHIELD)){
                strongboxShieldCount.setText(String.valueOf(clientPlayer.getStrongbox().getAll().get(resourceType).size()));
            }
            else if(resourceType.equals(ResourceType.SERVANT)){
                strongboxServantCount.setText(String.valueOf(clientPlayer.getStrongbox().getAll().get(resourceType).size()));
            }
            else if(resourceType.equals(ResourceType.STONE)){
                strongboxStoneCount.setText(String.valueOf(clientPlayer.getStrongbox().getAll().get(resourceType).size()));
            }
        }
    }

    /**
     * this method updates the player's deposit content
     * @param clientPlayer the player to update
     */
    public void updateDeposit(ClientPlayer clientPlayer){

        ClientDeposit deposit = clientPlayer.getPlayerBoard().getDeposit();

        if(deposit.getNumberOfResourcesOnFloor(1)!=0) {
            ImageView res = new ImageView(new Image("/gui/Images/Resources/" + deposit.get(1).getType().label + ".png"));
            res.setFitHeight(30);
            res.setFitWidth(30);
            floor1.add(res, 0, 0);
        }

        if(deposit.getNumberOfResourcesOnFloor(2)!=0) {
            if(deposit.getNumberOfResourcesOnFloor(2)>=1) {
                ImageView res = new ImageView(new Image("/gui/Images/Resources/" + deposit.get(2).getType().label + ".png"));
                res.setFitHeight(30);
                res.setFitWidth(30);
                floor2.add(res, 0, 0);
            }
            if (deposit.getNumberOfResourcesOnFloor(2) == 2) {
                ImageView res = new ImageView(new Image("/gui/Images/Resources/" + deposit.get(2).getType().label + ".png"));
                res.setFitHeight(30);
                res.setFitWidth(30);
                floor2.add(res, 0, 1);
            }

        }

        if(deposit.getNumberOfResourcesOnFloor(3)!=0) {
            if(deposit.getNumberOfResourcesOnFloor(1)>=1) {
                ImageView res = new ImageView(new Image("/gui/Images/Resources/" + deposit.get(3).getType().label + ".png"));
                res.setFitHeight(30);
                res.setFitWidth(30);
                floor3.add(res, 0, 0);
            }
            if(deposit.getNumberOfResourcesOnFloor(1)>=2) {
                ImageView res = new ImageView(new Image("/gui/Images/Resources/" + deposit.get(3).getType().label + ".png"));
                res.setFitHeight(30);
                res.setFitWidth(30);
                floor3.add(res, 0, 1);
            }
            if(deposit.getNumberOfResourcesOnFloor(1)==3) {
                ImageView res = new ImageView(new Image("/gui/Images/Resources/" + deposit.get(3).getType().label + ".png"));
                res.setFitHeight(30);
                res.setFitWidth(30);
                floor3.add(res, 0, 2);
            }
        }


    }

    /**
     * this method initializes the player's board
     * @param clientPlayer the player the board belongs to
     */
    public void initialize(ClientPlayer clientPlayer){

        prodCardsList = new ArrayList<>();
        leaderCardsList = new ArrayList<>();

        //setting leadercards
        l1 = clientPlayer.getHand().get(0);
        leader1.setImage(new Image("gui/Images/LeaderCardsFront/" + l1.getId() + ".png"));
        l2=clientPlayer.getHand().get(1);
        leader2.setImage(new Image("gui/Images/LeaderCardsFront/" + l2.getId() + ".png"));

        //setting popeSpace
        popeSpaces = popeRoad.getChildren();
        if(clientPlayer.getPositionIndex() == 1 ) {
            p1.setStyle("-fx-background-color:  #51db51");
            popeSpaces.remove(0);
        }
        else {
            p0.setImage(new Image("gui/Images/PopeRoadResources/354687.png"));
            p0.setStyle("-fx-background-color:  #51db51");
        }

        //setting deposit
        updateDeposit(clientPlayer);

    }

    /**
     * this method updates the whole player board
     * @param clientPlayer the player to update
     */
    public void update(ClientPlayer clientPlayer){
        updateDeposit(clientPlayer);
        updateStrongBox(clientPlayer);
        updateDevelopmentCards(clientPlayer);

    }

    public void setGui(Gui gui){
        this.gui = gui;
    }

    public void setProductionClickable(Boolean bool){
        boardProdButton.setVisible(bool);
        cardProdButton.setVisible(bool);
        leaderProdButton.setVisible(bool);
    }

    public void setLeaderAction(Boolean bool){
        isLeaderAction = bool;
    }
}
