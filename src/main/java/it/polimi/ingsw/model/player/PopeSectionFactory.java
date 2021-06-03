package it.polimi.ingsw.model.player;

import com.google.gson.Gson;
import it.polimi.ingsw.model.cards.CardFactory;

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class PopeSectionFactory implements Serializable {

    private List<PopeSection> popeSections;

    public PopeSectionFactory() {}

    public List<PopeSection> getPopeSections() {


            //Reader reader = new FileReader("src/main/resources/popesections.json");
            Reader reader = new InputStreamReader(CardFactory.class.getResourceAsStream("/popesections.json"));
            Gson gson = new Gson();
            PopeSection[] popeSectionArray = gson.fromJson(reader, PopeSection[].class);
            popeSections = new ArrayList<>(Arrays.asList(popeSectionArray));


        return popeSections;
    }

}