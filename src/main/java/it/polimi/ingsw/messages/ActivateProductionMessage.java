package it.polimi.ingsw.messages;

import it.polimi.ingsw.model.cards.DevelopmentCard;
import it.polimi.ingsw.view.client.View;
import it.polimi.ingsw.view.server.VirtualView;

import java.io.Serializable;
import java.util.ArrayList;

public class ActivateProductionMessage implements Serializable, Message {
    private final MessageType messageType;
    private ArrayList<DevelopmentCard> production;
    private ArrayList<Integer> selected; //selected indexes of productions to activate (0 for the board production)

    public ActivateProductionMessage(ArrayList<DevelopmentCard> production, ArrayList<Integer> selected) {
        this.production = production;
        this.selected = selected;
        messageType = MessageType.ACTIVATE_PRODUCTION;
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
