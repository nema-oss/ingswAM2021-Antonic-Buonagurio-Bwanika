package it.polimi.ingsw;

import it.polimi.ingsw.player.Player;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.stream.IntStream;

import static org.junit.jupiter.api.Assertions.*;

class GameTest {

    private static final int FIRST_POPE_SPACE = 5;
    private static final int FIRST_POPE_SPACE_POINTS = 2;
    private static final int SECOND_POPE_SPACE = 10;
    private static final int SECOND_POPE_SPACE_POINTS = 3;
    private Game game;
    private Player player1;
    private Player player2;

    @BeforeEach
    void setUp() {
        game = new Game();
        player1 = new Player("Player1", game.getGameBoard(), game);
        player2 = new Player("Player2", game.getGameBoard(), game);

    }

    @Test
    @DisplayName("Testing the starting configuration of the match")
    void startGame() {
    }

    @Test
    @DisplayName("Testing the turn")
    void nextPlayer() {
    }

    @Test
    @DisplayName("Testing the single game Lorenzo action")
    void lorenzoTurn() {
    }

    @Test
    @DisplayName("Testing the end of the game")
    void endGame() {
    }

    @Test
    @DisplayName("Testing the vatican report action")
    void vaticanReport() {

        int previousPointsPlayer1 = player1.getVictoryPoints();
        int previousPointsPlayer2 = player1.getVictoryPoints();
        IntStream.range(0, FIRST_POPE_SPACE).forEach(i -> player1.moveOnPopeRoad());
        assertEquals(player1.getVictoryPoints(), previousPointsPlayer1 + FIRST_POPE_SPACE_POINTS);
        assertEquals(player2.getVictoryPoints(), previousPointsPlayer2);

        previousPointsPlayer1 = player1.getVictoryPoints();
        previousPointsPlayer2 = player1.getVictoryPoints();

        IntStream.range(previousPointsPlayer2, SECOND_POPE_SPACE).forEach(i -> player1.moveOnPopeRoad());
        assertEquals(player2.getVictoryPoints(), previousPointsPlayer2 + SECOND_POPE_SPACE_POINTS);
        assertEquals(previousPointsPlayer1, player1.getVictoryPoints());
        assertEquals(player1.getVictoryPoints(), FIRST_POPE_SPACE_POINTS);
        assertEquals(player2.getVictoryPoints(), SECOND_POPE_SPACE_POINTS);


    }
}