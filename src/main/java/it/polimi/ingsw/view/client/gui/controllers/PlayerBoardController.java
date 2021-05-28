package it.polimi.ingsw.view.client.gui.controllers;

import com.sun.javafx.scene.control.ContextMenuContent;
import it.polimi.ingsw.model.cards.DevelopmentCard;
import it.polimi.ingsw.model.cards.leadercards.LeaderCard;
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
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;

public class PlayerBoardController {

    @FXML
    ImageView leader1, leader2, res1, res2, result;

    @FXML
    Button boardProdButton;

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

    private Gui gui;

    private boolean is1active, is2active;
    private List<Node> popeSpaces;

    @FXML
    private void actionsOnLeader1(MouseEvent event){

        if(!is1active){
            ContextMenu inactiveMenu1= new ContextMenu();

            MenuItem activate = new MenuItem("Activate leader card");
            activate.setOnAction(event1 -> {
                is1active = true;
                leader1.getParent().setStyle("-fx-border-width: 5; -fx-border-color: #51db51");
                //TODO: tell gui to activate leader passing inactive1.getImage().getURL()
            });

            MenuItem discard = new MenuItem("Discard leader card");
            discard.setOnAction(event2 -> {
                //TODO: tell gui to discard this leader passing inactive1.getImage().getUrl()
            });

            inactiveMenu1.getItems().addAll(activate, discard);

            inactiveMenu1.show(leader1, event.getSceneX(), event.getSceneY());
        }

        else{
            ImageView card = (ImageView) event.getSource();
            //TODO: tell gui to activateLeaderProduction on card.getId();
        }
    }

    @FXML
    private void actionsOnLeader2(MouseEvent event){

        if(!is2active){
            ContextMenu inactiveMenu2= new ContextMenu();

            MenuItem activate = new MenuItem("Activate leader card");
            activate.setOnAction(event1 -> {
                is2active = true;
                leader2.getParent().setStyle("-fx-border-width: 5; -fx-border-color: #51db51");
                //TODO: tell gui to activate leader passing inactive2.getImage().getURL()
            });

            MenuItem discard = new MenuItem("Discard leader card");
            discard.setOnAction(event2 -> {
                //TODO: tell gui to discard this leader passing inactive2.getImage().getUrl()
            });

            inactiveMenu2.getItems().addAll(activate, discard);

            inactiveMenu2.show(leader2, event.getSceneX(), event.getSceneY());
        }

        else{
            ImageView card = (ImageView) event.getSource();
            //TODO: tell gui to activateLeaderProduction on card.getId();
        }
    }


    public void updatePopeRoad(ClientPlayer clientPlayer){
        int index = clientPlayer.getPositionIndex();
        moveOnPopeRoad(index-(25-popeSpaces.size()));
    }

    private void moveOnPopeRoad(Integer steps){
        popeSpaces.get(0).setVisible(false);
        if (steps > 0) {
            popeSpaces.subList(0, steps).clear();
        }
        popeSpaces.get(0).setVisible(true);
    }

    @FXML
    public void activateBoardProduction( MouseEvent event){
        //TODO: tell gui to activate board production
    }

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
        if(url.contains("coin"))
            view.setImage(new Image("/gui/Images/Resources/servant.png"));
        else if (url.contains("servant"))
            view.setImage(new Image("/gui/Images/Resources/shield.png"));
        else if (url.contains("shield"))
            view.setImage(new Image("/gui/Images/Resources/stone.png"));
        else if (url.contains("stone"))
            view.setImage(new Image("/gui/Images/Resources/coin.png"));
    }


    public void updateDevelopmentCards(ClientPlayer clientPlayer){

        devCards.getChildren().removeAll();
        for(int i=0; i<3; i++){
            ImageView card =new ImageView(new Image("/gui/Images/DevelopmentCardsFront/" + clientPlayer.getPlayerBoard().getDevelopmentCard(i).getId() + ".png"));
            card.setId(clientPlayer.getPlayerBoard().getDevelopmentCard(i).getId());
            card.setOnMouseClicked(event -> {
               //TODO tell gui passing card.getId() [activate production]
            });
            devCards.add(card,0,i);
        }
    }

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


    public void initialize(ClientPlayer clientPlayer){

        //setting leadercards
        leader1.setImage(new Image("gui/Images/LeaderCardsFront/" + clientPlayer.getHand().get(0).getId() + ".png"));
        leader2.setImage(new Image("gui/Images/LeaderCardsFront/" + clientPlayer.getHand().get(1).getId() + ".png"));

        //setting popeSpace
        popeSpaces = popeRoad.getChildren();
        if(clientPlayer.getPositionIndex() == 1 ) {
            p0.setVisible(false);
            p1.setVisible(true);
            popeSpaces.remove(0);
        }

        //setting deposit
        updateDeposit(clientPlayer);

    }

    public void update(ClientPlayer clientPlayer){
        updateDeposit(clientPlayer);
        updateStrongBox(clientPlayer);
        updateDevelopmentCards(clientPlayer);

    }

    public void setGui(Gui gui){
        this.gui = gui;
    }
}
