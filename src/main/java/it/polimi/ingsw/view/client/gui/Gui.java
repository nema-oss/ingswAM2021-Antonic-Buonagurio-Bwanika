package it.polimi.ingsw.view.client.gui;

import it.polimi.ingsw.messages.Message;
import it.polimi.ingsw.messages.setup.client.LoginRequest;
import it.polimi.ingsw.messages.setup.client.UpdateClientPlayerBoardsMessage;
import it.polimi.ingsw.messages.setup.server.DoLoginMessage;
import it.polimi.ingsw.messages.setup.server.LoginDoneMessage;
import it.polimi.ingsw.messages.utils.MessageSender;
import it.polimi.ingsw.model.ActionToken;
import it.polimi.ingsw.model.ActionTokenDiscard;
import it.polimi.ingsw.model.ActionTokenMove;
import it.polimi.ingsw.model.cards.DevelopmentCard;
import it.polimi.ingsw.model.cards.DevelopmentCardType;
import it.polimi.ingsw.model.cards.DevelopmentDeck;
import it.polimi.ingsw.model.cards.leadercards.AuxiliaryDeposit;
import it.polimi.ingsw.model.cards.leadercards.LeaderCard;
import it.polimi.ingsw.model.gameboard.Marble;
import it.polimi.ingsw.model.gameboard.Resource;
import it.polimi.ingsw.model.gameboard.ResourceType;
import it.polimi.ingsw.network.LocalMatchHandler;
import it.polimi.ingsw.network.client.EchoClient;
import it.polimi.ingsw.view.client.View;
import it.polimi.ingsw.view.client.gui.controllers.*;
import it.polimi.ingsw.view.client.utils.TurnActions;
import it.polimi.ingsw.view.client.viewComponents.ClientPlayerBoard;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.List;
import java.util.Map;

/**
 * this class represent the gui
 */
public class Gui extends View {

    public PlayerBoardController playerBoardController;
    public GameBoardController gameBoardController;

    private Stage primaryStage;
    private Scene startingScene;

    private NicknameController nicknameController;
    private Scene nicknameScene;

    private NumOfPlayersController numOfPlayersController;
    private Scene numberOfPlayersScene;

    private Scene loginWaitScene;
    private LoginWaitController loginWaitController;

    private ChooseLeaderController chooseLeaderController;
    private Scene chooseLeaderScene;

    private ChooseResourcesController chooseResourcesController;
    private Scene chooseResourcesScene;

    private ConnectionController connectionController;

    private Scene gameBoardScene;

    private Scene turnActionScene;
    private boolean isPlayingTurn;


    private ActionButtonsController actionButtonsController;

    private GameController gameSceneController;
    private Scene gameScene;
    private boolean isGameScene;

    private PlayerTabController playerTabController;

    private EndGameController endPlayerController;
    private EndMultiplayerController endMultiplayerController;
    private Scene endGameScene;
    private boolean lorenzoGame;

    public Gui(String ip, int port, Stage stage, Scene scene) {

        myIp = ip;
        myPort = port;
        this.primaryStage = stage;
        this.startingScene = scene;
        initLoginUsername();
        initNumberOfPlayers();
        initGameScene();
        initTurnActions();
        initEndGame();
        isGameScene = false;
        lorenzoGame = false;

    }

    public Gui(Stage stage, Scene scene, boolean localMatch) {

        this.primaryStage = stage;
        this.startingScene = scene;
        isGameScene = false;
        isLocalMatch = localMatch;
        initLoginUsername();
        initNumberOfPlayers();
        initGameScene();
        initTurnActions();
        initEndGame();

    }


    /**
     * this method initializes the login scene
     */
    private void initLoginUsername() {

        try {
            FXMLLoader loader = GuiManager.loadFXML("/gui/nickname");
            Parent root = loader.load();
            nicknameScene = new Scene(root);
            nicknameController = loader.getController();
            nicknameController.setGui(this);
            if (isLocalMatch)
                nicknameController.setLocalMatch(true);
        } catch (IOException e) {
            System.out.println("Could not initialize Nickname Scene");
        }
    }

