package it.polimi.ingsw.controller;

import it.polimi.ingsw.model.Game;
import it.polimi.ingsw.model.cards.DevelopmentCard;
import it.polimi.ingsw.model.cards.leadercards.LeaderCard;
import it.polimi.ingsw.model.exception.FullDepositException;
import it.polimi.ingsw.model.exception.InsufficientDevelopmentCardsException;
import it.polimi.ingsw.model.exception.InsufficientPaymentException;
import it.polimi.ingsw.model.exception.NonExistentCardException;
import it.polimi.ingsw.model.gameboard.Resource;
import it.polimi.ingsw.model.gameboard.ResourceType;
import it.polimi.ingsw.model.player.Player;
import it.polimi.ingsw.view.server.VirtualView;

import javax.naming.InsufficientResourcesException;
import java.util.*;

public class MatchController implements ControllerInterface{

    // manca la scelta della gamemode: ogni player che si connette dovrebbe scegliere in che modalità vuole giocare.
    // invece di una lista di errori posso mandare una lista di exceptions?



    private Game game;
    private VirtualView virtualView;

    public MatchController() {
        game = new Game();
        virtualView = null;
    }

    public void setVirtualView(VirtualView virtualView){
        this.virtualView = virtualView;
    }


    @Override
    //aggiungo giocatore al game
    public List<Error> onNewPlayer(String nickname) {

        //manca il controllo sullo stato del gioco
        if(nickname == null || nickname.isEmpty()) throw new IllegalArgumentException("Invalid nickname");
        return game.addPlayer(new Player(nickname, game.getGameBoard(), game));
    }

    @Override
    // assegnazione turni
    public List<Error> onStartGame() {

        //manca controllo sullo stato del gioco
        int numberOfPlayers = game.getListOfPlayers().size();
        if(numberOfPlayers == 1){
            game.setSinglePlayerCPU();
            //la virtual view manda il messaggio sulla gamemode??
        }
        game.setPlayersOrder();
        //che errori può ritornare oltre allo stato del gioco?
    }

    @Override
    // mando le 4 carte tra cui scegliere
    public void sendChooseLeaderCards() {

        List<Error> errors = new ArrayList<>();
        LeaderCard[] leaders = new LeaderCard[4];

        game.getLeaderDeck().shuffle();
        for (int i=0; i<4; i++){
            leaders[i] = game.getLeaderDeck().drawCard();
        }
        virtualView.toDoChooseLeaderCards(game.getCurrentPlayer().getNickname(), leaders );
    }

    @Override
    // ricevo le leader scelte e le assegno
    public List<Error> onLeaderCardsChosen(String nickname, List<LeaderCard> leaderCardsChosen){

        //manca il controllo se non è il suo turno
        List<Error> errors = new ArrayList<>();

        if(!nickname.equals(game.getCurrentPlayer().getNickname())) {
            errors.add(Error.NOT_YOUR_TURN);
            return errors;
        }
        game.getCurrentPlayer().setHand(leaderCardsChosen);

        return errors;
    }

    @Override
    //dico di scegliere le risorse e assegno i punti fede
    public void sendChooseResources() {

        if(game.getCurrentPlayer().equals(game.getListOfPlayers().get(0)))
            //manda messaggio che non ha diritto a nulla
            ;
        else if(game.getCurrentPlayer().equals(game.getListOfPlayers().get(1)) || game.getCurrentPlayer().equals(game.getListOfPlayers().get(2))) {
            virtualView.toDoChooseResources(game.getCurrentPlayer().getNickname(), 1);
            if(game.getCurrentPlayer().equals(game.getListOfPlayers().get(2)))
                game.getCurrentPlayer().moveOnPopeRoad();
        }

        else if(game.getCurrentPlayer().equals(game.getListOfPlayers().get(3))) {
            virtualView.toDoChooseResources(game.getCurrentPlayer().getNickname(), 0);
            game.getCurrentPlayer().moveOnPopeRoad(2);
        }
    }

    @Override
    //ricevo le risorse sclete e gliele do
    public List<Error> onResourcesChosen(String nickname, Map<ResourceType,Integer> resourcesChosen){

        List<Error> errors = new ArrayList<>();

        if(!nickname.equals(game.getCurrentPlayer().getNickname())) {
            errors.add(Error.NOT_YOUR_TURN);
            return errors;
        }

        for(ResourceType r : resourcesChosen.keySet()) {
            try {
                game.getCurrentPlayer().addResourceToDeposit(resourcesChosen.get(r), new Resource(r));
            }catch(Exception | FullDepositException e){
                // manda messaggio : non puoi aggiungere le risorse
            }
        }

        return errors;
    }

