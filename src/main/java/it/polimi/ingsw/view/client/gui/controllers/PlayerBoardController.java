package it.polimi.ingsw.view.client.gui.controllers;

import it.polimi.ingsw.messages.Message;
import it.polimi.ingsw.messages.actions.*;
import it.polimi.ingsw.model.cards.DevelopmentCard;
import it.polimi.ingsw.model.cards.leadercards.AuxiliaryDeposit;
import it.polimi.ingsw.model.cards.leadercards.LeaderCard;
import it.polimi.ingsw.model.cards.leadercards.LeaderCardType;
import it.polimi.ingsw.model.gameboard.*;
import it.polimi.ingsw.view.client.gui.Gui;
import it.polimi.ingsw.view.client.viewComponents.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;

import java.util.*;

/**
 * this class is the controller for the "playerBoard.fxml" file
 * @author chiara
 */
public class PlayerBoardController {

    @FXML
    ImageView leader1, leader2, res1, res2, result, dep1a, dep1b, dep2a, dep2b, firstVaticanSection, secondVaticanSection, thirdVaticanSection;

    @FXML
    Button boardProdButton, cardProdButton, leaderProdButton, place1, place2, place3;

    @FXML
    GridPane floor1, floor2, floor3, popeRoad;

    @FXML
    AnchorPane strongbox, pBoard, extraDeposit1, extraDeposit2;

    @FXML
    BorderPane dev11, dev12, dev13, dev21, dev22, dev23, dev31, dev32, dev33;

    BorderPane firstDevCard, secondDevCard, thirdDevCard;

    @FXML
    Label strongboxCoinCount, strongboxShieldCount, strongboxServantCount, strongboxStoneCount;

    @FXML
    ImageView p0, p1, p2, p3, p4, p5, p6, p7, p8, p9, p10, p11, p12, p13, p14, p15, p16, p17, p18, p19, p20, p21, p22, p23, p24;

    @FXML
    ComboBox<String> resourceChoiceBox1, resourceChoiceBox2;

    public Gui gui;
    private LeaderCard l1, l2;
    private List<DevelopmentCard> prodCardsList;
    private Map<LeaderCard, ResourceType> leaderProductionChoice;
    private String extraDeposit1Type, extraDeposit2Type;
    private boolean is1active, is2active, isLeaderAction, isDev1Selected, isDev2Selected, isDev3Selected, isL1Selected, isL2Selected;
    private int currentPosition;


