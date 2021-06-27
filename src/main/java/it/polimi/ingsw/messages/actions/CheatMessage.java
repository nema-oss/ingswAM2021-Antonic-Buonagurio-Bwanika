package it.polimi.ingsw.messages.actions;

import it.polimi.ingsw.model.gameboard.Resource;
import it.polimi.ingsw.model.gameboard.ResourceType;
import it.polimi.ingsw.view.client.View;
import it.polimi.ingsw.view.server.VirtualView;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class CheatMessage implements ActionMessage, Serializable {

    private final String user;
    private Map<ResourceType, List<Resource>> updatedStrongbox;
    private List<List<Resource>> updatedWarehouse;


    public CheatMessage(String user){
        this.user = user;
    }

    /**
     * Execute the request server side
     * @param virtualView: receiver view
     */
    @Override
    public void execute(VirtualView virtualView) {
        virtualView.cheat(user);
    }

    /**
     * Execute the request client side
     * @param view: receiver view
     */
    @Override
    public void execute(View view) {
        view.showProductionResult(updatedStrongbox,updatedWarehouse, new ArrayList<>());
    }

    /**
     * sets the updated strongbox and warehouse
     * @param updatedStrongbox the current strongbox
     * @param updatedWarehouse the current warehouse
     */
    public void setProductionResult(Map<ResourceType, List<Resource>> updatedStrongbox, List<List<Resource>> updatedWarehouse) {
        this.updatedStrongbox = updatedStrongbox;
        this.updatedWarehouse = updatedWarehouse;
    }

}
