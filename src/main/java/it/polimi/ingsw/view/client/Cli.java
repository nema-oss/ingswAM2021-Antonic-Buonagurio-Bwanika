package it.polimi.ingsw.view.client;

import it.polimi.ingsw.messages.utils.MessageSender;
import it.polimi.ingsw.messages.setup.server.DoLoginMessage;
import it.polimi.ingsw.messages.setup.client.LoginRequest;
import it.polimi.ingsw.model.cards.DevelopmentCard;
import it.polimi.ingsw.model.cards.DevelopmentCardType;
import it.polimi.ingsw.model.cards.leadercards.*;
import it.polimi.ingsw.model.exception.NonExistentCardException;
import it.polimi.ingsw.model.gameboard.*;
import it.polimi.ingsw.model.player.Board;
import it.polimi.ingsw.model.player.Player;

import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.*;

import static it.polimi.ingsw.view.client.ColorCode.*;
import static it.polimi.ingsw.view.client.Unicode.*;

/**
 * this class implements the CLI view in the client
 */
public class Cli extends View {

    private final int max_spaces = 13;
    private final Scanner scanner;
    private Socket socket;
    private ObjectOutputStream outputStream;
    private final InputValidator inputValidator;
    String playerColor = ANSI_CYAN.escape();
    public Cli(){
        this.scanner = new Scanner(System.in);
        this.inputValidator = new InputValidator();
    }

    public void setReceiver(Socket socket, ObjectOutputStream outputStream) {
        this.socket = socket;
        this.outputStream = outputStream;
    }

    /**
     * This method tells the user that it has to play its turn
     */
    @Override
    public void showPlayTurn() {

    }

    /**
     * This method shows the resource market
     */
    @Override
    public void showResourceMarket() {

    }

    /**
     * This method shows the user's deposit
     */
    @Override
    public void showDeposit() {

    }

    /**
     * This method shows the user's development cards
     */
    @Override
    public void showDevelopmentCards() {

    }

    /**
     * This method shows the user's leader cards
     */
    @Override
    public void showLeaderCards(ArrayList<LeaderCard> leaderCards) {
        String color;
       showFullLineTop(leaderCards.size());
        for(int i = 0; i< leaderCards.size(); i++){//for every card in the list
            LeaderCard leaderCard = leaderCards.get(i);
            System.out.print(BOLD_VERTICAL.escape() + " ");
            int spaces = max_spaces; //max numbers of spaces per card on a row
            for(Integer j: leaderCard.getCostDevelopment().keySet()){//checking costDevelopment
                for(DevelopmentCardType k: leaderCard.getCostDevelopment().get(j).keySet()){
                    System.out.print(leaderCard.getCostDevelopment().get(j).get(k));
                    spaces--;
                    switch(k){
                        case BLUE:
                            System.out.print(ANSI_BLUE.escape() + DEVELOPMENTCARD.escape() + ANSI_RESET.escape());
                            break;
                        case GREEN:
                            System.out.print(ANSI_GREEN.escape() + DEVELOPMENTCARD.escape()+ ANSI_RESET.escape());
                            break;
                        case PURPLE:
                            System.out.print(ANSI_PURPLE.escape() + DEVELOPMENTCARD.escape()+ ANSI_RESET.escape());
                            break;
                        case YELLOW:
                            System.out.print(ANSI_YELLOW.escape() + DEVELOPMENTCARD.escape()+ ANSI_RESET.escape());
                            break;
                    }
                    spaces--;
                }
                if(j!=0){
                    spaces-=j;
                    for(int h=0; h<j; h++)
                        System.out.print(LEVEL.escape());
                }

            }
            //checking costResources
            for(ResourceType resourceType: leaderCard.getCostResource().keySet()){
                System.out.print(getResourceTypeColor(resourceType)+ leaderCard.getCostResource().get(resourceType) + RESOURCE.escape()+ANSI_RESET.escape());
            }
            System.out.print("\t\t"+BOLD_VERTICAL.escape() + "\t");
        }
        System.out.print("\n");
        showBlankLine(leaderCards.size());
        showBlankLine(leaderCards.size());
        for(int i=0; i< leaderCards.size(); i++){
            System.out.print(BOLD_VERTICAL.escape()+"\t  " + leaderCards.get(i).getVictoryPoints() + "  \t"+BOLD_VERTICAL.escape()+"\t");
        }
        System.out.print("\n");
        showBlankLine(leaderCards.size());
        for(int i=0; i<leaderCards.size(); i++){
            LeaderCardType type = leaderCards.get(i).getLeaderType();
            switch(type){
                case DISCOUNT:
                    ResourceType resourceType = ((Discount) leaderCards.get(i)).getDiscountType();
                    color = getResourceTypeColor(resourceType);
                    System.out.print(BOLD_VERTICAL.escape()+"    -" + ((Discount) leaderCards.get(i)).getDiscountAmount()+" "+color +RESOURCE.escape()+ ANSI_RESET.escape()+"\t"+BOLD_VERTICAL.escape()+"\t");
                    break;
                case EXTRA_DEPOSIT:
                    color = getResourceTypeColor(((ExtraDeposit) leaderCards.get(i)).getStorageType());
                    System.out.print(BOLD_VERTICAL.escape()+"   " + color + SQUARE.escape()+ " " +SQUARE.escape()+ANSI_RESET.escape()+"\t"+BOLD_VERTICAL.escape()+"\t");
                    break;
                case EXTRA_PRODUCTION:
                    int spaces = max_spaces;
                    for(ResourceType resourceType1: ((ExtraProduction) leaderCards.get(i)).getProductionRequirement().keySet()){
                        System.out.print(BOLD_VERTICAL.escape()+((ExtraProduction) leaderCards.get(i)).getProductionRequirement().get(resourceType1) + getResourceTypeColor(resourceType1) +
                                RESOURCE.escape() + ANSI_RESET.escape()+"\t");
                        spaces-=3;
                    }
                    System.out.print("->");
                    spaces-=2;
                    for(Producible producible: ((ExtraProduction) leaderCards.get(i)).getProductionResult()){
                        String flag = "";
                        if(producible instanceof Resource){
                            if(((Resource) producible).getType() == null){
                                flag = JOLLY.escape();
                                color = WHITE.escape();
                            }
                            else {
                                flag = RESOURCE.escape();
                                color = getResourceTypeColor(((Resource) producible).getType());
                            }
                        }
                        else{
                            //faithpoint
                            flag = CROSS.escape();
                            color = ANSI_RED.escape();
                        }
                       System.out.print(color +"1" + flag + ANSI_RESET.escape());
                        spaces-=3;
                    }
                    System.out.print("\t"+BOLD_VERTICAL.escape()+"\t");
                    break;
                case WHITE_TO_RESOURCE:
                    System.out.print(BOLD_VERTICAL.escape()+"   " +ANSI_WHITE.escape() +RESOURCE.escape() + ANSI_RESET.escape()+ " -> "+getResourceTypeColor(((WhiteToResource) leaderCards.get(i)).getResult())
                    + RESOURCE.escape() + ANSI_RESET.escape()+ "\t"+BOLD_VERTICAL.escape()+"\t");
                    break;
            }
        }
        System.out.print("\n");
        showBlankLine(leaderCards.size());
        showFullLineBottom(leaderCards.size());
    }

