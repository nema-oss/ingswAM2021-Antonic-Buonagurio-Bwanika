package it.polimi.ingsw.controller;

import it.polimi.ingsw.model.Game;
import it.polimi.ingsw.model.cards.DevelopmentCard;
import it.polimi.ingsw.model.cards.leadercards.LeaderCard;
import it.polimi.ingsw.model.exception.FullDepositException;
import it.polimi.ingsw.model.exception.InsufficientDevelopmentCardsException;
import it.polimi.ingsw.model.exception.NonExistentCardException;
import it.polimi.ingsw.model.gameboard.*;
import it.polimi.ingsw.model.player.Player;
import it.polimi.ingsw.network.server.EchoServer;
import it.polimi.ingsw.view.server.VirtualView;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import javax.naming.InsufficientResourcesException;
import java.util.*;

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
        controller.setVirtualView(new VirtualView(controller, 1, new EchoServer(1234)));
    }

    @Test
    @DisplayName("testing setting virtual view in the controller")
    public void setVirtualViewTest(){

        controller.setVirtualView(new VirtualView(controller, 1, new EchoServer(1234) {
        }));
    }

    @Test
    @DisplayName("testing connection of new player")
    public void onControllerNewPlayerTest(){

        List<Error> error;

        game.setGamePhase(GamePhase.PLAY_TURN);
        assertTrue((controller.onNewPlayer(p.getNickname())).contains(Error.WRONG_GAME_PHASE));
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
    @DisplayName("testing start of game")
    public void onStartGameTest(){

        game.setGamePhase(GamePhase.PLAY_TURN);
        game.setCurrentPlayer(p);

        assertTrue((controller.onStartGame()).contains(Error.WRONG_GAME_PHASE));
    }

    @Test
    @DisplayName("testing placing resources into deposit")
    public void onPlaceResourceTest(){

        game.setCurrentPlayer(p);
        game.setGamePhase(GamePhase.PLAY_TURN);

        Map<Resource, Integer> placingMap = new HashMap<>();

        placingMap.put(new Resource(ResourceType.SHIELD), 1);
        placingMap.put(new Resource(ResourceType.COIN), 2);
        placingMap.put(new Resource(ResourceType.COIN),2);

        assertTrue((controller.onPlaceResources(p.getNickname(), placingMap)).isEmpty());


        Resource wrongResource = new Resource(ResourceType.COIN);
        placingMap.put(wrongResource, 2);
        game.setCurrentPlayer(p);
        assertTrue((controller.onPlaceResources(p.getNickname(), placingMap)).contains(Error.DEPOSIT_IS_FULL));

        game.setCurrentPlayer(p);
        placingMap.replace(wrongResource, 2, 3);
        assertTrue((controller.onPlaceResources(p.getNickname(), placingMap)).contains(Error.DEPOSIT_IS_FULL));

    }

    @Test
    @DisplayName("testing player reconnection")
    public void onPlayerReconnection(){
        game.setGamePhase(GamePhase.LOGIN);
        controller.onNewPlayer(p.getNickname());
        controller.onNewPlayer(p2.getNickname());
        controller.onNewPlayer(new Player("paperino", game.getGameBoard(), game ).getNickname());

        controller.onPlayerDisconnection(p.getNickname());
        assertFalse(game.getListOfPlayers().contains(p));

        controller.onPlayerReconnection(p.getNickname());
        //assertTrue(game.getListOfPlayers().contains(p));

    }


    @Test
    @DisplayName("testing initial leader cards choice")
    public void onLeaderCardsChosenTest(){

        game.setGamePhase(GamePhase.LOGIN);
        controller.onNewPlayer(p.getNickname());
        controller.onNewPlayer(p2.getNickname());
        game.setCurrentPlayer(p);

        List<LeaderCard> leaderCardsChosen = new ArrayList<>();
        leaderCardsChosen.add(game.getLeaderDeck().drawCard());

        assertTrue((controller.onLeaderCardsChosen(game.getCurrentPlayer().getNickname(), leaderCardsChosen)).contains(Error.WRONG_GAME_PHASE));

        game.setGamePhase(GamePhase.CHOOSE_LEADERS);

        List<Error> errors = controller.onLeaderCardsChosen(game.getCurrentPlayer().getNickname(), null);
        assertTrue(errors.contains(Error.INVALID_ACTION));

        errors = controller.onLeaderCardsChosen(game.getCurrentPlayer().getNickname(), leaderCardsChosen );
        assertTrue(errors.contains(Error.INVALID_ACTION));

        game.setCurrentPlayer(p);
        assertTrue((controller.onLeaderCardsChosen(p2.getNickname(), leaderCardsChosen)).contains(Error.NOT_YOUR_TURN));
    }


    @Test
    @DisplayName("testing initial resources choice")
    public void onResourcesChosenTest(){

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
    @DisplayName("testing production activation")
    public void onActivateProductionTest(){

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

    @Test
    @DisplayName("testing development card production activation")
    public void onActivateDevelopmentProductionTest() throws FullDepositException {

        game.setCurrentPlayer(p);
        game.setGamePhase(GamePhase.PLAY_TURN);
        controller.onActivateProduction(p.getNickname());
        assertTrue(controller.onActivateDevelopmentProduction(p.getNickname(), new ArrayList<>()).contains(Error.GENERIC));

        game.getCurrentPlayer().getPlayerBoard().addDevelopmentCard(game.getDevelopmentDeck().getTop());
        List<DevelopmentCard> listOfCards = new ArrayList<>();
        listOfCards.add(game.getCurrentPlayer().getPlayerBoard().getDevelopmentCards().get(0).peek());
        assertTrue((controller.onActivateDevelopmentProduction(p.getNickname(), listOfCards)).contains(Error.INSUFFICIENT_PAYMENT));

        p.addResourceToDeposit(1, new Resource(ResourceType.COIN));
        assertTrue((controller.onActivateDevelopmentProduction(p.getNickname(), listOfCards)).isEmpty());

        assertTrue((controller.onActivateDevelopmentProduction(p.getNickname(), listOfCards)).contains(Error.INVALID_ACTION));


    }

    @Test
    @DisplayName("testing leader card production activation")
    public void onActivateLeaderProductionTest() throws FullDepositException, InsufficientResourcesException, InsufficientDevelopmentCardsException, NonExistentCardException {

        game.setCurrentPlayer(p);
        game.setGamePhase(GamePhase.PLAY_TURN);
        controller.onActivateProduction(p.getNickname());

        List<LeaderCard> leaderCards = new ArrayList<>();
        leaderCards.add(game.getLeaderDeck().getListOfCards().get(12));
        game.getCurrentPlayer().setHand(leaderCards);

        game.getCurrentPlayer().getPlayerBoard().addDevelopmentCard(game.getDevelopmentDeck().getListOfCards().get(3));
        game.getCurrentPlayer().getPlayerBoard().addDevelopmentCard(game.getDevelopmentDeck().getListOfCards().get(19));

        System.out.println(p.getHand().get(0).getCostDevelopment());
        game.getCurrentPlayer().activateLeaderCard(0);

        Map<LeaderCard, ResourceType> productionMap = new HashMap<>();
        productionMap.put(leaderCards.get(0), ResourceType.COIN);

        assertTrue((controller.onActivateLeaderProduction(p.getNickname(), productionMap)).contains(Error.INSUFFICIENT_PAYMENT));

        p.addResourceToDeposit(1, new Resource(ResourceType.SHIELD));
        assertTrue((controller.onActivateLeaderProduction(p.getNickname(), productionMap)).isEmpty());

        assertTrue((controller.onActivateLeaderProduction(p.getNickname(), productionMap)).contains(Error.INVALID_ACTION));
        productionMap.put(game.getLeaderDeck().getListOfCards().get(0), ResourceType.COIN);
        assertTrue(controller.onActivateLeaderProduction(p.getNickname(), productionMap).contains(Error.INVALID_ACTION));

    }

    @Test
    @DisplayName("testing board production activation and end production")
    public void onActivateBoardTest() throws FullDepositException {

        game.setGamePhase(GamePhase.LOGIN);
        controller.onNewPlayer(p.getNickname());
        game.setCurrentPlayer(p);
        game.setGamePhase(GamePhase.PLAY_TURN);
        controller.onActivateProduction(p.getNickname());

        assertTrue(controller.onEndProduction(p.getNickname()).contains(Error.INVALID_ACTION));

        List<ResourceType> toGive = new ArrayList<>();
        toGive.add(ResourceType.COIN);
        toGive.add(ResourceType.SHIELD);

        Resource toGet = new Resource(ResourceType.SERVANT);

        Map<Resource, List<ResourceType>> productionMap = new HashMap<>();
        productionMap.put(toGet, toGive);

        assertTrue((controller.onActivateBoardProduction(p.getNickname(), productionMap)).contains(Error.INSUFFICIENT_PAYMENT));

        game.getCurrentPlayer().addResourceToDeposit(1, new Resource(ResourceType.SHIELD));
        game.getCurrentPlayer().addResourceToDeposit(2, new Resource(ResourceType.COIN));

        assertTrue((controller.onActivateBoardProduction(p.getNickname(), productionMap)).isEmpty());

        assertTrue((controller.onActivateBoardProduction(p.getNickname(), productionMap)).contains(Error.INVALID_ACTION));


    }


    @Test
    @DisplayName("testing buying development card from market")
    public void onBuyDevelopmentCardsTest(){

        game.setGamePhase(GamePhase.LOGIN);
        controller.onNewPlayer(p.getNickname());
        game.setCurrentPlayer(p);
        game.setGamePhase(GamePhase.PLAY_TURN);
        controller.onActivateProduction(p.getNickname());


        assertTrue((controller.onBuyDevelopmentCards(p.getNickname(), 2, 0)).contains(Error.INSUFFICIENT_PAYMENT));

        game.setCurrentPlayer(p);
        p.getStrongbox().addResourceCheat();

        assertTrue((controller.onBuyDevelopmentCards(p.getNickname(), 2, 0)).isEmpty());
        assertTrue((controller.onBuyDevelopmentCards(p.getNickname(), 2, 0)).contains(Error.INVALID_ACTION));

        game.setCurrentPlayer(p);
        p.setStandardActionPlayed(false);
        assertTrue((controller.onBuyDevelopmentCards(p.getNickname(), 2, 0)).isEmpty());
        game.setCurrentPlayer(p);
        p.setStandardActionPlayed(false);
        assertTrue((controller.onBuyDevelopmentCards(p.getNickname(), 2, 0)).isEmpty());
        game.setCurrentPlayer(p);
        p.setStandardActionPlayed(false);

        assertTrue((controller.onBuyDevelopmentCards(p.getNickname(), 3, 0)).contains(Error.CARD_DOESNT_EXIST));

    }

    @Test
    @DisplayName("testing buying resources from market")
    public void onBuyResourcesTest(){

        game.setGamePhase(GamePhase.LOGIN);
        controller.onNewPlayer(p.getNickname());
        game.setCurrentPlayer(p);
        game.setGamePhase(GamePhase.PLAY_TURN);
        controller.onActivateProduction(p.getNickname());

        assertTrue((controller.onBuyResources(p.getNickname(), 1, -1)).isEmpty());

        List<LeaderCard> leaderCards = new ArrayList<>();
        leaderCards.add(game.getLeaderDeck().getListOfCards().get(4));

        p.setHand(leaderCards);
        game.setCurrentPlayer(p);
        p.getStrongbox().addResourceCheat();
        controller.onActivateLeader(p.getNickname(), p.getHand().get(0));

        game.setCurrentPlayer(p);

        for(int i=0; i<3; i++)
            if(Arrays.stream((game.getGameBoard().getMarket().marbles()[i])).anyMatch(Marble -> Marble.getColor().equals(MarbleType.GREY))){
                controller.onBuyResources(p.getNickname(), i, -1);
                assertEquals(0, p.getActiveEffects().getAuxiliaryDeposit(0).getAuxiliaryDeposit().size());
                break;
            }
    }


    @Test
    @DisplayName("testing leader card discard")
    public void onDiscardLeaderTest(){

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
    @DisplayName("testing end of turn")
    public void EndTurnTest(){

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
    @DisplayName("testing deposit swaps")
    public void onMoveDepositTest(){

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
    @DisplayName("testing resources discard")
    public void onDiscardResourceTest(){

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
        //List<Error> errors = controller.onDiscardResource(game.getCurrentPlayer().getNickname(),ResourceType.COIN);

        //assertTrue(errors.isEmpty());

    }

    @Test
    @DisplayName("testing pass of turn")
    public void nextTurnTest(){

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
    @DisplayName("testing control on game phases and current player")
    public void controlTurnTest(){

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
    @DisplayName("testing control on player's turn: standard action")
    public void onControlStandardActionTest(){

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
    @DisplayName("testing control on player's turn: leader action")
    public void onControlLeaderActionTest(){

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
    @DisplayName("testing player disconnection")
    public void onPlayerDisconnectionTest(){

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

