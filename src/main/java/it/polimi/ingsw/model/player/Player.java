package it.polimi.ingsw.model.player;

import it.polimi.ingsw.model.Game;
import it.polimi.ingsw.model.cards.DevelopmentCard;
import it.polimi.ingsw.model.cards.DevelopmentCardType;
import it.polimi.ingsw.model.cards.leadercards.*;
import it.polimi.ingsw.model.exception.*;
import it.polimi.ingsw.model.gameboard.*;
import javax.naming.InsufficientResourcesException;
import java.util.*;

/**
 * this class represent the player
 */

public class Player{

    private final GameBoard gameBoard;
    private final Game currentGame;
    private String nickname;
    private List<LeaderCard> hand;
    private final List<LeaderCard> activeLeaderCards;
    private int victoryPoints;
    private final Cell position;
    private final Board playerBoard;
    private final Effects activeEffects;

    private boolean standardActionPlayed;
    private boolean[] leaderActionPlayed;

    public Player(String nickname, GameBoard gameBoard, Game currentGame){

        playerBoard = new Board();
        position = playerBoard.getPopeRoad().getCurrentPosition();
        this.nickname = nickname;
        this.gameBoard = gameBoard;
        this.currentGame = currentGame;
        victoryPoints = 0;
        activeEffects = new Effects();
        activeLeaderCards = new ArrayList<>();

        standardActionPlayed = false;
        leaderActionPlayed = new boolean[2];

    }


    /**
     * @return the cards in player's hand
     */
    public List<LeaderCard> getHand() {
        return hand;
    }

    /**
     * @return player's personal board
     */
    public Board getPlayerBoard() {
        return playerBoard;
    }

    /**
     * @return player's active leader cards
     */
    public List<LeaderCard> getActiveLeaderCards() {
        return activeLeaderCards;
    }

    /**
     * this method sets the cards in player's hand
     * @param hand the cards to give
     */
    public void setHand(List<LeaderCard> hand) {
        this.hand = hand;
    }

    /**
     * @return the player's position index in pope road
     */
    public int getPositionIndex() {
        return playerBoard.getPopeRoad().getCurrentPositionIndex();
    }

    /**
     * @return player's position
     */
    public Cell getPosition() {
        return getPopeRoad().getCurrentPosition();
    }

    /**
     * @return player's deposit
     */
    public Deposit getDeposit(){
        return playerBoard.getDeposit();
    }

    /**
     * @return player's strongbox
     */
    public Strongbox getStrongbox(){
        return playerBoard.getStrongbox();
    }

    /**
     * @return layer's pope road
     */
    public PopeRoad getPopeRoad(){
        return playerBoard.getPopeRoad();
    }

    /**
     * @return pplayer's nickname
     */
    public String getNickname() {
        return nickname;
    }

    /**
     * @return player's active effects
     */
    public Effects getActiveEffects(){
        return activeEffects;
    }

    /**
     * this method gives victory points to the player
     * @param victoryPoints the amount of points to give
     */
    public void addVictoryPoints(int victoryPoints) {
        this.victoryPoints += victoryPoints;
    }

    /**
     * @return player's victory points
     */
    public int getVictoryPoints() {
        return victoryPoints;
    }

    /**
     * this method sets the player's nickname
     * @param nickname the nickname chosen
     */
    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    /**
        * this method lets the player buy a DevelopmentCard from the market
        * @param x row index
        * @param y column index
        * @exception InsufficientPaymentException if the player doesn't have enough resources to buy the card
        * @exception NonExistentCardException if the card is not present
     */

    public void buyDevelopmentCard(int x, int y) throws InsufficientPaymentException, NonExistentCardException, Exception {

        CardMarket market = gameBoard.getCardMarket();
        DevelopmentCard newCard = market.getCard(x,y);

        int cardLevel = newCard.getLevel();
        boolean levelRequirements = false;
        List<Stack<DevelopmentCard>> developmentCards = getPlayerBoard().getDevelopmentCards();
        for(Stack<DevelopmentCard> cards: developmentCards){
            if((cards.empty() &&  cardLevel == 1) || (!cards.empty() && cards.peek().getLevel() == cardLevel - 1) )
                levelRequirements = true;
        }
        if(!levelRequirements) throw new InsufficientPaymentException();

        if(activeEffects.isDiscount())
            activeEffects.useDiscountEffect(newCard.getCost());
        checkCardRequirements(newCard.getCost());
        Map<ResourceType,Integer> cost = newCard.getCost();
        takeResourceForAction(cost);
        newCard = market.buyCard(x,y);
        getPlayerBoard().addDevelopmentCard(newCard);


    }