    public String getResourceTypeColor(ResourceType resourceType){
        String color = "";
        switch (resourceType){
            case COIN:
                color = ANSI_YELLOW.escape();
                break;
            case STONE:
                color = ANSI_GREY.escape();
                break;
            case SHIELD:
                color = ANSI_BLUE.escape();
                break;
            case SERVANT:
                color = ANSI_PURPLE.escape();
                break;

        }
        return color;
    }


    public void showBlankLine(int n){
        for(int i = 0; i< n; i++){
            System.out.print(BOLD_VERTICAL.escape()+"\t\t\t"+BOLD_VERTICAL.escape()+ "\t");
        }
        System.out.print("\n");
    }

    public void showFullLineTop(int n){
        for(int i = 0; i< n; i++){
            System.out.print(UP_LEFT.escape());
            for(int j=0; j<max_spaces; j++)
                System.out.print(BOLD_HORIZ.escape());
            System.out.print(UP_RIGHT.escape() + "\t");
        }
        System.out.print("\n");
    }
    public void showFullLineBottom(int n){
        for(int i = 0; i< n; i++){
            System.out.print(DOWN_LEFT.escape());
            for(int j=0; j<max_spaces; j++)
                System.out.print(BOLD_HORIZ.escape());
            System.out.print(DOWN_RIGHT.escape() + "\t");
        }
        System.out.print("\n");
    }
    /**
     * This method shows the card market
     */
    @Override
    public void showCardMarket(CardMarket cardMarket) {

    }



    @Override
    public void setMyIp() {

    }

    @Override
    public void setMyPort() {

    }

    @Override
    public void setUsername() {

    }

    /**
     * This method allows to start a match
     */
    @Override
    public void startMatch() {

    }

    /**
     * This method show the login view, asks nickname and number of players to the user and checks if the input is valid
     * @param message the login message sent by the server
     */
    @Override
    public void showLogin(DoLoginMessage message) {

        boolean correct;
        String nickname;

        do {
            System.out.println("Insert your username (must be at least 3 characters long and no more than 10, valid characters: A-Z, a-z, 1-9, _)");
            nickname = scanner.nextLine();
            correct = inputValidator.isNickname(nickname);
            if (!correct)
                System.out.println("Invalid username, try again");
        }while (!correct);

        LoginRequest loginRequest = new LoginRequest(nickname);

        if(message.isFirstPlayer()){
            do{
                System.out.println("Insert number of players [1..4]");
                int numberOfPlayers = scanner.nextInt();
                correct = inputValidator.isNumberOfPlayers(numberOfPlayers);
                if(!correct){
                    System.out.println("Incorrect number of players. Try again");
                }
                else{
                    loginRequest.setNumberOfPlayers(numberOfPlayers);
                }
            }while (!correct);
        }

        new MessageSender(socket,loginRequest).sendMsg(outputStream);
    }

    /**
     * This method tells the user that login has been successful and asks to wait for other players to join
     */
    @Override
    public void showLoginDone() {
        System.out.println("Login done, waiting to start the match ...");
    }

    /**
     * This method tells the user that a new player has joined the match
     * @param username: username of the newly logged in player
     */
    @Override
    public void showNewUserLogged(String username) {

        System.out.println(username + " has joined the match");
    }

    /**
     * This methods asks the user to wait for its turn
     * @param waitFor: reason why they're waiting
     * @param nowPlaying: username of the player's turn
     */
    @Override
    public void showWaitMessage(String waitFor, String nowPlaying) {
        //We use a WAITMESSAGE that call this method on its execute
        //on the server we do:
            //Message waitMessage = new WaitMessage(waitFor,nowPlaying)
    }

    /**
     * This methods tells the user that the match has started
     */
    @Override
    public void showMatchStarted() {
        System.out.println("Match started kids");
    }

