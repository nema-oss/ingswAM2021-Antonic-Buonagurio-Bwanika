package it.polimi.ingsw.view.client.viewComponents;

import it.polimi.ingsw.model.cards.DevelopmentCard;
import it.polimi.ingsw.model.gameboard.Resource;
import it.polimi.ingsw.model.player.*;

import java.util.*;

public class ClientPlayerBoard {

    private final PopeRoad popeRoad;
    private final Deposit deposit;
    private final Strongbox strongbox;
    private final List<Stack<DevelopmentCard>> developmentCards;
    private HashMap<ArrayList<Resource>,ArrayList<Resource>> productionPower;
    private final CellFactory cellFactory;


    public ClientPlayerBoard(){

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

    public PopeRoad getPopeRoad() {
        return popeRoad;
    }

    public Deposit getDeposit() {
        return deposit;
    }

    public Strongbox getStrongbox() {
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



}
