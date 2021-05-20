package it.polimi.ingsw.messages.utils;

import it.polimi.ingsw.messages.actions.server.ActivateProductionAccepted;
import it.polimi.ingsw.messages.actions.server.BuyResourcesAccepted;
import it.polimi.ingsw.messages.actions.server.LeaderActionAccepted;
import it.polimi.ingsw.messages.setup.server.*;
import it.polimi.ingsw.messages.Message;
import it.polimi.ingsw.messages.actions.*;
import it.polimi.ingsw.model.cards.DevelopmentCard;
import it.polimi.ingsw.model.cards.leadercards.LeaderCard;
import it.polimi.ingsw.model.gameboard.ResourceType;

import java.util.List;
import java.util.Map;


/**
 * This class create the update messages sent by the server
 */
public class UpdateWriter {

    /**
     * This method create and return the login update that it's sent to other players when a new player join the match
     * @param nickname the nickname of the new player
     * @return the update login message
     */
    public Message loginUpdate(String nickname) {
        return  new NewUserLogged(nickname);
    }

    public Message cardSelectionAccepted(String user, List<LeaderCard> leaderCards) {
        return new ChooseLeadersMessage(user, leaderCards, true);
    }

    public Message resourceTypeSelectionAccepted(String user, Map<ResourceType,Integer> resourceTypeChoice) {
        return new ChooseResourcesMessage( user, resourceTypeChoice, true);
    }

    public Message moveDepositRequestAccepted(String user,int a, int b) {
        return new MoveDepositMessage(user,a, b, true);
    }

    public Message buyCardAccepted(String user,int x, int y) {
        return new BuyDevelopmentCardMessage(user,x, y, true);
    }

    public Message buyResourceAccepted(String user, int x, int y){
        return new BuyResourcesAccepted(user,x,y);
        //BuyResourcesMessage message = new BuyResourcesMessage(user,x, y, true);
    }

    public Message productionCardAccepted(String user,List<DevelopmentCard> cards) {
        return new ActivateProductionAccepted();
    }

    public Message productionBoardAccepted(String user,Map<ResourceType,List<ResourceType>> userChoice) {
        return new ActivateProductionAccepted();
    }

    public Message productionLeaderAccepted(List<LeaderCard> leaderCards) {
        return new ActivateProductionAccepted();
        //return new ActivateLeaderProductionMessage(leaderCards, true);
    }

    public Message activateLeaderAccepted(LeaderCard card) {
        return new LeaderActionAccepted();
    }

    public Message discardLeaderAccepted(LeaderCard card) {
        return new LeaderActionAccepted();
    }


    public Message playerDisconnection(String user){
        return new DisconnectionMessage(user);
    }

    public Message loginDone(String nickname) {
        return new LoginDoneMessage(nickname,true);
    }
}