    /**
     * This method shows the player personal board
     */
    @Override
    public void showBoard(Board board, Player player) {
        //first off draw the poperoad
        int cell_width = 6;
        int section_card_width = 12;
        int x;
        String normalColor = ANSI_RESET.escape();

        int position = player.getPositionIndex();
        //--first row
        showEmptyCells(2 , cell_width);
        if(position==4) {
            x=1;
        }
        else
            x=-1;
        showFullCells(1, "top", cell_width,normalColor, x);
        if(position<=8 && position >=5) {
            x=4-(position-5);
        }
        else
            x=-1;
        showFullCells(4, "top", cell_width, ANSI_GREEN.escape(), x);
        if(position==9) {
            x=1;
        }
        else
            x=-1;
        showFullCells(1, "top", cell_width,normalColor, x);
        showEmptyCells(1, cell_width);
        showFullCells(1, "top", section_card_width, ANSI_YELLOW.escape(), -1);
        showEmptyCells(1, cell_width);
        if(position==18) {
            x=1;
        }
        else
            x=-1;
        showFullCells(1, "top", cell_width, normalColor, x);
        if(position<=24 && position >=19) {
            x=6-(position-19);
        }
        else
            x=-1;
        showFullCells(6, "top", cell_width, ANSI_RED.escape(), x);
        System.out.print("\n");

        //--second row
        showEmptyCells(2 , cell_width);
        if(position==4) {
            x=1;
        }
        else
            x=-1;
        showFullCells(1, "middle", cell_width,normalColor, x);
        if(position<=8 && position >=5) {
            x=4-(position-5);
        }
        else
            x=-1;
        showFullCells(4, "middle", cell_width, ANSI_GREEN.escape(), x);
        if(position==9) {
            x=1;
        }
        else
            x=-1;
        showFullCells(1, "middle", cell_width,normalColor, x);
        showEmptyCells(1, cell_width);
        showFullCells(1, "middle", section_card_width, ANSI_YELLOW.escape(), -1);
        showEmptyCells(1, cell_width);
        if(position==18) {
            x=1;
        }
        else
            x=-1;
        showFullCells(1, "middle", cell_width, normalColor, x);
        if(position<=24 && position >=19) {
            x=6-(position-19);
        }
        else
            x=-1;
        showFullCells(6, "middle", cell_width, ANSI_RED.escape(), x);
        System.out.print("\n");

        //--third row
        showEmptyCells(2 , cell_width);
        if(position==4) {
            x=1;
        }
        else
            x=-1;
        showFullCells(1, "bottom", cell_width,normalColor, x);
        if(position<=8 && position >=5) {
            x=4-(position-5);
        }
        else
            x=-1;
        showFullCells(4, "bottom", cell_width, ANSI_GREEN.escape(), x);
        if(position==9) {
            x=1;
        }
        else
            x=-1;
        showFullCells(1, "bottom", cell_width,normalColor, x);
        showEmptyCells(1, cell_width);
        showFullCells(1, "middle", section_card_width, ANSI_YELLOW.escape(), -1);
        showEmptyCells(1, cell_width);
        if(position==18) {
            x=1;
        }
        else
            x=-1;
        showFullCells(1, "bottom", cell_width, normalColor, x);
        if(position<=24 && position >=19) {
            x=6-(position-19);
        }
        else
            x=-1;
        showFullCells(6, "bottom", cell_width, ANSI_RED.escape(), x);
        System.out.print("\n");

        //--fourth row
        showEmptyCells(2 , cell_width);
        if(position == 3){
            x=1;
        }
        else
            x=-1;
        showFullCells(1, "top", cell_width, normalColor, x);
        showEmptyCells(1 , cell_width);
        showFullCells(1, "top", section_card_width, ANSI_GREEN.escape(), -1);
        showEmptyCells(1 , cell_width);
        if(position == 10){
            x=1;
        }
        else
            x=-1;
        showFullCells(1, "top", cell_width, normalColor, x);
        showEmptyCells(1 , cell_width);
        showFullCells(1, "middle", section_card_width, ANSI_YELLOW.escape(), -1);
        showEmptyCells(1 , cell_width);
        if(position == 17){
            x=1;
        }
        else
            x=-1;
        showFullCells(1, "top", cell_width, normalColor, x);
        showEmptyCells(2 , cell_width);
        showFullCells(1, "top", section_card_width, ANSI_RED.escape(), -1);
        showEmptyCells(2 , cell_width);
        System.out.print("\n");
        //--fifth row
        showEmptyCells(2 , cell_width);
        if(position == 3){
            x=1;
        }
        else
            x=-1;
        showFullCells(1, "middle", cell_width, normalColor, x);
        showEmptyCells(1 , cell_width);
        showFullCells(1, "middle", section_card_width, ANSI_GREEN.escape(), -1);
        showEmptyCells(1 , cell_width);
        if(position == 10){
            x=1;
        }
        else
            x=-1;
        showFullCells(1, "middle", cell_width, normalColor, x);
        showEmptyCells(1 , cell_width);
        showFullCells(1, "bottom", section_card_width, ANSI_YELLOW.escape(), -1);
        showEmptyCells(1 , cell_width);
        if(position == 17){
            x=1;
        }
        else
            x=-1;
        showFullCells(1, "middle", cell_width, normalColor, x);
        showEmptyCells(2 , cell_width);
        showFullCells(1, "middle", section_card_width, ANSI_RED.escape(), -1);
        showEmptyCells(2 , cell_width);
        System.out.print("\n");
        //-sixth row
        showEmptyCells(2 , cell_width);
        if(position == 3){
            x=1;
        }
        else
            x=-1;
        showFullCells(1, "bottom", cell_width, normalColor, x);
        showEmptyCells(1 , cell_width);
        showFullCells(1, "middle", section_card_width, ANSI_GREEN.escape(), -1);
        showEmptyCells(1 , cell_width);
        if(position == 10){
            x=1;
        }
        else
            x=-1;
        showFullCells(1, "bottom", cell_width, normalColor, x);
        showEmptyCells(5 , cell_width);
        if(position == 17){
            x=1;
        }
        else
            x=-1;
        showFullCells(1, "bottom", cell_width, normalColor, x);
        showEmptyCells(2 , cell_width);
        showFullCells(1, "middle", section_card_width, ANSI_RED.escape(), -1);
        showEmptyCells(2 , cell_width);
        System.out.print("\n");
        //--seventh row
        if(position<=2){
            x=3-position;
        }
        else
            x=-1;
        showFullCells(3, "top", cell_width, normalColor, x);
        showEmptyCells(1 , cell_width);
        showFullCells(1, "middle", section_card_width, ANSI_GREEN.escape(), -1);
        showEmptyCells(1 , cell_width);
        if(position==11){
            x=1;
        }
        else
            x=-1;
        showFullCells(1, "top", cell_width, normalColor, x);
        if(position<=16 && position >=12){
            x=5-(position-12);
        }
        else
            x=-1;
        showFullCells(5, "top", cell_width, ANSI_YELLOW.escape(), x);
        showEmptyCells(2 , cell_width);
        showFullCells(1, "middle", section_card_width, ANSI_RED.escape(), -1);
        System.out.print("\n");
        //-eighth row
        if(position<=2){
            x=3-position;
        }
        else
            x=-1;
        showFullCells(3, "middle", cell_width, normalColor, x);
        showEmptyCells(1 , cell_width);
        showFullCells(1, "bottom", section_card_width, ANSI_GREEN.escape(), -1);
        showEmptyCells(1 , cell_width);
        if(position==11){
            x=1;
        }
        else
            x=-1;
        showFullCells(1, "middle", cell_width, normalColor, x);
        if(position<=16 && position >=12){
            x=5-(position-12);
        }
        else
            x=-1;
        showFullCells(5, "middle", cell_width, ANSI_YELLOW.escape(), x);
        showEmptyCells(2 , cell_width);
        showFullCells(1, "bottom", section_card_width, ANSI_RED.escape(), -1);
        System.out.print("\n");
        //-ninth row
        if(position<=2){
            x=3-position;
        }
        else
            x=-1;
        showFullCells(3, "bottom", cell_width, normalColor, x);
        showEmptyCells(5 , cell_width);
        if(position==11){
            x=1;
        }
        else
            x=-1;
        showFullCells(1, "bottom", cell_width, normalColor, x);
        if(position<=16 && position >=12){
            x=5-(position-12);
        }
        else
            x=-1;
        showFullCells(5, "bottom", cell_width, ANSI_YELLOW.escape(), x);
        System.out.print("\n");


        //now the rest
        if(player.getDeposit().getNumberOfResourcesOnFloor(1) ==1)
            System.out.println("\t\t\t" + getResourceTypeColor(player.getDeposit().get(1).getType()) + RESOURCE.escape() + ANSI_RESET.escape());
        System.out.print("\t\t\t"+HORIZ_POPE.escape());

        System.out.println("   \t\t\t"+JOLLY.escape());


        for(int i=0; i<player.getDeposit().getNumberOfResourcesOnFloor(2); i++)
            System.out.print("\t\t" + getResourceTypeColor(player.getDeposit().get(2).getType()) + RESOURCE.escape() + ANSI_RESET.escape());

        System.out.print("\t\t\t\t-> "+JOLLY.escape());
        String color= ANSI_RESET.escape();
        for(Stack<DevelopmentCard> stack: player.getPlayerBoard().getDevelopmentCards()){
            System.out.print("\t\t");
            if(!stack.empty()){
                //if there is at least a card i'll get its outline color
                color = getDevelopmentTypeColor(stack.peek().getType());

            }
            else{
                //if no card is present i'll use the default outline
                color = ANSI_RESET.escape();
            }
            System.out.print(color+ UP_LEFT.escape());
            for(int i=0; i<max_spaces; i++){
                System.out.print(BOLD_HORIZ.escape());
            }
            System.out.print(color+ UP_RIGHT.escape()+"\t"+ANSI_RESET.escape());
        }


        System.out.print("\n\t\t");

        for(int i=0; i<8; i++)
            System.out.print(HORIZ_POPE.escape());

        System.out.print("\t\t\t"+JOLLY.escape()+"   \t");

        for(Stack<DevelopmentCard> stack: player.getPlayerBoard().getDevelopmentCards()){
            System.out.print("\t\t");
            if(!stack.empty()){
                //if there is at least a card i'll get its outline color
                color = getDevelopmentTypeColor(stack.peek().getType());
                System.out.print(color+ BOLD_VERTICAL.escape());
                for(ResourceType resourceType: stack.peek().getCost().keySet()){
                    System.out.print(getResourceTypeColor(resourceType) + stack.peek().getCost().get(resourceType) + RESOURCE.escape() +ANSI_RESET.escape());
                }
                if(stack.peek().getCost().keySet().size()>2)
                    System.out.print(color+ "\t" +BOLD_VERTICAL.escape()+ANSI_RESET.escape()+"\t");
                else if(stack.peek().getCost().keySet().size()>1)
                    System.out.print(color+ "\t\t" +BOLD_VERTICAL.escape()+ANSI_RESET.escape()+"\t");
                else{
                    System.out.print(color+"\t\t\t" + BOLD_VERTICAL.escape()+ANSI_RESET.escape()+"\t");

                }
            }
            else{
                //if no card is present i'll use the default outline
                System.out.print(BOLD_VERTICAL.escape()+"\t\t\t"+BOLD_VERTICAL.escape()+"\t");
            }

        }



        System.out.print("\n");
        for(int i=0; i<player.getDeposit().getNumberOfResourcesOnFloor(3); i++)
            System.out.print("   \t" + getResourceTypeColor(player.getDeposit().get(3).getType()) + RESOURCE.escape() + ANSI_RESET.escape());
        System.out.print("\t\t\t\t");
        for(Stack<DevelopmentCard> stack: player.getPlayerBoard().getDevelopmentCards()){
            System.out.print("\t\t");
            if(!stack.empty()){
                //if there is at least a card i'll get its outline color
                color = getDevelopmentTypeColor(stack.peek().getType());
                System.out.print(color + BOLD_VERTICAL.escape() + ANSI_RESET.escape());
                for(int j=0; j<stack.peek().getLevel(); j++){
                    System.out.print(color + LEVEL.escape() + ANSI_RESET.escape());
                }
                if(stack.peek().getLevel()==3)
                    System.out.print(" ");
                else
                    System.out.print("\t\t");

                for(int j=0; j<stack.peek().getLevel(); j++){
                    System.out.print(color + LEVEL.escape() + ANSI_RESET.escape());
                }
                System.out.print(color +"\t"+ BOLD_VERTICAL.escape() + ANSI_RESET.escape()+"\t");
                }

            else{
            //if no card is present i'll use the default outline
                System.out.print(BOLD_VERTICAL.escape()+"\t\t\t"+BOLD_VERTICAL.escape()+"\t");
            }

        }
        System.out.print("\n\t");
        for(int i=0; i<16; i++)
            System.out.print(HORIZ_POPE.escape());
        System.out.print("\t\t\t\t");
        for(Stack<DevelopmentCard> stack: player.getPlayerBoard().getDevelopmentCards()){
            System.out.print("\t\t");
            if(!stack.empty()){
                color = getDevelopmentTypeColor(stack.peek().getType());
            }
            else{
                color = ANSI_RESET.escape();
            }
            System.out.print(color + BOLD_VERTICAL.escape()+"\t\t\t"+BOLD_VERTICAL.escape()+"\t");
        }
        System.out.print("\n\t\t\t\t\t\t\t\t\t\t\t");

        ArrayList<HashMap<ResourceType, Integer>> results = new ArrayList<>();
        ArrayList<Integer> faithResults = new ArrayList<>();//maps of results for every card in the first row
        //first of all i fill the map with card information so that i can use them later
        for(Stack<DevelopmentCard> stack: player.getPlayerBoard().getDevelopmentCards()){
            //check if a card has faithpoint production
            HashMap<ResourceType, Integer> map = new HashMap<>();
            if(stack.size()>0){
            long count = stack.peek().getProductionResults().stream().filter(xa -> (xa instanceof FaithPoint)).count();
            faithResults.add((int) count);
            //add the remaining to resource

            for(Producible p: stack.peek().getProductionResults()){
                if(!(p instanceof FaithPoint)){

                    if(map.containsKey(((Resource) p).getType())){
                        map.put(((Resource) p).getType(), map.get(((Resource) p).getType())+1);
                    }
                    else{
                        map.put(((Resource) p).getType(), 1);
                    }


                }
            }}
            results.add(map);
        }

        for(int i=0; i<3; i++){
            if(player.getPlayerBoard().getDevelopmentCards().get(i).size()>0) {
                color = getDevelopmentTypeColor(player.getPlayerBoard().getDevelopmentCards().get(i).get(0).getType());
                System.out.print(color + BOLD_VERTICAL.escape() + ANSI_RESET.escape());
                for (ResourceType resourceType : player.getPlayerBoard().getDevelopmentCards().get(i).get(0).getProductionRequirements().keySet()) {
                    System.out.print(getResourceTypeColor(resourceType) + player.getPlayerBoard().getDevelopmentCards().get(i).get(0).getProductionRequirements().get(resourceType) +
                            RESOURCE.escape() + ANSI_RESET.escape() + " ");
                }
                showGameBoardCardUtil(results, faithResults, i);
                if (player.getPlayerBoard().getDevelopmentCards().get(i).get(0).getProductionRequirements().keySet().size() > 1)
                    System.out.print(color + BOLD_VERTICAL.escape() + ANSI_RESET.escape() + "\t\t\t");
                else
                    System.out.print("\t" + color + BOLD_VERTICAL.escape() + ANSI_RESET.escape() + "\t\t\t");
            }
            else{
                System.out.print(ANSI_RESET.escape()+"\t\t" + BOLD_VERTICAL.escape()+"\t\t\t"+BOLD_VERTICAL.escape()+"\t");
            }
        }
        System.out.print("\n\t");
        Map<ResourceType,List<Resource>> strongbox = player.getStrongbox().getAll();
        for(ResourceType resourceType: strongbox.keySet()){
            System.out.print(getResourceTypeColor(resourceType) + strongbox.get(resourceType).size() + RESOURCE.escape()+ANSI_RESET.escape()+"\t");
        }
        System.out.print("\t\t\t\t\t\t");
        for(int i=0; i<3; i++) {
            if(player.getPlayerBoard().getDevelopmentCards().get(i).size()>0) {
                color = getDevelopmentTypeColor(player.getPlayerBoard().getDevelopmentCards().get(i).get(0).getType());
                System.out.print(color + BOLD_VERTICAL.escape() + ANSI_RESET.escape() + "\t");
                if (results.get(i).keySet().size() > 0) {
                    for (ResourceType resourceType : results.get(i).keySet()) {
                        System.out.print(getResourceTypeColor(resourceType) + results.get(i).get(resourceType) + RESOURCE.escape() + ANSI_RESET.escape() + "\t");
                        results.get(i).remove(resourceType);
                        break;
                    }

                    System.out.print("\t" + color + BOLD_VERTICAL.escape() + ANSI_RESET.escape() + "\t\t\t");
                } else {
                    System.out.print("\t\t" + color + BOLD_VERTICAL.escape() + ANSI_RESET.escape() + "\t\t\t");

                }
            }
            else{
                System.out.print(ANSI_RESET.escape()+"\t\t" + BOLD_VERTICAL.escape()+"\t\t\t"+BOLD_VERTICAL.escape()+"\t");
            }
        }
        System.out.print("\n\t\t\t\t\t\t\t\t\t\t\t");

        for(int i=0; i<3; i++) {
            if(player.getPlayerBoard().getDevelopmentCards().get(i).size()>0) {
                color = getDevelopmentTypeColor(player.getPlayerBoard().getDevelopmentCards().get(i).get(0).getType());
                System.out.print(color + BOLD_VERTICAL.escape() + ANSI_RESET.escape() + "\t");
                if (results.get(i).keySet().size() > 0) {
                    for (ResourceType resourceType : results.get(i).keySet()) {
                        System.out.print(getResourceTypeColor(resourceType) + results.get(i).get(resourceType) + RESOURCE.escape() + ANSI_RESET.escape() + "\t");
                        results.get(i).remove(resourceType);
                        break;
                    }

                    System.out.print("\t" + color + BOLD_VERTICAL.escape() + ANSI_RESET.escape() + "\t\t\t");
                } else {
                    System.out.print("\t\t" + color + BOLD_VERTICAL.escape() + ANSI_RESET.escape() + "\t\t\t");

                }
            }
            else{
                System.out.print(ANSI_RESET.escape()+"\t\t" + BOLD_VERTICAL.escape()+"\t\t\t"+BOLD_VERTICAL.escape()+"\t");
            }
        }
        System.out.print("\n\t\t\t\t\t\t\t\t\t\t\t");

        for(int j=0; j<3; j++) {
            if(player.getPlayerBoard().getDevelopmentCards().get(j).size()>0) {
                color = getDevelopmentTypeColor(player.getPlayerBoard().getDevelopmentCards().get(j).get(0).getType());
                System.out.print(color + BOLD_VERTICAL.escape() + ANSI_RESET.escape() + "\t");
                System.out.print((player.getPlayerBoard().getDevelopmentCards().get(j).get(0).getVictoryPoints()));
                System.out.print("\t\t" + color + BOLD_VERTICAL.escape() + ANSI_RESET.escape() + "\t\t\t");
            }
            else{
                System.out.print(ANSI_RESET.escape()+"\t\t" + BOLD_VERTICAL.escape()+"\t\t\t"+BOLD_VERTICAL.escape()+"\t");
            }

        }
        System.out.print("\n\t\t\t\t\t\t\t\t\t\t\t");

        for(int j=0; j<3; j++){
            if(player.getPlayerBoard().getDevelopmentCards().get(j).size()>0) {
                color = getDevelopmentTypeColor(player.getPlayerBoard().getDevelopmentCards().get(j).get(0).getType());
                System.out.print(color + DOWN_LEFT.escape());
                for (int k = 0; k < max_spaces; k++)
                    System.out.print(color + BOLD_HORIZ.escape());
                System.out.print(color + DOWN_RIGHT.escape() + "\t" + ANSI_RESET.escape()+"\t\t");
            }
            else{
                System.out.print(ANSI_RESET.escape()+DOWN_LEFT.escape());
                for(int i=0; i<max_spaces; i++){
                    System.out.print(BOLD_HORIZ.escape());
                }
                System.out.print(ANSI_RESET.escape()+DOWN_RIGHT.escape()+"\t\t\t");
            }
        }
        System.out.print("\n\t\t\t\t\t\t\t\t\t\t\t");

        for(int j=0; j<3; j++) {
            if (player.getPlayerBoard().getDevelopmentCards().get(j).size() > 1) {
                color = getDevelopmentTypeColor(player.getPlayerBoard().getDevelopmentCards().get(j).get(1).getType());
                System.out.print(color + BOLD_VERTICAL.escape() + ANSI_RESET.escape() + "\t");
                System.out.print((player.getPlayerBoard().getDevelopmentCards().get(j).get(0).getVictoryPoints()));
                System.out.print("\t\t" + color + BOLD_VERTICAL.escape() + ANSI_RESET.escape() + "\t\t\t");
            }
        }
        System.out.print("\n\t\t\t\t\t\t\t\t\t\t\t");
        for(int j=0; j<3; j++) {
            if (player.getPlayerBoard().getDevelopmentCards().get(j).size() > 1) {
                color = getDevelopmentTypeColor(player.getPlayerBoard().getDevelopmentCards().get(j).get(1).getType());
                System.out.print(color + DOWN_LEFT.escape());
                for (int k = 0; k < max_spaces; k++)
                    System.out.print(color + BOLD_HORIZ.escape());
                System.out.print(color + DOWN_RIGHT.escape() + "\t" + ANSI_RESET.escape() + "\t\t");
            }
        }
        System.out.print("\n\t\t\t\t\t\t\t\t\t\t\t");
        for(int j=0; j<3; j++) {
            if (player.getPlayerBoard().getDevelopmentCards().get(j).size() > 2) {
                color = getDevelopmentTypeColor(player.getPlayerBoard().getDevelopmentCards().get(j).get(2).getType());
                System.out.print(color + BOLD_VERTICAL.escape() + ANSI_RESET.escape() + "\t");
                System.out.print((player.getPlayerBoard().getDevelopmentCards().get(j).get(0).getVictoryPoints()));
                System.out.print("\t\t" + color + BOLD_VERTICAL.escape() + ANSI_RESET.escape() + "\t\t\t");
            }
        }
        System.out.print("\n\t\t\t\t\t\t\t\t\t\t\t");
        for(int j=0; j<3; j++) {
            if (player.getPlayerBoard().getDevelopmentCards().get(j).size() > 2) {
                color = getDevelopmentTypeColor(player.getPlayerBoard().getDevelopmentCards().get(j).get(2).getType());
                System.out.print(color + DOWN_LEFT.escape());
                for (int k = 0; k < max_spaces; k++)
                    System.out.print(color + BOLD_HORIZ.escape());
                System.out.print(color + DOWN_RIGHT.escape() + "\t" + ANSI_RESET.escape() + "\t\t");
            }
        }
    }

