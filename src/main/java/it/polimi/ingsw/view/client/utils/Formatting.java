package it.polimi.ingsw.view.client.utils;

/**
 * This class helps with formatting the output in CLI mode
 */
public class Formatting {

    public static void clearScreen() {
    }

    public enum Unicode {
        RESOURCE("\u2B24"),
        CROSS("🕂"),
        DEVELOPMENTCARD("⚑"),
        LEVEL("★"),
        FULL_SQUARE("⏹"),
        JOLLY("❓"),
        BOLD_VERTICAL("┃"),
        BOLD_HORIZ("━"),
        UP_LEFT("┏"),
        UP_RIGHT("┓"),
        DOWN_LEFT("┗"),
        DOWN_RIGHT("┛"),
        SQUARE("\u29C8"),
        LEFT_ARROW("←"),
        UP_ARROW("↑"),
        UP_LEFT_POPE("┌"),
        HORIZ_POPE("─"),
        UP_RIGHT_POPE("┐"),
        VERTICAL_POPE("│"),
        DOWN_LEFT_POPE("└"),
        DOWN_RIGHT_POPE("┘"),
        ONE("①"),
        TWO("②"),
        THREE("③");

        private String escape;

        Unicode (String escape){
            this.escape = escape;
        }

        /**
         * This method allow to get directly the code by returning it as a String
         * @return the desired value as a String
         */

        public String escape(){
            return escape;
        }
    }

    public enum ColorCode {

        ANSI_BLACK("\033[30m"),
        ANSI_RED("\033[31m"),
        ANSI_GREEN("\033[32m"),
        ANSI_YELLOW("\033[33m"),
        ANSI_BLUE("\033[34m"),
        ANSI_MAGENTA("\033[35m"),
        ANSI_CYAN("\033[36m"),
        ANSI_WHITE("\033[37m"),
        WHITE("\033[48;5;255m"),
        ORANGE1("\033[48;5;209m"),
        ORANGE("\\033[48;2;255;165;0m" ),
        GREY("\033[48;5;246m"),
        TEXT_GREY("\033[38;5;246m"),
        AZURE("\033[48;5;75m"),
        ANSI_RESET("\033[0m"),
        LEVEL_0_GREEN_BACKGROUND("\033[48;5;34m"),
        LEVEL_1_SAND_BACKGROUND("\033[48;5;112m"),
        LEVEL_2_GRAY_BACKGROUND("\033[48;5;180m"),
        LEVEL_3_WHITE_BACKGROUND("\033[48;5;253m"),
        LEVEL_DOME_BLUE_BACKGROUND("\033[48;5;32m"),
        ANSI_PURPLE("\u001B[35m"),
        ANSI_GREY("\u001B[37m");

        private String escape;

        ColorCode(String escape){
            this.escape = escape;
        }

        /**
         * This method allow to get directly the code by returning it as a String
         * @return the desired value as a String
         */

        public String escape(){
            return escape;
        }
    }
}
