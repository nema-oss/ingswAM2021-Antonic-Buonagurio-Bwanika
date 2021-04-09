package it.polimi.ingsw.player;

/*
    * this class represent the player
    * @ author René
 */

/*
    *TODO: how to handle effects -- Nema
    * buyResource should check if there is a WhiteToResource effect active and apply it -- Nema
    * buyDevelopmentCard check if there is a Discount effect active and apply it -- Nema
    *TODO: how to manage the faith point -- René
 */

import it.polimi.ingsw.Game;
import it.polimi.ingsw.cards.Card;
import it.polimi.ingsw.cards.DevelopmentCard;
import it.polimi.ingsw.cards.leadercards.AuxiliaryDeposit;
import it.polimi.ingsw.cards.leadercards.LeaderCard;
import it.polimi.ingsw.cards.leadercards.LeaderCardType;
import it.polimi.ingsw.exception.*;
import it.polimi.ingsw.gameboard.*;
import javax.naming.InsufficientResourcesException;
import java.util.*;

public class Player{

    private GameBoard gameBoard;
    private Game currentGame;
    private String nickname;
    private List<LeaderCard> hand;
    private List<LeaderCard> activeLeaderCards;
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


    public List<LeaderCard> getHand() {
        return hand;
    }

    public Board getPlayerBoard() {
        return playerBoard;
    }