    public void showFullCells(int x, String where, int cell_width, String color, int PV){
        switch(where){
            case "top":
                while(x>0){
                    if(x==PV) {
                        showTopPopeRoadCell(cell_width, playerColor);

                    }
                    else
                        showTopPopeRoadCell(cell_width, color);
                    x--;
                }
                break;
            case "bottom":
                while(x>0){
                    if(x==PV)
                        showBottomPopeRoadCell(cell_width, playerColor);
                    else
                        showBottomPopeRoadCell(cell_width, color);
                    x--;
                }
                break;
            case "middle":
                while(x>0){
                    if(x==PV)
                        showMiddlePopeRoadCell(cell_width, playerColor);
                    else
                        showMiddlePopeRoadCell(cell_width, color);
                    x--;
                }
                break;
        }
    }
    public void showEmptyCells(int x, int cell_width){
        int numOfTabs = 0;
        numOfTabs = getNumOfTabs(numOfTabs, x*cell_width);
    }

    public int getNumOfTabs(int numOfTabs, int x) {
        while(x>4){
            numOfTabs++;
            x-=4;
        }
        while(x>0){
            System.out.print(" ");
            x--;
        }
        while(numOfTabs>=0){
            System.out.print("\t");
            numOfTabs--;
        }
        return numOfTabs;
    }

