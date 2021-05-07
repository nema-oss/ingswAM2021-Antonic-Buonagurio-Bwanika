package it.polimi.ingsw.messages;

import it.polimi.ingsw.model.cards.DevelopmentCard;
import it.polimi.ingsw.model.cards.leadercards.LeaderCard;
import it.polimi.ingsw.model.gameboard.ResourceType;

public class UpdateWriter {

    /**
     * This method create and return the login update that it's sent to other players when a new player join the match
     * @param nickname the nickname of the new player
     * @return the update login message
     */
    public Message loginUpdate(String nickname) {
        return (Message) new Object();
    }

    public Message cardSelectionAccepted(LeaderCard leaderCard) {
    }

    public Message resourceTypeSelectionAccepted(ResourceType resourceType) {
    }

    public Message moveDepositRequestAccepted() {
    }

    public Message moveDepositRequestRejected() {
    }

    public Message buyCardAccepted() {
    }

    public Message buyResourceAccepted(int x, int y) {
    }

    public Message productionCardAccepted(DevelopmentCard card) {
    }

    public Message productionBoardAccepted(ResourceType resourceType) {
    }

    public Message productionLeaderAccepted(LeaderCard card) {
    }

    public Message activateLeaderAccepted(LeaderCard card) {
    }

    public Message discardLeaderAccepted(LeaderCard card) {
    }

    public Message activateLeaderRejected(LeaderCard card) {
    }

    public Message playerDisconnection(String nickname) {
    }
}