    public List<LeaderCard> getActiveLeaderCards() {
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

    /*
        * this method let the player buy a DevelopmentCard from the market
        * @param card coordinates
        * @exception player has not enough resources to buy the card or card is not present
     */

    public void buyDevelopmentCard(int x, int y) throws InsufficientPaymentException, NonexistentCardException, InsufficientResourcesException {

        CardMarket market = gameBoard.getCardMarket();
        DevelopmentCard newCard = market.getCard(x,y);
        // here I want to check if there is an active card with a discount effect
        checkCardRequirementsBuy(newCard); // also check if a discount is applicable
        Map<ResourceType,Integer> cost = newCard.getCost();
        for(ResourceType type: cost.keySet())
            getPlayerBoard().getDeposit().getResources(type,cost.get(type));
        getPlayerBoard().addDevelopmentCard(newCard);

    }

    /*
        * this method check if the player has the requirements to buy the selected card
        * @param the selected card
        * @exception player has not enough resources to buy the card or card is not present
     */

    private void checkCardRequirementsBuy(DevelopmentCard newCard) throws InsufficientPaymentException {

        Map<ResourceType,Integer> cost = newCard.getCost();
        checkActiveDiscount(cost);
        Map<ResourceType,List<Resource>> availableResources = getDeposit().getAll();
        for(ResourceType type: cost.keySet()){
            if(cost.get(type) > availableResources.get(type).size())
                throw new InsufficientPaymentException();
        }
    }

    /*
        * this method check if there is an active leader card with discount effect
        * @param the cost that has to be discounted
     */

    private void checkActiveDiscount(Map<ResourceType, Integer> cost) {

        // if discount is doable, reduce the cost by the discount amount
    }

    /*
     * this method let the player buy a resources from the market
     * @param card coordinates
     */

    public List<Resource> buyResources(int x, int y) throws FullDepositException {

        // here I want to check if there is an card with an usable effect
        MarbleMarket market = gameBoard.getMarket();
        List<Marble> marbles = market.buy(x, y);
        List<Producible> result = new ArrayList<Producible>();
        for (Marble marble : marbles) {
            if (marble.getProduct().isPresent()) {
                //if false it means that the marble can't produce a new resource without special effects
                result.add(marble.getProduct().get());
            } else {
                // need to check if in list of active cards, there's a card that can transform the marble
                //if not present, move on with the payment
                //checkActiveToResourceEffect(marble);
            }
        }

        // we have to take the result List<Producible> and make a list of resource, maybe we can solve the casting
        result.removeIf(producible -> producible.useEffect(getPopeRoad()));
        List<Resource> newResources = new ArrayList<Resource>();
        for (Producible producible : result) {
            newResources.add((Resource) producible);
        }

        return newResources;

    }



    /*
     * this method let the player activate the production effect of his cards
     * @param card coordinates
     * @exception player has not enough resources to activate the card or player can't store all resources
     */

    public void activateProduction(int positionIndex) throws FullDepositException, ProductionRequirementsException, InsufficientResourcesException {

        DevelopmentCard card = getPlayerBoard().getDevelopmentCard(positionIndex);
        checkCardRequirementsProduction(card);
        Map<ResourceType,Integer>  requirements = card.getProductionRequirements();
        for(ResourceType type: requirements.keySet())
            getPlayerBoard().getDeposit().getResources(type,requirements.get(type));
        getStrongbox().addResource(card.getProductionResults());

    }

    /*
        * this method use the production effect of a leader card
     */

    public void activateProductionLeader(int positionIndex) throws ProductionRequirementsException {

        LeaderCard card = activeLeaderCards.get(positionIndex);
        if(card.getLeaderType() == LeaderCardType.EXTRA_PRODUCTION){
            Map<Resource,Integer> cost = card.getCostResource(); // here I prefer ResourceType not Resource
            Map<ResourceType,List<Resource>> availableResources = getDeposit().getAll();
            for(Resource resource: cost.keySet()){
                ResourceType type = resource.getType();
                if(cost.get(type) > availableResources.get(type).size()) throw new ProductionRequirementsException();
            }

            //List<Resource> result = card.useLeaderAction(); // here I want the result of leader card production
            //getStrongbox().addResource(result);
        }

    }


    /*
     * this method check if the player has the requirements to buy the selected card
     * @param the selected card
     * @exception player has not enough resources to buy the card or card is not present
     */

    private void checkCardRequirementsProduction(DevelopmentCard newCard) throws ProductionRequirementsException {

        Map<ResourceType,Integer> requirements = newCard.getProductionRequirements();
        Map<ResourceType,List<Resource>> availableResources = getDeposit().getAll();
        for(ResourceType type: requirements.keySet()){
            if(requirements.get(type) > availableResources.get(type).size())
                throw new ProductionRequirementsException();
        }

    }

    /*
        *this method move the player on the popeRoad of the given steps
        *@param amount of steps
     */

    public void moveOnPopeRoad(int steps){

        getPopeRoad().move();
        if(getPosition().isPopeSpace()) currentGame.checkPlayersPosition(getPositionIndex());
        ;
    }

    /*
     *this method move the player on the popeRoad of one single step
     */

    public void moveOnPopeRoad(){
        getPopeRoad().move();
        Cell position = getPosition();
        addVictoryPoints(position.getPoints());
        if(position.isPopeSpace()) currentGame.checkPlayersPosition(getPositionIndex());

    }

    /*
        * this method moves the player if a resource is discarded, in this case it doesn't called the vatican Report
     */

    public void moveOnPopeRoadDiscard(int steps){

        getPopeRoad().move(steps);
        Cell position = getPosition();
        addVictoryPoints(position.getPoints());

    }

    /*
        * this method discard a Leader card from hand and move the player on poperoad
        * @param the card to discard (type: LeaderCard)
     */
    public void discardLeader(LeaderCard leaderCard) throws Exception {

        if(!hand.contains(leaderCard)) throw new Exception();

        hand.remove(leaderCard);
        moveOnPopeRoad();

    }

    /*
        * this method activate the effect of a Leader Card
        * @param the leader Card selected
     */

    /*
        *this method allows the player to use a leaderCard from his hand
        * @param the leader card
     */

    public void activateLeaderCard(int positionIndex) throws Exception{

        if(hand.get(positionIndex) == null) throw new Exception(); // this can be fixed in the controller

        LeaderCard card = hand.get(positionIndex);
        //card.activateEffect(); // this method should just activate the effects, adding it in the list of active effects
        activeLeaderCards.add(hand.remove(positionIndex));

    }

    /*
        * this method allows the player to move the deposit's floor before adding new resources
    */

    public void swapDepositFloors(int x, int y) throws WrongDepositSwapException {

        getDeposit().swapFloors(x,y);

    }

    /*
        * this method allows the player to add the resource on a floor
     */

    public void addResourceToDeposit(int floor, Resource resource) throws FullDepositException, Exception {

        getDeposit().addResource(floor,resource);
    }

    /*
       * this method add a resource to the extra deposit
     */

    public void addResourceToExtraDeposit(Resource resource){

        // I don't like the optional here, better to implement a isEmpty() method
        //if(auxiliaryDeposit.isPresent()){
            //auxiliaryDeposit.addResource(resource);
        //}
    }

    /*
        * this method allows to discard the resources.
     */
    public void discardResources(){}
}
