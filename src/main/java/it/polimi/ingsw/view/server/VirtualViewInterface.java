package it.polimi.ingsw.view.server;


import it.polimi.ingsw.model.cards.leadercards.LeaderCard;
import it.polimi.ingsw.model.gameboard.Resource;

import java.util.ArrayList;
import java.util.List;

/**
 * Virtual view interface with the methods that controller can call
 */
public interface VirtualViewInterface {

    /**
     * Asks a user to choose its leader card
     */
    void toDoChooseLeaderCards(String nickname, List<LeaderCard> leaders);

    /**
     * Asks the user to choose its resources
     * @param nickname the user's nickname
     * @param numberOfResources the amount of resources to select
     */
    void toDoChooseResources(String nickname, int numberOfResources);

    /**
     * This method alerts the clients that it is the last turn to play
     */
    void lastRound();

    /**
     * This method alerts the clients that the match is finished
     */
    void endMatch();

    /**
     * This method asks a user to play its turn
     * @param nickname the user's nickname
     */
    void playTurn(String nickname);

    /**
     * This method alerts a user that its turn is finished
     * @param nickname the user's nickname
     */
    void endTurn(String nickname);

    void sendResourcesBought(List<Resource> resources);
}
