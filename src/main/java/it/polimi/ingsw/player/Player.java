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
import it.polimi.ingsw.cards.DevelopmentCardType;
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
    private AuxiliaryDeposit auxiliaryDeposit;
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

    public void buyDevelopmentCard(int x, int y) throws InsufficientPaymentException, NonExistentCardException, Exception {

        CardMarket market = gameBoard.getCardMarket();
        DevelopmentCard newCard = market.getCard(x,y);

        checkCardRequirementsBuy(newCard); // also check if a discount is applicable
        Map<ResourceType,Integer> cost = newCard.getCost();
        Map<ResourceType,List<Resource>> availableResourcesDeposit = getDeposit().getAll();
        Map<ResourceType,List<Resource>> availableResourcesStrongbox = getStrongbox().getAll();

        // default mode: first deposit the strongbox
        for(ResourceType type: cost.keySet()) {
            if(availableResourcesDeposit.containsKey(type)) {
                if (cost.get(type) <= availableResourcesDeposit.get(type).size())
                    getDeposit().getResources(type, cost.get(type));
                else {
                    int g = availableResourcesDeposit.get(type).size();
                    getDeposit().getResources(type, g);
                    int diff = cost.size() - g;
                    getStrongbox().getResource(type, diff);
                }
            }
            else{
                getStrongbox().getResource(type, cost.get(type));
            }
        }

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
        Map<ResourceType,List<Resource>> availableResourcesDeposit = getDeposit().getAll();
        Map<ResourceType,List<Resource>> availableResourcesStrongbox = getStrongbox().getAll();
        int fromStrongbox;
        int fromDeposit;

        for(ResourceType type: cost.keySet()){

            fromStrongbox = 0;
            fromDeposit = 0;
            if(availableResourcesStrongbox.containsKey(type))
                fromStrongbox = availableResourcesStrongbox.get(type).size();
            if(availableResourcesDeposit.containsKey(type))
                fromDeposit = availableResourcesDeposit.get(type).size();

            if(cost.get(type) > fromStrongbox + fromDeposit )
                throw new InsufficientPaymentException();
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
            }


        }

        result.removeIf(producible -> producible.useEffect(getPopeRoad())); // using faith points
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
        Cell position = getPosition();
        addVictoryPoints(position.getPoints());
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

    public void activateLeaderCard(int positionIndex) throws NonExistentCardException, InsufficientResourcesException, InsufficientDevelopmentCardsException{

        if(hand.get(positionIndex) == null) throw new NonExistentCardException(); // this can be fixed in the controller

        int resourceCounter = 0;
        if(!hand.get(positionIndex).getCostResource().isEmpty()){ //this means the resource requirement for the LeaderCard needs to be satisfied
            //need to check player's deposit, auxiliary deposit (if present) and strongbox
            for(ResourceType resourceType : hand.get(positionIndex).getCostResource().keySet()) { // for every resource needed count how many resources the player has
                resourceCounter = 0;
                resourceCounter += playerBoard.getDeposit().getAll().get(resourceType).size(); //checking the deposit
                if(auxiliaryDeposit.isPresent()){//checking the aux deposit
                    resourceCounter += auxiliaryDeposit.get().checkAuxiliaryDeposit().size();
                }
                resourceCounter += playerBoard.getStrongbox().getStrongbox().get(resourceType).size();
                if(resourceCounter < hand.get(positionIndex).getCostResource().get(resourceType)) throw new InsufficientResourcesException();
            }
        }
        int numberOfCards = 0;
        if(!hand.get(positionIndex).getCostDevelopment().isEmpty()){ //this means the development card requirements need to be satisfied
            //need to check the playerBoard
            for(Integer integer : hand.get(positionIndex).getCostDevelopment().keySet()){ //maybe there's a more elegant way to do this
                for(DevelopmentCardType developmentCardType : hand.get(positionIndex).getCostDevelopment().get(integer).keySet()){
                    for(Stack<DevelopmentCard> developmentCards : playerBoard.getDevelopmentCards()){
                        for(DevelopmentCard developmentCard : developmentCards){
                            if(developmentCard.getType() == developmentCardType)
                                numberOfCards++;
                        }
                    }
                }
                if(numberOfCards < integer) throw new InsufficientDevelopmentCardsException();
            }
        }


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
       * this method add resources to the extra deposit
     */

    public void addResourceToExtraDeposit(List<Resource> resources){

        if(activeEffects.isExtraDeposit()){
            activeEffects.useExtraDepositEffect(resources);
        }
    }

    /*
        * this method allows to discard the resources.
     */
    public void discardResources(){}

    public Effects getActiveEffects() {
        return activeEffects;
    }

    public AuxiliaryDeposit getAuxiliaryDeposit() {
        return auxiliaryDeposit;
    }
}
