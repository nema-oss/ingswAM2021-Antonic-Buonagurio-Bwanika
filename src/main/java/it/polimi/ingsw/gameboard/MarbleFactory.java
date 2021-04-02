package it.polimi.ingsw.gameboard;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Arrays;

import com.google.gson.Gson;
import com.google.gson.JsonParser;
import it.polimi.ingsw.cards.DevelopmentCard;


public class MarbleFactory {

    private Marble[] marbles;

    public void factoryMarbles() {
        try {
            Reader reader = new FileReader("C:\\Users\\Chiara\\IdeaProjects\\ingswAM2021-Antonic-Buonagurio-Bwanika\\src\\main\\java\\it\\polimi\\ingsw\\gameboard\\marbles.json");

            Gson gson = new Gson();
            marbles = gson.fromJson(reader, Marble[].class);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public ArrayList<Marble> getMarbles() {

        return new ArrayList<>(Arrays.asList(marbles));
    }
}
