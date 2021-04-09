package it.polimi.ingsw.gameboard;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.Reader;
import java.util.ArrayList;
import java.util.Arrays;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class MarbleFactory {

    private Marble[] marbles;

    public MarbleFactory() { }

    public ArrayList<Marble> getMarbles() {
        try {
            Reader reader = new FileReader("src/main/resources/marbles.json");

            final GsonBuilder builder = new GsonBuilder();

            builder.registerTypeAdapter(Producible.class, new InterfaceAdapter<>());
            Gson gson = builder.create();

            marbles = gson.fromJson(reader, Marble[].class);

        } catch (FileNotFoundException e){
            e.printStackTrace();
        }

        return new ArrayList<>(Arrays.asList(marbles));


    }
}
