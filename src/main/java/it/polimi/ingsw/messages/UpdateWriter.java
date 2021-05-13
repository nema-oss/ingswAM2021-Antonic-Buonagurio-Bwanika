package it.polimi.ingsw.messages;

import it.polimi.ingsw.messages.actions.*;
import it.polimi.ingsw.messages.setup.ChooseLeadersMessage;
import it.polimi.ingsw.messages.setup.ChooseResourcesMessage;
import it.polimi.ingsw.messages.setup.server.NewUserLogged;
import it.polimi.ingsw.model.cards.DevelopmentCard;
import it.polimi.ingsw.model.cards.leadercards.LeaderCard;
import it.polimi.ingsw.model.gameboard.ResourceType;

import java.util.List;
import java.util.Map;

public class UpdateWriter {

    /**
     * This method create and return the login update that it's sent to other players when a new player join the match
     * @param nickname the nickname of the new player
     * @return the update login message
     */
    public Message loginUpdate(String nickname) {
        return  new NewUserLogged(nickname);
    }

    public Message cardSelectionAccepted(List<LeaderCard> leaderCards) {
        return new ChooseLeadersMessage(leaderCards, true);
    }

    public Message resourceTypeSelectionAccepted(Map<ResourceType,Integer> resourceTypeChoice) {
        return new ChooseResourcesMessage( resourceTypeChoice, true);
    }

    public Message moveDepositRequestAccepted(int a, int b) {
        return new MoveDepositMessage(a, b, true);
    }

    public Message moveDepositRequestRejected(int a, int b) {
        return new MoveDepositMessage(a, b, false);
    }

    public Message buyCardAccepted(int x, int y) {
        return new BuyDevelopmentCardMessage(x, y, true);
    }

    public Message buyResourceAccepted(int x, int y) {
        return new BuyResourcesMessage(x, y, true);
    }

    public Message productionCardAccepted(List<DevelopmentCard> cards) {
        return new ActivateCardProductionMessage(cards, true);
    }

    public Message productionBoardAccepted(ResourceType resourceType) {
        return new ActivateBoardProductionMessage(resourceType, true);
    }

    public Message productionLeaderAccepted(List<LeaderCard> leaderCards) {
        return new ActivateLeaderProductionMessage(leaderCards, true);
    }

    public Message activateLeaderAccepted(LeaderCard card) {
        return new ActivateLeaderCardMessage(card, true);
    }

    public Message discardLeaderAccepted(LeaderCard card) {
        return new DiscardLeaderCardMessage(card);
    }

    public Message activateLeaderRejected(LeaderCard card) {
        return new ActivateLeaderCardMessage(card, false);
    }

    public Message playerDisconnection(String user){
        return new DisconnectionMessage(user);
    }
}
