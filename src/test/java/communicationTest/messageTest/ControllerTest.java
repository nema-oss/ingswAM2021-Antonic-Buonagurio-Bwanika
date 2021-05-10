package communicationTest.messageTest;

import it.polimi.ingsw.controller.Error;
import it.polimi.ingsw.controller.GamePhase;
import it.polimi.ingsw.controller.MatchController;
import it.polimi.ingsw.model.Game;
import it.polimi.ingsw.model.player.Player;
import it.polimi.ingsw.view.server.InGameDisconnectionHandler;
import it.polimi.ingsw.view.server.VirtualView;
import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.BeforeEach;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class ControllerTest {

    private MatchController controller;
    private Game game;
    private Player p,p2;

    @BeforeEach
    void setUp(){
        controller = new MatchController();
        game = controller.getGame();
        p = new Player("Pippo", game.getGameBoard(), game);
        p2 = new Player("Pluto", game.getGameBoard(), game);
    }

    @Test
    void setVirtualViewTest(){
        controller.setVirtualView(new VirtualView(controller, 1, new InGameDisconnectionHandler()));
    }

    @Test
    void onControllerNewPlayerTest(){

        List<Error> error;

        error = controller.onNewPlayer(p.getNickname());
        assertTrue(game.getListOfPlayers().contains(p));
        assertTrue(error.isEmpty());

        error = controller.onNewPlayer(p2.getNickname());
        assertEquals(game.getListOfPlayers().size(), 2);
        assertTrue(game.getListOfPlayers().contains(p2));
        assertTrue(error.isEmpty());


        error = controller.onNewPlayer(p.getNickname());
        assertTrue(error.contains(Error.NICKNAME_ALREADY_EXISTS));

        try {
            controller.onNewPlayer("");
        }catch(IllegalArgumentException e){
            System.out.println("nickname not valid");
        }

        assertEquals(game.getGamePhase(), GamePhase.LOGIN);

        game.setGamePhase(GamePhase.PLAY_TURN);
        Player p3 = new Player("Topolino", game.getGameBoard(), game);
        controller.onNewPlayer(p3.getNickname());


    }

    @Test
    void onStartGameTest(){

        controller.onNewPlayer(p.getNickname());
        controller.onNewPlayer(p2.getNickname());

        controller.onStartGame();
        assertEquals(game.getGamePhase(), GamePhase.CHOOSE_LEADERS);

    }

    @Test
    void sendChooseLeaderCardsTest(){}

    @Test
    void onLeaderCardsChosenTest(){}

    @Test
    void sendChooseResourcesTest(){}

    @Test
    void onResourcesChosenTest(){}

    @Test
    void onActivateProductionTest(){}

    @Test
    void onActivateDevelopmentProductionTest(){}

    @Test
    void onActivateLeaderProductionTest(){}

    @Test
    void onActivateBoardTest(){}

    @Test
    void onEndProductionTest(){}

    @Test
    void onBuyDevelopmentCardsTest(){}

    @Test
    void onBuyResourcesTest(){}

    @Test
    void onActivateLeaderTest(){}

    @Test
    void onDiscardLeaderTest(){}

    @Test
    void EndTurnTest(){}

    @Test
    void onMoveDepositTest(){}

    @Test
    void onDiscardResourceTest(){}

    @Test
    void controlEndOfGameTest(){}

    @Test
    void nextTurnTest(){}

    @Test
    void controlTurnTest(){}

    @Test
    void onControlStandardActionTest(){}

    @Test
    void onCOntrolLeaderActionTest(){}

    @Test
    void onPlayerDisconnectionTest(){}

    @Test
    void sendPlayTurnTest(){}

    @Test
    void sendeEndTurnTest(){}


}

