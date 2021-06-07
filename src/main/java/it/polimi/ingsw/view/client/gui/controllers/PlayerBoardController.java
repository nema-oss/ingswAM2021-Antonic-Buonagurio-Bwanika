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
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;

import java.net.URL;
import java.util.*;

/**
 * this class is the controller for the "playerBoard.fxml" file
 * @author chiara
 */
public class PlayerBoardController {

    @FXML
    ImageView leader1, leader2, res1, res2, result, dep1a, dep1b, dep2a, dep2b;

    @FXML
    Button boardProdButton, cardProdButton, leaderProdButton, place1, place2, place3;

    @FXML
    GridPane devCards, floor1, floor2, floor3, popeRoad;

    @FXML
    AnchorPane strongbox, pBoard, extraDeposit1, extraDeposit2;

    @FXML
    BorderPane dev1, dev2, dev3;

    @FXML
    Label strongboxCoinCount, strongboxShieldCount, strongboxServantCount, strongboxStoneCount;

    @FXML
    ImageView p0, p1, p2, p3, p4, p5, p6, p7, p8, p9, p10, p11, p12, p13, p14, p15, p16, p17, p18, p19, p20, p21, p22, p23, p24;

    public Gui gui;
    private LeaderCard l1, l2;
    private List<DevelopmentCard> prodCardsList;
    private List <LeaderCard> leaderCardsList;

