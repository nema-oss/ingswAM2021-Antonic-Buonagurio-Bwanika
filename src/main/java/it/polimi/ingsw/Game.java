package it.polimi.ingsw;

import it.polimi.ingsw.cards.ActionTokenDeck;
import it.polimi.ingsw.cards.Deck;
import it.polimi.ingsw.cards.DevelopmentCard;
import it.polimi.ingsw.cards.leadercards.LeaderCard;
import it.polimi.ingsw.cards.leadercards.LeaderDeck;
import it.polimi.ingsw.exception.InsufficientPaymentException;
import it.polimi.ingsw.exception.NonexistentCardException;
import it.polimi.ingsw.gameboard.CardMarket;
import it.polimi.ingsw.gameboard.GameBoard;
import it.polimi.ingsw.gameboard.Resource;
import it.polimi.ingsw.gameboard.ResourceType;
import it.polimi.ingsw.player.Board;
import it.polimi.ingsw.player.Cell;
import it.polimi.ingsw.player.PopeRoad;

import java.util.*;
import java.util.concurrent.ThreadLocalRandom;

 /*public class Game {


    private ArrayList<Player> listOfPlayers;
    private Player currentPlayer;
    private ArrayList<Player> leaderboard;
    private Player winner;
    private Queue<Integer> popeSpaces;
    private PopeRoad lorenzoPopeRoad;
    private ActionTokenDeck actionDeck;
    private LeaderDeck leaderDeck;
    private GameBoard gameBoard;
    private int lorenzoPoints;
    private int lastPopeSpace;
    private final int RESOURCE_VICTORY_POINTS_RATIO = 5;

    Game(){



    }


    public void startGame(){

        for(Player p: listOfPlayers){
            ArrayList<LeaderCard> hand = new ArrayList<LeaderCard>();
            for(int i = 0; i < 4; i++){
                hand.add(leaderDeck.drawCard());

            }
            p.setHand(hand);
        }

        Collections.shuffle(listOfPlayers);
        currentPlayer = listOfPlayers.get(0);
        // player know choose the resource they want, could be passed to the method as a map Player:ResourceType

        // this is based on the game logic, hardcoded, need a fix
        for(int i = 0; i < listOfPlayers.size(); i++){
            //give victory points to third and fourth
            if(i + 1 == 2 || i + 1 == 4) listOfPlayers.get(i).addVictoryPoints(1);
        }

    }


    public Player nextPlayer(){

        int i = listOfPlayers.indexOf(currentPlayer);
        Player nextPlayer;
        if(i < listOfPlayers.size()-1)
             nextPlayer = listOfPlayers.get(i+1);
        else{
            i = 0;
            nextPlayer = listOfPlayers.get(i);
        }
        return nextPlayer;
    }


    public Player getPlayerByNickname(String nickname) throws Exception {


        for (Player p : listOfPlayers) {
            if (p.getNickname().equals(nickname)) {
                return p;
            }
        }
        throw new Exception();

    }



    public Player getWinner() {
        return winner;
    }



    public ArrayList<Player> getListOfPlayers() {
        return listOfPlayers;
    }


    public Player getCurrentPlayer(){ return currentPlayer;}


    public void checkPlayersPosition(int currentPlayerPositionIndex) {

        Cell currentPlayerPosition = currentPlayer.getPosition();
        int currentPlayerVaticanId = currentPlayerPosition.getVaticanReportSectionId();

        if(currentPlayerPositionIndex > popeSpaces.peek()){
            int victoryPoints = currentPlayer.getPopeRoad().VaticanReport(currentPlayerPosition);
            currentPlayer.addVictoryPoints(victoryPoints);
            for(Player p: listOfPlayers){
                if(p.getPosition().getVaticanReportSectionId() >= currentPlayerVaticanId)
                    p.getPopeRoad().VaticanReport(p.getPosition());
            }
            popeSpaces.remove();
        }

        else if(currentPlayerPositionIndex == lastPopeSpace) endGame();

    }


    public void LorenzoTurn(){

        ActionToken actionToken = actionDeck.drawCard();
        //useActionToken(actionDeck.drawCard());
    }



    public void checkLorenzoPosition(int lorenzoPosition){

        if(lorenzoPosition > popeSpaces.peek()){
            int lorenzoVaticanId = lorenzoPopeRoad.getCurrentPosition().getVaticanReportSectionId();
            if(currentPlayer.getPosition().getVaticanReportSectionId() >= lorenzoVaticanId)
                currentPlayer.getPopeRoad().VaticanReport(currentPlayer.getPosition());
            popeSpaces.remove();
        }
        else if(lorenzoPosition == lastPopeSpace) lostGame();
    }



    public void useActionToken(ActionTokenMove actionToken){

        lorenzoPopeRoad.move(actionToken.getSteps());
        if(lorenzoPopeRoad.getCurrentPosition().isPopeSpace())
            checkLorenzoPosition(lorenzoPopeRoad.getCurrentPositionIndex());
        if(actionToken.isShuffle())
            actionDeck.shuffle();

    }



    public void useActionToken(ActionTokenDiscard actionToken){

        CardMarket market = gameBoard.getCardMarket();
        market.discardCard(actionToken.getAmount(), actionToken.getType());
        //lostGame();

    }


    private void lostGame() {
    }

    public void endGame(){

        for(Player p: listOfPlayers){

            p.addVictoryPoints(checkCardsPoints(p));
            p.addVictoryPoints(checkResourcePoints(p));
        }

        winner = listOfPlayers.stream().max(Comparator.comparing(Player::getVictoryPoints)).get();

    }


    private int checkCardsPoints(Player p) {

        int points = 0;
        for(Stack<DevelopmentCard> cards: p.getPlayerBoard().getDevelopmentCards()){
            for(DevelopmentCard card: cards){
                points += card.getVictoryPoints();
            }
        }

        for(LeaderCard card: p.getActiveLeaderCards()){
            points += card.getVictoryPoints();
        }
        return points;
    }


    private int checkResourcePoints(Player p) {

       return Math.floorDiv(p.getDeposit().getAll().size() , RESOURCE_VICTORY_POINTS_RATIO);

    }


} */


