package it.polimi.ingsw.view.client.gui.controllers;

import it.polimi.ingsw.model.cards.leadercards.LeaderCard;
import it.polimi.ingsw.view.client.gui.Gui;
import it.polimi.ingsw.view.client.gui.GuiManager;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class ChooseLeaderController implements Initializable {

   @FXML
   BorderPane mainPane;

   @FXML
   ImageView l1, l2, l3, l4;

   @FXML
   Button leadersOk;


    @FXML
    private void switchOnChooseResources() throws IOException {
        GuiManager.changeScene("/gui/chooseResources");
    }

    @FXML
    private void leaderClicked(MouseEvent event){

    }



    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        l1.setImage(new Image("gui/Images/LeaderCardsFront/Masters of Renaissance_Cards_FRONT_3mmBleed_1-49-1.png"));
        l2.setImage(new Image("gui/Images/LeaderCardsFront/Masters of Renaissance_Cards_FRONT_3mmBleed_1-50-1.png"));
        l3.setImage(new Image("gui/Images/LeaderCardsFront/Masters of Renaissance_Cards_FRONT_3mmBleed_1-51-1.png"));
        l4.setImage(new Image("gui/Images/LeaderCardsFront/Masters of Renaissance_Cards_FRONT_3mmBleed_1-52-1.png"));


        try {
            AnchorPane gameBoard = GuiManager.loadFXML("/gui/gameBoard").load();
            mainPane.setCenter(gameBoard);
            mainPane.getCenter().relocate(300,300);

        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public void hideFinalConfirmButton() {
    }

    public void setInstructionLabel(String infoMessage) {
    }

    public void setGui(Gui gui) {
    }

    public void initializeLeaderCards(List<LeaderCard> cardChoice) {
    }
}
