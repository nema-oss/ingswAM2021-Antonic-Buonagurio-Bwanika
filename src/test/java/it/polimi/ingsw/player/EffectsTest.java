package it.polimi.ingsw.player;

import it.polimi.ingsw.gameboard.ResourceType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class EffectsTest {

    private Effects effects;
    private Player player;

    @BeforeEach
    void setUp(){
        effects = new Effects();

    }
    @Test
    void activateWhiteToResource() {
        effects.activateWhiteToResource(ResourceType.COIN);

    }

    @Test
    void activateDiscount() {
    }

    @Test
    void isExtraProduction() {
    }

    @Test
    void useDiscountEffect() {
    }

    @Test
    void useExtraDepositEffect() {
    }
}