package it.polimi.ingsw.view.client;

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
