package it.polimi.ingsw.view.client;

import java.util.regex.Pattern;


/**
 * This class check if the input in CLI mode is correct (syntax)
 */
public class InputValidator {

    // user nickname pattern
    private static final String NICKNAME_REGEX = "^[A-Za-z0-9_-]{3,10}$";

    // number of players in a match pattern
    private static final String NUMBER_OF_PLAYERS_REGEX = "[1-4]";



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
