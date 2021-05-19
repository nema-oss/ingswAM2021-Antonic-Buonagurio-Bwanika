package it.polimi.ingsw.messages.setup.server;

import it.polimi.ingsw.messages.setup.SetupMessage;
import it.polimi.ingsw.messages.setup.SetupMessageType;
import it.polimi.ingsw.model.cards.CardFactory;
import it.polimi.ingsw.model.cards.leadercards.LeaderCard;
import it.polimi.ingsw.view.client.View;
import it.polimi.ingsw.view.server.VirtualView;

import java.io.Serializable;
import java.util.List;

/**
 * message sent when the game begins
 * @author Nemanja Antonic
 */
public class MatchStartedMessage implements Serializable, SetupMessage {
    private final SetupMessageType messageType;

    /**
     * Server-side constructor to create the message
     */
    public MatchStartedMessage() {
        this.messageType = SetupMessageType.START_GAME;
    }

    @Override
    public void execute(VirtualView virtualView) {

    }

    /**
     * Execute the request client side
     * @param view: receiver view
     */
    public void execute(View view){
        view.showMatchStarted();
        /*
        CardFactory cardFactory = new CardFactory();
        List<LeaderCard> leaderCards = cardFactory.getLeaderCards();
        leaderCards = cardFactory.getLeaderCards();
        System.out.println(leaderCards.get(0).getLeaderType());
        view.setLeaderCardChoice(leaderCards.subList(0,4));

         */

    }
    /**
     * Get the message type
     * @return the message type
     */
    public SetupMessageType getType() {
        return messageType;
    }
}
