package it.polimi.ingsw.gameboard;

import java.util.Optional;

/*
 *This class represents marbles
 * @author Chiara Buonagurio
 */
public class Marble {

    MarbleType color;
    Producible product;

    public Marble(MarbleType color, Producible product){
        this.color = color;
        this.product = product;
    }

    public MarbleType getColor() {
        return color;
    }

    /*
     *This method returns the Optional<Producible> corresponding to the marble's color.
     * @return Optional<Producible>
     */
    public Optional<Producible> getProduct(){
        return Optional.ofNullable(product);
    }

}
