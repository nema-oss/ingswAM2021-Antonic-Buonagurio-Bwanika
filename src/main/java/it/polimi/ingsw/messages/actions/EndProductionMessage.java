package it.polimi.ingsw.messages.actions;

import it.polimi.ingsw.messages.actions.ActionMessage;
import it.polimi.ingsw.model.cards.leadercards.AuxiliaryDeposit;
import it.polimi.ingsw.model.gameboard.Resource;
import it.polimi.ingsw.model.gameboard.ResourceType;
import it.polimi.ingsw.view.client.View;
import it.polimi.ingsw.view.server.VirtualView;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

public class EndProductionMessage implements ActionMessage, Serializable {

    private final String user;
    private Map<ResourceType, List<Resource>> updatedStrongbox;
    private List<List<Resource>> updatedWarehouse;
    private boolean hasAuxiliaryDeposit;
    private List<AuxiliaryDeposit> auxiliaryDeposit;

    public EndProductionMessage(String user){
        this.user = user;
        this.hasAuxiliaryDeposit = false;
        auxiliaryDeposit = null;
    }

    /**
     * Execute the request server side
     * @param virtualView: receiver view
     */
    @Override
    public void execute(VirtualView virtualView) {
        virtualView.endProduction(user);
    }

    /**
     * Execute the request client side
     * @param view: receiver view
     */
    @Override
    public void execute(View view) {

        view.showProductionResult(updatedStrongbox,updatedWarehouse,auxiliaryDeposit);
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

    /**
     * Set the updated auxiliary deposit
     * @param auxiliaryDeposit the auxiliary deposit
     */
    public void setAuxiliaryDeposit(List<AuxiliaryDeposit> auxiliaryDeposit) {
        hasAuxiliaryDeposit = true;
        this.auxiliaryDeposit = auxiliaryDeposit;
    }

    /**
     * Get the updated auxiliary deposit
     * @return the auxiliary deposit
     */
    public List<AuxiliaryDeposit> getAuxiliaryDeposit() {
        return auxiliaryDeposit;
    }

}
