package it.polimi.ingsw.player;

import it.polimi.ingsw.gameboard.CardMarket;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class PlayerTest {

    private Player player;
    private static final String nickname = "testing";



    @BeforeEach
    void setUp(){

    }

    @Test
    @DisplayName("Testing the buy a Card action")
    void buyDevelopmentCard() {
        CardMarket cardMarket;
        //player.buyDevelopmentCard(x);
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