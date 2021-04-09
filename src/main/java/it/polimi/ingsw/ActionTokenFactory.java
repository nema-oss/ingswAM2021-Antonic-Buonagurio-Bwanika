package it.polimi.ingsw;

import com.google.gson.Gson;
import it.polimi.ingsw.player.Cell;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.Reader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ActionTokenFactory {

    private ActionToken[] actionTokens;

    public ArrayList<ActionToken> getTokens() {

        try {
            Reader reader = new FileReader("");
            Gson gson = new Gson();
            actionTokens = gson.fromJson(reader, ActionToken[].class);

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }


        return new ArrayList<ActionToken>(Arrays.asList(actionTokens));
    }
}
