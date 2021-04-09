package it.polimi.ingsw.player;

/*
       * sketch of the Player class, most methods are unfinished
 */


import it.polimi.ingsw.cards.Card;
import it.polimi.ingsw.cards.DevelopmentCard;
import it.polimi.ingsw.cards.leadercards.AuxiliaryDeposit;
import it.polimi.ingsw.cards.leadercards.LeaderCard;
import it.polimi.ingsw.exception.FullDepositException;
import it.polimi.ingsw.exception.InsufficientPaymentException;
import it.polimi.ingsw.exception.NonexistentCardException;
import it.polimi.ingsw.exception.ProductionRequirementsException;
import it.polimi.ingsw.gameboard.*;

import javax.naming.InsufficientResourcesException;
import java.util.ArrayList;
import java.util.Map;
import java.util.Optional;

 public class Player{}

/*
    private GameBoard gameBoard;
    private Game currentGame;
    private String nickname;
    private ArrayList<LeaderCard> hand;
    private ArrayList<LeaderCard> activeLeaderCards;
    private int victoryPoints;
    private Optional<AuxiliaryDeposit> auxiliaryDeposit;
    private int positionIndex;
    private Cell position;


    private Board playerBoard;

    public Player(String nickname, GameBoard gameBoard, Game currentGame){

        playerBoard = new Board();
        position = playerBoard.getPopeRoad().getCurrentPosition();
        this.nickname = nickname;
        this.gameBoard = gameBoard;
        this.currentGame = currentGame;
        victoryPoints = 0;
    }


    public ArrayList<LeaderCard> getHand() {
        return hand;
    }

    public Board getPlayerBoard() {
        return playerBoard;
    }

    public ArrayList<LeaderCard> getActiveLeaderCards() {
        return activeLeaderCards;
    }

    public void setHand(ArrayList<LeaderCard> hand) {
        this.hand = hand;
    }

    public int getPositionIndex() {
        return playerBoard.getPopeRoad().getCurrentPositionIndex();
    }

    public Cell getPosition() {
        return position;
    }

    public Deposit getDeposit(){
        return playerBoard.getDeposit();
    }

    public Strongbox getStrongbox(){
        return playerBoard.getStrongbox();
    }

    public PopeRoad getPopeRoad(){
        return playerBoard.getPopeRoad();
    }

    public String getNickname() {
        return nickname;
    }

    public void addVictoryPoints(int victoryPoints) {

        this.victoryPoints += victoryPoints;
    }

    public int getVictoryPoints() {
        return victoryPoints;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }



    public void buyDevelopmentCard(int x, int y) throws InsufficientPaymentException, NonexistentCardException, InsufficientResourcesException {

        CardMarket market = gameBoard.getCardMarket();
        DevelopmentCard newCard = market.getCard(x,y);
        checkCardRequirementsBuy(newCard);
        Map<ResourceType,Integer> cost = newCard.getCost();
        for(ResourceType type: cost.keySet())
            getPlayerBoard().getDeposit().getResources(type,cost.get(type));
        getPlayerBoard().addDevelopmentCard(newCard);


    }



    private void checkCardRequirementsBuy(DevelopmentCard newCard) throws InsufficientPaymentException {

        Map<ResourceType,Integer> cost = newCard.getCost();
        Map<ResourceType,ArrayList<Resource>> availableResources = getDeposit().getAll();
        for(ResourceType type: cost.keySet()){
            if(cost.get(type) > availableResources.get(type).size())
                throw new InsufficientPaymentException();
        }
    }



    public void buyResources(int x, int y) throws FullDepositException {

        MarbleMarket market = gameBoard.getMarket();
        ArrayList<Producible> newResources = market.buy(x,y);
        for(Producible producible: newResources){
           // producible.useEffect(curr
    }



    private void handleProducible( ) {


    }




    public void activateProduction(int positionIndex) throws FullDepositException, ProductionRequirementsException, InsufficientResourcesException {

        DevelopmentCard card = getPlayerBoard().getDevelopmentCard(positionIndex);
        checkCardRequirementsProduction(card);
        Map<ResourceType,Integer>  requirements = card.getProductionRequirements();
        for(ResourceType type: requirements.keySet())
            getPlayerBoard().getDeposit().getResources(type,requirements.get(type));
        getDeposit().addResources(card.getProductionResults());

    }


    private void checkCardRequirementsProduction(DevelopmentCard newCard) throws ProductionRequirementsException {

        Map<ResourceType,Integer> requirements = newCard.getProductionRequirements();
        Map<ResourceType,ArrayList<Resource>> availableResources = getDeposit().getAll();
        for(ResourceType type: requirements.keySet()){
            if(requirements.get(type) > availableResources.get(type).size())
                throw new ProductionRequirementsException();
        }

    }


    public void moveOnPopeRoad(int steps){

        getPopeRoad().move();
        if(getPosition().isPopeSpace()) currentGame.checkPlayersPosition(getPositionIndex());
        ;
    }

    public void moveOnPopeRoad(){
        getPopeRoad().move();
        Cell position = getPosition();
        addVictoryPoints(position.getPoints());
        if(position.isPopeSpace()) currentGame.checkPlayersPosition(getPositionIndex());

    }


    public void discardLeader(LeaderCard leaderCard) throws Exception {

        if(!hand.contains(leaderCard)) throw new Exception();

        hand.remove(leaderCard);
        moveOnPopeRoad();

    }



    public void activateLeaderCard(LeaderCard leaderCard) throws Exception{

        if(!hand.contains(leaderCard)) throw new Exception();

        for(int i = 0; i < hand.size(); i++){
            if(hand.get(i).equals(leaderCard)){
                LeaderCard toPlay = hand.remove(i);
                toPlay.useLeaderAction();
                activeLeaderCards.add(toPlay);
            }
        }

    }


    public void checkActiveEffects(){
        }
    }
*/