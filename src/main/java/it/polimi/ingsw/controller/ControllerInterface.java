package it.polimi.ingsw.controller;


import it.polimi.ingsw.model.cards.DevelopmentCard;
import it.polimi.ingsw.model.cards.DevelopmentDeck;
import it.polimi.ingsw.model.cards.leadercards.LeaderCard;
import it.polimi.ingsw.model.gameboard.Marble;
import it.polimi.ingsw.model.gameboard.Resource;
import it.polimi.ingsw.model.gameboard.ResourceType;
import it.polimi.ingsw.model.player.Board;
import it.polimi.ingsw.view.server.VirtualViewInterface;

import java.util.List;
import java.util.Map;

/**
 * this interface represents the controller
 * @author Chiara
 */
public interface ControllerInterface {


    /**
     * this method adds a player to the game
     * @param nickname the chosen nickname
     * @return the list of errors generated
     */
    List<Error> onNewPlayer(String nickname);

    /**
     * this method starts the game
     */
    List<Error> onStartGame();


    /**
     * this method assigns the leaderCards chosen to the player's hand
     * @param nickname of the player
     * @param leaderCardsChosen cards chosen by the player
     * @return the list of errors generated
     */
    List<Error> onLeaderCardsChosen(String nickname, List<LeaderCard> leaderCardsChosen);


    /**
     * this method gives the initial resources chosen to the player
     * @param nickname player's nickname
     * @param resourcesChosen resourceTypes chosen
     * @return the list of errors generated
     */
    List<Error> onResourcesChosen(String nickname, Map<ResourceType, Integer> resourcesChosen);


    /**
     * this method controls if the player canactivate the production
     * @param nickname player's nickname
     * @return the list of errors generated
     */
    List<Error> onActivateProduction(String nickname);

    /**
     * this method activates the production on DevelopmentCards
     * @param nickname the player's nickname
     * @param developmentCards tha cards chosen for production
     * @return the list of errors generated
     */

    List<Error> onActivateDevelopmentProduction(String nickname, List<DevelopmentCard> developmentCards);

    /**
     * this method activates production on active leader cards
     * @param nickname the player's nickname
     * @param leaderCards the leaderCards chosen for production
     * @return the list of errors generated
     */
    List<Error> onActivateLeaderProduction(String nickname, List<LeaderCard> leaderCards);

    /**
     * this method activates the board production
     * @param nickname the player's nickname
     * @param userChoiceMap a map containing the resource the player wants to get e the ones to put in the production
     * @return the list of errors generated
     */
    List<Error> onActivateBoardProduction(String nickname, Map<Resource, List<ResourceType>> userChoiceMap);

    /**
     * this method end a player's production
     * @param nickname the player's nickname
     * @return the list of errors generated
     */
    List<Error> onEndProduction(String nickname);


    /**
     * this method calls Player's method buyDevelopmentCard
     * @param nickname the player's nickname
     * @param row the row of the cardMarket
     * @param column the column of the cardMarket
     * @return the list of errors generated
     */
    List<Error> onBuyDevelopmentCards(String nickname, int row, int column);


    /**
     * this method calls the Player's method buyResoources
     * @param nickname the player's nickname
     * @param row the row of the marbleMarket
     * @param column the column of the marbleMarket
     * @return the list of errors generated
     */
    List<Error> onBuyResources(String nickname, int row, int column);


    /**
     * this method adds the resources just bought from the marbleMarket to the player's deposit
     * @param nickname the player's nickname
     * @param resources the resources and the floor to put them in
     * @return the list of errors generated
     */
    List<Error> onPlaceResources(String nickname, Map<Resource, Integer> resources);


    /**
     * this method activates a player's leaderCard
     * @param nickname the player's nickname
     * @param leaderCard the leaderCard to activate
     * @return the list of errors generated
     */
    List<Error> onActivateLeader(String nickname, LeaderCard leaderCard);


    /**
     * this method discards the player's chosen LeaderCard
     * @param nickname the player's nickname
     * @param leaderCard the card to discard
     * @return the list of errors generated
     */
    List<Error> onDiscardLeader(String nickname, LeaderCard leaderCard);

    /**
     * this method reacts to a disconnection removing the player who has disconnected from the game
     * @param nickname nickname of the player who has disconnected
     * @return the list of errors generated
     */
    List<Error> onPlayerDisconnection(String nickname);


    /**
     * this method manages the case in which a player decides to end his turn before playing all the possible actions
     * @param nickname the player's nickname
     * @return the list of errors generated
     */
    List<Error> onEndTurn(String nickname);

    /**
     * this method calls player's method swapDepositFloor
     * @param nickname the player's nickname
     * @param x first floor
     * @param y second floor
     * @return the list of errors generated
     */
    List<Error> onMoveDeposit(String nickname, int x, int y);

    /**
     * this method calls the player's method discardResources
     * @param nickname the player's nickname
     * @param numberOfResourcesToDiscard the number of resources to discard
     * @return
     */
    List<Error> onDiscardResource(String nickname, int numberOfResourcesToDiscard);


    /**
     * this method gives 50 of each resource to the player
     * @param nickname of the player
     * @return the list of errors generated
     */
    List<Error> onCheat(String nickname);

    /**
     * this method calls the virtualView's method PlayTurn
     */
    void sendPlayTurn();

    /**
     * this method calls the virtualView's method endTurn
     */
    void sendEndTurn();

    /**
     * ths method calls the virtualView's method to send message "Choose initial Resources" and distributes initial FaithPoints
     */
    void sendChooseResources();

    /**
     * this method calls the virtualView's method to send message "Choose Leader Cards"
     */
    void sendChooseLeaderCards();

    /**
     * this method sets the virtual view for the controller
     * @param virtualView the game's virtualView
     */
    void setVirtualView(VirtualViewInterface virtualView);

    /**
     * Sends the updated board to the client after a successful standard action
     */
    Board sendBoardUpdate(String user);

    /**
     * Add the previously disconnected player to the game
     * @param disconnectedPlayer the previously disconnected player
     */
    void onPlayerReconnection(String disconnectedPlayer);


    /**
     * @return the marble market
     */
    Marble[][] getMarbleMarket();

    /**
     * @return the game's cardMarket
     */
    DevelopmentDeck[][] getCardMarket();

    /**
     * @param nickname the player's nickname
     * @return the player's position index in the poperoad
     */
    int getPlayerCurrentPosition(String nickname);

    /**
     * @return the free marble of the market
     */
    Marble getFreeMarble();


    /**
     * @return the current player's warehouse
     */
    List<List<Resource>> getUpdatedDeposit();

    /**
     * @return the current player's strongbox
     */
    Map<ResourceType, List<Resource>> getUpdatedStrongbox();

}
