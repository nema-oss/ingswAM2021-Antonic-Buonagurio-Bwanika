package it.polimi.ingsw.messages;

import it.polimi.ingsw.messages.actions.*;
import it.polimi.ingsw.messages.setup.*;
import it.polimi.ingsw.messages.setup.server.LoginDoneMessage;
import it.polimi.ingsw.view.client.View;
import it.polimi.ingsw.view.server.VirtualView;

import java.net.Socket;

/**
 * this class manage the sending of messages from server to client, the parsing of client's request and the creation of
 * new messages
 */

public class MessageManager {


    public void parseMessage(VirtualView virtualView, Message message) {
        if (message instanceof ActivateLeaderCardMessage){
            ((ActivateLeaderCardMessage) message).execute(virtualView);
        }
        else if(message instanceof ActivateProductionMessage){
            ((ActivateProductionMessage) message).execute(virtualView);
        }
        else if(message instanceof BuyDevelopmentCardMessage){
            ((BuyDevelopmentCardMessage) message).execute(virtualView);
        }
        else if(message instanceof BuyResourcesMessage){
            ((BuyResourcesMessage) message).execute(virtualView);
        }
        else if(message instanceof DiscardLeaderCardMessage){
            ((DiscardLeaderCardMessage) message).execute(virtualView);
        }
        else if(message instanceof DiscardResourcesMessage){
            ((DiscardResourcesMessage) message).execute(virtualView);
        }
        else if(message instanceof MoveDepositMessage){
            ((MoveDepositMessage) message).execute(virtualView);
        }
        else if(message instanceof PlaceResourcesMessage){
            ((PlaceResourcesMessage) message).execute(virtualView);
        }
        else if(message instanceof ChooseLeadersMessage){
            ((ChooseLeadersMessage) message).execute(virtualView);
        }
        else if(message instanceof ChooseResourcesMessage){
            ((ChooseResourcesMessage) message).execute(virtualView);
        }
        else if(message instanceof GameModeMessage){
            ((GameModeMessage) message).execute(virtualView);
        }
        else if(message instanceof LoginMessage){
            ((LoginMessage) message).execute(virtualView);
        }
        else if(message instanceof ActivateBoardProductionMessage){
            ((ActivateBoardProductionMessage) message).execute(virtualView);
        }
        else if(message instanceof ActivateCardProductionMessage){
            ((ActivateCardProductionMessage) message).execute(virtualView);
        }
        else if(message instanceof ActivateLeaderProductionMessage){
            ((ActivateLeaderProductionMessage) message).execute(virtualView);
        }
    }

    public void parseMessageFromServer(Socket server, Message message, View view) {
        if (message instanceof ActivateLeaderCardMessage){
            ((ActivateLeaderCardMessage) message).execute(view);
        }
        else if(message instanceof ActivateProductionMessage){
            ((ActivateProductionMessage) message).execute(view);
        }
        else if(message instanceof BuyDevelopmentCardMessage){
            ((BuyDevelopmentCardMessage) message).execute(view);
        }
        else if(message instanceof BuyResourcesMessage){
            ((BuyResourcesMessage) message).execute(view);
        }
        else if(message instanceof DiscardLeaderCardMessage){
            ((DiscardLeaderCardMessage) message).execute(view);
        }
        else if(message instanceof DiscardResourcesMessage){
            ((DiscardResourcesMessage) message).execute(view);
        }
        else if(message instanceof MoveDepositMessage){
            ((MoveDepositMessage) message).execute(view);
        }
        else if(message instanceof PlaceResourcesMessage){
            ((PlaceResourcesMessage) message).execute(view);
        }
        else if(message instanceof ChooseLeadersMessage){
            ((ChooseLeadersMessage) message).execute(view);
        }
        else if(message instanceof ChooseResourcesMessage){
            ((ChooseResourcesMessage) message).execute(view);
        }
        else if(message instanceof GameModeMessage){
            ((GameModeMessage) message).execute(view);
        }
        else if(message instanceof LoginMessage){
            ((LoginMessage) message).execute(view);
        }
        else if(message instanceof LoginDoneMessage){
            ((LoginDoneMessage) message).execute(view);
        }
        else if(message instanceof BeginMessage){
            ((BeginMessage) message).execute(view);
        }
        else if(message instanceof DisconnectionMessage){
            ((DisconnectionMessage) message).execute(view);
        }
        else if(message instanceof EndGameMessage){
            ((EndGameMessage) message).execute(view);
        }
        else if(message instanceof ActivateBoardProductionMessage){
            ((ActivateBoardProductionMessage) message).execute(view);
        }
        else if(message instanceof ActivateCardProductionMessage){
            ((ActivateCardProductionMessage) message).execute(view);
        }
        else if(message instanceof ActivateLeaderProductionMessage){
            ((ActivateLeaderProductionMessage) message).execute(view);
        }
    }
}
