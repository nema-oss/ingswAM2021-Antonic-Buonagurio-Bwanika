package it.polimi.ingsw.view.client.gui.controllers;

import it.polimi.ingsw.view.client.gui.Gui;
import it.polimi.ingsw.view.client.gui.GuiManager;
import javafx.fxml.FXML;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class LocalMatchController {

    @FXML
    AnchorPane anchorPane;

    private Gui gui;

    /**
     * Start the gui
     * */
    @FXML
    public void onStartLocalMatch() {

        GuiManager.executorService.execute(new Thread(() -> {
            gui = new Gui((Stage) anchorPane.getScene().getWindow(), anchorPane.getScene(), true);
            gui.startLocalMatch();
        }));
    }

}
