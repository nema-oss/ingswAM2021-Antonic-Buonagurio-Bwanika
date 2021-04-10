package it.polimi.ingsw.gameboard;

import java.util.Optional;

/*
 *This class represents marbles
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
     *This method returns the Producible corresponding to the marble's color.
     */
    public Optional<Producible> getProduct(){
        return Optional.ofNullable(product);
    }

}
