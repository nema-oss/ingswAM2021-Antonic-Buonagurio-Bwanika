package it.polimi.ingsw.messages.utils;

import it.polimi.ingsw.messages.actions.server.LeaderActionAccepted;
import it.polimi.ingsw.messages.setup.server.*;
import it.polimi.ingsw.messages.Message;
import it.polimi.ingsw.messages.actions.*;
import it.polimi.ingsw.model.cards.DevelopmentCard;
import it.polimi.ingsw.model.cards.leadercards.LeaderCard;
import it.polimi.ingsw.model.gameboard.Resource;
import it.polimi.ingsw.model.gameboard.ResourceType;

import java.util.List;
import java.util.Map;


/**
 * This class create the update messages sent by the server
 */
public class UpdateWriter {

    /**
     * This method creates and returns the login update that is sent to other players when a new player joins the match
     * @param nickname the nickname of the new player
     * @return the update login message
     */
    public Message loginUpdate(String nickname) {
        return new NewUserLogged(nickname);
    }


    /**
     * this method creates and returns the message that tells the player to choose the leader cards
     * @param user the player's nickname
     * @param leaderCards the leader cards to choose from
     * @return the card selection message
     */
    public Message cardSelectionAccepted(String user, List<LeaderCard> leaderCards) {
        return new ChooseLeadersMessage(user, leaderCards, true);
    }

    /**
     * this method creates and returns the message that sets the initial resources chosen by the player
     * @param user the player's nickname
     * @param resourceTypeChoice map with the resources chosen
     * @return the message with the chosen resources
     */
    public Message resourceTypeSelectionAccepted(String user, Map<ResourceType,Integer> resourceTypeChoice) {
        return new ChooseResourcesMessage( user, resourceTypeChoice, true);
    }

    /**
     * this method creates and returns the message representing a change in player's deposit
     * @param user the player's nickname
     * @param a first floor to swap
     * @param b second floor to swap
     * @return message to swap deposit floors
     */
    public Message moveDepositRequestAccepted(String user,int a, int b) {
        return new MoveDepositMessage(user,a, b, true);
    }

    /**
     * this method creates and returns the message representing the fact that the player has bought a development card
     * @param user the player's nickname
     * @param x row index in crad market
     * @param y column index in card market
     * @return message to buy development cards
     */
    public Message buyCardAccepted(String user,int x, int y) {
        return new BuyDevelopmentCardMessage(user,x, y, true);
    }

    /**
     * this method creates and returns the message representing the fact that the player wants to buy from marble market
     * @param user the player's nickname
     * @param x row index of the marble market
     * @param y column index of the marble market
     * @return message to buy resources
     */
    public Message buyResourceAccepted(String user, int x, int y){
        return new BuyResourcesMessage(user,x, y, true);
    }

    /**
     * this method creates and returns the message that tells that the player wants to activate his card production
     * @param user the player's nickname
     * @param cards the cards to activate the production on
     * @return the message to activate card production
     */
    public Message productionCardAccepted(String user,List<DevelopmentCard> cards) {
        return new ActivateCardProductionMessage(user,cards,true);
    }

    /**
     * this method creates and returns the message that tells that the player wants to activate his board production
     * @param user the player's nickname
     * @param userChoice the resources to put in the production and the resource type to get from it
     * @return the message to activate board production
     */
    public Message productionBoardAccepted(String user, Map<Resource, List<ResourceType>> userChoice) {
        return new ActivateBoardProductionMessage(user,userChoice,true);
    }

    /**
     * this method creates and returns the message that tells that the player wants to activate his leader cards production
     * @param user the player's nickname
     * @param leaderCards the cards to activate the production on
     * @return the message to activate leader card production
     */
    public Message productionLeaderAccepted(String user, List<LeaderCard> leaderCards) {
        return new ActivateLeaderProductionMessage(user, leaderCards, true);
    }

    /**
     * this method creates and returns the message representing the fact that a player wants to activate a leader card
     * @param card the leader card to activate
     * @return the message to perform a leader action of activation
     */
    public Message activateLeaderAccepted(LeaderCard card) {
        return new LeaderActionAccepted(card,true);
    }

    /**
     * this method creates and returns the message representing the fact that the player wants to discard a leader card
     * @param card the leader card to discard
     * @return the message to perform a leader action od discard
     */
    public Message discardLeaderAccepted(LeaderCard card) {
        return new LeaderActionAccepted(card,false);
    }


    /**
     * this method creates and returns a message representing the fact that a player has disconnected
     * @param user the player's nickname
     * @return the disconnection message
     */
    public Message playerDisconnection(String user){
        return new DisconnectionMessage(user);
    }

    /**
     * this method creates and returns the message representing the fact that a player has logged in
     * @param nickname the player's nickname
     * @return the message to end login
     */
    public Message loginDone(String nickname) {
        return new LoginDoneMessage(nickname,true);
    }

    /**
     * this method creates and returns the message representing the fact that the player wants to place the resources in his deposit
     * @param user the player's nickname
     * @param userChoice the resources and the floor where to put them
     * @return the message to place the resources
     */
    public Message placeResourceAccepted(String user, Map<Resource, Integer> userChoice) {
        PlaceResourcesMessage message = new PlaceResourcesMessage(user,userChoice);
        message.setAccepted(true);
        return message;
    }
}