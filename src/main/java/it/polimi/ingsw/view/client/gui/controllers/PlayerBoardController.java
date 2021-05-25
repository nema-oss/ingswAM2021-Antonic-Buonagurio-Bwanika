package it.polimi.ingsw.view.client.gui.controllers;

import it.polimi.ingsw.model.cards.leadercards.LeaderCard;
import it.polimi.ingsw.model.gameboard.Resource;
import it.polimi.ingsw.model.gameboard.ResourceType;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

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


  /*  private void leaderActivated(String leaderId){

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
        for(int i =0; i<steps; i++)
            popeSpaces.remove(0);
        popeSpaces.get(0).setVisible(true);
    }

    private void addDevelopmentCard(String cardId, int column){}

    private void addResourceToStrongbox(ResourceType resourceType){

        ImageView res = new ImageView(new Image("/Images/Resources/" + resourceType.label + ".png"));
        res.setFitHeight(15.0);
        res.setFitWidth(15.0);
        strongbox.getChildren().add(res);

    }

    private void addResourceToDeposit(ResourceType resourceType, int floor){

        ImageView res = new ImageView(new Image("/Images/Resources/" + resourceType.label + ".png"));


        }
    }

    private void initialize(List<String> inactiveLeaders, String popePosition, Map<ResourceType, Integer> depositResources){


        //setting leadercards
        inactive1.setImage(new Image("/Images/LeaderCardsFront/" + inactiveLeaders.get(0) + ".png"));
        active1.setImage(new Image("/Images/LeaderCardsFront/" + inactiveLeaders.get(0) + ".png"));
        active1.setVisible(false);
        inactive2.setImage(new Image("/Images/LeaderCardsFront/" + inactiveLeaders.get(0) + ".png"));
        active2.setImage(new Image("/Images/LeaderCardsFront/" + inactiveLeaders.get(0) + ".png"));
        active2.setVisible(false);


        //setting popeSpace
        popeSpaces = popeRoad.getChildren();
        if(popePosition.equals("1")) {
            p0.setVisible(false);
            p1.setVisible(true);
            popeSpaces.remove(0);
        }


        //setting deposit
        for(ResourceType r : depositResources.keySet()){
            ImageView img = new ImageView(new Image("/Images/Resources/" + r + ".png"));
            if(depositResources.get(r) == 1)
                floor1.add(img, 0,0);
            else if (depositResources.get(r) == 2)
                floor2.add(img, 0, 0);
            else if(depositResources.get(r) == 3)
                floor3.add(img, 0, 0);
        }
    } */

}
