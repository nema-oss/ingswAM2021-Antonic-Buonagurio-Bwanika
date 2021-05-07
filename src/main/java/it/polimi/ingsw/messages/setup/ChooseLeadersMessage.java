package it.polimi.ingsw.messages.setup;

import it.polimi.ingsw.messages.MessageType;
import it.polimi.ingsw.model.cards.leadercards.LeaderCard;
import it.polimi.ingsw.view.client.View;
import it.polimi.ingsw.view.server.VirtualView;

import java.io.Serializable;
import java.util.ArrayList;

public class ChooseLeadersMessage implements Serializable, SetupMessage{
    private final MessageType messageType;
    private ArrayList<LeaderCard> choice;

    public ChooseLeadersMessage(ArrayList<LeaderCard> choice) {
        this.choice = choice;
        messageType = MessageType.CHOOSE_LEADERS;
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
