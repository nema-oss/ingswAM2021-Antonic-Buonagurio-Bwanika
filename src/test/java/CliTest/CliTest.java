package CliTest;

import it.polimi.ingsw.model.Game;
import it.polimi.ingsw.model.cards.CardFactory;
import it.polimi.ingsw.model.cards.DevelopmentCard;
import it.polimi.ingsw.model.cards.DevelopmentDeck;
import it.polimi.ingsw.model.cards.leadercards.LeaderCard;
import it.polimi.ingsw.model.exception.FullDepositException;
import it.polimi.ingsw.model.exception.InsufficientPaymentException;
import it.polimi.ingsw.model.exception.NonExistentCardException;
import it.polimi.ingsw.model.exception.WrongDepositSwapException;
import it.polimi.ingsw.model.gameboard.CardMarket;
import it.polimi.ingsw.model.gameboard.GameBoard;
import it.polimi.ingsw.model.gameboard.Resource;
import it.polimi.ingsw.model.gameboard.ResourceType;
import it.polimi.ingsw.model.player.Board;
import it.polimi.ingsw.model.player.Deposit;
import it.polimi.ingsw.model.player.Player;
import it.polimi.ingsw.view.client.Cli;
import it.polimi.ingsw.view.client.utils.Formatting;
import it.polimi.ingsw.view.client.utils.InputValidator;
import it.polimi.ingsw.view.client.viewComponents.ClientGameBoard;
import it.polimi.ingsw.view.client.viewComponents.ClientPlayer;
import it.polimi.ingsw.view.client.viewComponents.ClientGameBoard;
import org.junit.Test;

import java.text.Normalizer;
import java.util.*;

public class CliTest {
    private CardFactory cardFactory;
    private List<LeaderCard> leaderCards;

    @Test
    public void clearScreen(){
        Formatting.clearScreen();
        System.out.println(4);
        Formatting.clearScreen();

    }
    @Test
    public void showTitle(){
        Cli cli = new Cli();
        Scanner scanner = new Scanner(System.in);
        String input = scanner.nextLine();
        List<Resource> resources = new ArrayList<>();
        resources.add(new Resource(ResourceType.SHIELD));
        resources.add(new Resource(ResourceType.STONE));
        resources.add(new Resource(ResourceType.COIN));
        resources.add(new Resource(ResourceType.SERVANT));
        Map<Resource,Integer> userChoice = InputValidator.isValidPlaceResourceAction(resources,input);
        System.out.println(userChoice);

    }

    @Test
    public void showLeaders(){
        cardFactory = new CardFactory();
        leaderCards = cardFactory.getLeaderCards();
        Map<LeaderCard,Boolean> cards = new HashMap<>();
        ArrayList<LeaderCard> leaders = new ArrayList<>();
        cards.put(leaderCards.get(12),true);
        cards.put(leaderCards.get(13),false);
        Cli cli = new Cli();
        cli.showLeaderCards(cards);
        for(int i=0; i<16; i+=4)
            leaders.add(leaderCards.get(i));
        cli.showLeaderCards(leaderCards);
    }

    @Test
    public void showGameBoard() {
        ClientGameBoard gameBoard = new ClientGameBoard();
        Cli cli = new Cli();
        cli.showGameBoard(gameBoard);

    }

   @Test
    public void showEmptyGameBoard() {
        ClientGameBoard gameBoard = new ClientGameBoard();
        Cli cli = new Cli();
        for(int i=0; i<2; i++){

                for(int k=0; k<4; k++){
                    gameBoard.remove(i,0);
                }

        }
        cli.showGameBoard(gameBoard);
        Formatting.clearScreen();
        cli.showGameBoard(gameBoard);
        cli.askTurnAction();

    }

    @Test
    public void showBoard() throws WrongDepositSwapException, FullDepositException, Exception, NonExistentCardException, InsufficientPaymentException {
        Board board = new Board();
        Cli cli = new Cli();
        GameBoard gameBoard = new GameBoard();
        ClientGameBoard cgameBoard = new ClientGameBoard();
        ClientPlayer p = new ClientPlayer("Paolo", cgameBoard);
        //cli.showBoard(cgameBoard, p);
        List<Resource> aLot = new ArrayList<>();
        for(int i=0; i<10; i++) {
            aLot.add(new Resource(ResourceType.SHIELD));
            aLot.add(new Resource(ResourceType.COIN));
            aLot.add(new Resource(ResourceType.SERVANT));
            aLot.add(new Resource(ResourceType.STONE));
        }

        p.getDeposit().addResource(1,new Resource(ResourceType.SHIELD));
        p.getDeposit().addResource(2,new Resource(ResourceType.STONE));
        p.getStrongbox().addResource(aLot);
        p.getPlayerBoard().addDevelopmentCard(gameBoard.getCardMarket().getCard(2,1));
        p.getPlayerBoard().addDevelopmentCard(gameBoard.getCardMarket().getCard(1,1));
        p.getPlayerBoard().addDevelopmentCard(gameBoard.getCardMarket().getCard(0,1));
        p.getPlayerBoard().addDevelopmentCard(gameBoard.getCardMarket().getCard(2,2));
        p.getPlayerBoard().addDevelopmentCard(gameBoard.getCardMarket().getCard(1,2));
        p.getPlayerBoard().addDevelopmentCard(gameBoard.getCardMarket().getCard(0,2));
        p.getPlayerBoard().addDevelopmentCard(gameBoard.getCardMarket().getCard(2,0));
        p.getPlayerBoard().getPopeRoad().move(22);
        cli.showBoard(cgameBoard, p);
        //cli.showPopeRoad(p);
        //cli.showOtherPlayerBoard("paolo", p.getPlayerBoard());
    }

    @Test
    public void showAllResources(){
        Cli cli = new Cli();
        cli.showAllAvailableResources();

    }

    @Test
    public void showActiveLeaders(){
        cardFactory = new CardFactory();
        leaderCards = cardFactory.getLeaderCards();
        Cli cli = new Cli();
        ClientPlayer clientPlayer = new ClientPlayer("prova", new ClientGameBoard());
        ArrayList<LeaderCard> hand = new ArrayList<>();
        hand.add(leaderCards.get(12));
        hand.add(leaderCards.get(13));
        clientPlayer.setHand(hand);
        HashMap<LeaderCard, Boolean> active = new HashMap<>();
        active.put(hand.get(0), true);
        active.put(hand.get(1), false);
        cli.showLeaderCards(active);
    }



}
