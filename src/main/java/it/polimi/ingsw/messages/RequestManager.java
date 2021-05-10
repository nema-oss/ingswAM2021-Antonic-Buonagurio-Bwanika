package it.polimi.ingsw.messages;

import it.polimi.ingsw.messages.actions.*;
import it.polimi.ingsw.messages.setup.ChooseLeadersMessage;
import it.polimi.ingsw.messages.setup.ChooseResourcesMessage;
import it.polimi.ingsw.messages.setup.GameModeMessage;
import it.polimi.ingsw.messages.setup.client.LoginRequest;
import it.polimi.ingsw.model.cards.DevelopmentCard;
import it.polimi.ingsw.model.cards.leadercards.LeaderCard;
import it.polimi.ingsw.model.gameboard.Resource;
import it.polimi.ingsw.model.gameboard.ResourceType;
import it.polimi.ingsw.view.client.View;

import java.net.Socket;
import java.util.ArrayList;

/**
 * this class manage the creation, the sending of messages request from client to the server
 */
public class RequestManager {
    MessageSender messageSender;
    Socket socket;
    View view;

    public RequestManager(View view) {
        this.view = view;
    }

    public void sendNickname(String nickname){
        messageSender = new MessageSender(socket, new LoginRequest(nickname));
        messageSender.sendMsg();
    }

    public void sendActivateBoardProduction(ResourceType resourceType){
        messageSender = new MessageSender(socket, new ActivateBoardProductionMessage(resourceType, true));
        messageSender.sendMsg();
    }

    public void sendActivateCardProduction(DevelopmentCard developmentCard){
        messageSender = new MessageSender(socket, new ActivateCardProductionMessage(developmentCard, true));
        messageSender.sendMsg();
    }

    public void sendActivateLeaderCard(LeaderCard leaderCard){
        messageSender = new MessageSender(socket, new ActivateLeaderProductionMessage(leaderCard, true));
        messageSender.sendMsg();
    }

    public void sendActivateLeaderProduction(LeaderCard leaderCard){
        messageSender = new MessageSender(socket, new ActivateLeaderProductionMessage(leaderCard, true));
        messageSender.sendMsg();
    }

    public void sendActivateProduction(){
        messageSender = new MessageSender(socket, new ActivateProductionMessage());
        messageSender.sendMsg();
    }

    public void sendBuyDevelopmentCard(int x, int y){
        messageSender = new MessageSender(socket, new BuyDevelopmentCardMessage(x, y, true));
        messageSender.sendMsg();
    }

    public void sendBuyResources(int x, int y){
        messageSender = new MessageSender(socket, new BuyResourcesMessage(x, y, true));
        messageSender.sendMsg();
    }

    public void sendDiscardLeaderCard(LeaderCard leaderCard){
        messageSender = new MessageSender(socket, new DiscardLeaderCardMessage(leaderCard));
        messageSender.sendMsg();
    }

    public void sendDiscardResources(ArrayList<Resource> resources){
        messageSender = new MessageSender(socket, new DiscardResourcesMessage(resources));
        messageSender.sendMsg();
    }

    public void sendMoveDeposit(int a, int b){
        messageSender = new MessageSender(socket, new MoveDepositMessage(a, b, true));
        messageSender.sendMsg();
    }

    public void sendPlaceResources(ArrayList<Resource> resources, ArrayList<Integer> toShelves){
        messageSender = new MessageSender(socket, new PlaceResourcesMessage(resources, toShelves));
        messageSender.sendMsg();
    }

    public void sendChooseLeaders(LeaderCard leaderCard){
        messageSender = new MessageSender(socket, new ChooseLeadersMessage(leaderCard, true));
        messageSender.sendMsg();
    }

    public void sendChooseResources(Resource resource){
        messageSender = new MessageSender(socket, new ChooseResourcesMessage(resource, true));
        messageSender.sendMsg();
    }

    public void sendGameMode(int n){
        messageSender = new MessageSender(socket, new GameModeMessage(n));
        messageSender.sendMsg();
    }

    public void sendPing(){
        messageSender = new MessageSender(socket, new PingMessage());
        messageSender.sendMsg();
    }
}