    /**
     * this method initializes the scene to choose number of players
     */
    private void initNumberOfPlayers() {

        try {
            FXMLLoader loader = GuiManager.loadFXML("/gui/numOfPlayers");
            Parent root = loader.load();
            numberOfPlayersScene = new Scene(root);
            numOfPlayersController = loader.getController();
            numOfPlayersController.setGui(this);
        } catch (IOException e) {
            System.out.println("Could not initialize Number Of Players Scene");
        }
    }

    /**
     * This method loads the FXML of the lobby scene and initializes its controller
     */

    private void initLoginWait() {
        try {
            FXMLLoader loader = GuiManager.loadFXML("/gui/loginWait");
            Parent root = loader.load();
            loginWaitScene = new Scene(root);
            loginWaitController = loader.getController();
        } catch (IOException e) {
            System.out.println("Could not initialize loginWait Scene");
        }
    }


    /**
     * this method initializes the scene to choose leader cards
     *
     * @param leaderCards the cards to choose from
     */
    private void initChooseLeadersSelection(List<LeaderCard> leaderCards) {

        chooseLeaderController = gameSceneController.getChooseLeaderController();
        chooseLeaderController.setGui(this);
        chooseLeaderController.initializeLeaderCards(leaderCards);
    }

    /**
     * this method initializes the scene to choose the resources
     */
    private void initChooseResourcesSelection() {

        try {
            FXMLLoader loader = GuiManager.loadFXML("/gui/chooseResources");
            gameSceneController.leftBorder.setCenter(loader.load());
            chooseResourcesController = loader.getController();
            chooseResourcesController.setGui(this);

        } catch (IOException e) {
            System.out.println("Could not initialize Choose Resources Scene");
        }
    }


    /**
     * this method initializes the game scene
     */
    private void initGameScene() {

        try {
            FXMLLoader loader = GuiManager.loadFXML("/gui/game");
            Parent root = loader.load();
            gameScene = new Scene(root);
            gameSceneController = loader.getController();
            gameSceneController.setGui(this);
            gameBoardController = gameSceneController.gameBoardController;
            playerTabController = gameSceneController.playerTabController;
            playerTabController.setGui(this);


        } catch (IOException e) {
            System.out.println("Could not initialize Game Scene");
        }
    }

