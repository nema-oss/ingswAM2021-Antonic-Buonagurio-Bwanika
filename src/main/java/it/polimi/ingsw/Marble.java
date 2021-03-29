package it.polimi.ingsw;

import java.util.Optional;

/*
 *This class represents marbles
 */
public class Marble {

    Optional<Producible> product;
    MarbleType color;

    public Marble(MarbleType color, Optional<Producible> product){
        this.color = color;
        this.product = product;
    }

    public MarbleType getColor() {
        return color;
    }

    /*
     *This method returns the Producible corresponding to the marble's color.
     */
    public Optional<Producible> getProduct(){

        return product;
    }

}
