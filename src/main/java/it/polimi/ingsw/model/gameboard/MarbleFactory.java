package it.polimi.ingsw.model.gameboard;

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import it.polimi.ingsw.model.cards.CardFactory;

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
            //Reader reader = new FileReader("src/main/resources/marbles.json");
            Reader reader = new InputStreamReader(CardFactory.class.getResourceAsStream("/marbles.json"));
            final GsonBuilder builder = new GsonBuilder();

            builder.registerTypeAdapter(Producible.class, new InterfaceAdapter<>());
            Gson gson = builder.create();

            marbles = gson.fromJson(reader, Marble[].class);


        return new ArrayList<>(Arrays.asList(marbles));


    }
}