    private boolean is1active, is2active, isLeaderAction, isDev1Selected, isDev2Selected, isDev3Selected, isL1Selected, isL2Selected;
    private int currentPosition;
    public List<Node> popeSpaces;


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
     * this method shows the active leader cards
     */
    public void showActiveLeaders(){
        if(is1active)
            leader1.setVisible(true);
        if(is2active)
            leader2.setVisible(true);
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
                    Map<LeaderCard,Boolean> userChoice = new HashMap<>();
                    userChoice.put(l1,true);
                    Message msg = new LeaderActionMessage(gui.getPlayerNickname(), userChoice, true);
                    gui.sendMessage(msg);

                });

                MenuItem discard = new MenuItem("Discard leader card");
                discard.setOnAction(event2 -> {
                    Map<LeaderCard,Boolean> userChoice = new HashMap<>();
                    userChoice.put(l1,false);
                    Message msg = new LeaderActionMessage(gui.getPlayerNickname(), userChoice, true);
                    leader1.setVisible(false);
                    gui.sendMessage(msg);
                });

                inactiveMenu1.getItems().addAll(activate, discard);

                inactiveMenu1.show(leader1, event.getSceneX(), event.getSceneY());
            } else {
                if(!isL1Selected) {
                    leader1.getParent().setStyle("-fx-border-width: 5; -fx-border-color: #515fdb");
                    leaderCardsList.add(l1);
                    isL1Selected = true;
                }
                else {
                    leader1.getParent().setStyle("-fx-border-width: 5; -fx-border-color: #51db51");
                    leaderCardsList.remove(l1);
                    isL1Selected = false;
                }
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

                    Map<LeaderCard,Boolean> userChoice = new HashMap<>();
                    userChoice.put(l2,true);
                    Message msg = new LeaderActionMessage(gui.getPlayerNickname(), userChoice, true);
                    gui.sendMessage(msg);

                });

                MenuItem discard = new MenuItem("Discard leader card");
                discard.setOnAction(event2 -> {
                    Map<LeaderCard,Boolean> userChoice = new HashMap<>();
                    userChoice.put(l2,false);
                    Message msg = new LeaderActionMessage(gui.getPlayerNickname(), userChoice, true);
                    leader2.setVisible(false);
                    gui.sendMessage(msg);
                });

                inactiveMenu2.getItems().addAll(activate, discard);

                inactiveMenu2.show(leader2, event.getSceneX(), event.getSceneY());
            } else {
                if(!isL2Selected) {
                    leader2.getParent().setStyle("-fx-border-width: 5; -fx-border-color: #1c3899");
                    leaderCardsList.add(l2);
                    isL1Selected = true;
                }
                else {
                    leader2.getParent().setStyle("-fx-border-width: 5; -fx-border-color: #51db51");
                    leaderCardsList.remove(l2);
                    isL1Selected = false;
                }
            }
        }
    }

    /**
     * this method removes the green border if the leader action was not accepted
     */
    public void leaderActivationResult(){

        if(!gui.getClientPlayer().getActiveLeaderCards().contains(l1)){
            is1active = false;
            leader1.getParent().setStyle("");
        }
        if(!gui.getClientPlayer().getActiveLeaderCards().contains(l2)){
            is2active = false;
            leader2.getParent().setStyle("");
        }
    }


    /**
     * this method updates the pope road
     * @param clientPlayerBoard the player's board
     */
    public void updatePopeRoad(ClientPlayerBoard clientPlayerBoard){
        int index = clientPlayerBoard.getPopeRoad().getCurrentPositionIndex();
        popeRoad.getChildren().get(currentPosition).setVisible(false);
        popeRoad.getChildren().get(index).setVisible(true);
        currentPosition = index;
    }


    /**
     * this method activates board production sending the message
     */
    @FXML
    public void activateBoardProduction(){
        Map<Resource, List<ResourceType>> map = new HashMap<>();
        List<ResourceType> toGive = new ArrayList<>();

        String url = res1.getImage().getUrl();
        ResourceType key;

        toGive.add(getUrlResource(url));

        url = res2.getImage().getUrl();

        toGive.add(getUrlResource(url));

        url = result.getImage().getUrl();

        key = getUrlResource(url);

        map.put(new Resource(key), toGive);

        Message msg = new ActivateBoardProductionMessage(gui.getPlayerNickname(), map, true);
        gui.sendMessage(msg);
    }

    /**
     * this method takes the corresponding resource from an url
     * @param url the url to take the substring from
     * @return the resourceType defined by the url
     */
    private ResourceType getUrlResource(String url){
        if(url.contains("coin"))
            return ResourceType.COIN;
        else if(url.contains("shield"))
            return ResourceType.SHIELD;
        else if(url.contains("servant"))
            return ResourceType.SERVANT;
        else
            return ResourceType.STONE;
    }

    /**
     * this method activates card production sending the corresponding message
     */
    @FXML
    public void activateCardsProduction (){
        if(prodCardsList.size()!=0) {
            Message msg = new ActivateCardProductionMessage(gui.getPlayerNickname(), prodCardsList, true);
            gui.sendMessage(msg);
            prodCardsList.clear();
            dev1.setStyle("");
            dev2.setStyle("");
            dev3.setStyle("");
            isDev1Selected = false;
            isDev2Selected = false;
            isDev3Selected = false;
        }
    }

    /**
     * this method activates leader production sending the corresponding message
     */
    @FXML
    public void activateLeaderProduction(){
        if(leaderCardsList.size()!=0) {
            Message msg = new ActivateLeaderProductionMessage(gui.getPlayerNickname(), leaderCardsList, false);
            gui.sendMessage(msg);
            leaderCardsList.clear();
            isL1Selected = false;
            isL2Selected = false;
            if(is1active){
                leader1.getParent().setStyle("-fx-border-width: 5; -fx-border-color: #51db51");
            }
            if(is2active){
                leader2.getParent().setStyle("-fx-border-width: 5; -fx-border-color: #51db51");
            }
        }
    }

    /**
     * this method changes the resources on the board's production panel
     * @param event mouse clicked
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

    /**
     * this method switches the images in the board production panel; default order is coin -> servant -> shield -> stone
     * @param view the ImageView clicked
     * @param url the Image contained in the ImageView
     */
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
     * @param clientPlayerBoard the player board to update
     */
    public void updateDevelopmentCards(ClientPlayerBoard clientPlayerBoard){

        for(int i=0; i<3; i++){
            if(clientPlayerBoard.getDevelopmentCards().get(i).size() != 0) {
                ImageView card = new ImageView(new Image("/gui/Images/DevelopmentCardsFront/" + clientPlayerBoard.getDevelopmentCard(i).getId() + ".png"));
                card.setId(clientPlayerBoard.getDevelopmentCard(i).getId());
                card.setFitWidth(110);
                card.setFitHeight(168);

                int finalI = i;
                card.setOnMouseClicked(event -> {
                    boolean bool = controlDevelopmentCard(card);

                    if(bool)
                        prodCardsList.add(clientPlayerBoard.getDevelopmentCard(finalI));
                    else
                        prodCardsList.remove(clientPlayerBoard.getDevelopmentCard(finalI));
                });

                if(i==0) {
                    dev1.setCenter(card);
                }
                else if(i==1) {
                    dev2.setCenter(card);
                }
                else{
                    dev3.setCenter(card);
                }

            }
        }
    }

    /**
     * this method adds a blue border if the development card has been selected for production, and removes it otherwise
     * @param card the card clicked
     * @return true if card is selected, false otherwise
     */
    public boolean controlDevelopmentCard(ImageView card){
        if(card.getParent().equals(dev1)){
            if(!isDev1Selected){
                isDev1Selected = true;
                dev1.setStyle("-fx-border-width: 5; -fx-border-color: #143595");
                return true;
            }
            else{
                isDev1Selected = false;
                dev1.setStyle("");
                return false;
            }
        }
        else if(card.getParent().equals(dev2)){
            if(!isDev2Selected){
                isDev2Selected = true;
                dev2.setStyle("-fx-border-width: 5; -fx-border-color: #143595");
                return true;
            }
            else{
                isDev2Selected = false;
                dev2.setStyle("");
                return false;
            }

        }
        else {
            if(!isDev3Selected){
                isDev3Selected = true;
                dev3.setStyle("-fx-border-width: 5; -fx-border-color: #143595");
                return true;
            }
            else{
                isDev3Selected = false;
                dev3.setStyle("");
                return false;
            }

        }
    }

    /**
     * this method updates the player's strongbox content
     * @param clientStrongbox the player strongbox to update
     */
    public void updateStrongBox(ClientStrongbox clientStrongbox){

        for(ResourceType resourceType : clientStrongbox.getAll().keySet()){
            if(resourceType.equals(ResourceType.COIN)){
                strongboxCoinCount.setText(String.valueOf(clientStrongbox.getAll().get(resourceType).size()));
            }
            else if(resourceType.equals(ResourceType.SHIELD)){
                strongboxShieldCount.setText(String.valueOf(clientStrongbox.getAll().get(resourceType).size()));
            }
            else if(resourceType.equals(ResourceType.SERVANT)){
                strongboxServantCount.setText(String.valueOf(clientStrongbox.getAll().get(resourceType).size()));
            }
            else if(resourceType.equals(ResourceType.STONE)){
                strongboxStoneCount.setText(String.valueOf(clientStrongbox.getAll().get(resourceType).size()));
            }
        }

        if(!clientStrongbox.getAll().keySet().contains(ResourceType.COIN))
            strongboxCoinCount.setText("0");
        if(!clientStrongbox.getAll().keySet().contains(ResourceType.SHIELD))
            strongboxShieldCount.setText("0");
        if(!clientStrongbox.getAll().keySet().contains(ResourceType.SERVANT))
            strongboxServantCount.setText("0");
        if(!clientStrongbox.getAll().keySet().contains(ResourceType.STONE))
            strongboxStoneCount.setText("0");

    }

    /**
     * this method updates the player's deposit content
     * @param deposit the player deposit
     */
    public void updateDeposit(ClientDeposit deposit){


        removeNodeByRowColumnIndex(0,0,floor1);
        removeNodeByRowColumnIndex(0,0,floor2);
        removeNodeByRowColumnIndex(0,1,floor2);
        removeNodeByRowColumnIndex(0,0,floor3);
        removeNodeByRowColumnIndex(0,1,floor3);
        removeNodeByRowColumnIndex(0,2,floor3);

        if(deposit.getNumberOfResourcesOnFloor(1)!=0) {
            ImageView res = new ImageView(new Image("/gui/Images/Resources/" + deposit.get(1).getType().label + ".png"));
            res.setFitHeight(30);
            res.setFitWidth(30);

            floor1.add(res, 0, 0);
        }
        else{
            floor1.add(new ImageView(), 0, 0);
        }

        ImageView res1 = new ImageView();
        ImageView res2 = new ImageView();

        if(deposit.getNumberOfResourcesOnFloor(2)!=0) {
            if(deposit.getNumberOfResourcesOnFloor(2)>=1) {
                res1.setImage(new Image("/gui/Images/Resources/" + deposit.get(2).getType().label + ".png"));
                res1.setFitHeight(30);
                res1.setFitWidth(30);

            }
            if (deposit.getNumberOfResourcesOnFloor(2) == 2) {
                res2.setImage(new Image("/gui/Images/Resources/" + deposit.get(2).getType().label + ".png"));
                res2.setFitHeight(30);
                res2.setFitWidth(30);
            }
        }
        floor2.add(res1, 0, 0);
        floor2.add(res2, 1, 0);

        ImageView res3 = new ImageView();
        ImageView res4 = new ImageView();
        ImageView res5 = new ImageView();

        if(deposit.getNumberOfResourcesOnFloor(3)!=0) {
            if(deposit.getNumberOfResourcesOnFloor(3)>=1) {
                res3.setImage(new Image("/gui/Images/Resources/" + deposit.get(3).getType().label + ".png"));
                res3.setFitHeight(30);
                res3.setFitWidth(30);

            }
            if(deposit.getNumberOfResourcesOnFloor(3)>=2) {
                res4.setImage(new Image("/gui/Images/Resources/" + deposit.get(3).getType().label + ".png"));
                res4.setFitHeight(30);
                res4.setFitWidth(30);

            }
            if(deposit.getNumberOfResourcesOnFloor(3)==3) {
                res5.setImage(new Image("/gui/Images/Resources/" + deposit.get(3).getType().label + ".png"));
                res5.setFitHeight(30);
                res5.setFitWidth(30);
            }
        }
        floor3.add(res3, 0, 0);
        floor3.add(res4, 1, 0);
        floor3.add(res5, 2, 0);

    }

    public void removeNodeByRowColumnIndex(final int row,final int column,GridPane gridPane) {

        ObservableList<Node> children = gridPane.getChildren();
        for(Node node : children) {
            if(node instanceof ImageView && GridPane.getRowIndex(node) == row && GridPane.getColumnIndex(node) == column) {
                ImageView imageView= (ImageView) node;
                gridPane.getChildren().remove(imageView);
                break;
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
        checkExtraDeposit(l1);
        l2=clientPlayer.getHand().get(1);
        leader2.setImage(new Image("gui/Images/LeaderCardsFront/" + l2.getId() + ".png"));
        checkExtraDeposit(l2);

        //setting popeSpace
       // popeSpaces = popeRoad.getChildren();
        if(clientPlayer.getPositionIndex() == 1 ) {
            p0.setVisible(false);
            p1.setVisible(true);
            currentPosition = 1;
           // popeSpaces.remove(0);
        }
        else{
            p0.setVisible(true);
            currentPosition = 0;
        }

        //setting deposit
        updateDeposit(clientPlayer.getDeposit());

    }

    /**
     * this method updates the whole player board
     * @param clientPlayerBoard the player to update
     */
    public void update(ClientPlayerBoard clientPlayerBoard){
        updateDeposit(clientPlayerBoard.getDeposit());
        updateStrongBox(clientPlayerBoard.getStrongbox());
        updateDevelopmentCards(clientPlayerBoard);
        updatePopeRoad(clientPlayerBoard);
        showActiveLeaders();
    }

    public void setGui(Gui gui){
        this.gui = gui;
    }

    /**
     * this method allows or denies click on production elements and shows or hides their buttons
     * @param bool true to allow, false to deny
     */
    public void setProductionClickable(Boolean bool){
        boardProdButton.setVisible(bool);
        cardProdButton.setVisible(bool);
        leaderProdButton.setVisible(bool);
    }

    /**
     * this method sets the messages to send when placing a development card
     * @param card card bought
     */
    public void setCardPlaceable(DevelopmentCard card){
        setPlacingVisible(true);

        place1.setOnAction(event -> {
            Message msg = new PlaceCardMessage(gui.getPlayerNickname(), card, 0);
            gui.sendMessage(msg);
        });
        place2.setOnAction(event -> {
            Message msg = new PlaceCardMessage(gui.getPlayerNickname(), card, 1);
            gui.sendMessage(msg);
        });
        place3.setOnAction(event -> {
            Message msg = new PlaceCardMessage(gui.getPlayerNickname(), card, 2);
            gui.sendMessage(msg);
        });

    }

    /**
     * this method shows/hides placing buttons
     * @param value true to show, false to hide
     */
    public void setPlacingVisible(Boolean value){
        place1.setVisible(value);
        place2.setVisible(value);
        place3.setVisible(value);
    }

    /**
     * this method sets the fact that a leader action is being played
     * @param bool
     */
    public void setLeaderAction(Boolean bool){
        isLeaderAction = bool;
    }

    /**
     * this method adds the extra deposit images to a leader card
     * @param leaderCard the leader card chosen
     */
    public void checkExtraDeposit(LeaderCard leaderCard){

        if(leaderCard.getLeaderType().equals(LeaderCardType.EXTRA_DEPOSIT)){
            String resource;
            switch (leaderCard.getId()) {
                case "l5":
                    resource = "stone";
                    break;
                case "l6":
                    resource = "servant";
                    break;
                case "l7":
                    resource = "shield";
                    break;
                default:
                    resource = "coin";
                    break;
            }

            if(leaderCard.equals(l1)){
                extraDeposit1.setVisible(true);
                dep1a.setImage(new Image("/gui/Images/Resources/" + resource + ".png"));
                dep1a.setVisible(false);
                dep1b.setImage(new Image("/gui/Images/Resources/" + resource + ".png"));
                dep1b.setVisible(false);
                extraDeposit1.toFront();
            }
            else {
                extraDeposit2.setVisible(true);
                dep2a.setImage(new Image("/gui/Images/Resources/" + resource + ".png"));
                dep2a.setVisible(false);
                dep2b.setImage(new Image("/gui/Images/Resources/" + resource + ".png"));
                dep2b.setVisible(false);
                extraDeposit2.toFront();
            }
        }
    }

}
