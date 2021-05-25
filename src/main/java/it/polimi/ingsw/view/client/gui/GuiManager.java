package it.polimi.ingsw.view.client.gui;

import it.polimi.ingsw.model.Game;
import it.polimi.ingsw.view.client.gui.controllers.GameController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.concurrent.ExecutorService;

public class GuiManager extends Application {

    public static ExecutorService executorService;

    public static Stage stage;
    public static GameController gameController;

    @Override
    public void start(Stage stage) throws IOException {

        this.stage = stage;
        Parent root = FXMLLoader.load(getClass().getResource("/gui/connection.fxml"));
        Scene scene = new Scene(root,1600,900);
        stage.setScene(scene);
        stage.show();

    }


    public static FXMLLoader loadFXML(String fxml) throws IOException {
        return new FXMLLoader(GuiManager.class.getResource( fxml + ".fxml")); //va messa la cartella giusta
    }

    public static void changeScene(String fxml) throws IOException {

        Parent root = loadFXML(fxml).load();
        Scene scene = new Scene(root, 1600, 900);
        stage.setScene(scene);
    }

    public static void changeGameScene(String fxml) throws IOException {

        gameController.leftBorder.setCenter(loadFXML(fxml).load());

    }


    public static void main(String[] args) {
        launch();
    }
}
