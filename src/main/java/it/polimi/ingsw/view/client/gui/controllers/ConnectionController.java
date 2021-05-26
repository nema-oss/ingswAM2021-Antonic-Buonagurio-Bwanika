package it.polimi.ingsw.view.client.gui.controllers;

import it.polimi.ingsw.view.client.gui.Gui;
import it.polimi.ingsw.view.client.gui.GuiManager;
import it.polimi.ingsw.view.client.utils.InputValidator;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;

public class ConnectionController{

    @FXML
    public TextField port;

    @FXML
    public TextField ip;

    @FXML
    AnchorPane anchorPane;

    private Gui gui;

    @FXML
    public void onConnection(ActionEvent event) throws IOException {
        String ipAddress = ip.getText();
        String portNumber = port.getText();


        if(ipAddress.isEmpty())
            ipAddress = "127.0.0.1";
        if(portNumber.isEmpty())
            portNumber = "1234";

        if( !InputValidator.validateIP(ipAddress))
            notifyInvalidIp();

        else if(!InputValidator.validatePORT(portNumber))
            notifyInvalidPort();

        else{
           /* GuiManager.executorService.submit(new Thread(() -> {
                Gui gui = new Gui(ip.getText(), Integer.parseInt(port.getText()), (Stage) anchorPane.getScene().getWindow(), anchorPane.getScene());
                gui.start();
            })); */
            GuiManager.changeScene("/gui/nickname");

        }
    }

    private void notifyInvalidIp(){
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setHeaderText("Wrong IP address. Please insert a valid IP.");
        alert.showAndWait();
    }

    private void notifyInvalidPort(){
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setHeaderText("Unavailable port. Please insert another port number.");
        alert.showAndWait();
    }

}