    public void showBottomPopeRoadCell(int width, String color){
        System.out.print(color + DOWN_LEFT_POPE.escape());
        width-=2; //cutting off the corners
        while(width>=0){
            System.out.print(color +HORIZ_POPE.escape());
            width--;
        }
        System.out.print(color +DOWN_RIGHT_POPE.escape()+" ");
    }
    public void showMiddlePopeRoadCell(int width, String color){

        System.out.print(color + VERTICAL_POPE.escape());
        while(width>1){
            System.out.print(" ");
            width--;
        }

        System.out.print(color +VERTICAL_POPE.escape()+" ");

    }
    public void showTopPopeRoadCell(int width, String color){
        System.out.print(color +UP_LEFT_POPE.escape());
        width-=2; //cutting off the corners
        while(width>=0){
            System.out.print(color +HORIZ_POPE.escape());
            width--;
        }
        System.out.print(color +UP_RIGHT_POPE.escape()+" ");
    }
    /**
     * This method shows the common game board
     */
    @Override
    public void showGameBoard(GameBoard gameBoard) throws NonExistentCardException {
        for(int i=0; i<gameBoard.getCardMarketColumns(); i++) {
            String color = getDevelopmentTypeColor(gameBoard.getCardMarket().getCard(0, i).getType());
            System.out.print(color + UP_LEFT.escape());
            for(int j=0; j<max_spaces; j++)
                System.out.print(color +BOLD_HORIZ.escape());
            System.out.print(color +UP_RIGHT.escape() + "\t" + ANSI_RESET.escape());
        }
        showMarbleMarketLine(0, gameBoard);

        for(int i=0; i<gameBoard.getCardMarketColumns(); i++){
            String color = getDevelopmentTypeColor(gameBoard.getCardMarket().getCard(0, i).getType());
            System.out.print(color + BOLD_VERTICAL.escape() + ANSI_RESET.escape());
            for(ResourceType resourceType: gameBoard.getCardMarket().getCard(0, i).getCost().keySet()){
                System.out.print(getResourceTypeColor(resourceType) + gameBoard.getCardMarket().getCard(0, i).getCost().get(resourceType) + RESOURCE.escape() +ANSI_RESET.escape());
            }
            if(gameBoard.getCardMarket().getCard(0, i).getCost().keySet().size() > 1)
                System.out.print(color +"\t\t" + BOLD_VERTICAL.escape() + "\t"+ ANSI_RESET.escape());
            else
                System.out.print(color +"\t\t\t" + BOLD_VERTICAL.escape() + "\t"+ ANSI_RESET.escape());

        }
        showMarbleMarketLine(1, gameBoard);

        for(int i=0; i<gameBoard.getCardMarketColumns(); i++) {
            String color = getDevelopmentTypeColor(gameBoard.getCardMarket().getCard(0, i).getType());
            System.out.print(color + BOLD_VERTICAL.escape() + ANSI_RESET.escape());
            for(int j=0; j<gameBoard.getCardMarket().getCard(0, i).getLevel(); j++){
                System.out.print(color + LEVEL.escape() + ANSI_RESET.escape());
            }
            System.out.print("\t");
            for(int j=0; j<gameBoard.getCardMarket().getCard(0, i).getLevel(); j++){
                System.out.print(color + LEVEL.escape() + ANSI_RESET.escape());
            }
            System.out.print(color + BOLD_VERTICAL.escape() + ANSI_RESET.escape()+"\t");
        }

        showMarbleMarketLine(2, gameBoard);

        for(int i=0; i<gameBoard.getCardMarketColumns(); i++) {
            String color = getDevelopmentTypeColor(gameBoard.getCardMarket().getCard(0, i).getType());
            System.out.print(color +BOLD_VERTICAL.escape()+"\t\t\t"+BOLD_VERTICAL.escape()+ "\t" + ANSI_RESET.escape());
        }
        System.out.print("\t");

        for(int i=0; i<gameBoard.getMarbleMarketColumns(); i++){
            System.out.print(UP_ARROW.escape() + " ");
        }
        System.out.print("\n");

        ArrayList<HashMap<ResourceType, Integer>> results = new ArrayList<>();
        ArrayList<Integer> faithResults = new ArrayList<>();//maps of results for every card in the first row
        //first of all i fill the map with card information so that i can use them later
        for(int i=0; i<gameBoard.getCardMarketColumns(); i++){
            //check if a card has faithpoint production
            long count = gameBoard.getCardMarket().getCard(0,i).getProductionResults().stream().filter(x -> (x instanceof FaithPoint)).count();
            faithResults.add((int) count);
            //add the remaining to resource
            HashMap<ResourceType, Integer> map = new HashMap<>();
            for(Producible p: gameBoard.getCardMarket().getCard(0,i).getProductionResults()){
                if(!(p instanceof FaithPoint)){

                        if(map.containsKey(((Resource) p).getType())){
                            map.put(((Resource) p).getType(), map.get(((Resource) p).getType())+1);
                        }
                        else{
                            map.put(((Resource) p).getType(), 1);
                        }


                }
            }
            results.add(map);
        }

        for(int i=0; i<gameBoard.getCardMarketColumns(); i++){
            String color = getDevelopmentTypeColor(gameBoard.getCardMarket().getCard(0, i).getType());
            System.out.print(color +BOLD_VERTICAL.escape() + ANSI_RESET.escape());
            for(ResourceType resourceType: gameBoard.getCardMarket().getCard(0, i).getProductionRequirements().keySet()){
                System.out.print(getResourceTypeColor(resourceType)+ gameBoard.getCardMarket().getCard(0, i).getProductionRequirements().get(resourceType)+
                        RESOURCE.escape()+ANSI_RESET.escape()+" ");
            }
            showGameBoardCardUtil(results, faithResults, i);
            if(gameBoard.getCardMarket().getCard(0, i).getProductionRequirements().keySet().size()>1)
                System.out.print(color +BOLD_VERTICAL.escape() + ANSI_RESET.escape()+"\t");
            else
                System.out.print("\t"+color +BOLD_VERTICAL.escape() + ANSI_RESET.escape()+"\t");
        }
        showCardsUtil(gameBoard, results);
        showCardsUtil(gameBoard, results);
        System.out.print("\n");

        for(int i=0; i<gameBoard.getCardMarketColumns(); i++) {
            String color = getDevelopmentTypeColor(gameBoard.getCardMarket().getCard(0, i).getType());
            System.out.print(color + BOLD_VERTICAL.escape() + ANSI_RESET.escape()+"\t");
            System.out.print(gameBoard.getCardMarket().getCard(0, i).getVictoryPoints());
            System.out.print("\t\t"+color +BOLD_VERTICAL.escape() + ANSI_RESET.escape()+"\t");
        }
        System.out.print("\n");

        for(int i=0; i<gameBoard.getCardMarketColumns(); i++){
            String color = getDevelopmentTypeColor(gameBoard.getCardMarket().getCard(0, i).getType());
            System.out.print(color + DOWN_LEFT.escape());
            for(int j=0; j<max_spaces; j++)
                System.out.print(color +BOLD_HORIZ.escape());
            System.out.print(color +DOWN_RIGHT.escape() + "\t" + ANSI_RESET.escape());
        }
        System.out.print("\n");

        //now the fun begins: all other rows need to be printed column by column
        for(int i=1; i<gameBoard.getCardMarketRow(); i++){
            for(int j=0; j<gameBoard.getCardMarketColumns();j++){
                String color = getDevelopmentTypeColor(gameBoard.getCardMarket().getCard(i,j).getType());
                System.out.print(color + UP_LEFT.escape());
                for(int k=0; k<max_spaces; k++)
                    System.out.print(color +BOLD_HORIZ.escape());
                System.out.print(color +UP_RIGHT.escape() + "\t" + ANSI_RESET.escape());
            }
            System.out.print("\n");
            for(int k=0; k<gameBoard.getCardMarketColumns(); k++){
                String color = getDevelopmentTypeColor(gameBoard.getCardMarket().getCard(i, k).getType());
                System.out.print(color + BOLD_VERTICAL.escape() + ANSI_RESET.escape());
                for(ResourceType resourceType: gameBoard.getCardMarket().getCard(i, k).getCost().keySet()){
                    System.out.print(getResourceTypeColor(resourceType) + gameBoard.getCardMarket().getCard(i, k).getCost().get(resourceType) + RESOURCE.escape() +ANSI_RESET.escape());
                }
                if(gameBoard.getCardMarket().getCard(i, k).getCost().keySet().size() > 1)
                    if(gameBoard.getCardMarket().getCard(i, k).getCost().keySet().size() > 2)
                        System.out.print(color +"\t" + BOLD_VERTICAL.escape() + "\t"+ ANSI_RESET.escape());
                    else
                        System.out.print(color +"\t\t" + BOLD_VERTICAL.escape() + "\t"+ ANSI_RESET.escape());
                else
                    System.out.print(color +"\t\t\t" + BOLD_VERTICAL.escape() + "\t"+ ANSI_RESET.escape());

            }
            System.out.print("\n");
            for(int k=0; k<gameBoard.getCardMarketColumns(); k++) {
                String color = getDevelopmentTypeColor(gameBoard.getCardMarket().getCard(i,k).getType());
                System.out.print(color + BOLD_VERTICAL.escape() + ANSI_RESET.escape());
                for(int j=0; j<gameBoard.getCardMarket().getCard(i,k).getLevel(); j++){
                    System.out.print(color + LEVEL.escape() + ANSI_RESET.escape());
                }
                if(i==2)
                    System.out.print("\t\t");
                else
                    System.out.print("\t");
                for(int j=0; j<gameBoard.getCardMarket().getCard(i,k).getLevel(); j++){
                    System.out.print(color + LEVEL.escape() + ANSI_RESET.escape());
                }
                System.out.print("\t");
                System.out.print(color + BOLD_VERTICAL.escape() + ANSI_RESET.escape()+"\t");
            }
            System.out.print("\n");
            for(int k=0; k<gameBoard.getCardMarketColumns(); k++) {
                String color = getDevelopmentTypeColor(gameBoard.getCardMarket().getCard(i,k).getType());
                System.out.print(color +BOLD_VERTICAL.escape()+"\t\t\t"+BOLD_VERTICAL.escape()+ "\t" + ANSI_RESET.escape());
            }
            System.out.print("\n");

            results = new ArrayList<>();
            faithResults = new ArrayList<>();

            for(int j=0; j<gameBoard.getCardMarketColumns(); j++){
                //check if a card has faithpoint production
                long count = gameBoard.getCardMarket().getCard(i,j).getProductionResults().stream().filter(x -> (x instanceof FaithPoint)).count();
                faithResults.add((int) count);
                //add the remaining to resource
                HashMap<ResourceType, Integer> map = new HashMap<>();
                for(Producible p: gameBoard.getCardMarket().getCard(i,j).getProductionResults()){
                    if(!(p instanceof FaithPoint)){
                        if(map.containsKey(((Resource) p).getType())){
                            map.put(((Resource) p).getType(), map.get(((Resource) p).getType())+1);
                        }
                        else{
                            map.put(((Resource) p).getType(), 1);
                        }
                    }
                }
                results.add(map);
            }

            for(int j=0; j<gameBoard.getCardMarketColumns();j++){
                String color = getDevelopmentTypeColor(gameBoard.getCardMarket().getCard(i,j).getType());
                System.out.print(color +BOLD_VERTICAL.escape() + ANSI_RESET.escape());
                for(ResourceType resourceType: gameBoard.getCardMarket().getCard(i,j).getProductionRequirements().keySet()){
                    System.out.print(getResourceTypeColor(resourceType)+ gameBoard.getCardMarket().getCard(i,j).getProductionRequirements().get(resourceType)+
                            RESOURCE.escape()+ANSI_RESET.escape()+" ");
                }
                showGameBoardCardUtil(results, faithResults, j);
                if(gameBoard.getCardMarket().getCard(i,j).getProductionRequirements().keySet().size()>1)
                    System.out.print(color +BOLD_VERTICAL.escape() + ANSI_RESET.escape()+"\t");
                else
                    System.out.print("\t"+color +BOLD_VERTICAL.escape() + ANSI_RESET.escape()+"\t");
            }
            showCardsUtil(gameBoard, results);
            showCardsUtil(gameBoard, results);
            System.out.print("\n");

            for(int j=0; j<gameBoard.getCardMarketColumns(); j++) {
                String color = getDevelopmentTypeColor(gameBoard.getCardMarket().getCard(i,j).getType());
                System.out.print(color + BOLD_VERTICAL.escape() + ANSI_RESET.escape()+"\t");
                System.out.print(gameBoard.getCardMarket().getCard(i,j).getVictoryPoints());
                System.out.print("\t\t"+color +BOLD_VERTICAL.escape() + ANSI_RESET.escape()+"\t");
            }
            System.out.print("\n");

            for(int j=0; j<gameBoard.getCardMarketColumns(); j++){
                String color = getDevelopmentTypeColor(gameBoard.getCardMarket().getCard(i,j).getType());
                System.out.print(color + DOWN_LEFT.escape());
                for(int k=0; k<max_spaces; k++)
                    System.out.print(color +BOLD_HORIZ.escape());
                System.out.print(color +DOWN_RIGHT.escape() + "\t" + ANSI_RESET.escape());
            }
            System.out.print("\n");
        }
    }