    /**
     * this method takes the resources from the player's board
     * @param cost the resources to take
     * @throws InsufficientResourcesException if the player doesn't have enough resources
     */
    public void takeResourceForAction(Map<ResourceType, Integer> cost) throws InsufficientResourcesException {

        Map<ResourceType,List<Resource>> availableResourcesDeposit = getDeposit().getAll();
        Map<ResourceType,List<Resource>> availableResourcesStrongbox = getStrongbox().getAll();

        // default mode: first deposit then strongbox
        for(ResourceType type: cost.keySet()) {
            if(availableResourcesDeposit.containsKey(type)) {
                if (cost.get(type) <= availableResourcesDeposit.get(type).size())
                    getDeposit().getResources(type, cost.get(type));
                else {
                    int maxFromDeposit = availableResourcesDeposit.get(type).size();
                    getDeposit().getResources(type, maxFromDeposit);
                    int minFromStrongbox = cost.size() - maxFromDeposit;
                    getStrongbox().getResource(type, minFromStrongbox);
                }
            }
            else{
                getStrongbox().getResource(type, cost.get(type));
            }
        }
    }

    /**
        * this method check if the player has enough resources to pay the given cost
        * @param cost the cost
        * @exception InsufficientPaymentException if the player has not enough resources to buy the card or card is not present
     */

    public void checkCardRequirements(Map<ResourceType, Integer> cost) throws InsufficientPaymentException {

        Map<ResourceType,List<Resource>> availableResourcesDeposit = getDeposit().getAll();
        Map<ResourceType,List<Resource>> availableResourcesStrongbox = getStrongbox().getAll();
        if(activeEffects.isExtraDeposit()){
            AuxiliaryDeposit auxiliaryDeposit =  activeEffects.getAuxiliaryDeposit(0);
        }
        int fromStrongbox;
        int fromDeposit;
        int fromExtraDeposit;

        for(ResourceType type: cost.keySet()){

            fromStrongbox = 0;
            fromDeposit = 0;
            fromExtraDeposit = 0;

            if(availableResourcesStrongbox.containsKey(type))
                fromStrongbox = availableResourcesStrongbox.get(type).size();
            if(availableResourcesDeposit.containsKey(type))
                fromDeposit = availableResourcesDeposit.get(type).size();
            if(activeEffects.isExtraDeposit()){
                AuxiliaryDeposit auxiliaryDeposit =  activeEffects.getAuxiliaryDeposit(0);
                fromExtraDeposit += auxiliaryDeposit.getAuxiliaryDeposit().stream().filter(r -> r.getType() == type).count();
            }
            if(cost.get(type) > fromStrongbox + fromDeposit + fromExtraDeposit )
                throw new InsufficientPaymentException();
        }
    }

    /**
     * this method let the player buy a resources from the market
     * @param x row index in market
     * @param y column index in market
     */

    public List<Resource> buyResources(int x, int y) {

        MarbleMarket market = gameBoard.getMarket();
        List<Marble> marbles = market.buy(x,y);
        List<Producible> result = new ArrayList<>();
        for (Marble marble : marbles) {
            if (marble.getProduct().isPresent()) {
                result.add(marble.getProduct().get());
            } else{
                if(activeEffects.isWhiteToResource()){
                    Producible extraResource = activeEffects.useWhiteToResourceEffect(0);// workaround to fix
                    result.add(extraResource);
                }
            }
        }

        result.removeIf(producible -> producible.useEffect(getPopeRoad())); // using faith points
        List<Resource> newResources = new ArrayList<>();
        for (Producible producible : result) {
            newResources.add((Resource) producible);
        }
        return newResources;
    }



    /**
     * this method let the player activate the production effect of his cards
     * @param positionIndex coordinates
     */

    public void activateProduction(int positionIndex) throws InsufficientPaymentException, InsufficientResourcesException {

        DevelopmentCard card = getPlayerBoard().getDevelopmentCard(positionIndex);
        checkCardRequirements(card.getProductionRequirements());
        Map<ResourceType,Integer>  requirements = card.getProductionRequirements();
        takeResourceForAction(requirements);

        List<Resource> result = new ArrayList<>();
        List<Producible> productionResult = card.getProductionResults();
        for(Producible producible: productionResult){
            if(!producible.useEffect(getPopeRoad())) {
                result.add(new Resource((ResourceType) producible.getType()));
            }
        }

        getStrongbox().addResourceTemporary(result);

    }

    /**
     * this method use the production effect of a leader card
     */

    public void activateProductionLeader(int position) throws InsufficientPaymentException {

        if(activeEffects.isExtraProduction()){
            activeEffects.useExtraProductionEffect(this, position);

        }

    }

    /**
        *this method moves the player on the popeRoad
        *@param steps amount of steps to make
     */
    public void moveOnPopeRoad(int steps){

        while(steps > 0){
            moveOnPopeRoad();
            steps--;
        }
    }

    /**
     *this method moves the player on the popeRoad of one single step
     */

    public void moveOnPopeRoad(){
        getPopeRoad().move();
        Cell position = getPopeRoad().getCurrentPosition();
        addVictoryPoints(position.getPoints());
    }

    /**
        * this method moves the player if a resource is discarded, in this case it doesn't call the vatican Report
     */

