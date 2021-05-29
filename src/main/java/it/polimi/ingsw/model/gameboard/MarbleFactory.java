package it.polimi.ingsw.model.gameboard;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.Reader;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
/*
 * this class is used to create the marbles by parsing the json file marbles.json
 * @autor Chiara Buonagurio
 */
public class MarbleFactory implements Serializable {

    private Marble[] marbles;

    public MarbleFactory() { }

    /*
     *This method returns the marbles created by this factory
     * @return ArrayList<Marble> : list of all marbles
     */

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
