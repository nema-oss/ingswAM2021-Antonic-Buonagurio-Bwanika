package it.polimi.ingsw.view.client.utils;

import it.polimi.ingsw.model.cards.Card;
import it.polimi.ingsw.model.cards.DevelopmentCard;
import it.polimi.ingsw.model.cards.leadercards.LeaderCard;
import it.polimi.ingsw.model.gameboard.ResourceType;

import javax.print.DocFlavor;
import javax.swing.*;
import java.util.*;
import java.util.regex.Pattern;
import java.util.stream.Collectors;


/**
 * This class check if the input in CLI mode is correct (syntax)
 */
public class InputValidator {

    // user nickname pattern
    private static final String NICKNAME_REGEX = "^[A-Za-z0-9_-]{3,10}$";

    // game board dimensions
    private static final int CARD_MARKET_ROW = 3;
    private static final int CARD_MARKET_COLUMNS = 4;
    private static final int MARBLE_MARKET_ROW = 3;
    private static final int MARBLE_MARKET_COLUMNS = 4;

    // number of players in a match pattern
    private static final String NUMBER_OF_PLAYERS_REGEX = "[1-4]";

    // leader actions
    private static final String DISCARD = "discard";
    private static final String ACTIVATE = "activate";
    private static final String DEVELOPMENT_CARD = "develop";
    private static final String LEADER = "leader";
    private static final String BOARD = "board";


    /**
     * Checks if the input is a correct resource type
     * @param input the user input
     * @return the selected resource type, null if incorrect input
     */
    public static ResourceType isResourceType(String input) {

        return ResourceType.valueOfLabel(input.toLowerCase(Locale.ROOT));
    }

    /**
     * Check if input is a correct coordinates on card market
     * @param input coordinates
     * @return a 2D point, null if incorrect input
     */
    public static Point isValidBuyCardAction(String input) {

        List<String> splitInput = Arrays.asList(input.split("\\s*,\\s*"));
        Point coordinates = new Point();
        int x;
        int y;

        if(splitInput.size() == 2) {
            try {
                x = Integer.parseInt(splitInput.get(0));
                y = Integer.parseInt(splitInput.get(1));
                if( !(0 <= x && x <= CARD_MARKET_ROW && 0 <= y && y <= CARD_MARKET_COLUMNS )) return null;
            } catch (NumberFormatException e) {
                return null;
            }

            coordinates.setX(x);
            coordinates.setY(y);
            return coordinates;
        }

        return null;

    }


    /**
     * Check if input is a correct coordinates on resources market
     * @param input coordinates
     * @return a 2D point, null if incorrect input
     */
    public static Point isValidBuyResourcesAction(String input) {

        List<String> splitInput = Arrays.asList(input.split("\\s*,\\s*"));
        Point coordinates = new Point();
        int x;
        int y;

        if(splitInput.size() == 2) {
            try {
                x = Integer.parseInt(splitInput.get(0));
                y = Integer.parseInt(splitInput.get(1));
                if( !(0 <= x && x <= MARBLE_MARKET_ROW && 0 <= y && y <= MARBLE_MARKET_COLUMNS )) return null;
            } catch (NumberFormatException e) {
                return null;
            }

            coordinates.setX(x);
            coordinates.setY(y);
            return coordinates;
        }

        return null;
    }

    /**
     *
     * @param leaderCards
     * @param input
     * @return
     */
    public static Map<LeaderCard, Boolean> isValidLeaderCardAction(List<LeaderCard> leaderCards, String input) {

        List<String> splitInput = Arrays.asList(input.split("\\s*,\\s*"));
        int cardSelectedIdx;
        LeaderCard cardSelected;
        Map<LeaderCard,Boolean> userChoice = new HashMap<>();


        if(splitInput.size() > 2 || splitInput.isEmpty()) return null;

        for(String command: splitInput){
            List<String> singleAction = Arrays.asList(command.split("\\s+"));
            if(singleAction.size() != 2) return null;
            String action = singleAction.get(0).toLowerCase(Locale.ROOT);
            try{
                cardSelectedIdx = Integer.parseInt(singleAction.get(1));
                cardSelected = leaderCards.get(cardSelectedIdx);
                if(cardSelected == null)
                    return null;
                if(action.equals(DISCARD)) {
                    userChoice.put(cardSelected, false);
                }else if(action.equals(ACTIVATE)){
                    userChoice.put(cardSelected,true);
                }else
                    return null;
            }catch (NumberFormatException e){
                return null;
            }

        }

        return null;
    }

