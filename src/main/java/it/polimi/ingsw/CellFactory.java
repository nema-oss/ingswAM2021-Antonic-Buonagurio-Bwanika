package it.polimi.ingsw;

import com.google.gson.Gson;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.Reader;
import java.util.ArrayList;
import java.util.List;

public class CellFactory {

    private Cell[] cells;

    CellFactory(){

    }


    public Cell[] getCells(int popeRoadSize) {


        try {
            Reader reader = new FileReader("");
            Gson gson = new Gson();

            cells = gson.fromJson(reader, Cell[].class);


        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }


        return cells;
    }
}
