package it.polimi.ingsw.gameboard;

public class FaithPoint implements Producible {

    FaithType type;
    private String className;

    public FaithPoint(FaithType type) {
        this.className = getClass().getName();
        this.type = type;
    }

    public FaithType getType(){
        return type;
    }

    @Override
    public String getClassName() {
        return className;
    }
    /*
     *This method applies the effect of a Faith Point, increasing the player's position by one in his PopeRoad
     */

   /* public void useEffect(Player p){

        p.getPopeRoad().move();
    } */

}
