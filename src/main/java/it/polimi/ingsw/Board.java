package it.polimi.ingsw;

/*
 *this class represents the player's personal game board
 *@author René
*/

public class Board {


    private PopeRoad popeRoad;
    private Deposit deposit;
    private Strongbox strongbox;
    /*
    *ArrayList<DevelopmentCard> developmentCards;
    *Pair<ArrayList<Resource>,ArrayList<Resource>> productionPower;
    */


    Board(){
        popeRoad = new PopeRoad();
        deposit = new Deposit();
        strongbox = new Strongbox();
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

    /* public void addDevelopmentCard(){
        developmentCards.add();
    }

    public void useProductionPower(ArrayList<Resource> requirements){
        return productionPower[1];
    }
     */

}
