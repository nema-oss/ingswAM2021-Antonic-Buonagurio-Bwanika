package it.polimi.ingsw.model.player;

import com.google.gson.Gson;
import it.polimi.ingsw.model.cards.CardFactory;

import java.io.*;
import java.util.Objects;

/**
 * this method creates the oppe road cells from a json file
 */
public class CellFactory implements Serializable {

    public CellFactory(){
    }


    /**
     * this method creates the cells
     * @return the array of cells createdd
     */
    public Cell[] getCells() {
            Reader reader = new InputStreamReader(Objects.requireNonNull(CardFactory.class.getResourceAsStream("/cells.json")));
            Gson gson = new Gson();

        return gson.fromJson(reader, Cell[].class);
    }
}