    public static List<DevelopmentCard> isValidDevelopmentCardChoice(List<DevelopmentCard> cards, String input) {


        List<String> rawList = Arrays.asList(input.split("\\s*-\\s*"));
        if(rawList.size() != 2) return null;

        String command = rawList.get(0).toLowerCase(Locale.ROOT);
        if(!command.equals(LEADER)) return null;

        List<String> list = Arrays.asList(rawList.get(1).split("\\s*,\\s*"));
        List<String> splitInput = list.stream()
                .distinct()
                .collect(Collectors.toList());

        List<DevelopmentCard> userChoice;
        int idx;

        try{
            userChoice = new ArrayList<>();
            for(String choice: splitInput) {
                idx = Integer.parseInt(choice);
                userChoice.add(cards.get(idx));
            }
        }catch (NumberFormatException | IndexOutOfBoundsException e){
            return null;
        }

        return userChoice;
    }


    public static List<LeaderCard> isValidLeaderCardChoice(List<LeaderCard> cards, String input) {


        List<String> rawList = Arrays.asList(input.split("\\s*-\\s*"));
        if(rawList.size() != 2) return null;

        String command = rawList.get(0).toLowerCase(Locale.ROOT);
        if(!command.equals(DEVELOPMENT_CARD)) return null;

        List<String> list = Arrays.asList(rawList.get(1).split("\\s*,\\s*"));
        List<String> splitInput = list.stream()
                .distinct()
                .collect(Collectors.toList());

        List<LeaderCard> userChoice;
        int idx;

        try{
            userChoice = new ArrayList<>();
            for(String choice: splitInput) {
                idx = Integer.parseInt(choice);
                userChoice.add(cards.get(idx));
            }
        }catch (NumberFormatException | IndexOutOfBoundsException e){
            return null;
        }

        return userChoice;
    }

    public static Map<ResourceType, List<ResourceType>> isValidBoardProductionChoice(String input) {

        List<String> rawList = Arrays.asList(input.split("\\s*-\\s*"));
        if(rawList.size() != 2) return null;

        String command = rawList.get(0).toLowerCase(Locale.ROOT);
        if(!command.equals(BOARD)) return null;

        List<String> list = Arrays.asList(rawList.get(1).split("\\s*=\\s*"));

        if(list.size() != 2) return null;

        List<String> resourcesToGiveString = Arrays.asList(list.get(0).split("\\s*,\\s*"));
        if(resourcesToGiveString.size() != 2) return null;

        Map<ResourceType, List<ResourceType>> userChoice = new HashMap<>();
        ResourceType resourceTypeToReceive = isResourceType(list.get(1));
        List<ResourceType> listOfResourcesToGive = new ArrayList<>();
        if(resourceTypeToReceive == null) return null;


        for(String resource: resourcesToGiveString){
            if(isResourceType(resource) != null)
                listOfResourcesToGive.add(isResourceType(resource));
            else
                return null;
        }

        userChoice.put(resourceTypeToReceive,listOfResourcesToGive);
        return userChoice;
    }

    public static TurnActions isValidAction(String input) {

        return TurnActions.valueOfLabel(input.toLowerCase(Locale.ROOT));
    }


    /**
     * This method checks if the selected nickname is correct
     * @param nickname the selected nickname
     * @return true if correct
     */
    public boolean isNickname(String nickname) {
        return Pattern.matches(NICKNAME_REGEX,nickname);
    }

    /**
     * This method checks if the selected number of players is correct
     * @param numberOfPlayers the selected number
     * @return true if correct
     */
    public boolean isNumberOfPlayers(int numberOfPlayers) {
        return Pattern.matches(NUMBER_OF_PLAYERS_REGEX,String.valueOf(numberOfPlayers));
    }
}
