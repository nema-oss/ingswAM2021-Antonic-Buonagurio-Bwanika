package it.polimi.ingsw.view.client.gui.controllers;

import it.polimi.ingsw.view.client.gui.GuiManager;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;

public class ConnectionController{

    @FXML
    public TextField port;

    @FXML
    public TextField ip;

    @FXML
    AnchorPane anchorPane;

    @FXML
    public void onConnection(ActionEvent event) throws IOException {
        String ipAddress = ip.getText();
        String portNumber = port.getText();

        //controllo validità con input validator e faccio notifyInvalidPort() e notifyInvalidIp()


        //altrimenti creo un nuovo thread con una nuova gui e la faccio partire.
     /*   GuiManager.executorService.submit(new Thread(() -> {
            Gui gui = new Gui(ip.getText(), Integer.parseInt(port.getText()), (Stage) anchorPane.getScene().getWindow(), anchorPane.getScene());
           gui.start();
        }));
        */

        GuiManager.changeScene("/gui/nickname");


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
