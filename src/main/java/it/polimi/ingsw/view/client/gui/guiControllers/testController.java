package it.polimi.ingsw.view.client.gui.guiControllers;

import it.polimi.ingsw.view.client.gui.Gui;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;

public class testController {

    @FXML
    private Button clicktest;

    public Gui gui;

    @FXML
    private void createGui(ActionEvent event){

        gui = new Gui();

    }
}
