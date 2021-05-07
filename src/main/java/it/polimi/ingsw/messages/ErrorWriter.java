package it.polimi.ingsw.messages;

import it.polimi.ingsw.model.cards.DevelopmentCard;
import it.polimi.ingsw.model.cards.leadercards.LeaderCard;
import it.polimi.ingsw.model.gameboard.ResourceType;

public class ErrorWriter {


    public Message rejectedLogin() {
    }

    public Message cardSelectionRejected(LeaderCard leaderCard) {
    }

    public Message resourceTypeSelectionRejected(ResourceType resourceType) {
    }

    public Message buyCardRejected() {
    }

    public Message buyResourceRejected(int x, int y) {
    }

    public Message productionCardRejected(DevelopmentCard card) {
    }

    public Message productionBoardRejected(ResourceType resourceType) {
    }

    public Message productionLeaderRejected(LeaderCard card) {
    }

    public Message activateLeaderRejected(LeaderCard card) {
    }
}
