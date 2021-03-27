package it.polimi.ingsw;

import java.util.ArrayList;

public class WhiteToResource implements EffectStrategy {
    private Producible result;

    public Producible getResult() {
        return result;
    }

    public WhiteToResource(Producible result) {
        this.result = result;
    }

    public void useEffect(Player p){
        /*
        ask the market for the arrayList<Producible>
         */
        ArrayList<Producible> market = getResourcesFromMarket();

        for(int i=0; i<market.size(); i++){
            if(market.get(i).equals(null))
                market.set(i, result);

        }

        /*
        return the new array via an ad hoc method
         */
        returnMarket(market);
        return;
    }

    public ArrayList<Producible> getResourcesFromMarket(){
        ArrayList<Producible> r = new ArrayList<>();
        r.add(null);
        return r;
    }

    void returnMarket(ArrayList<Producible> market){
        return;
    }
}
