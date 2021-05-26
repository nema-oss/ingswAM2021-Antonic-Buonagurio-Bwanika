package it.polimi.ingsw.view.client.gui.controllers;

import it.polimi.ingsw.model.cards.DevelopmentCard;
import it.polimi.ingsw.model.cards.leadercards.LeaderCard;
import it.polimi.ingsw.view.client.gui.Gui;
import it.polimi.ingsw.view.client.utils.TurnActions;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.layout.BorderPane;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class GameController {

    @FXML
    BorderPane gameBoardPane, myPlayerPane, otherPlayersPane;


    public void setGui(Gui gui) {
    }

    public void showActionButtons() {
    }

    public void showYourTurnMessage() {
    }

    public void hideActionButtons() {
    }

    public void showOtherTurnMessage(String currentPlayer) {
    }

    public TurnActions getTurnAction() {
        return null;
    }

    public void setInstructionLabel(String s) {
    }

    public void setLeaderCardHand(List<LeaderCard> hand) {
    }

    public void showResourceMarket() {
    }

    public void showCardMarket() {
    }

    public void setProductionDevelopmentCard(List<DevelopmentCard> developmentCards) {
    }

    public void setProductionLeaderCard(List<LeaderCard> leaderCards) {
    }

    public void showPlaceResourcesButton() {
    }
}
