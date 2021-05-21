package it.polimi.ingsw.model.player;

import com.google.gson.Gson;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.Reader;
import java.io.Serializable;

public class CellFactory implements Serializable {

    private Cell[] cells;

    public CellFactory(){

    }


    public Cell[] getCells() {


        try {
            Reader reader = new FileReader("src/main/resources/cells.json");
            Gson gson = new Gson();
            cells = gson.fromJson(reader, Cell[].class);

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }


        return cells;
    }
}
