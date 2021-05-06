package it.polimi.ingsw.messages;

import it.polimi.ingsw.model.gameboard.Resource;
import it.polimi.ingsw.view.client.View;
import it.polimi.ingsw.view.server.VirtualView;

import java.io.Serializable;
import java.util.ArrayList;

public class MoveResourcesMessage implements Serializable, Message {
    private final MessageType messageType;
    private ArrayList<Resource> resources; //up to 3 possible resources to move (1 per type)
    private ArrayList<Integer> toShelves; //where i want to move the resources to

    public MoveResourcesMessage(ArrayList<Resource> resources, ArrayList<Integer> toShelves) {
        this.resources = resources;
        this.toShelves = toShelves;
        messageType = MessageType.MOVE_RESOURCES;
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
