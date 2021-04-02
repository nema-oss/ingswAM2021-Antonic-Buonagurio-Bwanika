package it.polimi.ingsw.player;


/*
 *this class represents the player's personal game board
 *@author René
*/

import it.polimi.ingsw.cards.DevelopmentCard;
import it.polimi.ingsw.gameboard.Resource;
import it.polimi.ingsw.gameboard.ResourceType;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Stack;

public class Board {


    private PopeRoad popeRoad;
    private Deposit deposit;
    private Strongbox strongbox;
    private ArrayList<Stack<DevelopmentCard>> developmentCards;
    private HashMap<ArrayList<Resource>,ArrayList<Resource>> productionPower;
    private static int popeRoadSize = 20;
    private CellFactory cellFactory;


    public Board(){
        cellFactory = new CellFactory();
        Cell[] cells = cellFactory.getCells(popeRoadSize);
        popeRoad = new PopeRoad(cells);
        deposit = new Deposit();
        strongbox = new Strongbox();
        developmentCards = new ArrayList<Stack<DevelopmentCard>>(3);
    }

    public ArrayList<Stack<DevelopmentCard>> getDevelopmentCards() {
        return developmentCards;
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

    /*
        * this method activate the production power of the personal player's game board
        * @param the resources to transform (type: ArrayList<resource>) and the requested type of th
        * result (type:ResourceType) @exception if the number of resources to transform is not enough
     */

    public void useProductionPower(ArrayList<Resource> toGive, ResourceType request) throws Exception{

        if(toGive.size() != 2) throw new Exception();

        strongbox.addResource(new Resource(request));

    }


}
