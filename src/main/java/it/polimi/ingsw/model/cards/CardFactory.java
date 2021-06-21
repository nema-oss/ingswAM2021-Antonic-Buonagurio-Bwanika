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
import java.util.Objects;

/**
 * this class returns the cards crated from the json file
 */
public class CardFactory {


    private DevelopmentCard[] developmentCards;
    private LeaderCard[] leaderCards;

    public CardFactory() { }

    /**
     * this method parses the Json file configuration and creates the Development cards
     * @return the list of development cards created
     */


    public ArrayList<DevelopmentCard> getDevelopmentCards() {
        try {
            Reader file = new InputStreamReader(Objects.requireNonNull(CardFactory.class.getResourceAsStream("/developmentCards.json")));
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

    /**
     * this method parses the Json file configuration and creates the leader cards
     * @return the list of leader cards created
     */

    public List<LeaderCard> getLeaderCards() {

            Reader file = new InputStreamReader(Objects.requireNonNull(CardFactory.class.getResourceAsStream("/leaderCards.json")));
            final GsonBuilder builder = new GsonBuilder();

            builder.registerTypeAdapter(Producible.class, new InterfaceAdapter<>());
            builder.registerTypeAdapter(LeaderCard.class ,new LeaderCardDeserializer());

            Gson gson = builder.create();
            leaderCards = gson.fromJson(file, LeaderCard[].class);

        return new ArrayList<>(Arrays.asList(leaderCards));
    }
}
