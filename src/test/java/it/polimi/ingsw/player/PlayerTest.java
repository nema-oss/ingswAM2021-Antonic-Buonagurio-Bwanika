package it.polimi.ingsw.player;

import it.polimi.ingsw.Game;
import it.polimi.ingsw.cards.CardFactory;
import it.polimi.ingsw.cards.DevelopmentCard;
import it.polimi.ingsw.cards.DevelopmentDeck;
import it.polimi.ingsw.exception.InsufficientPaymentException;
import it.polimi.ingsw.exception.NonexistentCardException;
import it.polimi.ingsw.gameboard.CardMarket;
import it.polimi.ingsw.gameboard.GameBoard;
import it.polimi.ingsw.gameboard.Resource;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Assertions.*;

import javax.naming.InsufficientResourcesException;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class PlayerTest {

    private Player player;
    private static final String nickname = "testing";
    private GameBoard  gameBoard;



    @BeforeEach
    void setUp(){
        gameBoard = new GameBoard(3,4,3,4);
        player = new Player(nickname, gameBoard,new Game());
    }

    @Test
    @DisplayName("Testing the buy a Card action")
    void buyDevelopmentCard() {
    }

    @Test
    @DisplayName("Testing the buy a Resource action")
    void buyResources() {
    }

    @Test
    @DisplayName("Testing the activate production action")
    void activateProduction() {
    }

    @Test
    @DisplayName("Testing the movement on popeRoad")
    void moveOnPopeRoad() {
    }


    @Test
    @DisplayName("Testing the discard a Leader Card action")
    void discardLeader() {
    }

    @Test
    @DisplayName("Testing the activate a Leader Card action")
    void activateLeaderCard() {
    }
}