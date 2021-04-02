package it.polimi.ingsw;

public class FaithPoint implements Producible {

    public FaithPoint() {
    }

    public void useEffect(Player p) {
        p.getPlayerBoard().getPopeRoad().move();
    }
}
