package it.polimi.ingsw.messages.utils;

import it.polimi.ingsw.messages.Message;
import it.polimi.ingsw.messages.actions.*;
import it.polimi.ingsw.messages.setup.ChooseLeadersMessage;
import it.polimi.ingsw.messages.setup.ChooseResourcesMessage;
import it.polimi.ingsw.messages.setup.server.LoginDoneMessage;
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
    public Message rejectedLogin() {
        return new LoginRefusedMessage();
    }



    public Message cardSelectionRejected(List<LeaderCard> leaderCards) {
        return new ChooseLeadersMessage(leaderCards, false);
    }

    public Message resourceTypeSelectionRejected(Map<ResourceType,Integer> resourceType) {
        return new ChooseResourcesMessage( resourceType, false);
    }

    public Message buyCardRejected(int x, int y) {
        return new BuyDevelopmentCardMessage(x, y, false);
    }

    public Message buyResourceRejected(int x, int y) {
        return new BuyResourcesMessage(x, y, false);
    }

    public Message productionCardRejected(List<DevelopmentCard> cards) { return new ActivateCardProductionMessage(cards, false);
    }

    public Message productionBoardRejected(ResourceType resourceType) { return new ActivateBoardProductionMessage(resourceType, false);
    }

    public Message productionLeaderRejected(List<LeaderCard> card) { return new ActivateLeaderProductionMessage(card, false);
    }

    public Message activateLeaderRejected(LeaderCard card) {
        return new ActivateLeaderCardMessage(card, false);
    }
}
