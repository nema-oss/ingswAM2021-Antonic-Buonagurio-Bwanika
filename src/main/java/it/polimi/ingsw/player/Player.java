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
import it.polimi.ingsw.cards.DevelopmentCard;
import it.polimi.ingsw.cards.leadercards.*;
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
    private Effects activeEffects;

    public Player(String nickname, GameBoard gameBoard, Game currentGame){

        playerBoard = new Board();
        position = playerBoard.getPopeRoad().getCurrentPosition();
        this.nickname = nickname;
        this.gameBoard = gameBoard;
        this.currentGame = currentGame;
        victoryPoints = 0;
        activeEffects = new Effects();
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

    public void setHand(List<LeaderCard> hand) {
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

    public void buyDevelopmentCard(int x, int y) throws InsufficientPaymentException, NonExistentCardException, InsufficientResourcesException {

        CardMarket market = gameBoard.getCardMarket();
        DevelopmentCard newCard = market.getCard(x,y);
        // here I want to check if there is an active card with a discount effect
        checkCardRequirementsBuy(newCard); // also check if a discount is applicable
        Map<ResourceType,Integer> cost = newCard.getCost();
        for(ResourceType type: cost.keySet())
            getPlayerBoard().getDeposit().getResources(type,cost.get(type));
        newCard = market.buyCard(x,y);
        getPlayerBoard().addDevelopmentCard(newCard);


    }

    /*
        * this method check if the player has the requirements to buy the selected card
        * @param the selected card
        * @exception player has not enough resources to buy the card or card is not present
     */

    private void checkCardRequirementsBuy(DevelopmentCard newCard) throws InsufficientPaymentException {

        Map<ResourceType,Integer> cost = newCard.getCost();
        if(activeEffects.isDiscount())
            activeEffects.useDiscountEffect(cost);
        //checkActiveDiscount(cost);
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
        for(LeaderCard leaderCard: activeLeaderCards){
            if(leaderCard instanceof Discount){
                for(ResourceType type : cost.keySet()){
                    if(type.equals(((Discount) leaderCard).getDiscountType()) && cost.get(type)>0){
                       cost.put(type, cost.get(type) - ((Discount) leaderCard).getDiscountAmount());
                    }
                }
            }
        }
    }

    /*
     * this method let the player buy a resources from the market
     * @param card coordinates
     */

    public List<Resource> buyResources(int x, int y) throws FullDepositException {

        MarbleMarket market = gameBoard.getMarket();
        List<Marble> marbles = market.buy(x,y);
        List<Producible> result = new ArrayList<>();
        for (Marble marble : marbles) {
            if (marble.getProduct().isPresent()) {
                //if false it means that the marble can't produce a new resource without special effects
                result.add(marble.getProduct().get());
            } else{
                if(activeEffects.isWhiteToResource()){
                    Producible extraResource = activeEffects.useWhiteToResourceEffect(0);// workaround to fix
                    result.add(extraResource);
                }
                /*
                // need to check if in list of active cards, there's a card that can transform the marble
                //if not present, move on with the payment
                //checkActiveToResourceEffect(marble);
                for(LeaderCard leaderCard : activeLeaderCards){
                    if(leaderCard instanceof WhiteToResource){
                        result.add(((WhiteToResource) leaderCard).useEffect());
                    }
                    break; //ugly but for now it's the only way to use just 1 effect out of 2 possible WhiteToResource LeaderCards; Player needs to be asked which to use
                }

                 */
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
        checkCardRequirementsProduction(card.getProductionRequirements());
        Map<ResourceType,Integer>  requirements = card.getProductionRequirements();
        for(ResourceType type: requirements.keySet())
            getPlayerBoard().getDeposit().getResources(type,requirements.get(type));

        List<Resource> result = new ArrayList<>();
        List<Producible> productionResult = card.getProductionResults();
        for(Producible producible: productionResult){
            if(!producible.useEffect(getPopeRoad())) {
                result.add(new Resource((ResourceType) producible.getType()));
            }
        }

        getStrongbox().addResource(result);

    }

    /*
        * this method use the production effect of a leader card
     */

    public void activateProductionLeader(int positionIndex) throws ProductionRequirementsException {

        if(activeEffects.isExtraProduction()){
            activeEffects.useExtraProductionEffect(this);

        }
        /*
        LeaderCard card = activeLeaderCards.get(positionIndex);
        if(card.getLeaderType() == LeaderCardType.EXTRA_PRODUCTION){
            Map<ResourceType,Integer> cost = card.getCostResource(); // here I prefer ResourceType not Resource
            Map<ResourceType,List<Resource>> availableResources = getDeposit().getAll();
            for(ResourceType resource: cost.keySet()){
                if(cost.get(resource) > availableResources.get(resource).size()) throw new ProductionRequirementsException();
            }

            //List<Resource> result = (List<Resource>) card.useEffect(); // here I want the result of leader card production
            //getStrongbox().addResource(result);
        }

         */

    }


    /*
     * this method check if the player has the requirements to buy the selected card
     * @param the selected card
     * @exception player has not enough resources to buy the card or card is not present
     */

    public void checkCardRequirementsProduction(Map<ResourceType, Integer> requirements) {

        try {
            Map<ResourceType, List<Resource>> availableResources = getDeposit().getAll();
            for (ResourceType type : requirements.keySet()) {
                if (requirements.get(type) > availableResources.get(type).size())
                    throw new ProductionRequirementsException();
            }
        }catch(ProductionRequirementsException e){e.printStackTrace();}

    }

    /*
        *this method move the player on the popeRoad of the given steps
        *@param amount of steps
     */

    public void moveOnPopeRoad(int steps){

        getPopeRoad().move();
        if(getPosition().isPopeSpace()) currentGame.vaticanReport(getPositionIndex());
        ;
    }

    /*
     *this method move the player on the popeRoad of one single step
     */

    public void moveOnPopeRoad(){
        getPopeRoad().move();
        Cell position = getPosition();
        addVictoryPoints(position.getPoints());
        if(position.isPopeSpace()) currentGame.vaticanReport(getPositionIndex());

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
    public void discardLeader(int positionIndex){

        hand.remove(positionIndex);
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

    public void activateLeaderCard(int positionIndex) throws NonExistentCardException{

        if(hand.get(positionIndex) == null) throw new NonExistentCardException(); // this can be fixed in the controller

        hand.get(positionIndex).useEffect(activeEffects);
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

    public Effects getActiveEffects() {
        return activeEffects;
    }
}
