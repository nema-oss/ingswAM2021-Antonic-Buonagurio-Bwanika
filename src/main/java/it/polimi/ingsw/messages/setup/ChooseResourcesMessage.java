package it.polimi.ingsw.messages.setup;

import it.polimi.ingsw.messages.MessageType;
import it.polimi.ingsw.messages.actions.ActionMessageType;
import it.polimi.ingsw.model.gameboard.Resource;
import it.polimi.ingsw.view.client.View;
import it.polimi.ingsw.view.server.VirtualView;


import java.io.Serializable;
import java.util.ArrayList;

public class ChooseResourcesMessage implements Serializable, SetupMessage {
    private final SetupMessageType messageType;
    private ArrayList<Resource> resourcesarrayList;

    public ChooseResourcesMessage(ArrayList<Resource> resourcesarrayList) {
        this.resourcesarrayList = resourcesarrayList;
        messageType = SetupMessageType.CHOOSE_RESOURCES;
    }

    public void execute(View view){
        //method in View to show the resources
    }

    public void execute(VirtualView virtualView){
        //method in virtualView
    }


    public SetupMessageType getType() {
        return messageType;
    }
}
