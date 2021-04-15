package it.polimi.ingsw.player;

/*
    * this class represent the player
    * @ author René Nema
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
        activeLeaderCards = new ArrayList<>();
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
        return getPopeRoad().getCurrentPosition();
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

    public Effects getActiveEffects(){
        return activeEffects;
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

        int cardLevel = newCard.getLevel();
        boolean levelRequirements = false;
        List<Stack<DevelopmentCard>> developmentCards = getPlayerBoard().getDevelopmentCards();
        for(Stack<DevelopmentCard> cards: developmentCards){
            if(cards.empty() || cards.peek().getLevel() == cardLevel - 1)
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

    private void takeResourceForAction(Map<ResourceType, Integer> cost) throws Exception {

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

    /*
        * this method check if the player has enough resources to pay the given cost
        * @param the cost
        * @exception player has not enough resources to buy the card or card is not present
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

    public void activateProduction(int positionIndex) throws FullDepositException, Exception, InsufficientPaymentException {

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

        getStrongbox().addResource(result);

    }

    /*
        * this method use the production effect of a leader card
     */

    public void activateProductionLeader(int positionIndex) throws ProductionRequirementsException, InsufficientPaymentException {

        if(activeEffects.isExtraProduction()){
            activeEffects.useExtraProductionEffect(this, positionIndex);

        }

    }

    /*
        *this method move the player on the popeRoad of the given steps
        *@param amount of steps
     */
    public void moveOnPopeRoad(int steps){

        while(steps > 0){
            moveOnPopeRoad();
            steps--;
        }
    }

    /*
     *this method move the player on the popeRoad of one single step
     */

    public void moveOnPopeRoad(){
        getPopeRoad().move();
        Cell position = getPopeRoad().getCurrentPosition();
        addVictoryPoints(position.getPoints());
        if(position.isPopeSpace()) currentGame.vaticanReport(getPositionIndex());

    }

    /*
        * this method moves the player if a resource is discarded, in this case it doesn't called the vatican Report
     */

    public void moveOnPopeRoadDiscard(int steps){

        while(steps > 0) {
            getPopeRoad().move();
            Cell position = getPopeRoad().getCurrentPosition();
            addVictoryPoints(position.getPoints());
            steps--;
        }


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
        *this method allows the player to use a leaderCard from his hand
        * @param the leader card index
     */

    public void activateLeaderCard(int positionIndex) throws NonExistentCardException, InsufficientResourcesException, InsufficientDevelopmentCardsException{

        if(hand.get(positionIndex) == null) throw new NonExistentCardException(); // this can be fixed in the controller

        int resourceCounter = 0;
        if(!hand.get(positionIndex).getCostResource().isEmpty()){ //this means the resource requirement for the LeaderCard needs to be satisfied
            //need to check player's deposit, auxiliary deposit (if present) and strongbox
            for(ResourceType resourceType : hand.get(positionIndex).getCostResource().keySet()) { // for every resource needed count how many resources the player has
                resourceCounter = 0;
                resourceCounter += playerBoard.getDeposit().getAll().get(resourceType).size(); //checking the deposit

                if(activeEffects.isExtraDeposit()){
                    resourceCounter += activeEffects.getAuxiliaryDeposit(0).getAuxiliaryDeposit().stream().filter(r -> r.getType() == resourceType).count();
                }

                resourceCounter += playerBoard.getStrongbox().getAll().get(resourceType).size();
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

    public void addResourceToExtraDeposit(List<Resource> resources, int positionIndex){

        if(activeEffects.isExtraDeposit()){
            activeEffects.useExtraDepositEffect(resources, positionIndex);
        }
    }

    /*
        * this method allows to discard the resources.
     */
    public void discardResources(){}

}
