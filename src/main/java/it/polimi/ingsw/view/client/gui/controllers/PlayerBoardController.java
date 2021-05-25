package it.polimi.ingsw.view.client.gui.controllers;

import it.polimi.ingsw.model.cards.DevelopmentCard;
import it.polimi.ingsw.model.cards.leadercards.LeaderCard;
import it.polimi.ingsw.model.gameboard.Resource;
import it.polimi.ingsw.model.gameboard.ResourceType;
import it.polimi.ingsw.view.client.Cli;
import it.polimi.ingsw.view.client.viewComponents.*;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;

public class PlayerBoardController {

    @FXML
    ImageView active1, active2, inactive1, inactive2;

    @FXML
    GridPane devCards, floor1, floor2, floor3, popeRoad;

    @FXML
    AnchorPane strongbox;

    @FXML
    AnchorPane pBoard;

    @FXML
    ImageView p0, p1, p2, p3, p4, p5, p6, p7, p8, p9, p10, p11, p12, p13, p14, p15, p16, p17, p18, p19, p20, p21, p22, p23, p24;


    private List<Node> popeSpaces;


    private void leaderActivated(String leaderId){

        if (active1.getImage().getUrl().contains(leaderId)){
            active1.setVisible(true);
            inactive1.setVisible(false);
        }
        else if(active2.getImage().getUrl().contains(leaderId)){
            active2.setVisible(true);
            inactive2.setVisible(false);
        }
    }


    private void moveOnPopeRoad(Integer steps){
        popeSpaces.get(0).setVisible(false);
        if (steps > 0) {
            popeSpaces.subList(0, steps).clear();
        }
        popeSpaces.get(0).setVisible(true);
    }


    private void updateDevelopmentCards(ClientPlayer clientPlayer){

        for(int i=0; i<3; i++){
            devCards.add(new ImageView(new Image("/gui/Images/DevelopmentCardsFront/" + clientPlayer.getPlayerBoard().getDevelopmentCard(0).getId() + ".png")),0,i);
        }
    }

    private void updateStrongBox(ClientPlayer clientPlayer){

        for(ResourceType resourceType : clientPlayer.getStrongbox().getAll().keySet()){
            for(Resource res : clientPlayer.getStrongbox().getAll().get(resourceType)){
                strongbox.getChildren().add(new ImageView(new Image("/gui/Images/resources" + resourceType.label + ".png")));
            }
        }
    }

    private void updateDeposit(ClientPlayer clientPlayer){

        ClientDeposit deposit = clientPlayer.getPlayerBoard().getDeposit();

        if(deposit.getNumberOfResourcesOnFloor(3)!=0)
            floor3.add(new ImageView(new Image("/gui/Images/Resources/" + deposit.get(3).getType().label + ".png")), 0, 0);

        if(deposit.getNumberOfResourcesOnFloor(2)!=0) {
            if(deposit.getNumberOfResourcesOnFloor(2)>=1)
                floor2.add(new ImageView(new Image("/gui/Images/Resources/" + deposit.get(2).getType().label + ".png")), 0, 0);
            if(deposit.getNumberOfResourcesOnFloor(2)==2)
                floor2.add(new ImageView(new Image("/gui/Images/Resources/" + deposit.get(2).getType().label + ".png")), 0, 1);
        }

        if(deposit.getNumberOfResourcesOnFloor(1)!=0) {
            if(deposit.getNumberOfResourcesOnFloor(1)>=1)
                floor1.add(new ImageView(new Image("/gui/Images/Resources/" + deposit.get(1).getType().label + ".png")), 0, 0);
            if(deposit.getNumberOfResourcesOnFloor(1)>=2)
                floor1.add(new ImageView(new Image("/gui/Images/Resources/" + deposit.get(1).getType().label + ".png")), 0, 1);
            if(deposit.getNumberOfResourcesOnFloor(1)==3)
                floor1.add(new ImageView(new Image("/gui/Images/Resources/" + deposit.get(1).getType().label + ".png")), 0, 2);
        }


    }


    private void initialize(ClientPlayer clientPlayer){


        //setting leadercards
        inactive1.setImage(new Image("gui/Images/LeaderCardsFront/" + clientPlayer.getHand().get(0).getId() + ".png"));
        active1.setImage(new Image("gui/Images/LeaderCardsFront/" + clientPlayer.getHand().get(0).getId() + ".png"));
        active1.setVisible(false);
        inactive2.setImage(new Image("gui/Images/LeaderCardsFront/" + clientPlayer.getHand().get(1).getId() + ".png"));
        active2.setImage(new Image("gui/Images/LeaderCardsFront/" + clientPlayer.getHand().get(1).getId() + ".png"));
        active2.setVisible(false);


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

}
