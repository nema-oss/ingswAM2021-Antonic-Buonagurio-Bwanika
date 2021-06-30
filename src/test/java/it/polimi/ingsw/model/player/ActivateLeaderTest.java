package it.polimi.ingsw.model.player;

import it.polimi.ingsw.model.Game;
import it.polimi.ingsw.model.cards.CardFactory;
import it.polimi.ingsw.model.cards.leadercards.LeaderCard;
import it.polimi.ingsw.model.exception.InsufficientDevelopmentCardsException;
import it.polimi.ingsw.model.exception.NonExistentCardException;
import it.polimi.ingsw.model.gameboard.GameBoard;
import org.junit.Test;
import org.junit.jupiter.api.DisplayName;

import javax.naming.InsufficientResourcesException;
import java.util.ArrayList;
import java.util.List;

public class ActivateLeaderTest {

    @Test
    @DisplayName("testing player's hand setting")
    public void activationTest() throws InsufficientDevelopmentCardsException, NonExistentCardException, InsufficientResourcesException {
        Player player = new Player("mario", new GameBoard(), new Game());
        CardFactory cardFactory = new CardFactory();
        List<LeaderCard> leaderCards = cardFactory.getLeaderCards();
        ArrayList<LeaderCard> hand = new ArrayList<>();
        hand.add(leaderCards.get(6));
        hand.add(leaderCards.get(7));
        player.setHand(hand);
//        player.activateLeaderCard(0);
    }
}
