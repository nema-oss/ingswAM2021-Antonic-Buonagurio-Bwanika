package it.polimi.ingsw;

import it.polimi.ingsw.model.Game;
import it.polimi.ingsw.model.player.Player;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

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
    void startGame(){}


    @Test
    @DisplayName("Testing the end of the game")
    void endGame(){}

    @Test
    @DisplayName("Testing the vatican report action")
    void vaticanReport() {

        assertEquals(player1, game.getCurrentPlayer());
        player1.moveOnPopeRoad(FIRST_POPE_SPACE);
      //  assertEquals(FIRST_POPE_SPACE_POINTS + 3, player1.getVictoryPoints());
        assertEquals(0, player2.getVictoryPoints());

        game.nextPlayer();
        assertEquals(game.getCurrentPlayer(),player2);
        player2.moveOnPopeRoad(FIRST_POPE_SPACE);
//        assertEquals(FIRST_POPE_SPACE_POINTS + 3, player1.getVictoryPoints());
        assertEquals(3, player2.getVictoryPoints());

    }

    @Test
    @DisplayName("Testing Lorenzo's Turn")
    void lorenzoTurn(){


        game.setSinglePlayerCPU();
        game.lorenzoTurn();

    }
}