    @Override
    //attivo la produzione
    public List<Error> onActivateProduction(String nickname, int cardIndex) {

        //manca controllo sulle azioni possibili
        List<Error> errors = new ArrayList<>();

        if(!nickname.equals(game.getCurrentPlayer().getNickname())) {
            errors.add(Error.NOT_YOUR_TURN);
            return errors;
        }

        try{
            game.getCurrentPlayer().activateProduction(cardIndex);
        } catch (FullDepositException e){
            errors.add(Error.DEPOSIT_IS_FULL);
        } catch(InsufficientPaymentException e){
            errors.add(Error.INSUFFICIENT_PAYMENT);
        } catch (Exception e){
            errors.add(Error.GENERIC);
        }
        return errors;

    }

    @Override
    //do le developmentCrads al player
    public List<Error> onBuyDevelopmentCards(String nickname, int row, int column) {

        //manca controllo sulle azioni possibili
        List<Error> errors = new ArrayList<>();
        if(!nickname.equals(game.getCurrentPlayer().getNickname())){
            errors.add(Error.NOT_YOUR_TURN);
            return errors;
        }

        try {
            game.getCurrentPlayer().buyDevelopmentCard(row,column);
        } catch (InsufficientPaymentException e) {
            errors.add(Error.INSUFFICIENT_PAYMENT);
        } catch (NonExistentCardException e) {
            errors.add(Error.CARD_DOESNT_EXIST);
        } catch (Exception e) {
            errors.add(Error.GENERIC);
        }

        return errors;
    }

    @Override
    //do le risorse al player
    public List<Error> onBuyResources(String nickname, int row, int column) {

        //manca il controllo sulle azioni possibili
        List<Error> errors = new ArrayList<>();

        if(!nickname.equals(game.getCurrentPlayer().getNickname())){
            errors.add(Error.NOT_YOUR_TURN);
            return errors;
        }

        try {
            game.getCurrentPlayer().buyResources(row, column);
        } catch (FullDepositException e) {
            errors.add(Error.DEPOSIT_IS_FULL);
        }

        return errors;
    }

    @Override
    //eseguo l'azione del leader scelto
    public List<Error> onActivateLeader(String nickname, int index) {

        //manca il controllo sulle azioni possibili
        List<Error> errors = new ArrayList<>();

        if(!nickname.equals(game.getCurrentPlayer().getNickname())){
            errors.add(Error.NOT_YOUR_TURN);
            return errors;
        }

        try {
            game.getCurrentPlayer().activateLeaderCard(index);
        } catch (NonExistentCardException e) {
            errors.add(Error.CARD_DOESNT_EXIST);
        } catch (InsufficientResourcesException e) {
            errors.add(Error.INSUFFICIENT_PAYMENT);
        } catch (InsufficientDevelopmentCardsException e) {
            errors.add(Error.INSUFFICIENT_PAYMENT);
        }

        return errors;
    }

    @Override
    //rimuovo il leader dalla hand del player
    public List<Error> onDiscardLeader(String nickname, int index) {
        // manca il controllo sulle azioni possibili

        List<Error> errors = new ArrayList<>();

        if(!nickname.equals(game.getCurrentPlayer().getNickname())){
            errors.add(Error.NOT_YOUR_TURN);
            return errors;
        }

        try {
            game.getCurrentPlayer().discardLeader(index);
        } catch (NonExistentCardException e) {
            errors.add(Error.CARD_DOESNT_EXIST);
        }
        return errors;
    }


    @Override
    public void sendPlayTurn() {

    }

    @Override
    public List<Error> onPlayerDisconnection(String nickname) {
        List<Error> errors = new ArrayList<>();
        return errors;
    }

    public List<Error> onEndTurn(String nickname){

        List<Error> errors = new ArrayList<>();

        if(!nickname.equals(game.getCurrentPlayer().getNickname())){
            errors.add(Error.NOT_YOUR_TURN);
            return errors;
        }

        //controllo se si è a fine game
        int numOfCards = 0;
        for(Stack<DevelopmentCard> stack : game.getCurrentPlayer().getPlayerBoard().getDevelopmentCards())
            numOfCards = numOfCards + stack.size();
        if(numOfCards == 7 || game.getCurrentPlayer().getPositionIndex() == game.getCurrentPlayer().getPopeRoad().getSize()-1) {
            Player winner = game.endGame();
            //manda messaggio di vittoria
            return errors;
        }

        game.nextPlayer();
        //dopo questo devo fare altro?
        return errors;
    }
}
