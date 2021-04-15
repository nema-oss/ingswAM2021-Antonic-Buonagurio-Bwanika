package it.polimi.ingsw.player;

import com.google.gson.Gson;
import it.polimi.ingsw.player.PopeSection;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.Reader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class PopeSectionFactory {

    private List<PopeSection> popeSections;

    public PopeSectionFactory() {}

    public List<PopeSection> getPopeSections() {

        try {
            Reader reader = new FileReader("src/main/resources/popesections.json");
            Gson gson = new Gson();
            PopeSection[] popeSectionArray = gson.fromJson(reader, PopeSection[].class);
            popeSections = new ArrayList<>(Arrays.asList(popeSectionArray));

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        return popeSections;
    }

}