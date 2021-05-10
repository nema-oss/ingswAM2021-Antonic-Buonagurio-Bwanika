package it.polimi.ingsw.messages;

import it.polimi.ingsw.messages.actions.ActivateLeaderCardMessage;
import it.polimi.ingsw.messages.actions.BuyDevelopmentCardMessage;
import it.polimi.ingsw.messages.actions.BuyResourcesMessage;
import it.polimi.ingsw.messages.setup.ChooseLeadersMessage;
import it.polimi.ingsw.messages.setup.ChooseResourcesMessage;
import it.polimi.ingsw.model.cards.DevelopmentCard;
import it.polimi.ingsw.model.cards.leadercards.LeaderCard;
import it.polimi.ingsw.model.gameboard.Resource;
import it.polimi.ingsw.model.gameboard.ResourceType;

public class ErrorWriter {


    public Message rejectedLogin() {
    }

    public Message cardSelectionRejected(LeaderCard leaderCard) {
        return new ChooseLeadersMessage(leaderCard, false);
    }

    public Message resourceTypeSelectionRejected(ResourceType resourceType) {
        return new ChooseResourcesMessage(new Resource(resourceType), false);
    }

    public Message buyCardRejected(int x, int y) {
        return new BuyDevelopmentCardMessage(x, y, false);
    }

    public Message buyResourceRejected(int x, int y) {
        return new BuyResourcesMessage(x, y, false);
    }

    public Message productionCardRejected(DevelopmentCard card) {
    }

    public Message productionBoardRejected(ResourceType resourceType) {
    }

    public Message productionLeaderRejected(LeaderCard card) {
    }

    public Message activateLeaderRejected(LeaderCard card) {
        return new ActivateLeaderCardMessage(card, false);
    }
}
