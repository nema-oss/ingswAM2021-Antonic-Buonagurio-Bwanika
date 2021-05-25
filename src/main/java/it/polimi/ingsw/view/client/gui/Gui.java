package it.polimi.ingsw.view.client.gui;

import it.polimi.ingsw.messages.setup.server.DoLoginMessage;
import it.polimi.ingsw.model.cards.DevelopmentCard;
import it.polimi.ingsw.model.cards.leadercards.LeaderCard;
import it.polimi.ingsw.model.gameboard.CardMarket;
import it.polimi.ingsw.model.gameboard.Resource;
import it.polimi.ingsw.model.player.Board;
import it.polimi.ingsw.view.client.View;
import it.polimi.ingsw.view.client.viewComponents.ClientGameBoard;
import it.polimi.ingsw.view.client.viewComponents.ClientPlayer;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.List;
import java.util.Map;

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
