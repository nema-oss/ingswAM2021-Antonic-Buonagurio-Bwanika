package it.polimi.ingsw.cards.leadercards;

import it.polimi.ingsw.cards.DevelopmentCardType;
import it.polimi.ingsw.gameboard.Resource;
import it.polimi.ingsw.cards.Card;
import it.polimi.ingsw.gameboard.ResourceType;

import java.util.Map;

public abstract class LeaderCard <T> implements Card {

    public Map<ResourceType, Integer> costResource;
    public Map<Integer, Map<DevelopmentCardType, Integer>> costDevelopment;
    public int victoryPoints;


    public LeaderCard(Map<ResourceType, Integer> costResource, Map<Integer,Map<DevelopmentCardType, Integer>> costDevelopment, int victoryPoints) {
        this.costResource = costResource;
        this.costDevelopment = costDevelopment;
        this.victoryPoints = victoryPoints;
    }

    public Map<ResourceType, Integer> getCostResource() {
        return costResource;
    }

    public  Map<Integer,Map<DevelopmentCardType, Integer>> getCostDevelopment() {
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

}
