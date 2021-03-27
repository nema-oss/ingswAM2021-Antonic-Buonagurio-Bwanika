package it.polimi.ingsw;


import java.util.ArrayList;
import java.util.Map;

import static it.polimi.ingsw.ResourceType.SHIELD;

/*
*ExtraDepositLeaderCard represents the LeaderCard with the effect of having two extra slots for a resource
*@author Nemanja
 */
public class ExtraDeposit implements EffectStrategy {
    private ResourceType storageType;
    private ArrayList<Resource> resources;
    private int availableSlots;
    private int victoryPoints;
    private Map<ResourceType, Integer> costResource;

    public ExtraDeposit(ResourceType storageType, ArrayList<Resource> resources, int availableSlots, int victoryPoints, Map<ResourceType, Integer> costResource) {
        this.storageType = storageType;
        this.resources = resources;
        this.availableSlots = availableSlots;
        this.victoryPoints = victoryPoints;
        this.costResource = costResource;
    }

    /*
     * this method returns the victory points of the leaderCard
     * @return victoryPoints(type:int) of ExtraDepositLeaderCard
     */
    public int getVictoryPoints() {
        return victoryPoints;
    }

    public Map<ResourceType, Integer> getCostResource() {
        return costResource;
    }

    /*
     * method that allows player p to put resources he bought at the market in the extra storage instead of his deposit
     * @return void
         */
    @Override
    public void useEffect(Player p) throws LeaderCardException{
        /*
        if the extra storage is full throw an exception
         */
        if(resources.size()>=availableSlots) {
            throw new LeaderCardException();
        }
        else
            addResources();
        return;

    }

    public void addResources(){
        /*
        ask the user for the resources and add them to the arrayList
        STUB
         */
        resources.add(new Resource(SHIELD));
    }

    /*
        * method that allows player p to withdraw n resources stored in the leaderCard
        * @return resources(type:ArrayList<Resources>)
     */


    public ArrayList<Resource> withdraw(Player p, int n) {
        ArrayList<Resource> resourcesOut = new ArrayList<Resource>();
        if(n>resources.size())
            n=resources.size();

        for(int i=0; i<n; i++){
            resourcesOut.add(resources.get(i));
            resources.remove(i);
        }

        return resourcesOut;
    }

    /*
     * this method returns the type of the extra resources the leaderCard lets the player hold
     * @return storageType(type:ResourceType) of ExtraDepositLeaderCard
     */
    public ResourceType getStorageType() {
        return storageType;
    }
    /*
     * this method returns the number of used slots in the leaderCard
     * @return usedStorage(type:int) of ExtraDepositLeaderCard
     */
    public ArrayList<Resource> getResources() {
        return resources;
    }
    /*
     * this method returns the number of extra slots the leaderCard has
     * @return availableSlots(type:int) of ExtraDepositLeaderCard
     */
    public int getAvailableSlots() {
        return availableSlots;
    }
}
