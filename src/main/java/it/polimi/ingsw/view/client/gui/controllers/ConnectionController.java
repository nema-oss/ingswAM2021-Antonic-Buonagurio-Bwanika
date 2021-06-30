package it.polimi.ingsw.view.client.gui.controllers;

import it.polimi.ingsw.view.client.gui.Gui;
import it.polimi.ingsw.view.client.gui.GuiManager;
import it.polimi.ingsw.view.client.utils.InputValidator;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

/**
 * this class is the controller for the "connection.fxml" file
 * @author chiara
 */
public class ConnectionController{

    @FXML
    public TextField port;

    @FXML
    public TextField ip;

    @FXML
    AnchorPane anchorPane;

    private Gui gui;

    /**
     * this method starts the gui after reading Ip and port
     */
    @FXML
    public void onConnection() {
        String ipAddress = ip.getText();
        String portNumber = port.getText();

        if( !InputValidator.validateIP(ipAddress))
            notifyInvalidIp();

        else if(!InputValidator.validatePORT(portNumber))
            notifyInvalidPort();

        else{
           GuiManager.executorService.execute(new Thread(() -> {

                gui = new Gui(ip.getText(), Integer.parseInt(port.getText()), (Stage) anchorPane.getScene().getWindow(), anchorPane.getScene());
                gui.start();
            }));

        }
    }

    /**
     * this method alerts that the ip inserted is not in a valid format
     */
    private void notifyInvalidIp(){
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setHeaderText("Wrong IP address. Please insert a valid IP.");
        alert.showAndWait();
    }

    /**
     * this method alerts that the port inserted is not in a valid format
     */
    private void notifyInvalidPort(){
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setHeaderText("Unavailable port. Please insert another port number.");
        alert.showAndWait();
    }

}
