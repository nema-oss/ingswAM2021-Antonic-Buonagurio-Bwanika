package it.polimi.ingsw.model.player;


/*
 *this class represents the player's personal game board
 *@author René
*/

import it.polimi.ingsw.model.cards.DevelopmentCard;
import it.polimi.ingsw.model.gameboard.Resource;
import it.polimi.ingsw.model.gameboard.ResourceType;

import java.io.Serializable;
import java.util.*;

public class Board implements Serializable {


    private PopeRoad popeRoad;
    private Deposit deposit;
    private Strongbox strongbox;
    private List<Stack<DevelopmentCard>> developmentCards;
    private HashMap<ArrayList<Resource>,ArrayList<Resource>> productionPower;
    private static int popeRoadSize = 24;
    private CellFactory cellFactory;


    public Board(){

        cellFactory = new CellFactory();
        List<Cell> cells = new ArrayList<>(Arrays.asList(cellFactory.getCells()));
        popeRoad = new PopeRoad(cells);
        deposit = new Deposit();
        strongbox = new Strongbox();
        developmentCards = new ArrayList<Stack<DevelopmentCard>>();
        developmentCards.add(new Stack<DevelopmentCard>());
        developmentCards.add(new Stack<DevelopmentCard>());
        developmentCards.add(new Stack<DevelopmentCard>());
    }

    /*
        * this method returns all the DevelopmentCards in the board
        * @return all the cards
     */

    public List<Stack<DevelopmentCard>> getDevelopmentCards() {
        return developmentCards;
    }

    /*
        * this method returns the card at the given index
        * @return the selected card
     */

    public DevelopmentCard getDevelopmentCard(int positionIndex) {
        return developmentCards.get(positionIndex).peek();
    }

    /*
     *this method returns the player's personal popeRoad
     *@return the popeRoad
     */
    public PopeRoad getPopeRoad() {
        return popeRoad;
    }

    /*
     *this method returns the player's personal deposit
     *@return the deposit
     */

    public Deposit getDeposit() {
        return deposit;
    }

    /*
     *this method returns the player's personal strongbox
     *@return the strongbox
     */

    public Strongbox getStrongbox() {
        return strongbox;
    }

    /*
        *this method add a new DevelopmentCard to the board
        *@param the card to add
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

    /*
        * this method activate the production power of the personal player's game board
        * @param the resources to transform (type: ArrayList<resource>) and the requested type of th
        * result (type:ResourceType)
        *  @exception if the number of resources to transform is not enough
     */

    public void useProductionPower(List<Resource> toGive, ResourceType request) throws Exception{

        if(toGive.size() != 2) throw new Exception();

        strongbox.addResourceTemporary(new Resource(request));

    }


}
