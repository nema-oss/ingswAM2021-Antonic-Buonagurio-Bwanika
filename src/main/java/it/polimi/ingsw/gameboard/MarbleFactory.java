package it.polimi.ingsw.gameboard;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;

import com.google.gson.Gson;
import com.google.gson.JsonParser;
import it.polimi.ingsw.cards.DevelopmentCard;


public class MarbleFactory {

    private Marble[] marbles;

    public void factoryMarbles() {
        try {
            Reader reader = new FileReader("marbles.json");

            Gson gson = new Gson();

            // Convert JSON File to Java Object
            marbles = gson.fromJson(reader, Marble[].class);

            reader.close();

        } catch (IOException e){
            e.printStackTrace();
        }


    }

    public ArrayList<Marble> getMarbles() {

        return new ArrayList<>(Arrays.asList(marbles));
    }
}
