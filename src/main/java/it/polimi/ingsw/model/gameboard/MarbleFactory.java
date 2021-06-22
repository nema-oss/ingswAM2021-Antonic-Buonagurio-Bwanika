package it.polimi.ingsw.model.gameboard;

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Objects;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import it.polimi.ingsw.model.cards.CardFactory;

/**
 * this class is used to create the marbles by parsing the json file marbles.json
 * @author Chiara
 */
public class MarbleFactory implements Serializable {

    public MarbleFactory() { }

    /**
     *This method returns the marbles created by this factory
     * @return list of all marbles
     */
    public ArrayList<Marble> getMarbles() {

            Reader reader = new InputStreamReader(Objects.requireNonNull(CardFactory.class.getResourceAsStream("/marbles.json")));
            final GsonBuilder builder = new GsonBuilder();

            builder.registerTypeAdapter(Producible.class, new InterfaceAdapter<>());
            Gson gson = builder.create();

        Marble[] marbles = gson.fromJson(reader, Marble[].class);


        return new ArrayList<>(Arrays.asList(marbles));


    }
}