    public void showGameBoardCardUtil(ArrayList<HashMap<ResourceType, Integer>> results, ArrayList<Integer> faithResults, int j) {
        System.out.print("->");
        if(faithResults.get(j)>0) {
            System.out.print(ANSI_RED.escape() + faithResults.get(j) + CROSS.escape() + ANSI_RESET.escape());

        }
        else {
            for(ResourceType resourceType: results.get(j).keySet()){
                System.out.print(getResourceTypeColor(resourceType) + results.get(j).get(resourceType) + RESOURCE.escape() + ANSI_RESET.escape());
                results.get(j).remove(resourceType);
                break;
            }
        }
    }

    public void showCardsUtil(GameBoard gameBoard, ArrayList<HashMap<ResourceType, Integer>> results) throws NonExistentCardException {
        System.out.print("\n");

        for(int i=0; i<gameBoard.getCardMarketColumns(); i++) {
            String color = getDevelopmentTypeColor(gameBoard.getCardMarket().getCard(0, i).getType());
            System.out.print(color + BOLD_VERTICAL.escape() + ANSI_RESET.escape()+"\t");
            if(results.get(i).keySet().size()>0){
                for(ResourceType resourceType: results.get(i).keySet()){
                    System.out.print(getResourceTypeColor(resourceType) + results.get(i).get(resourceType) + RESOURCE.escape() + ANSI_RESET.escape()+"\t");
                    results.get(i).remove(resourceType);
                    break;
                }

                System.out.print("\t"+color +BOLD_VERTICAL.escape() + ANSI_RESET.escape()+"\t");
            }
            else{
                System.out.print("\t\t"+color +BOLD_VERTICAL.escape() + ANSI_RESET.escape()+"\t");

            }
        }
    }


