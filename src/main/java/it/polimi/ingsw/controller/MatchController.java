package it.polimi.ingsw.controller;

import it.polimi.ingsw.model.Game;
import it.polimi.ingsw.model.cards.leadercards.LeaderCard;
import it.polimi.ingsw.model.exception.FullDepositException;
import it.polimi.ingsw.model.gameboard.Resource;
import it.polimi.ingsw.model.gameboard.ResourceType;
import it.polimi.ingsw.model.player.Player;
import it.polimi.ingsw.view.server.VirtualView;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class MatchController implements ControllerInterface{
    private Game game;
    private VirtualView virtualView;
    private PlayerController playerController;

    public MatchController() {
        game = new Game();
        virtualView = null;
        playerController = new PlayerController();
    }

    public void setVirtualView(VirtualView virtualView){
        this.virtualView = virtualView;
    }

    @Override
    public void onNewPlayer(String nickname) {
        //manca il controllo sullo stato del gioco
        //manca controllo di nickname già esistente
        if(nickname == null || nickname.isEmpty()) throw new IllegalArgumentException("Invalid nickname");
        playerController.addPlayer(new Player(nickname, game.getGameBoard(), game));
    }

    @Override
    public void onSTartGame() {
        int numberOfPlayers = playerController.getNumberOfPlayers();
        //manca avviso che dice se il gioco è in single o in multiplayer e con quanti giocatori
        //manca avviso del "giochi al tot turno"
    }

    @Override
    public void onChooseLeaderCards(String nickname) {
        LeaderCard[] leaders = new LeaderCard[4];
        for (int i=0; i<4; i++){
            leaders[i] = game.getLeaderDeck().drawCard();
        }
        playerController.nextTurn();
    }

    @Override
    public void onLeaderCardsChosen(String nickname, List<LeaderCard> leaderCardsChosen){
        //manca il controllo se non è il suo turno
        game.getCurrentPlayer().setHand(leaderCardsChosen);
    }

    @Override
    public void onChooseResources(String nickname, ArrayList<ResourceType> resourceTypes) {
        int index = playerController.getPlayerIndex();
        if(index==0)
            //manda messaggio che non ha diritto a nulla
            ;
        else if(index==2||index==3) {
            //manda messaggio che possono scegliere una risorsa
            if(index==3)
                game.getCurrentPlayer().moveOnPopeRoad();
        }
        else if(index==4) {
            //manda messaggio: puoi scegliere due risorse
            game.getCurrentPlayer().moveOnPopeRoad(2);
        }
    }

    @Override
    public void onResourcesChosen(String nickname, Map<ResourceType,Integer> resourcesChosen){
        for(ResourceType r : resourcesChosen.keySet()) {
            try {
                game.getCurrentPlayer().addResourceToDeposit(resourcesChosen.get(r), new Resource(r));
            }catch(Exception | FullDepositException e){
                // di' che non puoi aggiungere le risorse
            }
        }
    }

    @Override
    public void onActivateProduction(String nickname) {

    }

    @Override
    public void onBuyDevelopmentCards(String nickname, int row, int column) {

    }

    @Override
    public void onBuyResources(String nickname, int row, int column) {

    }

    @Override
    public void onActivateLeader(String nickname, LeaderCard leaderCard) {

    }

    @Override
    public void onDiscardLeader(String nickname, LeaderCard leaderCard) {

    }

    @Override
    public void onEndTurn(String nickname) {

    }

    @Override
    public void sendChooseLeaderCards() {

    }

    @Override
    public void sendChooseResources() {

    }

    @Override
    public void sendPlayTurn() {

    }
}
