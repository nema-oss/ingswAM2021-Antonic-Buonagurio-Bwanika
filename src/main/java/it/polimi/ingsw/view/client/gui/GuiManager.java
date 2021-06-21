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
import java.util.Objects;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class GuiManager extends Application {

    public static ExecutorService executorService;

    public static Stage stage;
    public static GameController gameController;

    public static boolean isLocalMatch;


    @Override
    public void start(Stage stage) throws IOException {

        GuiManager.stage = stage;
        stage.setTitle("Masters of Renaissance");
        executorService = Executors.newCachedThreadPool();
        Parent root;
        if(isLocalMatch) {
            root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/gui/localMatch.fxml")));
        }
        else
            root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/gui/connection.fxml")));
        Scene scene = new Scene(root,1600,900);
        stage.setScene(scene);
        stage.show();
    }

    /**
     * this method creates the loader of a given fxml
     * @param fxml the file to load
     * @return the loader created
     * @throws IOException if unable to open the file
     */
    public static FXMLLoader loadFXML(String fxml) throws IOException {
        return new FXMLLoader(GuiManager.class.getResource( fxml + ".fxml"));
    }


    /**
     * this method starts the gui
     */
    public static void startGui() {
        launch();
    }

}
