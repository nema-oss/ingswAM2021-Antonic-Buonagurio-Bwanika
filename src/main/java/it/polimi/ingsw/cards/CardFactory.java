package it.polimi.ingsw.cards;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import it.polimi.ingsw.cards.DevelopmentCard;
import it.polimi.ingsw.cards.leadercards.LeaderCard;
import it.polimi.ingsw.gameboard.InterfaceAdapter;
import it.polimi.ingsw.gameboard.Producible;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.util.ArrayList;
import java.util.Arrays;

public class CardFactory {


    private DevelopmentCard[] developmentCards;
    private LeaderCard[] leaderCards;

    public CardFactory() { }

    /*
        * this method parse the Json file configuration and create the Development cards
     */

    public ArrayList<DevelopmentCard> getDevelopmentCards() {
        // look how to change this try and catch exception: search "file opening exception handling java"
        try {
            // doesn't need the full length file name + try function InputStreamReader("main".class.getResourceAsStream("filename")

            Reader file = new FileReader("src/main/resources/developmentCards.json");
            final GsonBuilder builder = new GsonBuilder();

            builder.registerTypeAdapter(Producible.class, new InterfaceAdapter<>());
            Gson gson = builder.create();
            developmentCards = gson.fromJson(file, DevelopmentCard[].class);
            file.close();
        } catch (IOException e){
            e.printStackTrace();
        }
        return new ArrayList<>(Arrays.asList(developmentCards));
    }

    /*
     * this method parse the Json file configuration and create the Leader cards
     */

    public ArrayList<LeaderCard> getLeaderCards() {

        try {
            // doesn't need the full length file name + try function InputStreamReader("main".class.getResourceAsStream("filename")

            Reader file = new FileReader("src/main/resources/leaderCards.json");
            final GsonBuilder builder = new GsonBuilder();
            builder.registerTypeAdapter(Producible.class, new InterfaceAdapter<>());

            Gson gson = builder.create();
            leaderCards = gson.fromJson(file, LeaderCard[].class);

        } catch (FileNotFoundException e){
            e.printStackTrace();
        }

        return new ArrayList<>(Arrays.asList(leaderCards));

    }

}
