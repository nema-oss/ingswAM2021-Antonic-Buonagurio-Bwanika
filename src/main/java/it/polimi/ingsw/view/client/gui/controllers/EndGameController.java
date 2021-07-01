package it.polimi.ingsw.view.client.gui.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Label;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class EndGameController {

    @FXML
    Label message, firstPlace, secondPlace, thirdPlace, fourthPlace ;


    /**
     * this method sets the message in the final scene
     * @param text the final message
     */
    public void setMessage(String text){
        message.setText(text);
    }

    /**
     * Set the game placements
     * @param pointsMap a map with each player and his points
     */
    public void setWinner(Map<String, Integer> pointsMap) {

        int numOfPlayers = pointsMap.size();

        List<Integer> pointsList =  pointsMap.values().stream().sorted().collect(Collectors.toList());
        List<String> players = new ArrayList<>();

        for(Integer points : pointsList)
            for(String user : pointsMap.keySet())
                if(pointsMap.get(user).equals(points)) {
                    players.add(user);
                    break;
                }

        if(numOfPlayers >= 2){
            firstPlace.setVisible(true);
            firstPlace.setText("FIRST PLACE: " +players.remove(0).toUpperCase() + "  with "+pointsList.remove(0)+" points");
            secondPlace.setVisible(true);
            secondPlace.setText("SECOND PLACE: " +players.remove(0).toUpperCase() + "  with "+pointsList.remove(0)+" points");
        }
        if(numOfPlayers >= 3){
            thirdPlace.setVisible(true);
            thirdPlace.setText("THIRD PLACE: " +players.remove(0).toUpperCase() + "  with "+pointsList.remove(0)+" points");
        }
        if(numOfPlayers==4){
            fourthPlace.setVisible(true);
            fourthPlace.setText("FOURTH PLACE: " +players.remove(0).toUpperCase() + "  with "+pointsList.remove(0)+" points");
        }
    }
}
