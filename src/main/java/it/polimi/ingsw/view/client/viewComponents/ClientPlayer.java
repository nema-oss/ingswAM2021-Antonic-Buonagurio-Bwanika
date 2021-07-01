package it.polimi.ingsw.view.client.viewComponents;

import it.polimi.ingsw.model.cards.DevelopmentCard;
import it.polimi.ingsw.model.cards.leadercards.AuxiliaryDeposit;
import it.polimi.ingsw.model.cards.leadercards.LeaderCard;
import it.polimi.ingsw.model.cards.leadercards.LeaderCardType;
import it.polimi.ingsw.model.gameboard.Resource;
import it.polimi.ingsw.model.gameboard.ResourceType;
import it.polimi.ingsw.model.player.*;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * this method represents the player client side
 */
public class ClientPlayer {

    private ClientPlayerBoard playerBoard;
    private boolean standardActionPlayed;
    private boolean[] actionLeaderPlayed;
    private String nickname;
    private final int victoryPoints;
    private final Effects activeEffects;
    private List<LeaderCard> activeLeaderCards;
    private List<LeaderCard> hand;
    private ClientGameBoard gameBoard;
    private List<Resource> boughtResources;

    public ClientPlayer(String nickname, ClientGameBoard gameBoard) {

        playerBoard = new ClientPlayerBoard();
        this.nickname = nickname;
        victoryPoints = 0;
        activeEffects = new Effects();
        hand = new ArrayList<>();
        activeLeaderCards = new ArrayList<>();
        this.standardActionPlayed = false;
        this.actionLeaderPlayed = new boolean[2];
        this.gameBoard = gameBoard;

    }


    /**
     * @return the list of leader cards in the player's hand
     */
    public List<LeaderCard> getHand() {
        return hand;
    }

    /**
     * @return the player's personal board
     */
    public ClientPlayerBoard getPlayerBoard() {
        return playerBoard;
    }

    /**
     * @return the list of active leader cards belonging to the player
     */
    public List<LeaderCard> getActiveLeaderCards() {
        return activeLeaderCards;
    }

    /**
     * this method gives leader cards to the player
     * @param hand the cards to give
     */
    public void setHand(List<LeaderCard> hand) {
        this.hand = hand;
    }

    /**
     * @return the player's pposition in the pope road
     */
    public Cell getPosition() {
        return getPopeRoad().getCurrentPosition();
    }

    /**
     * @return the player's deposit
     */
    public ClientDeposit getDeposit() {
        return playerBoard.getDeposit();
    }

    /**
     * @return the player's strongbox
     */
    public ClientStrongbox getStrongbox() {
        return playerBoard.getStrongbox();
    }

    /**
     * @return the board's pope road
     */
    public ClientPopeRoad getPopeRoad() {
        return playerBoard.getPopeRoad();
    }

    /**
     * @return the player's nickname
     */
    public String getNickname() {
        return nickname;
    }

    /**
     * @return the player's active leader effects
     */
    public Effects getActiveEffects() {
        return activeEffects;
    }

    /**
     * @return the amount of victory point belonging to the player
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
     * @return true if the player has played the standard action, false otherwise
     */
    public boolean isStandardActionPlayed() {
        return standardActionPlayed;
    }

    /**
     * @return the player's position index in the pope road
     */
    public int getPositionIndex() {
        return playerBoard.getPopeRoad().getCurrentPositionIndex();
    }

    /**
     * @return the list of development cards belonging to the player
     */
    public List<DevelopmentCard> getDevelopmentCards() {

        List<DevelopmentCard> developmentCards = new ArrayList<>();
        for (Stack<DevelopmentCard> s : playerBoard.getDevelopmentCards())
            developmentCards.addAll(s);
        return developmentCards;
    }

    /**
     * @return the list of leader cards with a production effect
     */
    public List<LeaderCard> getProductionLeaderCards() {

        Stream<LeaderCard> stream = hand.stream().filter(leaderCard -> leaderCard.getLeaderType().equals(LeaderCardType.EXTRA_PRODUCTION));
        return stream.collect(Collectors.toList());

    }

    /**
     * @return true if the player has played all the possible actions in his turn
     */
    public boolean allPossibleActionDone() {
        return standardActionPlayed && isActionLeaderPlayed();
    }

    /**
     * this method tells that the player has played the standard action
     */
    public void setStandardActionDone() {
        standardActionPlayed = true;
    }

