package it.polimi.ingsw.view.client.viewComponents;

import it.polimi.ingsw.model.cards.DevelopmentCard;
import it.polimi.ingsw.model.cards.leadercards.LeaderCard;
import it.polimi.ingsw.model.gameboard.Resource;
import it.polimi.ingsw.model.player.*;

import java.io.Serializable;
import java.util.*;

public class ClientPlayerBoard implements Serializable {

    private final ClientPopeRoad popeRoad;
    private final ClientDeposit deposit;
    private final ClientStrongbox strongbox;
    private final List<Stack<DevelopmentCard>> developmentCards;
    private HashMap<ArrayList<Resource>,ArrayList<Resource>> productionPower;
    private final CellFactory cellFactory;


    public ClientPlayerBoard(){

        cellFactory = new CellFactory();
        List<Cell> cells = new ArrayList<>(Arrays.asList(cellFactory.getCells()));
        popeRoad = new ClientPopeRoad(cells);
        deposit = new ClientDeposit();
        strongbox = new ClientStrongbox();
        developmentCards = new ArrayList<Stack<DevelopmentCard>>();
        developmentCards.add(new Stack<DevelopmentCard>());
        developmentCards.add(new Stack<DevelopmentCard>());
        developmentCards.add(new Stack<DevelopmentCard>());
    }

    public ClientPopeRoad getPopeRoad() {
        return popeRoad;
    }

    public ClientDeposit getDeposit() {
        return deposit;
    }

    public ClientStrongbox getStrongbox() {
        return strongbox;
    }

    public List<Stack<DevelopmentCard>> getDevelopmentCards() {
        return developmentCards;
    }

    public DevelopmentCard getDevelopmentCard(int positionIndex) {
        return developmentCards.get(positionIndex).peek();
    }

    public HashMap<ArrayList<Resource>, ArrayList<Resource>> getProductionPower() {
        return productionPower;
    }

    public static int getPopeRoadSize() {
        return 24;
    }

    public CellFactory getCellFactory() {
        return cellFactory;
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


    public List<LeaderCard> getActiveLeaderCards() {
        return null;
    }
}
