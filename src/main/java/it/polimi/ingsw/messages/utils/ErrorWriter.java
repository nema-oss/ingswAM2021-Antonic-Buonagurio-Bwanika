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

    /**
     * this method sends a card purchase rejected message
     * @param user the player to notify
     * @param x row index of card
     * @param y column index of card
     * @return the rejection message
     */
    public Message buyCardRejected(String user, int x, int y) {
        return new BuyDevelopmentCardMessage(user,x,y,false);
    }

    /**
     * this method sends a resource purchase rejected message
     * @param user the player to notify
     * @param x row index of market
     * @param y column index of market
     * @return the rejection message
     */
    public Message buyResourceRejected(String user, int x, int y) {
        return new BuyResourcesMessage(user, x, y,false);
    }

    /**
     * this method sends a card production rejected message
     * @param user the player to notify
     * @param cards the list of cards to activate production on
     * @return the rejection message
     */
    public Message productionCardRejected(String user,List<DevelopmentCard> cards) {
        return new ActivateCardProductionMessage(user,cards,false);
    }

    /**
     * this method sends a board production rejected message
     * @param user the player to notify
     * @param resourceType the map of resources to get and to give
     * @return the rejection message
     */
    public Message productionBoardRejected(String user, Map<Resource, List<ResourceType>> resourceType) {
        return new ActivateBoardProductionMessage(user,resourceType,false);
    }

    /**
     * this method sends a leader production rejected message
     * @param user the player to notify
     * @param choice the map with the leader cards ant the resources to get
     * @return the rejection message
     */
    public Message productionLeaderRejected(String user, Map<LeaderCard, ResourceType> choice) {
        return new ActivateLeaderProductionMessage(user, choice, false);
    }

    /**
     * this method sends a leader card activation rejected message
     * @param card the card to activated
     * @return the rejection message
     */
    public Message activateLeaderRejected(LeaderCard card) {
        return new LeaderActionRejected();
    }

    /**
     * this method sends a move deposit request rejected message
     * @param user the player to notify
     * @param a the first floor to swap
     * @param b the second floor to swap
     * @return the rejection message
     */
    public Message moveDepositRequestRejected(String user,int a, int b) {
        return new MoveDepositMessage(user,a, b, false);
    }

    /**
     * this method sends a discard leader rejected message
     * @param card the card to discard
     * @return the rejection message
     */
    public Message discardLeaderRejected(LeaderCard card) {
        return new LeaderActionRejected();
    }

    /**
     * this method sends a resources placement rejected message
     * @param user the player to notify
     * @param userChoice a map with the resources and the positions chosen
     * @return the rejection message
     */
    public Message placeResourceRejected(String user, Map<Resource, Integer> userChoice) {
        PlaceResourcesMessage message = new PlaceResourcesMessage(user,userChoice);
        message.setAccepted(false);
        return message;
    }
}