    public void showMarbleMarketLine(int n, GameBoard gameBoard){
        System.out.print("\t");
        for(int i=0; i<gameBoard.getMarbleMarketColumns(); i++){
            System.out.print(getMarbleTypeColor(gameBoard.getMarket().getMarble(n, i)) + RESOURCE.escape() + ANSI_RESET.escape()+ " ");
        }
        System.out.print(LEFT_ARROW.escape());
        if(n==0){
            System.out.println(getMarbleTypeColor(gameBoard.getMarket().getFreeMarble())+RESOURCE.escape()+ANSI_RESET.escape());
        }
        else
            System.out.print("\n");
    }

    public String getMarbleTypeColor(Marble marble){
        String color = "";
        switch(marble.getColor()){
            case YELLOW:
                color = ANSI_YELLOW.escape();
                break;
            case PURPLE:
                color = ANSI_PURPLE.escape();
                break;
            case BLUE:
                color = ANSI_BLUE.escape();
                break;
            case RED:
                color = ANSI_RED.escape();
                break;
            case GREY:
                color = ANSI_GREY.escape();
                break;
            case WHITE:
                color = ANSI_WHITE.escape();
                break;
        }
        return color;
    }

    public String getDevelopmentTypeColor(DevelopmentCardType developmentCardType){
        String color = "";
        switch(developmentCardType){
            case YELLOW:
                color = ANSI_YELLOW.escape();
                break;
            case PURPLE:
                color = ANSI_PURPLE.escape();
                break;
            case BLUE:
                color = ANSI_BLUE.escape();
                break;
            case GREEN:
                color = ANSI_GREEN.escape();
                break;

        }
        return color;
    }


