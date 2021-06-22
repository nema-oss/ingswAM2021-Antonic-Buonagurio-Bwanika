package it.polimi.ingsw.messages.setup.server;

import it.polimi.ingsw.messages.setup.SetupMessage;
import it.polimi.ingsw.model.cards.leadercards.LeaderCard;
import it.polimi.ingsw.view.client.View;
import it.polimi.ingsw.view.server.VirtualView;

import java.io.Serializable;
import java.util.List;

public class LeaderCardsUpdateMessage implements SetupMessage, Serializable {


    private final List<LeaderCard> hand;
    private final List<LeaderCard> activeLeaderCards;

    public LeaderCardsUpdateMessage(List<LeaderCard> hand, List<LeaderCard> activeLeaderCards) {
        this.hand = hand;
        this.activeLeaderCards = activeLeaderCards;
    }


    /**
     * Execute the request server side
     *
     * @param virtualView : receiver view
     */
    @Override
    public void execute(VirtualView virtualView) {

    }

    /**
     * Execute the request client side
     *
     * @param view : receiver view
     */
    @Override
    public void execute(View view) {
        view.showLeaderCardUpdate(hand,activeLeaderCards);
    }
}
