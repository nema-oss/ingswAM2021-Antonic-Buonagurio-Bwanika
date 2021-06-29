package it.polimi.ingsw.view.client.viewComponents;

import it.polimi.ingsw.model.cards.DevelopmentCard;
import it.polimi.ingsw.model.cards.leadercards.LeaderCard;
import it.polimi.ingsw.model.gameboard.Resource;
import it.polimi.ingsw.model.player.*;

import java.io.Serializable;
import java.util.*;

/**
 * this class represents the player's personal board clitn side
 */
public class ClientPlayerBoard implements Serializable {

    private final ClientPopeRoad popeRoad;
    private final ClientDeposit deposit;
    private final ClientStrongbox strongbox;
    private final List<Stack<DevelopmentCard>> developmentCards;
    private HashMap<ArrayList<Resource>,ArrayList<Resource>> productionPower;
    private final CellFactory cellFactory;
    private final List<LeaderCard> activeLeaderCard;
    private final int victoryPoints;


    public ClientPlayerBoard(){

        cellFactory = new CellFactory();
        List<Cell> cells = new ArrayList<>(Arrays.asList(cellFactory.getCells()));
        popeRoad = new ClientPopeRoad(cells);
        deposit = new ClientDeposit();
        strongbox = new ClientStrongbox();
        developmentCards = new ArrayList<>();
        developmentCards.add(new Stack<>());
        developmentCards.add(new Stack<>());
        developmentCards.add(new Stack<>());
        activeLeaderCard = new ArrayList<>();
        victoryPoints = 0;
    }

    /**
     * @return player's pope road
     */
    public ClientPopeRoad getPopeRoad() {
        return popeRoad;
    }

    /**
     * @return player's deposit
     */
    public ClientDeposit getDeposit() {
        return deposit;
    }

    /**
     * @return player's strongbox
     */
    public ClientStrongbox getStrongbox() {
        return strongbox;
    }

    /**
     * @return the list of all development cards belonging to the player
     */
    public List<Stack<DevelopmentCard>> getDevelopmentCards() {
        return developmentCards;
    }

    /**
     * this method returns the top development card in a stack
     * @param positionIndex the index of the stack in player's board
     * @return the card requested
     */
    public DevelopmentCard getDevelopmentCard(int positionIndex) {
        return developmentCards.get(positionIndex).peek();
    }

    /**
     * @return the board's production power
     */
    public HashMap<ArrayList<Resource>, ArrayList<Resource>> getProductionPower() {
        return productionPower;
    }

    /**
     * @return the pope road's size
     */
    public static int getPopeRoadSize() {
        return 24;
    }

    /***
     * @return the cell factory
     */
    public CellFactory getCellFactory() {
        return cellFactory;
    }

    /**
     *this method add a new DevelopmentCard to the board
     *@param card the card to add
     */

    public void addDevelopmentCard(DevelopmentCard card){

        for (Stack<DevelopmentCard> developmentCard : developmentCards) {
            if (developmentCard.empty()) {
                developmentCard.push(card);
                break;
            } else {
                DevelopmentCard top = developmentCard.peek();
                if (card.getLevel() == top.getLevel() + 1) {
                    developmentCard.push(card);
                    break;
                }
            }
        }
    }


    /**
     * @return the list of active leader cards
     */
    public List<LeaderCard> getActiveLeaderCards() {
        return activeLeaderCard;
    }

    /**
     * this method adds an active leader card
     * @param card the new active card
     */
    public void addActiveLeaderCard(LeaderCard card) {
        activeLeaderCard.add(card);
    }

}
