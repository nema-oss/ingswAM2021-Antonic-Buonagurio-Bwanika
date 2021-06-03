package it.polimi.ingsw.model.cards;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import it.polimi.ingsw.model.cards.leadercards.LeaderCard;
import it.polimi.ingsw.model.cards.leadercards.LeaderCardDeserializer;
import it.polimi.ingsw.model.gameboard.InterfaceAdapter;
import it.polimi.ingsw.model.gameboard.Producible;

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

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

            //Reader file = new FileReader("src/main/resources/developmentCards.json");
            Reader file = new InputStreamReader(CardFactory.class.getResourceAsStream("/developmentCards.json"));
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

    public List<LeaderCard> getLeaderCards() {


            // doesn't need the full length file name + try function InputStreamReader("main".class.getResourceAsStream("filename")

            //Reader file = new FileReader("src/main/resources/leaderCards.json");
            Reader file = new InputStreamReader(CardFactory.class.getResourceAsStream("/leaderCards.json"));
            final GsonBuilder builder = new GsonBuilder();
            builder.registerTypeAdapter(Producible.class, new InterfaceAdapter<>());
            builder.registerTypeAdapter(LeaderCard.class ,new LeaderCardDeserializer());

            Gson gson = builder.create();
            leaderCards = gson.fromJson(file, LeaderCard[].class);




        return new ArrayList<>(Arrays.asList(leaderCards));

    }

}
