package communicationTest.messageTest;

import it.polimi.ingsw.controller.Error;
import it.polimi.ingsw.controller.GamePhase;
import it.polimi.ingsw.controller.MatchController;
import it.polimi.ingsw.model.Game;
import it.polimi.ingsw.model.cards.DevelopmentCard;
import it.polimi.ingsw.model.cards.leadercards.LeaderCard;
import it.polimi.ingsw.model.exception.NonExistentCardException;
import it.polimi.ingsw.model.gameboard.ResourceType;
import it.polimi.ingsw.model.player.Player;
import it.polimi.ingsw.view.server.InGameDisconnectionHandler;
import it.polimi.ingsw.view.server.VirtualView;
import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.BeforeEach;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
    public void setVirtualViewTest(){
        controller.setVirtualView(new VirtualView(controller, 1, new InGameDisconnectionHandler()));
    }

    @Test
    public void onControllerNewPlayerTest(){

        List<Error> error;

        game.setGamePhase(GamePhase.LOGIN);
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
    public void onStartGameTest(){

        game.setGamePhase(GamePhase.LOGIN);
        controller.onNewPlayer(p.getNickname());
        controller.onNewPlayer(p2.getNickname());

        List<Error> errors = controller.onStartGame();
        assertEquals(game.getGamePhase(), GamePhase.CHOOSE_LEADERS);
        assertTrue(errors.isEmpty());

        game.setGamePhase(GamePhase.PLAY_TURN);
        errors = controller.onStartGame();
        assertFalse(errors.isEmpty());
    }


    @Test
    public void onLeaderCardsChosenTest(){

        game.setGamePhase(GamePhase.LOGIN);
        controller.onNewPlayer(p.getNickname());
        controller.onNewPlayer(p2.getNickname());

        List<LeaderCard> leaderCardsChosen = new ArrayList<>();
        leaderCardsChosen.add(game.getLeaderDeck().drawCard());

        game.setGamePhase(GamePhase.CHOOSE_LEADERS);

        List<Error> errors = controller.onLeaderCardsChosen(game.getCurrentPlayer().getNickname(), null);
        assertTrue(errors.contains(Error.INVALID_ACTION));

        errors = controller.onLeaderCardsChosen(game.getCurrentPlayer().getNickname(), leaderCardsChosen );
        assertTrue(errors.contains(Error.INVALID_ACTION));

        leaderCardsChosen.add(game.getLeaderDeck().drawCard());
        errors = controller.onLeaderCardsChosen(game.getCurrentPlayer().getNickname(), leaderCardsChosen);
        assertTrue(errors.isEmpty());
        assertTrue(game.getCurrentPlayer().getHand().containsAll(leaderCardsChosen));

        game.setGamePhase(GamePhase.LOGIN);
        errors = controller.onLeaderCardsChosen(game.getCurrentPlayer().getNickname(), leaderCardsChosen);
        assertTrue(errors.contains(Error.WRONG_GAME_PHASE));
    }

    @Test
    public void onResourcesChosenTest(){

        game.setGamePhase(GamePhase.LOGIN);
        controller.onNewPlayer(p.getNickname());
        controller.onNewPlayer(p2.getNickname());

        game.setGamePhase(GamePhase.CHOOSE_RESOURCES);

        Map<ResourceType, Integer> resourcesChosen = new HashMap<>();
        resourcesChosen.put(ResourceType.SERVANT,1);
        resourcesChosen.put(ResourceType.COIN, 2);

        List<Error> errors = controller.onResourcesChosen(game.getCurrentPlayer().getNickname(), resourcesChosen);
        assertTrue(errors.isEmpty());

        game.setGamePhase(GamePhase.LOGIN);
        errors = controller.onResourcesChosen(game.getCurrentPlayer().getNickname(), resourcesChosen);
        assertTrue(errors.contains(Error.WRONG_GAME_PHASE));
    }

    @Test
    public void onActivateProductionTest(){

        game.setGamePhase(GamePhase.LOGIN);
        controller.onNewPlayer(p.getNickname());
        controller.onNewPlayer(p2.getNickname());

        List<Error> errors = controller.onActivateProduction(game.getCurrentPlayer().getNickname());
        assertTrue(errors.contains(Error.WRONG_GAME_PHASE));

        p.setStandardActionPlayed(true);
        p2.setStandardActionPlayed(true);
        errors = controller.onActivateProduction(game.getCurrentPlayer().getNickname());
        assertTrue(errors.contains(Error.INVALID_ACTION));

        game.setGamePhase(GamePhase.PLAY_TURN);
        errors = controller.onActivateProduction(game.getCurrentPlayer().getNickname());
        assertTrue(errors.isEmpty());
    }

    @Test
    public void onActivateDevelopmentProductionTest(){}

    @Test
    public void onActivateLeaderProductionTest(){}

    @Test
    public void onActivateBoardTest(){}

    @Test
    public void onEndProductionTest(){}

    @Test
    public void onBuyDevelopmentCardsTest(){}

    @Test
    public void onBuyResourcesTest(){}

    @Test
    public void onActivateLeaderTest(){}

    @Test
    public void onDiscardLeaderTest(){}

    @Test
    public void EndTurnTest(){}

    @Test
    public void onMoveDepositTest(){}

    @Test
    public void onDiscardResourceTest(){}

    @Test
    public void nextTurnTest(){

        game.setGamePhase(GamePhase.LOGIN);
        controller.onNewPlayer(p.getNickname());
        controller.onNewPlayer(p2.getNickname());

        game.setGamePhase(GamePhase.PLAY_TURN);

        Player player = game.getCurrentPlayer();
        player.setLeaderActionPlayed(false);
        controller.nextTurn();
        assertEquals(player, game.getCurrentPlayer());

        player.setLeaderActionPlayed(true);
        player.setStandardActionPlayed(true);
        controller.nextTurn();
        assertNotEquals(player, game.getCurrentPlayer());

    }

    @Test
    public void controlTurnTest(){

        game.setGamePhase(GamePhase.LOGIN);
        controller.onNewPlayer(p.getNickname());

        List<Error> errors = controller.controlTurn(p.getNickname());
        assertFalse(errors.isEmpty());

        game.setGamePhase(GamePhase.PLAY_TURN);
        errors = controller.controlTurn("paperino");
        assertFalse(errors.isEmpty());

        errors = controller.controlTurn(p.getNickname());
        assertTrue(errors.isEmpty());

    }

    @Test
    public void onControlStandardActionTest(){

        game.setGamePhase(GamePhase.LOGIN);
        controller.onNewPlayer(p.getNickname());

        p.setStandardActionPlayed(true);
        List<Error> errors = controller.controlStandardAction();
        assertFalse(errors.isEmpty());

        p.setStandardActionPlayed(false);

        errors = controller.controlStandardAction();
        assertTrue(errors.isEmpty());
    }

    @Test
    public void onControlLeaderActionTest(){

        game.setGamePhase(GamePhase.LOGIN);
        controller.onNewPlayer(p.getNickname());

        p.setLeaderActionPlayed(true);
        List<Error> errors = controller.controlLeaderAction();
        assertFalse(errors.isEmpty());

        p.setLeaderActionPlayed(false);

        errors = controller.controlLeaderAction();
        assertTrue(errors.isEmpty());

    }

    @Test
    public void onPlayerDisconnectionTest(){

        game.setGamePhase(GamePhase.LOGIN);
        controller.onNewPlayer(p.getNickname());
        controller.onNewPlayer(p2.getNickname());

        List<Error> errors = controller.onPlayerDisconnection("Paperino");
        assertFalse(errors.isEmpty());

        errors = controller.onPlayerDisconnection(p.getNickname());
        assertTrue(errors.isEmpty());
        assertEquals(1, game.getListOfPlayers().size());
        assertFalse(game.getListOfPlayers().contains(p));
    }


}

