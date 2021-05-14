package it.polimi.ingsw.messages.utils;

import it.polimi.ingsw.messages.Message;
import it.polimi.ingsw.messages.actions.*;
import it.polimi.ingsw.messages.setup.server.ChooseLeadersMessage;
import it.polimi.ingsw.messages.setup.server.ChooseResourcesMessage;
import it.polimi.ingsw.messages.setup.server.LoginRefusedMessage;
import it.polimi.ingsw.model.cards.DevelopmentCard;
import it.polimi.ingsw.model.cards.leadercards.LeaderCard;
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
    public Message cardSelectionRejected(List<LeaderCard> leaderCards) {
        return new ChooseLeadersMessage(leaderCards, false);
    }

    /**
     * This method sends a resource selection rejected message
     * @param resourceType the selected types
     * @return the rejection message
     */
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

    public Message moveDepositRequestRejected(String user,int a, int b) {
        return new MoveDepositMessage(user,a, b, false);
    }

}
