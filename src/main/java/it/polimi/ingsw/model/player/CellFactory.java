package it.polimi.ingsw.model.player;

import com.google.gson.Gson;
import it.polimi.ingsw.model.cards.CardFactory;

import java.io.*;

public class CellFactory implements Serializable {

    private Cell[] cells;

    public CellFactory(){

    }


    public Cell[] getCells() {



            //Reader reader = new FileReader("src/main/resources/cells.json");
            Reader reader = new InputStreamReader(CardFactory.class.getResourceAsStream("/cells.json"));
            Gson gson = new Gson();
            cells = gson.fromJson(reader, Cell[].class);




        return cells;
    }
}
