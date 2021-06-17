package it.polimi.ingsw;

import it.polimi.ingsw.network.server.EchoServer;
import it.polimi.ingsw.view.client.Cli;
import it.polimi.ingsw.view.client.gui.Gui;
import it.polimi.ingsw.view.client.gui.GuiManager;
import javafx.stage.Stage;

/**
 * Starting point of the game
 */
public class Main {

    public static void main(String[] args) {

        if (args[0].equals("-client")) {
            clientMode(args);
        }
        if (args[0].equals("-server")) {
            serverMode(args);
        }

    }

    /**
     * Manage the client starting
     * @param args cmd line args
     */
    private static void clientMode(String[] args) {
        if(args.length == 2 && args[1].equals("-cli"))
            new Cli().gameSetup();
        else if(args.length == 3 && args[1].equals("-local") && args[2].equals("-cli"))
            new Cli().gameSetupLocalMatch();
        else if(args.length == 2 && args[1].equals("-gui")){
            GuiManager.isLocalMatch = false;
            System.out.println("Diooo");
            GuiManager.startGui();
        }
        else if(args.length == 3 && args[1].equals("-local") && args[2].equals("-gui")) {
            GuiManager.isLocalMatch = true;
            GuiManager.startGui();
        }

    }

    /**
     * Manage the server starting
     * @param args cmd line arguments
     */
    private static void serverMode(String[] args){

        int port;
        if(args.length == 2)
            port = Integer.parseInt(args[1]);
        else
            port = 1234;

        EchoServer echoServer = new EchoServer(port);
        echoServer.start();
    }


}
