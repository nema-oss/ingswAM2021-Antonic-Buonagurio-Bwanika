package it.polimi.ingsw.view.client.gui;

import javafx.scene.Scene;
import javafx.stage.Stage;

public class Gui {

    private Stage primaryStage;
    private Scene startingScene;
    private Scene nicknameScene;

    public Gui(String ip, int port, Stage stage, Scene scene){
        //super(ip,port)
        this.primaryStage = stage;
        this.startingScene = scene;
        //inizializza prima scena

    }


    public void start(){}
}
