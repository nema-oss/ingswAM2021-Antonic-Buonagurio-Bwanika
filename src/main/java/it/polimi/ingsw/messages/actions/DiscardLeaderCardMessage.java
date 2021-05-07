package it.polimi.ingsw.messages.actions;

import it.polimi.ingsw.messages.MessageType;
import it.polimi.ingsw.model.cards.leadercards.LeaderCard;
import it.polimi.ingsw.view.client.View;
import it.polimi.ingsw.view.server.VirtualView;

import java.io.Serializable;
import java.util.ArrayList;

public class DiscardLeaderCardMessage implements Serializable, ActionMessage {
    private final MessageType messageType;
    private ArrayList<LeaderCard> hand;
    private LeaderCard choice;

    public DiscardLeaderCardMessage(ArrayList<LeaderCard> hand, LeaderCard choice) {
        this.hand = hand;
        this.choice = choice;
        messageType = MessageType.DISCARD_LEADERCARD;
    }

    public void execute(View view){
        //method in view to show the choice
    }

    public void execute(VirtualView virtualView){
        //method in virtualView
    }
    @Override
    public MessageType getType() {
        return messageType;
    }
}
