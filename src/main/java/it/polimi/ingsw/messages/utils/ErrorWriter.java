package it.polimi.ingsw.messages.utils;

import it.polimi.ingsw.messages.Message;
import it.polimi.ingsw.messages.actions.*;
import it.polimi.ingsw.messages.actions.server.LeaderActionRejected;
import it.polimi.ingsw.messages.setup.server.ChooseLeadersMessage;
import it.polimi.ingsw.messages.setup.server.ChooseResourcesMessage;
import it.polimi.ingsw.messages.setup.server.LoginRefusedMessage;
import it.polimi.ingsw.model.cards.DevelopmentCard;
import it.polimi.ingsw.model.cards.leadercards.LeaderCard;
import it.polimi.ingsw.model.gameboard.Resource;
import it.polimi.ingsw.model.gameboard.ResourceType;

import java.util.List;
import java.util.Map;


/**
 * This class handles the error messages sent by the server
 */
public class ErrorWriter {

    public ErrorWriter(){

    }

    /**
     * This method create and return login refused message
     * @return login refused message
     */
    public Message rejectedLogin(boolean isFirstPlayer) {
        return new LoginRefusedMessage(isFirstPlayer);
    }


    /**
     * This method sends a card selection rejected message
     * @param leaderCards the selected cards
     * @return the rejection message
     */
    public Message cardSelectionRejected(String user, List<LeaderCard> leaderCards) {
        return new ChooseLeadersMessage(user,leaderCards, false);
    }

    /**
     * This method sends a resource selection rejected message
     * @param resourceType the selected types
     * @return the rejection message
     */
    public Message resourceTypeSelectionRejected(String user, Map<ResourceType,Integer> resourceType) {
        return new ChooseResourcesMessage( user, resourceType, false);
    }

    public Message buyCardRejected(String user, int x, int y) {
        return new BuyDevelopmentCardMessage(user,x,y,false);
    }

    public Message buyResourceRejected(String user, int x, int y) {
        return new BuyResourcesMessage(user, x, y,false);
    }

    public Message productionCardRejected(String user,List<DevelopmentCard> cards) {
        return new ActivateCardProductionMessage(user,cards,false);
    }

    public Message productionBoardRejected(String user, Map<Resource, List<ResourceType>> resourceType) {
        return new ActivateBoardProductionMessage(user,resourceType,false);
    }

    public Message productionLeaderRejected(String user, List<LeaderCard> card) {
        return new ActivateLeaderProductionMessage(user, card, false);
    }

    public Message activateLeaderRejected(LeaderCard card) {
        return new LeaderActionRejected();
    }

    public Message moveDepositRequestRejected(String user,int a, int b) {
        return new MoveDepositMessage(user,a, b, false);
    }

    public Message discardLeaderRejected(LeaderCard card) {
        return new LeaderActionRejected();
    }

    public Message placeResourceRejected(String user, Map<Resource, Integer> userChoice) {
        Message message = new PlaceResourcesMessage(user,userChoice);
        ((PlaceResourcesMessage) message).setAccepted(false);
        return message;
    }
}
