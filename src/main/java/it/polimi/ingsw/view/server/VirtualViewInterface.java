package it.polimi.ingsw.view.server;


import it.polimi.ingsw.model.ActionToken;
import it.polimi.ingsw.model.cards.DevelopmentDeck;
import it.polimi.ingsw.model.cards.leadercards.LeaderCard;
import it.polimi.ingsw.model.gameboard.Marble;
import it.polimi.ingsw.model.gameboard.Resource;
import it.polimi.ingsw.model.gameboard.ResourceType;
import it.polimi.ingsw.model.player.Player;

import java.util.List;
import java.util.Map;

/**
 * Virtual view interface with the methods that controller can call
 */
public interface VirtualViewInterface {

    /**
     * Asks a user to choose its leader card
     */
    void toDoChooseLeaderCards(String user, List<LeaderCard> leaderCards);

    /**
     * Asks a user to choose its leader card
     */
    void toDoChooseLeaderCards(List<String> users, List<LeaderCard> leaders);

    /**
     * Asks the user to choose its resources
     * @param nickname          the user's nickname
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
     * Notify the winner and the losers
     */
    void notifyWinner(String winner);
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

    /**
     * this method alerts that resources have been bought
     * @param resources resources bought
     */
    void sendResourcesBought(List<Resource> resources);

    /**
     * this method sends the current game board
     * @param cardMarket current card market
     * @param market current marble market
     * @param freeMarble current free marble
     */
    void sendGameBoard(DevelopmentDeck[][] cardMarket, Marble[][] market, Marble freeMarble);

    /**
     * this method updates one's position
     * @param nickname pplayer's nickname
     */
    void updatePlayerPosition(String nickname);

    /**
     * tihs method alerts that lorenzo's turn has been played
     * @param lorenzoAction the action token drawn
     * @param lorenzoPosition lorenzo's position in pope road
     */
    void sendLorenzoTurn(ActionToken lorenzoAction, int lorenzoPosition);

    /**
     * this method updates the deposit
     * @param user player's nickname
     * @param updateStrongbox the updated strongbox
     * @param updatedWarehouse the uppdate warehouse
     */
    void updateDepositAfterAction(String user, Map<ResourceType, List<Resource>> updateStrongbox, List<List<Resource>> updatedWarehouse);
}

