package it.polimi.ingsw.view.client.gui.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Label;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class EndMultiplayerController {

    @FXML
    Label message, firstName, secondName, thirdName, fourthName, points1, points2, points3, points4, first, second, third, fourth;


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
            for(String user : pointsMap.keySet() )
                if(pointsMap.get(user).equals(points) && !players.contains(user)) {
                    players.add(user);
                    break;
                }

        if(numOfPlayers >= 2){
            first.setVisible(true);
            firstName.setVisible(true);
            points1.setVisible(true);
            firstName.setText(players.remove(players.size()-1));
            points1.setText(pointsList.remove(pointsList.size()-1).toString());
            setMessage(firstName.getText()+" won the match!");
            second.setVisible(true);
            secondName.setVisible(true);
            points2.setVisible(true);
            secondName.setText(players.remove(players.size()-1));
            points2.setText(pointsList.remove(pointsList.size()-1).toString());
        }

        if(numOfPlayers >= 3){
            third.setVisible(true);
            thirdName.setVisible(true);
            points3.setVisible(true);
            thirdName.setText(players.remove(players.size()-1));
            points3.setText(pointsList.remove(pointsList.size()-1).toString());
        }

        if(numOfPlayers==4){
            fourth.setVisible(true);
            fourthName.setVisible(true);
            points4.setVisible(true);
            fourthName.setText(players.remove(players.size()-1));
            points4.setText(pointsList.remove(pointsList.size()-1).toString());
        }
    }
}
