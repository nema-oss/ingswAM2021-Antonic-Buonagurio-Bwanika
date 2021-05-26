package it.polimi.ingsw.view.client.gui.controllers;

import it.polimi.ingsw.model.gameboard.ResourceType;
import it.polimi.ingsw.view.client.gui.Gui;
import it.polimi.ingsw.view.client.gui.GuiManager;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Group;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class ChooseResourcesController implements Initializable {

    private ArrayList<String> resources = new ArrayList<>();
    private String lastR;

    @FXML
    private ImageView resource;

    @FXML
    private BorderPane gb;

    @FXML
    private Label title;


    @FXML
    private void switchOnGame() throws IOException {
        lastR = resources.get(0);
        GuiManager.changeScene("/gui/game");
    }

    @FXML
    private void goldChosen(){
        resources.add("gold");
    }

    @FXML
    private void servantChosen(){
        resources.add("servant");
    }

    @FXML
    private void shieldChosen(){
        resources.add("shield");
    }

    @FXML
    private void stoneChosen(){
        resources.add("stone");
    }

    private String getLastR(){
        return lastR;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        Group group = new Group();
        resources = new ArrayList<>();

        gb.setCenter(GameBoardController.getGameBoard());

    }

    public void setGui(Gui gui) {
    }

    public void initializeResourceTypes(List<ResourceType> resourceTypes) {
    }
}