    /**
     * @return true if the player has played both the possible leader actions in his turn
     */
    public boolean isActionLeaderPlayed() {
        return actionLeaderPlayed[0] && actionLeaderPlayed[1];
    }

    /**
     * this method tells that the player has played a leader action
     */
    public void setLeaderActionDone() {
        if(!this.actionLeaderPlayed[0])
            this.actionLeaderPlayed[0]=true;
        else if(!this.actionLeaderPlayed[1])
            this.actionLeaderPlayed[1]=true;
    }

    /**
     * this method resets the actions played by the player
     */
    public void resetTurnActionCounter() {
        standardActionPlayed = false;
        actionLeaderPlayed[0] = false;
        actionLeaderPlayed[1] = false;
    }

    /**
     * this method buys a development card from the card market
     * @param x the row index in card market
     * @param y the column index in the card market
     * @return the development card requested
     */
    public DevelopmentCard buyDevelopmentCard(int x, int y) {
        DevelopmentCard developmentCard = gameBoard.getCardMarket().getCard(x, y);
        playerBoard.addDevelopmentCard(developmentCard);
        return developmentCard;
    }


    /**
     * this method sets the resources bought by the player
     * @param resources the resources just obtained
     */
    public void setBoughtResources(List<Resource> resources) {
        this.boughtResources = resources;
    }

    /**
     * @return the resources bought by the player
     */
    public List<Resource> getBoughtResources() {
        return boughtResources;
    }

    /**
     * this method sets the player's board
     * @param board the board to set
     */
    public void setPlayerBoard(ClientPlayerBoard board) {
        playerBoard = board;
    }

    /**
     * this method adds resources to the player's deposit
     * @param userChoice the resources to pput and floor where to put them
     */
    public void addResource(Map<Resource, Integer> userChoice) {

        ClientDeposit clientDeposit = playerBoard.getDeposit();

        for(Resource resource: userChoice.keySet()){
            clientDeposit.addResource(userChoice.get(resource), resource);
        }
    }

    /**
     * this method updates the player's position in the pope road
     * @param position the player's current position
     */
    public void updateCurrentPosition(int position) {

        ClientPopeRoad popeRoad = playerBoard.getPopeRoad();
        popeRoad.setCurrentPositionIndex(position);
        popeRoad.setCurrentPosition(position);
    }

    /**
     * this method activates or discards a leader card
     * @param card the card to perform the action on
     * @param activate true to activate the card, false to discard it
     */
    public void useLeaderCard(LeaderCard card, boolean activate){

        if(activate){
            LeaderCard leaderCard = hand.stream().filter(p->p.getId().equals(card.getId())).findAny().orElse(null);

            card.useEffect(activeEffects);

            if(leaderCard != null) {
                activeLeaderCards.add(leaderCard);
                playerBoard.addActiveLeaderCard(leaderCard);
            }
        }
        else
            hand.remove(card);
    }

    /**
     * this method updates the deposit and the strongbox
     * @param updatedStrongbox the updated strongbox
     * @param updatedWarehouse the updated warehouse
     * @param auxiliaryDeposits the update auxiliary deposits
     */
    public void updateDeposit(Map<ResourceType, List<Resource>> updatedStrongbox, List<List<Resource>> updatedWarehouse, List<AuxiliaryDeposit> auxiliaryDeposits) {

        ClientStrongbox strongbox = playerBoard.getStrongbox();
        ClientDeposit deposit = playerBoard.getDeposit();

        if(activeEffects.isExtraDeposit()) {
            activeEffects.updateAuxiliaryDeposits(auxiliaryDeposits);
            playerBoard.setPlayerAuxiliaryDeposit(activeEffects.getAuxiliaryDeposits());
        }

        strongbox.update(updatedStrongbox);
        deposit.update(updatedWarehouse);
    }

    /**
     * Check if the player has the given leader card active
     * @param leaderCard the card
     * @return true if it's active
     */
    public boolean isLeaderCardActive(LeaderCard leaderCard) {

        return activeLeaderCards.stream().anyMatch(p->p.getId().equals(leaderCard.getId()));
    }

    public Map<LeaderCard,Boolean> getLeaderCards() {

        Map<LeaderCard, Boolean> leaderCardBooleanMap = new HashMap<>();
        for(LeaderCard card: hand)
            leaderCardBooleanMap.put(card, activeLeaderCards.contains(card));

        return leaderCardBooleanMap;
    }
}