    @Override
    public void serverNotFound() {

    }

    /**
     * This method tells the user that another player has disconnected
     * @param otherClient the disconnected player
     */
    @Override
    public void showAnotherClientDisconnection(String otherClient) {

    }

    /**
     * This method alerts the user that the server disconnected
     */
    @Override
    public void showServerDisconnection() {

        System.out.println("Server says Bye Bye, sayonara");

    }

    /**
     * This method alerts the user that it has lost the game and tells who is the winner
     * @param winner : username of the winner
     */
    @Override
    public void showYouLose(String winner) {

    }

    /**
     * This method tells the winner he won the match
     */
    @Override
    public void showYouWin() {

    }

    /**
     *
     */
    @Override
    public void showEndGame() {

    }

    /**
     * This method tells the user that login has been rejected
     */
    @Override
    public void showLoginFailed(boolean isFirstPlayer) {

        System.out.println("Login failed. Try again with a different username");
        DoLoginMessage doLoginMessage = new DoLoginMessage();
        doLoginMessage.setFirstPlayer(isFirstPlayer);
        showLogin(doLoginMessage);
    }

    public void showTitle() {

        System.out.print(ANSI_BLUE.escape() +
                "___  ___          _                         __  ______                  \n"+
                "|  \\/  |         | |                       / _| | ___ \\                O \n"+
                "| .  . | __ _ ___| |_ ___ _ __ ___    ___ | |_  | |_/ /___ _ __   __ _ _ ___ ___  __ _ _ __   ___ ___ \n"+
                "| |\\/| |/ _` / __| __/ _ \\ '__/ __|  / _ \\|  _| |    // _ \\ '_ \\ / _` | / __/ __|/ _` | '_ \\ / __/ _ \\\n"+
                "| |  | | (_| \\__ \\ ||  __/ |  \\__ \\ | (_) | |   | |\\ \\  __/ | | | (_| | \\__ \\__ \\ (_| | | | | (_|  __/\n"+
                "\\_|  |_/\\__,_|___/\\__\\___|_|  |___/  \\___/|_|   \\_| \\_\\___|_| |_|\\__,_|_|___/___/\\__,_|_| |_|\\___\\___|\n"+
                "                                                                                                                  \n" +
                "By Nemanja Antonic, Chiara Buonagurio and Renè Bwanika"+ColorCode.ANSI_RESET.escape());
    }

}
