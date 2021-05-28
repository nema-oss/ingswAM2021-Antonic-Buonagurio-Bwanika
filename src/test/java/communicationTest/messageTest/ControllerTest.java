package communicationTest.messageTest;

import it.polimi.ingsw.controller.Error;
import it.polimi.ingsw.controller.GamePhase;
import it.polimi.ingsw.controller.MatchController;
import it.polimi.ingsw.model.Game;
import it.polimi.ingsw.model.cards.DevelopmentCard;
import it.polimi.ingsw.model.cards.leadercards.LeaderCard;
import it.polimi.ingsw.model.exception.FullDepositException;
import it.polimi.ingsw.model.exception.NonExistentCardException;
import it.polimi.ingsw.model.gameboard.Resource;
import it.polimi.ingsw.model.gameboard.ResourceType;
import it.polimi.ingsw.model.player.Player;
import it.polimi.ingsw.network.server.EchoServer;
import it.polimi.ingsw.view.server.InGameReconnectionHandler;
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

        controller = new MatchController();
        game = controller.getGame();
        p = new Player("Pippo", game.getGameBoard(), game);
        p2 = new Player("Pluto", game.getGameBoard(), game);

        controller.setVirtualView(new VirtualView(controller, 1, new EchoServer(1234) {
        }));
    }

    @Test
    public void onControllerNewPlayerTest(){

        controller = new MatchController();
        game = controller.getGame();
        p = new Player("Pippo", game.getGameBoard(), game);
        p2 = new Player("Pluto", game.getGameBoard(), game);

        List<Error> error;

        game.setGamePhase(GamePhase.LOGIN);
        error = controller.onNewPlayer(p.getNickname());
        assertEquals(p.getNickname(), game.getPlayerByNickname(p.getNickname()).getNickname());
        assertTrue(error.isEmpty());

        error = controller.onNewPlayer(p2.getNickname());
        assertEquals(game.getListOfPlayers().size(), 2);
        assertEquals(p2.getNickname(), game.getPlayerByNickname(p2.getNickname()).getNickname());
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

    }

    @Test
    public void onStartGameTest(){

        controller = new MatchController();
        game = controller.getGame();
        p = new Player("Pippo", game.getGameBoard(), game);
        p2 = new Player("Pluto", game.getGameBoard(), game);
        controller.setVirtualView(new VirtualView(controller, 1, new EchoServer(1234)));

        game.setGamePhase(GamePhase.LOGIN);
        controller.onNewPlayer(p.getNickname());
        controller.onNewPlayer(p2.getNickname());

/*        List<Error> errors = controller.onStartGame();
        assertEquals(game.getGamePhase(), GamePhase.CHOOSE_LEADERS);
        assertTrue(errors.isEmpty());

        game.setGamePhase(GamePhase.PLAY_TURN);
        errors = controller.onStartGame();
        assertFalse(errors.isEmpty()); */
    }


    @Test
    public void onLeaderCardsChosenTest(){

        controller = new MatchController();
        game = controller.getGame();
        p = new Player("Pippo", game.getGameBoard(), game);
        p2 = new Player("Pluto", game.getGameBoard(), game);
        controller.setVirtualView(new VirtualView(controller, 1, new EchoServer(1234)));

        game.setGamePhase(GamePhase.LOGIN);
        controller.onNewPlayer(p.getNickname());
        controller.onNewPlayer(p2.getNickname());
        game.setCurrentPlayer(p);

        List<LeaderCard> leaderCardsChosen = new ArrayList<>();
        leaderCardsChosen.add(game.getLeaderDeck().drawCard());

        game.setGamePhase(GamePhase.CHOOSE_LEADERS);

        List<Error> errors = controller.onLeaderCardsChosen(game.getCurrentPlayer().getNickname(), null);
        assertTrue(errors.contains(Error.INVALID_ACTION));

        errors = controller.onLeaderCardsChosen(game.getCurrentPlayer().getNickname(), leaderCardsChosen );
        assertTrue(errors.contains(Error.INVALID_ACTION));

        leaderCardsChosen.add(game.getLeaderDeck().drawCard());

     /*   Player player = game.getCurrentPlayer();
        errors = controller.onLeaderCardsChosen(game.getCurrentPlayer().getNickname(), leaderCardsChosen);

        assertTrue(errors.isEmpty());
        assertTrue(player.getHand().containsAll(leaderCardsChosen));

        game.setGamePhase(GamePhase.LOGIN);
        errors = controller.onLeaderCardsChosen(game.getCurrentPlayer().getNickname(), leaderCardsChosen);
        assertTrue(errors.contains(Error.WRONG_GAME_PHASE)); */
    }

    @Test
    public void onResourcesChosenTest(){

        controller = new MatchController();
        game = controller.getGame();
        p = new Player("Pippo", game.getGameBoard(), game);
        p2 = new Player("Pluto", game.getGameBoard(), game);
        controller.setVirtualView(new VirtualView(controller, 1, new EchoServer(1234)));

        game.setGamePhase(GamePhase.LOGIN);
        controller.onNewPlayer(p.getNickname());
        controller.onNewPlayer(p2.getNickname());
        game.setCurrentPlayer(p);

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

        controller = new MatchController();
        game = controller.getGame();
        p = new Player("Pippo", game.getGameBoard(), game);
        p2 = new Player("Pluto", game.getGameBoard(), game);
        controller.setVirtualView(new VirtualView(controller, 1, new EchoServer(1234)));

        game.setGamePhase(GamePhase.LOGIN);
        controller.onNewPlayer(p.getNickname());
        controller.onNewPlayer(p2.getNickname());
        game.setPlayersOrder();

        List<Error> errors = controller.onActivateProduction(game.getCurrentPlayer().getNickname());
        assertTrue(errors.contains(Error.WRONG_GAME_PHASE));

        game.setGamePhase(GamePhase.PLAY_TURN);
        game.getCurrentPlayer().setStandardActionPlayed(true);
        errors = controller.onActivateProduction(game.getCurrentPlayer().getNickname());

        assertTrue(errors.contains(Error.INVALID_ACTION));

        game.getCurrentPlayer().setStandardActionPlayed(false);
        errors = controller.onActivateProduction(game.getCurrentPlayer().getNickname());
        assertTrue(errors.isEmpty());
    }

    /* @Test
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
    public void onActivateLeaderTest(){} */

    @Test
    public void onDiscardLeaderTest(){

        controller = new MatchController();
        game = controller.getGame();
        p = new Player("Pippo", game.getGameBoard(), game);
        p2 = new Player("Pluto", game.getGameBoard(), game);

        game.setGamePhase(GamePhase.LOGIN);
        controller.onNewPlayer(p.getNickname());
        game.setCurrentPlayer(p);
        List<LeaderCard> hand = new ArrayList<>(game.getLeaderDeck().getListOfCards());
        p.setHand(hand);

        game.setGamePhase(GamePhase.PLAY_TURN);
        game.getCurrentPlayer().setLeaderActionPlayed(true);
        game.getCurrentPlayer().setLeaderActionPlayed(true);

        List<Error> errors = controller.onDiscardLeader(game.getCurrentPlayer().getNickname(), game.getCurrentPlayer().getHand().get(0) );
        assertFalse(errors.isEmpty());

        game.getCurrentPlayer().setLeaderActionPlayed(false);
        LeaderCard card = game.getCurrentPlayer().getHand().get(0);
        errors = controller.onDiscardLeader(game.getCurrentPlayer().getNickname(), game.getCurrentPlayer().getHand().get(0));
        assertTrue(errors.isEmpty());
        assertFalse(game.getCurrentPlayer().getHand().contains(card));
        errors = controller.onDiscardLeader(game.getCurrentPlayer().getNickname(), game.getCurrentPlayer().getHand().get(1));
        assertTrue(errors.isEmpty());
    }

    @Test
    public void EndTurnTest(){

        controller = new MatchController();
        game = controller.getGame();
        p = new Player("Pippo", game.getGameBoard(), game);
        p2 = new Player("Pluto", game.getGameBoard(), game);
        controller.setVirtualView(new VirtualView(controller, 1, new EchoServer(1234)));

        game.setGamePhase(GamePhase.LOGIN);
        controller.onNewPlayer(p.getNickname());
        game.setGamePhase(GamePhase.PLAY_TURN);
        game.setSinglePlayerCPU();
        game.setPlayersOrder();

        game.getCurrentPlayer().setStandardActionPlayed(false);
        List<Error> errors = controller.onEndTurn(game.getCurrentPlayer().getNickname());
        assertTrue(errors.contains(Error.INVALID_ACTION));

        game.getCurrentPlayer().setStandardActionPlayed(true);
        errors = controller.onEndTurn(game.getCurrentPlayer().getNickname());
        assertTrue(errors.isEmpty());
    }

    @Test
    public void onMoveDepositTest(){

        controller = new MatchController();
        game = controller.getGame();
        p = new Player("Pippo", game.getGameBoard(), game);
        p2 = new Player("Pluto", game.getGameBoard(), game);

        game.setGamePhase(GamePhase.LOGIN);
        controller.onNewPlayer(p.getNickname());
        game.setCurrentPlayer(p);

        try {
            p.addResourceToDeposit(1, new Resource(ResourceType.COIN));
            p.addResourceToDeposit(2, new Resource(ResourceType.SERVANT));
        } catch (FullDepositException | Exception e) {
            e.printStackTrace();
        }

        List<Error> errors = controller.onMoveDeposit(game.getCurrentPlayer().getNickname(), 1,2);
        assertFalse(errors.isEmpty());

        game.setGamePhase(GamePhase.PLAY_TURN);
        errors = controller.onMoveDeposit(game.getCurrentPlayer().getNickname(), 1, 2);
        assertTrue(errors.isEmpty());
        assertEquals(p.getDeposit().get(1).getType(), ResourceType.SERVANT);
        assertNotEquals(p.getDeposit().get(2).getType(), ResourceType.SERVANT);

    }

    @Test
    public void onDiscardResourceTest(){

        controller = new MatchController();
        game = controller.getGame();
        p = new Player("Pippo", game.getGameBoard(), game);
        p2 = new Player("Pluto", game.getGameBoard(), game);

        game.setGamePhase(GamePhase.LOGIN);
        controller.onNewPlayer(p.getNickname());
        controller.onNewPlayer(p2.getNickname());
        game.setCurrentPlayer(p);


        game.setGamePhase(GamePhase.PLAY_TURN);
        try {
            p.addResourceToDeposit(1, new Resource(ResourceType.COIN));
        } catch (FullDepositException | Exception e) {
            e.printStackTrace();
        }
        List<Error> errors = controller.onDiscardResource(game.getCurrentPlayer().getNickname(),ResourceType.COIN);

        assertTrue(errors.isEmpty());

    }

    @Test
    public void nextTurnTest(){

        controller = new MatchController();
        game = controller.getGame();

        p = new Player("Pippo", game.getGameBoard(), game);
        p2 = new Player("Pluto", game.getGameBoard(), game);

        controller.setVirtualView(new VirtualView(controller, 1, new EchoServer(1234)));

        game.setGamePhase(GamePhase.LOGIN);
        controller.onNewPlayer(p.getNickname());
        controller.onNewPlayer(p2.getNickname());
        game.setPlayersOrder();

        game.setGamePhase(GamePhase.PLAY_TURN);

        Player player = game.getCurrentPlayer();
        player.setLeaderActionPlayed(false);
        controller.nextTurn();
        assertEquals(player, game.getCurrentPlayer());

        player.setLeaderActionPlayed(true);
        player.setLeaderActionPlayed(true);
        player.setStandardActionPlayed(true);
        controller.nextTurn();
//        assertNotEquals(player, game.getCurrentPlayer());

    }

    @Test
    public void controlTurnTest(){

        controller = new MatchController();
        game = controller.getGame();
        p = new Player("Pippo", game.getGameBoard(), game);
        p2 = new Player("Pluto", game.getGameBoard(), game);

        game.setGamePhase(GamePhase.LOGIN);
        controller.onNewPlayer(p.getNickname());
        game.setCurrentPlayer(p);

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

        controller = new MatchController();
        game = controller.getGame();
        p = new Player("Pippo", game.getGameBoard(), game);
        p2 = new Player("Pluto", game.getGameBoard(), game);

        game.setGamePhase(GamePhase.LOGIN);
        controller.onNewPlayer(p.getNickname());
        game.setCurrentPlayer(p);

        p.setStandardActionPlayed(true);
        List<Error> errors = controller.controlStandardAction();
        assertFalse(errors.isEmpty());

        p.setStandardActionPlayed(false);

        errors = controller.controlStandardAction();
        assertTrue(errors.isEmpty());
    }

    @Test
    public void onControlLeaderActionTest(){

        controller = new MatchController();
        game = controller.getGame();
        p = new Player("Pippo", game.getGameBoard(), game);
        p2 = new Player("Pluto", game.getGameBoard(), game);

        game.setGamePhase(GamePhase.LOGIN);
        controller.onNewPlayer(p.getNickname());
        game.setCurrentPlayer(p);

        p.setLeaderActionPlayed(true);
        p.setLeaderActionPlayed(true);
        List<Error> errors = controller.controlLeaderAction();
        assertFalse(errors.isEmpty());

        p.setLeaderActionPlayed(false);

        errors = controller.controlLeaderAction();
        assertTrue(errors.isEmpty());

    }

    @Test
    public void onPlayerDisconnectionTest(){

        controller = new MatchController();
        game = controller.getGame();
        p = new Player("Pippo", game.getGameBoard(), game);
        p2 = new Player("Pluto", game.getGameBoard(), game);
        controller.setVirtualView(new VirtualView(controller, 1, new EchoServer(1234)));

        game.setGamePhase(GamePhase.LOGIN);
        controller.onNewPlayer(p.getNickname());
        controller.onNewPlayer(p2.getNickname());
        game.setCurrentPlayer(p);

        List<Error> errors = controller.onPlayerDisconnection("Paperino");
        assertFalse(errors.isEmpty());

        errors = controller.onPlayerDisconnection(p.getNickname());
        assertTrue(errors.isEmpty());
        assertEquals(1, game.getListOfPlayers().size());
        assertFalse(game.getListOfPlayers().contains(p));
    }


}

