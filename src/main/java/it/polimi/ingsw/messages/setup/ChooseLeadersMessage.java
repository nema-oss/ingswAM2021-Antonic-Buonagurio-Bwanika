package it.polimi.ingsw.messages.setup;

import it.polimi.ingsw.messages.MessageType;
import it.polimi.ingsw.messages.actions.ActionMessageType;
import it.polimi.ingsw.model.cards.leadercards.LeaderCard;
import it.polimi.ingsw.view.client.View;
import it.polimi.ingsw.view.server.VirtualView;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class ChooseLeadersMessage implements Serializable, SetupMessage{

    private final SetupMessageType messageType;
    private List<LeaderCard> choice;
    private boolean accepted;

    public ChooseLeadersMessage(List<LeaderCard> choice, boolean accepted) {
        this.choice = choice;
        this.accepted = accepted;
        messageType = SetupMessageType.CHOOSE_LEADERS;
    }

    public void execute(View view){
        //method in view to show the choice
    }

    public void execute(VirtualView virtualView){
        //method in virtualView
    }


    public SetupMessageType getType() {
        return messageType;
    }
}
