package it.polimi.ingsw;

import com.google.gson.Gson;

import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.util.ArrayList;
import java.util.List;

public class CardFactory {


    private DevelopmentCard[] developmentCards;
    private LeaderCard[] leaderCards;

    /*
        * this method parse the Json file configuration and create the Development cards
     */

    public void factoryDevelopmentCard() {

        // look how to change this try and catch exception: search "file opening exception handling java"
        try (
                // doesn't need the full length file name + try function InputStreamReader("main".class.getResourceAsStream("filename")

                Reader reader = new FileReader("/home/rene/Documents/ingswAM2021-Antonic-Buonagurio-Bwanika/src/main/java/it/polimi/ingsw/cards.json")) {

            Gson gson = new Gson();
            // Convert JSON File to Java Object
            developmentCards = gson.fromJson(reader, DevelopmentCard[].class);

            //System.out.println(card.getLevel());

        } catch (
                IOException e) {
            e.printStackTrace();
        }
    }

    /*
     * this method parse the Json file configuration and create the Leader cards
     */

    public void factoryLeaderCard() {

        // look how to change this try and catch exception: search "file opening exception handling java"

        try (
                // doesn't need the full length file name + try function InputStreamReader("main".class.getResourceAsStream("filename")
                Reader reader = new FileReader("/home/rene/Documents/ingswAM2021-Antonic-Buonagurio-Bwanika/src/main/java/it/polimi/ingsw/cards.json")) {

            Gson gson = new Gson();
            // Convert JSON File to Java Object
            leaderCards = gson.fromJson(reader, LeaderCard[].class);

            //System.out.println(card.getLevel());

        } catch (
                IOException e) {
            e.printStackTrace();
        }
    }

    public DevelopmentCard[] getDevelopmentCards() {
        return developmentCards;
    }

    public LeaderCard[] getLeaderCards() {
        return leaderCards;
    }





}
