package it.polimi.ingsw.model;

import it.polimi.ingsw.controller.GamePhase;
import it.polimi.ingsw.model.Game;
import it.polimi.ingsw.model.cards.CardFactory;
import it.polimi.ingsw.model.cards.leadercards.LeaderCard;
import it.polimi.ingsw.model.cards.leadercards.LeaderDeck;
import it.polimi.ingsw.model.gameboard.GameBoard;
import it.polimi.ingsw.model.player.Player;
import it.polimi.ingsw.model.player.PopeRoad;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class GameTest {

    private static final int FIRST_POPE_SPACE = 8;
    private static final int FIRST_POPE_SPACE_POINTS = 2;
    private static final int SECOND_POPE_SPACE = 16;
    private static final int SECOND_POPE_SPACE_POINTS = 5;
    private Game game;
    private Player player1;
    private Player player2;

    @BeforeEach
    void setUp() {
        game = new Game();
        player1 = new Player("Player1", game.getGameBoard(), game);
        player2 = new Player("Player2", game.getGameBoard(), game);
        game.addPlayer(player1);
        game.addPlayer(player2);
        game.nextPlayer();

    }

    @Test
    @DisplayName("Testing the starting configuration of the match")
    void startGame(){
        LeaderDeck leaderDeck = new LeaderDeck(new CardFactory().getLeaderCards());
        for (LeaderCard card : leaderDeck.getListOfCards()) {
            String id = card.getId();
            assertTrue(game.getLeaderDeck().getListOfCards().stream().anyMatch(LeaderCard -> LeaderCard.getId().equals(id)));
        }
    }


    @Test
    @DisplayName("Testing the end of the game")
    void endGame(){
        CardFactory cardFactory = new CardFactory();
        player1.getPlayerBoard().addDevelopmentCard(cardFactory.getDevelopmentCards().get(4));
        Player winner = game.endGame();
        assertTrue(winner.equals(player1));
    }

    @Test
    @DisplayName("Testing the vatican report action")
    void vaticanReport() {

        assertEquals(player1, game.getCurrentPlayer());
        player1.moveOnPopeRoad(FIRST_POPE_SPACE);
        game.vaticanReport(player1.getPositionIndex());
        assertEquals(FIRST_POPE_SPACE_POINTS + 3, player1.getVictoryPoints());
        assertEquals(0, player2.getVictoryPoints());

        game.nextPlayer();
        assertEquals(game.getCurrentPlayer(),player2);
        player2.moveOnPopeRoad(FIRST_POPE_SPACE);
        game.vaticanReport(player2.getPositionIndex());
        assertEquals(FIRST_POPE_SPACE_POINTS + 3, player1.getVictoryPoints());
        assertEquals(3, player2.getVictoryPoints());

    }

    @Test
    @DisplayName("Testing Lorenzo's Turn")
    void lorenzoTurn(){


        game.setSinglePlayerCPU();
        game.lorenzoTurn();
        assertEquals(25, game.getLorenzoPopeRoad().getSize());

    }

    @Test
    void getPlayerByNickname(){
        assertEquals(game.getPlayerByNickname("Player1").getNickname(), "Player1");
    }

    @Test
    void getListOfPlayers(){
        List<Player> expected = new ArrayList<>();
        expected.add(player1);
        expected.add(player2);
        assertEquals(game.getListOfPlayers(), expected);

        game.setPlayersOrder();
        game.setCurrentPlayer(player1);
        assertEquals(player1, game.getCurrentPlayer());
    }

    @Test
    void checkLorenzoPosition(){
        Game game1 = new Game();
        Player single = new Player("single", new GameBoard(), game1);
        game1.addPlayer(single);
        game1.setSinglePlayerCPU();
        ActionToken actionToken = game1.lorenzoTurn();
        System.out.println(actionToken.getId());
        for(int i = 0; i<20; i++){
            actionToken = game1.lorenzoTurn();
            System.out.println(actionToken.getId());
        } //10th cell
        single.moveOnPopeRoad(11);
        game1.checkLorenzoPosition(11);
        assertEquals(7, single.getVictoryPoints());
    }

    @Test
    void movePlayersDiscard(){
        game.movePlayersDiscard("Player1", 7);
        assertEquals(player2.getVictoryPoints(), 3);
        assertEquals(player1.getVictoryPoints(), 0);
    }

    @Test
    void removePlayer(){
        game.removePlayer("Player1");
        assertFalse(game.getListOfPlayers().contains(game.getPlayerByNickname("Player1")));
        assertTrue(game.getListOfPlayers().contains(game.getPlayerByNickname("Player2")));
        game.reconnectPlayer("Player1");
        assertTrue(game.getListOfPlayers().contains(game.getPlayerByNickname("Player1")));

    }

    @Test
    void setGamePhase(){
        game.setGamePhase(GamePhase.CHOOSE_LEADERS);
        assertEquals(GamePhase.CHOOSE_LEADERS, game.getGamePhase());
    }
}