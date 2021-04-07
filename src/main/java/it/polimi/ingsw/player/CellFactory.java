package it.polimi.ingsw.player;

import com.google.gson.Gson;
import it.polimi.ingsw.player.Cell;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.Reader;

public class CellFactory {

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
