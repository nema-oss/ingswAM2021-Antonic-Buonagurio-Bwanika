package it.polimi.ingsw.cards.leadercards;

import it.polimi.ingsw.gameboard.Resource;
import it.polimi.ingsw.cards.Card;
import it.polimi.ingsw.gameboard.ResourceType;

import java.util.Map;

public abstract class LeaderCard <T> implements Card {

    private Map<ResourceType, Integer> costResource;
    private Map<Map<ResourceType, Integer> , Integer> costDevelopment;
    private int victoryPoints;
    private Boolean isActive;

    public LeaderCard(Map<ResourceType, Integer> costResource, Map<Map<ResourceType, Integer>, Integer> costDevelopment, int victoryPoints, Boolean isActive) {
        this.costResource = costResource;
        this.costDevelopment = costDevelopment;
        this.victoryPoints = victoryPoints;
        this.isActive = isActive;
    }

    public Map<ResourceType, Integer> getCostResource() {
        return costResource;
    }

    public  Map<Map<ResourceType, Integer> , Integer> getCostDevelopment() {
        return costDevelopment;
    }

    @Override
    public int getVictoryPoints() {
        return victoryPoints;
    }

    public void useLeaderAction(){

    }

    public abstract LeaderCardType getLeaderType();

    public abstract T useEffect() ;

    public Boolean getActive() {
        return isActive;
    }

    public void activateEffect(){
        isActive = true;
    }
}
