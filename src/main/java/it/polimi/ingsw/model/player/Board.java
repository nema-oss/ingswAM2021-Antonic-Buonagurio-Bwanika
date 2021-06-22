package it.polimi.ingsw.model.player;

import it.polimi.ingsw.model.cards.DevelopmentCard;
import it.polimi.ingsw.model.gameboard.Resource;
import it.polimi.ingsw.model.gameboard.ResourceType;

import java.io.Serializable;
import java.util.*;


/**
 *this class represents the player's personal game board
 *@author René
 */

public class Board implements Serializable {


    private final PopeRoad popeRoad;
    private final Deposit deposit;
    private final Strongbox strongbox;
    private final List<Stack<DevelopmentCard>> developmentCards;


    public Board(){

        CellFactory cellFactory = new CellFactory();
        List<Cell> cells = new ArrayList<>(Arrays.asList(cellFactory.getCells()));
        popeRoad = new PopeRoad(cells);
        deposit = new Deposit();
        strongbox = new Strongbox();
        developmentCards = new ArrayList<>();
        developmentCards.add(new Stack<>());
        developmentCards.add(new Stack<>());
        developmentCards.add(new Stack<>());
    }

    /**
        * this method returns all the DevelopmentCards in the board
        * @return all the cards
     */

    public List<Stack<DevelopmentCard>> getDevelopmentCards() {
        return developmentCards;
    }

    /**
        * this method returns the card at the given index
        * @return the selected card
     */

    public DevelopmentCard getDevelopmentCard(int positionIndex) {
        return developmentCards.get(positionIndex).peek();
    }

    /**
     *this method returns the player's personal popeRoad
     *@return the popeRoad
     */
    public PopeRoad getPopeRoad() {
        return popeRoad;
    }

    /**
     *this method returns the player's personal deposit
     *@return the deposit
     */

    public Deposit getDeposit() {
        return deposit;
    }

    /**
     *this method returns the player's personal strongbox
     *@return the strongbox
     */

    public Strongbox getStrongbox() {
        return strongbox;
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

    public void addDevelopmentCard(DevelopmentCard card, int index) throws IllegalArgumentException{

        Stack<DevelopmentCard> stack = developmentCards.get(index);
        if(stack.empty()) {
            stack.push(card);
        }
        else{
            if(card.getLevel() == stack.peek().getLevel() + 1){
                stack.push(card);
            }
            else throw new IllegalArgumentException();
        }
    }

    /**
        * this method activate the production power of the personal player's game board
        * @param toGive the resources to transform
        * @param request the resource to get
        * @exception Exception if the number of resources to transform is not enough
     */

    public void useProductionPower(List<Resource> toGive, ResourceType request) throws Exception{
        if(toGive.size() != 2) throw new Exception();
        strongbox.addResourceTemporary(new Resource(request));
    }
}