    /**
     * this method initializes the turns
     */
    private void initTurnActions() {

        try {
            FXMLLoader loader = GuiManager.loadFXML("/gui/actions");
            gameSceneController.leftBorder.setCenter(loader.load());
            actionButtonsController = loader.getController();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * this method initializes the game board
     */
    private void initGameBoard() {

        try {
            gameSceneController.initializeGameBoard();
        } catch (NullPointerException e) {
            System.out.println("Could not initialize Game Board Scene");
        }
    }

    /**
     * this method initializes the final scene
     */
    private void initEndGame() {

        try {
            FXMLLoader loader = GuiManager.loadFXML("/gui/endGameSingle");
            Parent root = loader.load();
            endGameScene = new Scene(root);
            endPlayerController = loader.getController();
            FXMLLoader loader2 = GuiManager.loadFXML("/gui/endGameMultiPlayer");
            Parent root2 = loader2.load();
            endGameScene = new Scene(root2);
            endMultiplayerController = loader2.getController();
        }catch (IOException ignored) {
            ignored.printStackTrace();
            System.out.println("Could not initialize End game Scene");
        }

    }

    /**
     * This method throws an alert to the user and it is called when something goes wrong
     *
     * @param title alert's title
     * @param text  alert's text
     */

    public void alertUser(String title, String text, Alert.AlertType type) {
        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setHeaderText(text);
        alert.showAndWait();
    }

    /**
     * Allows to set the server IP address
     */
    @Override
    public void setMyIp() {

    }

    /**
     * Allows to set the port
     */
    @Override
    public void setMyPort() {

    }

    public String getPlayerNickname() {
        return player.getNickname();
    }

    public int getNumberOfPlayers() {
        return otherPlayerBoards.size() + 1;
    }

    /**
     * Asks the users to choose its leader card
     *
     * @param cardChoice the card pool
     */
    @Override
    public void setLeaderCardChoice(List<LeaderCard> cardChoice) {

        Platform.runLater(() -> {
            String infoMessage = "Select two leader cards.";
            initGameScene();
            initChooseLeadersSelection(cardChoice);
            primaryStage.setScene(gameScene);
            gameSceneController.initializeGameBoard();
            try {
                gameSceneController.initializePlayerBoard();
            } catch (IOException e) {
                e.printStackTrace();
            }
            primaryStage.show();
            chooseLeaderController.setInstructionLabel(infoMessage);
        });
    }

    /**
     * Asks the user to choose its resource
     *
     * @param numberOfResources number of resources the user can choose
     */
    @Override
    public void setResourceTypeChoice(int numberOfResources) {


        Platform.runLater(() -> {
            String infoMessage = "Select " + numberOfResources + " resource type.";
            initChooseResourcesSelection();
            chooseResourcesController.setInstructionalLabel(infoMessage);
            chooseResourcesController.setNumberOfResources(numberOfResources);
        });
    }


    /**
     * this method updates the game board
     *
     * @param cardMarket the card market
     * @param market     the marble market
     * @param freeMarble the free marble
     */
    public void updateGameBoard(DevelopmentDeck[][] cardMarket, Marble[][] market, Marble freeMarble) {


        Platform.runLater(() -> {
            gameBoard.getMarket().update(market, freeMarble);
            gameBoard.getCardMarket().update(cardMarket);
            gameBoardController.updateMarbleMarket(gameBoard);
            gameBoardController.updateCardMarket(gameBoard);
        });

    }

    @Override
    public void showLorenzoAction(ActionToken lorenzoAction, int lorenzoPosition) {

        Platform.runLater(() -> {
            if(!lorenzoGame){
                try {
                    actionButtonsController.showLorenzoPosition();
                    lorenzoGame = true;
                } catch (IOException e) {
                    System.out.println("Can't load lorenzo position");
                }
            }
            String lorenzoMessage = "";
            int amount;
            if (lorenzoAction instanceof ActionTokenDiscard) {
                DevelopmentCardType type = ((ActionTokenDiscard) lorenzoAction).getType();
                amount = ((ActionTokenDiscard) lorenzoAction).getAmount();
                lorenzoMessage = "Lorenzo discarded " + amount + " card of type: " + type.toString();
            } else if (lorenzoAction instanceof ActionTokenMove) {
                amount = ((ActionTokenMove) lorenzoAction).getSteps();
                lorenzoMessage = "Lorenzo move on his Poperoad by " + amount + ". ";
                actionButtonsController.updateLorenzoPosition(amount);
            }
            actionButtonsController.showLorenzoTurn(lorenzoAction, lorenzoMessage);
        });

    }

    @Override
    public void showProductionResult(Map<ResourceType, List<Resource>> updatedStrongbox, List<List<Resource>> updatedWarehouse, List<AuxiliaryDeposit> auxiliaryDeposit) {

        Platform.runLater(() -> {
            player.updateDeposit(updatedStrongbox, updatedWarehouse, auxiliaryDeposit);
            player.setStandardActionDone();
            playerTabController.updatePlayerBoard(player.getNickname(), player.getPlayerBoard());
            actionButtonsController.setLeaderActionVisible(true);
            actionButtonsController.setStandardActionVisible(false);
            actionButtonsController.setResourcePaneVisible(false);
            actionButtonsController.setSwapPaneVisible(false);
            actionButtonsController.setChooseStandardActionVisible(false);
            actionButtonsController.setEndTurnVisible(true);
        });
    }

    /**
     * Alerts the users that it's the last round of the match
     */
    @Override
    public void showLastRound() {

        Platform.runLater(() -> {
            alertUser("Last Round", "GET READY! We are at the end of the journey! LAST ROUND STARTS NOW!", Alert.AlertType.INFORMATION);
        });
    }

    /**
     * Asks the user to play its turn action
     */
    @Override
    public void askTurnAction() {

        TurnActions action = gameSceneController.getTurnAction();
        switch (action) {
            case BUY_CARD:
                setBuyCardAction(false);
                break;
            case BUY_RESOURCES:
                setBuyResourceAction(false);
                break;
            case ACTIVATE_PRODUCTION:
                setProductionChoice(player.getDevelopmentCards(), player.getProductionLeaderCards(), false);
                break;
            case LEADER_ACTION:
                setLeaderCardAction(player.getHand(), false);
                break;
            case END_TURN:

        }
    }

    /**
     * Asks the user what card productions it wants to use
     *
     * @param developmentCards     its development card
     * @param leaderCards          its leader card type production
     * @param actionRejectedBefore true if the action was rejected before
     */
    @Override
    public void setProductionChoice(List<DevelopmentCard> developmentCards, List<LeaderCard> leaderCards, boolean actionRejectedBefore) {

        Platform.runLater(() -> {
            if (!actionRejectedBefore) {
                gameSceneController.setInstructionLabel("Select which production power you want to activate.");
            } else {
                String informationMessage = "Production action rejected. Try again.";
                alertUser("Warning", "Try again.", Alert.AlertType.WARNING);
            }
        });
    }

    /**
     * Asks the user what leader card it wants to use
     *
     * @param leaderCards          its leader card
     * @param actionRejectedBefore true if the action was rejected before
     */
    @Override
    public void setLeaderCardAction(List<LeaderCard> leaderCards, boolean actionRejectedBefore) {

        Platform.runLater(() -> {
            if (!actionRejectedBefore) {
                gameSceneController.setInstructionLabel("Select which cards you want to discard/activate. ");
            } else {
                String informationMessage = "Leader card action rejected. Try again. ";
                alertUser("Warning!", informationMessage, Alert.AlertType.WARNING);

            }
        });
    }

    /**
     * Asks the user what card it wants to buy
     *
     * @param actionRejectedBefore true if the action was rejected before
     */
    @Override
    public void setBuyCardAction(boolean actionRejectedBefore) {

        Platform.runLater(() -> {
            alertUser("Warning!", "Try again", Alert.AlertType.WARNING);
        });
    }

    /**
     * Asks the user what resources it wants to buy
     *
     * @param actionRejectedBefore true if the action was rejected before
     */
    @Override
    public void setBuyResourceAction(boolean actionRejectedBefore) {

        Platform.runLater(() -> {
            if (!actionRejectedBefore) {
                gameSceneController.setInstructionLabel("Select which row/column you want to buy");
            } else {
                alertUser("Warning!", "Try again", Alert.AlertType.WARNING);
            }
        });
    }

    /**
     * Shows the login
     *
     * @param message login message
     */
    @Override
    public void showLogin(DoLoginMessage message) {

        boolean isFirstPlayer = message.isFirstPlayer();
        nicknameController.setIsFirstPlayer(isFirstPlayer);

        Platform.runLater(
                () -> {
                    primaryStage.setScene(nicknameScene);
                    primaryStage.show();
                });

    }

    /**
     * Shows the player that the login has been done
     *
     * @param user
     */
    @Override
    public void showLoginDone(String user) {

        newMatch(user);

        Platform.runLater(
                () -> {
                    initLoginWait();
                    String infoMessage = "Login done, matchmaking... ";
                    loginWaitController.setInformationBox(infoMessage);
                    primaryStage.setScene(loginWaitScene);
                    primaryStage.show();
                    if (!isLocalMatch)
                        sendMessage(new LoginDoneMessage(user, true));
                });

    }

    /**
     * Shows the player that a new player has logged in
     *
     * @param username : username of the newly logged in player
     */
    @Override
    public void showNewUserLogged(String username) {

        String infoMessage = loginWaitController.getInformationBox() + "\n" + username + " joined the match!";
        Platform.runLater(() -> {
            loginWaitController.setInformationBox(infoMessage);
        });
    }


    /**
     * Shows that the server has not been found
     */
    @Override
    public void serverNotFound() {

        Platform.runLater(() -> {
            alertUser("Error", "No server found", Alert.AlertType.ERROR);
        });
    }

    /**
     * Shows that another client has disconnected
     *
     * @param otherClient
     */
    @Override
    public void showAnotherClientDisconnection(String otherClient) {

        Platform.runLater(() -> {
            alertUser("Information", otherClient + " has disconnected from the match!", Alert.AlertType.INFORMATION);
            otherPlayerBoards.remove(otherClient);
            gameSceneController.removePlayerBoard(otherClient);
            otherPlayerBoards.forEach((k, v) -> gameSceneController.updatePlayerBoard(k, v));


        });
    }

    /**
     * This method alerts the user that the server disconnected
     */
    @Override
    public void showServerDisconnection() {
        Platform.runLater(() -> {
            alertUser("Error", "Server disconnected. Try again later", Alert.AlertType.ERROR);
            primaryStage.setScene(startingScene);
        });
    }

    /**
     * Shows that the player has lost
     *
     * @param winner : username of the winner
     */
    @Override
    public void showYouLose(String winner) {

    }

    /**
     * Shows that the player has won
     */
    @Override
    public void showYouWin() {

    }

    /**
     * Shows the game is finished
     *
     * @param leaderboard the game leader board
     */
    @Override
    public void showEndGame(Map<String, Integer> leaderboard) {

        Platform.runLater(() -> {
            if (leaderboard.size() == 1) {
                String winner = leaderboard.keySet().stream().findFirst().get();
                endPlayerController.setMessage(winner + " has won the match");
                primaryStage.setScene(endGameScene);
            }else{
                primaryStage.setScene(endGameScene);
                endMultiplayerController.setWinner(leaderboard);
            }
        });

        try {
            Thread.sleep(30000);
        } catch (InterruptedException ignored) {

        }
        Platform.runLater(() -> {
            primaryStage.setScene(startingScene);
        });

    }

    /**
     * This method tells the user that login has been rejected
     *
     * @param isFirstPlayer true if it's the first player in the lobby
     */
    @Override
    public void showLoginFailed(boolean isFirstPlayer) {

        Platform.runLater(() -> {
            alertUser("Error", "Login failed. Try again with a different username", Alert.AlertType.ERROR);
        });

    }


    /**
     * This method tells the user that it has to play his turn
     *
     * @param currentPlayer the player that is playing now
     */
    @Override
    public void showPlayTurn(String currentPlayer) {

        Platform.runLater(() -> {
            if (!isGameScene) {
                isGameScene = true;
                try {
                    FXMLLoader loader = GuiManager.loadFXML("/gui/actions");
                    Parent root = loader.load();
                    gameSceneController.leftBorder.setCenter(root);
                    actionButtonsController = loader.getController();
                    actionButtonsController.setGui(this);
                    actionButtonsController.setGameController(gameSceneController);
                    gameSceneController.initializeActions();


                } catch (IOException e) {
                    e.printStackTrace();
                }
                primaryStage.setScene(gameScene);
            }

            if (currentPlayer.equals(player.getNickname())) {
                player.resetTurnActionCounter();
                actionButtonsController.setWaitVisible(false);
                actionButtonsController.setChooseActionTypeVisible(true);
            } else {
                actionButtonsController.setLorenzoVisible(false);
                actionButtonsController.setWaitVisible(true);
                actionButtonsController.setChooseActionTypeVisible(false);
            }

        });
    }


    /**
     * This method set the phase to choose where to place the resources after a buy resource action
     */
    @Override
    public void setPlaceResourcesAction() {

        Platform.runLater(() -> {
            if (player.getBoughtResources().size() != 0) {
                actionButtonsController.setSwapPaneVisible(true);
                actionButtonsController.setPlaceResources(player.getBoughtResources());
                actionButtonsController.setChooseActionTypeVisible(false);
                actionButtonsController.setBackButtonVisible(false);
            } else {
                player.setStandardActionDone();
                actionButtonsController.setLeaderActionVisible(true);
                actionButtonsController.setStandardActionVisible(false);
                actionButtonsController.setEndTurnVisible(true);
            }
        });
    }

    /**
     * This method tells the user that the leader card action has been accepted
     *
     * @param user
     * @param card
     * @param activate
     */
    @Override
    public void showAcceptedLeaderAction(String user, LeaderCard card, boolean activate) {

        Platform.runLater(() -> {
            if (user.equals(player.getNickname())) {
                alertUser("Information", "Leader card action accepted.", Alert.AlertType.CONFIRMATION);
                player.useLeaderCard(card, activate);
                playerTabController.controlLeaders(player);
                actionButtonsController.setLeaderActionVisible(true);
                actionButtonsController.setChooseLeaderActionVisible(false);
                actionButtonsController.setStandardActionVisible(!player.isStandardActionPlayed());
                actionButtonsController.setEndTurnVisible(true);
                playerBoardController.showActiveLeaders();
            } else {
                if (activate) {
                    otherPlayerBoards.get(user).getActiveLeaderCards().add(card);
                    gameSceneController.addLeaderToOtherPlayer(user, card);
                }
            }

        });
    }

    /**
     * This method tells the user that the leader card action has been accepted
     */
    @Override
    public void showRejectedLeaderAction() {
        Platform.runLater(() -> {
            alertUser("Warning", "Leader card action rejected.", Alert.AlertType.WARNING);
            playerTabController.controlLeaders(player);
        });
    }

    /**
     * This method tells the user that the buy card action has been accepted
     *
     * @param x,   y card coordinates
     * @param user
     */
    @Override
    public void showAcceptedBuyDevelopmentCard(String user, int x, int y) {

        Platform.runLater(() -> {
            if (player.getNickname().equals(user)) {
                DevelopmentCard cardChosen = player.buyDevelopmentCard(x, y);
                player.setStandardActionDone();
                playerTabController.updatePlayerBoard(player.getNickname(), player.getPlayerBoard());
                alertUser("Information", "Accepted buy card", Alert.AlertType.INFORMATION);
                actionButtonsController.setBuyCardVisible(false);
                actionButtonsController.setLeaderActionVisible(true);
                actionButtonsController.setStandardActionVisible(false);
                actionButtonsController.setEndTurnVisible(true);
                gameSceneController.makeCardMarketClickable(false);
            } else {
                gameBoardController.setTurnActionInformationBox(user + " has bought a card from market.");
            }
        });

    }

    /**
     * Show the results of a card production request
     *
     * @param user     the current user
     * @param accepted true if request accepted, false if not
     */
    @Override
    public void showProductionRequestResults(String user, boolean accepted) {

        Platform.runLater(() -> {

            if (player.getNickname().equals(user)) {
                if (!accepted) {
                    alertUser("Warning", "Production request rejected.Try again", Alert.AlertType.WARNING);
                } else {
                    alertUser("Information", "Production activated. End the production to see your new resources", Alert.AlertType.INFORMATION);
                }
            } else {
                if (accepted) {
                    Platform.runLater(() -> {
                        gameBoardController.setTurnActionInformationBox(user + " has used the production power");
                    });
                }
            }
        });
    }

    /**
     * This method add the leader cards to the user's hand
     *
     * @param choice user choice
     */
    @Override
    public void showLeaderCardsSelectionAccepted(List<LeaderCard> choice) {

        Platform.runLater(() -> {
            player.setHand(choice);
            gameSceneController.addLeadersToPlayer();
            Message message = new UpdateClientPlayerBoardsMessage(player.getNickname(), player.getPlayerBoard());
            sendMessage(message);
            gameSceneController.hideLeaders();
            if (otherPlayerBoards.size() + 1 == 1) {
                try {
                    actionButtonsController.showLorenzoPosition();
                } catch (IOException e) {
                    System.out.println("Can't load Lorenzo's Button");
                }
            }
        });
    }


    /**
     * Shows the results of the move deposit request.
     *
     * @param x,y      the floors to swap
     * @param accepted true if the request has been accepted, false if rejected
     */
    @Override
    public void showMoveDepositResult(int x, int y, boolean accepted) {

        if (accepted) {
            Platform.runLater(() -> {
                player.getDeposit().swapFloors(x, y);
                playerTabController.updatePlayerBoard(player.getNickname(), player.getPlayerBoard());
            });
        } else {
            Platform.runLater(() -> {
                alertUser("Warning", "Move deposit request rejected. Try again", Alert.AlertType.WARNING);
            });
        }
    }

    /**
     * Shows the result of the place resources request.
     *
     * @param user
     * @param accepted   true if the request has been accepted
     * @param userChoice the user choice
     */
    @Override
    public void showPlaceResourcesResult(String user, boolean accepted, Map<Resource, Integer> userChoice) {

        if (player.getNickname().equals(user)) {
            if (accepted) {
                Platform.runLater(() -> {
                    player.addResource(userChoice);
                    player.setStandardActionDone();
                    playerTabController.updatePlayerBoard(player.getNickname(), player.getPlayerBoard());
                    actionButtonsController.setLeaderActionVisible(true);
                    actionButtonsController.setStandardActionVisible(false);
                    actionButtonsController.setResourcePaneVisible(false);
                    actionButtonsController.setSwapPaneVisible(false);
                    actionButtonsController.setEndTurnVisible(true);
                });
            } else {
                Platform.runLater(() -> {
                    alertUser("Warning", "Incorrect place resources. Try again.", Alert.AlertType.WARNING);
                    setPlaceResourcesAction();
                });
            }
        } else if (accepted)
            Platform.runLater(()->{
                gameBoardController.setTurnActionInformationBox(user + " has bought resources from market");
            });
    }

    /**
     * Show the results of the selection the initial resources
     *
     * @param resourceChoice the user choice
     */
    @Override
    public void showResourceSelectionAccepted(Map<ResourceType, Integer> resourceChoice) {

        Platform.runLater(() -> {

            int j = 3;
            for (ResourceType resourceType : resourceChoice.keySet()) {
                for (int i = 0; i < resourceChoice.get(resourceType); i++)
                    player.getDeposit().addResource(j, new Resource(resourceType));
                j--;
            }
            playerBoardController.update(player.getPlayerBoard());
            gameSceneController.updatePlayerBoard(player.getNickname(), player.getPlayerBoard());

            UpdateClientPlayerBoardsMessage message = new UpdateClientPlayerBoardsMessage(player.getNickname(), player.getPlayerBoard());
            sendMessage(message);

        });


    }

    @Override
    public void showReconnectionToMatch() {

        Platform.runLater(() -> {
            try {
                gameSceneController.initializeGameBoard();
                gameSceneController.initializePlayerBoard();
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
    }

    @Override
    public void updatePlayerPosition(int position) {

        Platform.runLater(() -> {
            player.updateCurrentPosition(position);
            playerTabController.updatePlayerPosition(player.getNickname(), player);
        });
    }

    @Override
    public void updateOtherPlayerBoards(String user, ClientPlayerBoard clientPlayerBoard) {

        Platform.runLater(() -> {
            if (!otherPlayerBoards.containsKey(user) && !user.equals(player.getNickname())) {
                otherPlayerBoards.put(user, clientPlayerBoard);
                gameSceneController.addPlayerBoard(user, clientPlayerBoard);
            } else {
                otherPlayerBoards.put(user, clientPlayerBoard);
                otherPlayerBoards.forEach((k, v) -> gameSceneController.updatePlayerBoard(k, v));
            }
        });
    }


    public void start() {
        new EchoClient(myIp, myPort, this).start();
    }

    /**
     * This method sends a message on the socket
     *
     * @param message the message to send
     */
    public void sendMessage(Message message) {

        if (isLocalMatch)
            localMatchHandler.executeMessageFromClient(message);
        else {
            new MessageSender(socket, message).sendMsg(outputStream);
        }
    }

    /**
     * this method sets the message in the num of players scene
     *
     * @param message the message to set
     */
    public void selectNumberOfPlayers(LoginRequest message) {

        Platform.runLater(() -> {
            numOfPlayersController.setNicknameMessage(message);
            if (isLocalMatch)
                numOfPlayersController.setLocalMatch();
            primaryStage.setScene(numberOfPlayersScene);

        });
    }

    /**
     * this method sets the player board's controller
     *
     * @param controller the controller to set
     */
    public void setPlayerBoardController(PlayerBoardController controller) {
        playerBoardController = controller;
    }

    /**
     * this method starts a local match
     */
    public void startLocalMatch() {

        Platform.runLater(() -> {
            localMatchHandler = new LocalMatchHandler(this);
            primaryStage.setScene(nicknameScene);
            primaryStage.show();
        });

    }

    /**
     * Update the player's hand and the active leader cards after reconnection
     *
     * @param hand              player's hand
     * @param activeLeaderCards player's active leader cards
     */
    @Override
    public void showLeaderCardUpdate(List<LeaderCard> hand, List<LeaderCard> activeLeaderCards) {

        Platform.runLater(() -> {
            player.setHand(hand);
            gameSceneController.addLeadersToPlayer();
            activeLeaderCards.forEach(p -> player.useLeaderCard(p, true));

        });

    }

    /**
     * Shows the vatican report action
     *
     * @param view the client's view
     */
    @Override
    public void showVaticanReport(View view) {

        Platform.runLater(() -> {
            alertUser("Vatican Report", "VATICAN REPORT! Be prepared to pay your duties to the Pope!", Alert.AlertType.INFORMATION);
        });
    }
}