    public void moveOnPopeRoadDiscard(int steps){

        while(steps > 0) {
            getPopeRoad().move();
            Cell position = getPopeRoad().getCurrentPosition();
            addVictoryPoints(position.getPoints());
            steps--;
        }


    }

    /**
        * this method discard a Leader card from hand and move the player on poperoad
        * @param positionIndex the card to discard (type: LeaderCard)
     */
    public void discardLeader(int positionIndex) throws NonExistentCardException{

        if(positionIndex<0 || positionIndex >= hand.size()) throw new NonExistentCardException();
        else {
            hand.remove(positionIndex);
            moveOnPopeRoad();
        }

    }

    /**
        *this method allows the player to use a leaderCard from his hand
        * @param positionIndex the leader card index
     */

    public void activateLeaderCard(int positionIndex) throws NonExistentCardException, InsufficientResourcesException, InsufficientDevelopmentCardsException{

        if(positionIndex < 0 || hand.size() < positionIndex || hand.get(positionIndex) == null) throw new NonExistentCardException();

        int resourceCounter = 0;
        if(!hand.get(positionIndex).getCostResource().isEmpty()){

            for(ResourceType resourceType : hand.get(positionIndex).getCostResource().keySet()) {
                resourceCounter = 0;
                if(playerBoard.getDeposit().getAll().get(resourceType)!=null)
                    resourceCounter += playerBoard.getDeposit().getAll().get(resourceType).size();

                if(activeEffects.isExtraDeposit()){
                    resourceCounter += activeEffects.getAuxiliaryDeposit(0).getAuxiliaryDeposit().stream().filter(r -> r.getType() == resourceType).count();
                }
                if(playerBoard.getStrongbox().getAll().get(resourceType)!=null)
                    resourceCounter += playerBoard.getStrongbox().getAll().get(resourceType).size();
                if(resourceCounter < hand.get(positionIndex).getCostResource().get(resourceType)) throw new InsufficientResourcesException();
            }
        }
        int numberOfCards = 0;
        if(!hand.get(positionIndex).getCostDevelopment().isEmpty()){

            for(Integer integer : hand.get(positionIndex).getCostDevelopment().keySet()){
                for(DevelopmentCardType developmentCardType : hand.get(positionIndex).getCostDevelopment().get(integer).keySet()){
                    numberOfCards = 0;
                    for(Stack<DevelopmentCard> developmentCards : playerBoard.getDevelopmentCards()){
                        for(DevelopmentCard developmentCard : developmentCards){
                            if(developmentCard.getType() == developmentCardType && (integer == 0 || developmentCard.getLevel() == integer))
                                numberOfCards++;
                        }
                    }
                    if(numberOfCards < hand.get(positionIndex).getCostDevelopment().get(integer).get(developmentCardType)) throw new InsufficientDevelopmentCardsException();
                }

            }
        }


        hand.get(positionIndex).useEffect(activeEffects);
        activeLeaderCards.add(hand.get(positionIndex));

    }

    /**
     * this method allows the player to move the deposit's floor before adding new resources
     * @param x,y the floors coordinates
    */

    public void swapDepositFloors(int x, int y) throws WrongDepositSwapException {

        getDeposit().swapFloors(x,y);

    }

    /**
     * this method allows the player to add the resource on a floor
     * @param resource the resource to add
     * @param floor the selected floor
     */

    public void addResourceToDeposit(int floor, Resource resource) throws FullDepositException {

        getDeposit().addResource(floor,resource);
    }

    /**
     * this method add resources to the extra deposit
     */
    public void addResourceToExtraDeposit(List<Resource> resources, int positionIndex){

        if(activeEffects.isExtraDeposit()){
            activeEffects.useExtraDepositEffect(resources, positionIndex);
        }
    }


    /**
     * @return true if the player has played the standard action
     */
    public boolean hasPlayedStandardAction() {
        return standardActionPlayed;
    }

    /**
     * @return true if the player has played both the leader actions possible
     */
    public boolean hasPlayedLeaderAction() {
        return leaderActionPlayed[0] && leaderActionPlayed[1];
    }

    /**
     * this method sets that the player has played the standard action
     * @param standardActionPlayed true if played, false otherwise
     */
    public void setStandardActionPlayed(boolean standardActionPlayed) {
        this.standardActionPlayed = standardActionPlayed;
    }

    /**
     * this method sets the fact that the player has played a leader action
     * @param leaderActionPlayed true if plated, false otherwise
     */
    public void setLeaderActionPlayed(boolean leaderActionPlayed) {
        if(leaderActionPlayed){
            if(!this.leaderActionPlayed[0])
                this.leaderActionPlayed[0]=true;
            else if(!this.leaderActionPlayed[1])
                this.leaderActionPlayed[1]=true;
        }
        else{
            this.leaderActionPlayed[0] = false;
            this.leaderActionPlayed[1] = false;
        }
    }
}
