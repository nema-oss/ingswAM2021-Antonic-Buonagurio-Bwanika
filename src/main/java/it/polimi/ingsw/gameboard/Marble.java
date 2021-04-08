package it.polimi.ingsw.gameboard;

import java.util.Optional;

/*
 *This class represents marbles
 */
public class Marble {

    MarbleType color;
    Optional<Producible> product;

    public Marble(MarbleType color, Producible product){
        this.color = color;
        this.product = Optional.ofNullable(product);
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