    /**
     * this method hides the inactive leader cards
     */
    public void hideInactiveLeaders() {
        if (!is1active)
            leader1.setVisible(false);
        if (!is2active)
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
     * this method shows tha actions which can be performed on the first leader
     * @param event the mouse click
     */
    @FXML
    private void actionsOnLeader1(MouseEvent event) {

        if (isLeaderAction) {
            if (!is1active) {
                createCardMenu(leader1, l1, event);

            } else if(leaderProdButton.isVisible()){
                if(!isL1Selected) {
                    leader1.getParent().setStyle("-fx-border-width: 5; -fx-border-color: #515fdb");
                    resourceChoiceBox1.setVisible(true);
                    isL1Selected = true;
                }
                else {
                    leader1.getParent().setStyle("-fx-border-width: 5; -fx-border-color: #51db51");
                    leaderProductionChoice.remove(l1);
                    isL1Selected = false;
                }
            }
        }
    }

    /**
     * this method shows tha actions which can be performed on the second leader
     * @param event the mouse click
     */
    @FXML
    private void actionsOnLeader2(MouseEvent event){

        if(isLeaderAction) {
            if (!is2active) {
                createCardMenu(leader2, l2, event);

            } else if(leaderProdButton.isVisible()) {
                if(!isL2Selected) {
                    leader2.getParent().setStyle("-fx-border-width: 5; -fx-border-color: #1c3899");

                    resourceChoiceBox2.setVisible(true);
                    isL2Selected = true;
                } else {
                    leader2.getParent().setStyle("-fx-border-width: 5; -fx-border-color: #51db51");
                    leaderProductionChoice.remove(l2);
                    isL2Selected = false;
                }
            }
        }
    }

    /**
     * this method creates menus for leader cards
     * @param leader the imageView to associate the menu to
     * @param l the leader card corresponding to the image view
     * @param event mouse click
     */
    private void createCardMenu( ImageView leader, LeaderCard l, MouseEvent event){
        ContextMenu inactiveMenu = new ContextMenu();

        MenuItem activate = new MenuItem("Activate leader card");
        activate.setOnAction(event1 -> {
            if (l.equals(l1))
                is1active = true;
            else if (l.equals(l2))
                is2active = true;
            leader.getParent().setStyle("-fx-border-width: 5; -fx-border-color: #51db51");

            Map<LeaderCard, Boolean> userChoice = new HashMap<>();
            userChoice.put(l, true);
            Message msg = new LeaderActionMessage(gui.getPlayerNickname(), userChoice, false);
            gui.sendMessage(msg);

        });
        inactiveMenu.getItems().add(activate);


        MenuItem discard = new MenuItem("Discard leader card");
        discard.setOnAction(event2 -> {
            Map<LeaderCard,Boolean> userChoice = new HashMap<>();
            userChoice.put(l,false);
            Message msg = new LeaderActionMessage(gui.getPlayerNickname(), userChoice, false);
            leader.setVisible(false);
            leader.getParent().setStyle("");
            if(l.equals(l1))
                is1active = false;
            else if (l.equals(l2))
                is2active = false;
            gui.sendMessage(msg);
        });

        inactiveMenu.getItems().add(discard);
        inactiveMenu.show(leader, event.getSceneX(), event.getSceneY());
    }

    /**
     * this method prepares the combobox containing the resources to choose when activating production on a leader card
     * @param resourceChoiceBox the combo box to set up
     */
    public void setUpProductionMenu(ComboBox<String> resourceChoiceBox){

        ObservableList<String> list = FXCollections.observableArrayList("coin","shield","servant","stone");
        resourceChoiceBox.setItems(list);
    }

    /**
     * this method converts text ina  resource type
     * @param text the text to convert
     * @return the corresponding resource type
     */
    public ResourceType convertIntoResourceType(String text){
        if(text.equals(ResourceType.COIN.label))
            return ResourceType.COIN;
        if(text.equals(ResourceType.SHIELD.label))
            return ResourceType.SHIELD;
        if(text.equals(ResourceType.SERVANT.label))
            return ResourceType.SERVANT;
        return ResourceType.STONE;

    }
    /**
     * this method removes the green border if the leader action was not accepted
     */
    public void leaderActivationResult(){

        if(!gui.getClientPlayer().isLeaderCardActive(l1)){
            is1active = false;
            leader1.getParent().setStyle("");
        }
        if(!gui.getClientPlayer().isLeaderCardActive(l2)){
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

        Message msg = new ActivateBoardProductionMessage(gui.getPlayerNickname(), map, false);
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
            Message msg = new ActivateCardProductionMessage(gui.getPlayerNickname(), prodCardsList, false);
            gui.sendMessage(msg);
            prodCardsList.clear();
            dev11.setStyle("");
            dev12.setStyle("");
            dev13.setStyle("");
            dev21.setStyle("");
            dev22.setStyle("");
            dev23.setStyle("");
            dev31.setStyle("");
            dev32.setStyle("");
            dev33.setStyle("");
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

        if(isL1Selected){
            leaderProductionChoice.put(l1, convertIntoResourceType(resourceChoiceBox1.getSelectionModel().getSelectedItem()));
        }

        if (isL2Selected) {
            leaderProductionChoice.put(l2, convertIntoResourceType(resourceChoiceBox2.getSelectionModel().getSelectedItem()));
        }

        if(leaderProductionChoice.size()!=0) {

            Message msg = new ActivateLeaderProductionMessage(gui.getPlayerNickname(), leaderProductionChoice, false);
            gui.sendMessage(msg);
            leaderProductionChoice.clear();
            resourceChoiceBox1.setVisible(false);
            resourceChoiceBox2.setVisible(false);
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

                int level = clientPlayerBoard.getDevelopmentCard(i).getLevel();
                if(level == 1) {
                    if (i == 0) {
                        dev11.setCenter(card);
                        dev11.toFront();
                        firstDevCard = dev11;
                        dev21.setVisible(false);
                        dev31.setVisible(false);
                    } else if (i == 1) {
                        dev12.setCenter(card);
                        dev12.toFront();
                        secondDevCard = dev12;
                        dev22.setVisible(false);
                        dev32.setVisible(false);
                    } else {
                        dev13.setCenter(card);
                        dev13.toFront();
                        thirdDevCard = dev13;
                        dev23.setVisible(false);
                        dev33.setVisible(false);
                    }
                }
                else if(level == 2) {
                    if (i == 0) {
                        dev21.setCenter(card);
                        dev21.toFront();
                        firstDevCard = dev21;
                        dev21.setVisible(true);
                        dev11.setDisable(true);
                    } else if (i == 1) {
                        dev22.setCenter(card);
                        dev22.toFront();
                        secondDevCard = dev22;
                        dev22.setVisible(true);
                        dev12.setDisable(true);
                    } else {
                        dev23.setCenter(card);
                        dev23.toFront();
                        thirdDevCard = dev23;
                        dev23.setVisible(true);
                        dev13.setDisable(true);
                    }
                }
                else if(level == 3) {
                    if (i == 0) {
                        dev31.setCenter(card);
                        dev31.toFront();
                        firstDevCard = dev31;
                        dev31.setVisible(true);
                        dev21.setDisable(true);
                    } else if (i == 1) {
                        dev32.setCenter(card);
                        dev32.toFront();
                        secondDevCard = dev32;
                        dev32.setVisible(true);
                        dev22.setDisable(true);
                    } else {
                        dev33.setCenter(card);
                        dev33.toFront();
                        thirdDevCard = dev33;
                        dev33.setVisible(true);
                        dev23.setDisable(true);
                    }
                }

            }
        }
    }

    /**
     * this method adds a blue border if the development card has been selected for production, and removes it otherwise
     * @param card the card clicked
     * @return true if card is selected, false otherwise
     */
    public boolean controlDevelopmentCard(ImageView card) {

        if (cardProdButton.isVisible()) {
            if (card.getParent().equals(firstDevCard)) {
                if (!isDev1Selected) {
                    isDev1Selected = true;
                    firstDevCard.setStyle("-fx-border-width: 5; -fx-border-color: #143595");
                    return true;
                } else {
                    isDev1Selected = false;
                    firstDevCard.setStyle("");
                    return false;
                }
            } else if (card.getParent().equals(secondDevCard)) {
                if (!isDev2Selected) {
                    isDev2Selected = true;
                    secondDevCard.setStyle("-fx-border-width: 5; -fx-border-color: #143595");
                    return true;
                } else {
                    isDev2Selected = false;
                    secondDevCard.setStyle("");
                    return false;
                }

            } else {
                if (!isDev3Selected) {
                    isDev3Selected = true;
                    thirdDevCard.setStyle("-fx-border-width: 5; -fx-border-color: #143595");
                    return true;
                } else {
                    isDev3Selected = false;
                    thirdDevCard.setStyle("");
                    return false;
                }

            }
        }
        return false;
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

        if(!clientStrongbox.getAll().containsKey(ResourceType.COIN))
            strongboxCoinCount.setText("0");
        if(!clientStrongbox.getAll().containsKey(ResourceType.SHIELD))
            strongboxShieldCount.setText("0");
        if(!clientStrongbox.getAll().containsKey(ResourceType.SERVANT))
            strongboxServantCount.setText("0");
        if(!clientStrongbox.getAll().containsKey(ResourceType.STONE))
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

    /**
     * this method removes an object from a gridPane
     * @param row the grid's row
     * @param column the grid's column
     * @param gridPane the grid pane
     */
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
        leaderProductionChoice = new HashMap<>();

        setUpProductionMenu(resourceChoiceBox1);
        setUpProductionMenu(resourceChoiceBox2);

        l1 = clientPlayer.getHand().get(0);
        leader1.setImage(new Image("gui/Images/LeaderCardsFront/" + l1.getId() + ".png"));
        initializeExtraDeposit(l1);
        l2 = clientPlayer.getHand().get(1);
        leader2.setImage(new Image("gui/Images/LeaderCardsFront/" + l2.getId() + ".png"));
        initializeExtraDeposit(l2);

        if(clientPlayer.getPositionIndex() == 1 ) {
            p0.setVisible(false);
            p1.setVisible(true);
            currentPosition = 1;
        }
        else{
            p0.setVisible(true);
            currentPosition = 0;
        }

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

        if(clientPlayerBoard.getPlayerAuxiliaryDeposits()!=null)
            updateExtraDeposits(clientPlayerBoard.getPlayerAuxiliaryDeposits());

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
     * this method allows or denies to click on leader cards
     * @param bool true to allow, false to deny
     */
    public void setLeadersClickable(Boolean bool){
        leader1.setDisable(!bool);
        leader2.setDisable(!bool);
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
     * @param bool true or false
     */
    public void setLeaderAction(Boolean bool){
        isLeaderAction = bool;
    }

    /**
     * this method adds the extra deposit images to a leader card
     * @param leaderCard the leader card chosen
     */
    public void initializeExtraDeposit(LeaderCard leaderCard){

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
                extraDeposit1Type = resource;
                dep1a.setImage(new Image("/gui/Images/Resources/" + resource + ".png"));
                dep1a.setVisible(false);
                dep1b.setImage(new Image("/gui/Images/Resources/" + resource + ".png"));
                dep1b.setVisible(false);
                extraDeposit1.toFront();
            }
            else {
                extraDeposit2.setVisible(true);
                extraDeposit2Type = resource;
                dep2a.setImage(new Image("/gui/Images/Resources/" + resource + ".png"));
                dep2a.setVisible(false);
                dep2b.setImage(new Image("/gui/Images/Resources/" + resource + ".png"));
                dep2b.setVisible(false);
                extraDeposit2.toFront();
            }
        }
    }

    /**
     * this method updates the extra deposits
     */
    public void updateExtraDeposits(List<AuxiliaryDeposit> auxiliaryDeposits){

        if(!auxiliaryDeposits.isEmpty())
            for(AuxiliaryDeposit auxiliaryDeposit : auxiliaryDeposits) {
                if(auxiliaryDeposit.getType().label.equals(extraDeposit1Type))
                    showExtraDeposit(extraDeposit1, auxiliaryDeposit.getSize());
                if(auxiliaryDeposit.getType().label.equals(extraDeposit2Type))
                    showExtraDeposit(extraDeposit2, auxiliaryDeposit.getSize());
            }
    }

    /**
     * this method shows an extra deposit if it is not empty
     * @param extraDeposit deposit to show
     * @param amount number of resources present in the deposit
     */
    public void showExtraDeposit(AnchorPane extraDeposit, int amount){

        if(extraDeposit.equals(extraDeposit1)) {
            if (amount >= 1)
                dep1a.setVisible(true);
            if (amount == 2)
                dep1b.setVisible(true);
        }
        else if(extraDeposit.equals(extraDeposit2)){
            if(amount >= 1)
                dep2a.setVisible(true);
            if(amount == 2)
                dep2b.setVisible(true);
        }
    }

    /**
     * this method adds a leader card to a player tab different from the client's one
     * @param card the leader card to add
     */
    public void addLeaderOnOtherClient(LeaderCard card) {

        if(l1 == null) {
            l1 = card;
            is1active = true;
            leader1.setImage(new Image("gui/Images/LeaderCardsFront/" + l1.getId() + ".png"));
            initializeExtraDeposit(l1);
        }else{
            l2 = card;
            is2active = true;
            leader2.setImage(new Image("gui/Images/LeaderCardsFront/" + l2.getId() + ".png"));
            initializeExtraDeposit(l2);
        }
    }

    /**
     * this method discards the vatican report tokens
     * @param vaticanReportNumber the number of the report(1,2 or 3)
     */
    public void hideVaticanReport(int vaticanReportNumber){
        if(vaticanReportNumber==1){
            firstVaticanSection.setVisible(false);
        }
        else if(vaticanReportNumber==2){
            secondVaticanSection.setVisible(false);
        }
        else if(vaticanReportNumber==3){
            thirdVaticanSection.setVisible(false);
        }
    }


}
