package it.polimi.ingsw.view.client.viewComponents;

import it.polimi.ingsw.model.cards.DevelopmentCard;
import it.polimi.ingsw.model.cards.DevelopmentCardType;
import it.polimi.ingsw.model.cards.leadercards.*;
import it.polimi.ingsw.model.gameboard.*;
import it.polimi.ingsw.view.client.utils.Formatting;

import java.util.*;

import static it.polimi.ingsw.view.client.utils.Formatting.ColorCode.*;
import static it.polimi.ingsw.view.client.utils.Formatting.ColorCode.ANSI_RESET;
import static it.polimi.ingsw.view.client.utils.Formatting.Unicode.*;

public class CliGraphics {

    protected final int MAX_SPACES = 15;
    protected String playerColor = ANSI_CYAN.escape();

    /**
     * This method shows the user's leader cards
     */
    public void showLeaderCards(Map<LeaderCard, Boolean> leaderCards) {
        String color;
        String activeColor = ANSI_GREEN.escape();
        for (LeaderCard leaderCard : leaderCards.keySet()) {
            if (leaderCards.get(leaderCard)) {
                showFullLineTop(1, activeColor);
            } else {
                showFullLineTop(1);
            }
        }

        System.out.print("\n");
        for (LeaderCard leaderCard : leaderCards.keySet()) {//for every card in the list
            if (leaderCards.get(leaderCard))
                color = activeColor;
            else
                color = ANSI_RESET.escape();
            System.out.print(color + BOLD_VERTICAL.escape() + " " + ANSI_RESET.escape());
            int spaces = MAX_SPACES; //max numbers of spaces per card on a row
            for (Integer j : leaderCard.getCostDevelopment().keySet()) {//checking costDevelopment
                for (DevelopmentCardType k : leaderCard.getCostDevelopment().get(j).keySet()) {
                    System.out.print(leaderCard.getCostDevelopment().get(j).get(k));
                    spaces--;
                    switch (k) {
                        case BLUE:
                            System.out.print(ANSI_BLUE.escape() + DEVELOPMENTCARD.escape() + ANSI_RESET.escape());
                            break;
                        case GREEN:
                            System.out.print(ANSI_GREEN.escape() + DEVELOPMENTCARD.escape() + ANSI_RESET.escape());
                            break;
                        case PURPLE:
                            System.out.print(ANSI_PURPLE.escape() + DEVELOPMENTCARD.escape() + ANSI_RESET.escape());
                            break;
                        case YELLOW:
                            System.out.print(ANSI_YELLOW.escape() + DEVELOPMENTCARD.escape() + ANSI_RESET.escape());
                            break;
                    }
                    spaces--;
                }
                if (j != 0) {
                    spaces -= j;
                    for (int h = 0; h < j; h++)
                        System.out.print(LEVEL.escape());
                }

            }
            //checking costResources
            for (ResourceType resourceType : leaderCard.getCostResource().keySet()) {
                System.out.print(getResourceTypeColor(resourceType) + leaderCard.getCostResource().get(resourceType) + RESOURCE.escape() + ANSI_RESET.escape());
            }
            if (leaderCards.get(leaderCard))
                color = activeColor;
            else
                color = ANSI_RESET.escape();
            System.out.print(color + "\t\t" + BOLD_VERTICAL.escape() + "\t" + ANSI_RESET.escape());
        }
        System.out.print("\n");
        for (LeaderCard leaderCard : leaderCards.keySet()) {
            if (leaderCards.get(leaderCard)) {
                showBlankLine(1, activeColor);
            } else {
                showBlankLine(1);
            }
        }
        System.out.print("\n");
        for (LeaderCard leaderCard : leaderCards.keySet()) {
            if (leaderCards.get(leaderCard)) {
                showBlankLine(1, activeColor);
            } else {
                showBlankLine(1);
            }
        }
        System.out.print("\n");
        for (LeaderCard leaderCard : leaderCards.keySet()) {
            if (leaderCards.get(leaderCard)) {
                color = activeColor;
            } else {
                color = ANSI_RESET.escape();
            }
            System.out.print(color + BOLD_VERTICAL.escape() + "\t" + leaderCard.getVictoryPoints() + "\t" + BOLD_VERTICAL.escape() + "\t" + ANSI_RESET.escape());
        }
        System.out.print("\n");
        for (LeaderCard leaderCard : leaderCards.keySet()) {
            if (leaderCards.get(leaderCard)) {
                showBlankLine(1, activeColor);
            } else {
                showBlankLine(1);
            }
        }
        System.out.print("\n");
        for (LeaderCard leaderCard : leaderCards.keySet()) {
            LeaderCardType type = leaderCard.getLeaderType();
            switch (type) {
                case DISCOUNT:
                    ResourceType resourceType = ((Discount) leaderCard).getDiscountType();
                    color = getResourceTypeColor(resourceType);
                    if (leaderCards.get(leaderCard)) {
                        System.out.print(activeColor + BOLD_VERTICAL.escape() + ANSI_RESET.escape() + "\t-" + ((Discount) leaderCard).getDiscountAmount() + " " + color + RESOURCE.escape() + ANSI_RESET.escape() + "\t" + activeColor + BOLD_VERTICAL.escape() + "\t" + ANSI_RESET.escape());
                    } else {
                        System.out.print(BOLD_VERTICAL.escape() + "\t-" + ((Discount) leaderCard).getDiscountAmount() + " " + color + RESOURCE.escape() + ANSI_RESET.escape() + "\t" + BOLD_VERTICAL.escape() + "\t");
                    }
                    break;
                case EXTRA_DEPOSIT:
                    color = getResourceTypeColor(((ExtraDeposit) leaderCard).getStorageType());
                    if (leaderCards.get(leaderCard)) {
                        System.out.print(activeColor + BOLD_VERTICAL.escape() + ANSI_RESET.escape() + "\t" + color + SQUARE.escape() + " " + SQUARE.escape() + ANSI_RESET.escape() + "\t" + activeColor + BOLD_VERTICAL.escape() + "\t" + ANSI_RESET.escape());
                    } else
                        System.out.print(BOLD_VERTICAL.escape() + "\t" + color + SQUARE.escape() + " " + SQUARE.escape() + ANSI_RESET.escape() + "\t" + BOLD_VERTICAL.escape() + "\t");
                    break;
                case EXTRA_PRODUCTION:
                    int spaces = MAX_SPACES;
                    if (leaderCards.get(leaderCard))
                        System.out.print(activeColor + BOLD_VERTICAL.escape() + ANSI_RESET.escape());
                    else
                        System.out.print(BOLD_VERTICAL.escape());
                    for (ResourceType resourceType1 : ((ExtraProduction) leaderCard).getProductionRequirement().keySet()) {
                        System.out.print(((ExtraProduction) leaderCard).getProductionRequirement().get(resourceType1) + getResourceTypeColor(resourceType1) +
                                RESOURCE.escape() + ANSI_RESET.escape());
                        spaces -= 3;
                    }
                    System.out.print("->");
                    spaces -= 2;
                    for (Producible producible : ((ExtraProduction) leaderCard).getProductionResult()) {
                        String flag = "";
                        if (producible instanceof Resource) {
                            if (((Resource) producible).getType() == null) {
                                flag = JOLLY.escape();
                                color = ANSI_RESET.escape();
                            } else {
                                flag = RESOURCE.escape();
                                color = getResourceTypeColor(((Resource) producible).getType());
                            }
                        } else {
                            //faithpoint
                            flag = CROSS.escape();
                            color = ANSI_RED.escape();
                        }
                        System.out.print(color + "1" + flag + ANSI_RESET.escape());
                        spaces -= 3;
                    }
                    if (leaderCards.get(leaderCard))
                        System.out.print(activeColor + "\t" + BOLD_VERTICAL.escape() + "\t" + ANSI_RESET.escape());
                    else
                        System.out.print("\t" + BOLD_VERTICAL.escape() + "\t");
                    break;
                case WHITE_TO_RESOURCE:
                    if (leaderCards.get(leaderCard))
                        System.out.print(activeColor + BOLD_VERTICAL.escape() + ANSI_RESET.escape() + "\t" + ANSI_WHITE.escape() + RESOURCE.escape() + ANSI_RESET.escape() + " -> " + getResourceTypeColor(((WhiteToResource) leaderCard).getResult())
                                + RESOURCE.escape() + ANSI_RESET.escape() + "\t" + activeColor + BOLD_VERTICAL.escape() + "\t" + ANSI_RESET.escape());
                    else
                        System.out.print(BOLD_VERTICAL.escape() + "\t" + ANSI_WHITE.escape() + RESOURCE.escape() + ANSI_RESET.escape() + " -> " + getResourceTypeColor(((WhiteToResource) leaderCard).getResult())
                                + RESOURCE.escape() + ANSI_RESET.escape() + "\t" + BOLD_VERTICAL.escape() + "\t");
                    break;
            }
        }
        System.out.print("\n");

        for (LeaderCard leaderCard : leaderCards.keySet()) {
            if (leaderCards.get(leaderCard)) {
                showBlankLine(1, activeColor);
            } else {
                showBlankLine(1);
            }
        }
        System.out.print("\n");
        for (LeaderCard leaderCard : leaderCards.keySet()) {
            if (leaderCards.get(leaderCard)) {
                showFullLineBottom(1, activeColor);
            } else {
                showFullLineBottom(1);
            }
        }
        System.out.print("\n");
    }

    public void showLowerBoard(ClientPlayerBoard clientPlayerBoard) {
        System.out.print("\n");
        if (clientPlayerBoard.getDeposit().getNumberOfResourcesOnFloor(1) == 1)
            System.out.println("\t\t\t" + ANSI_RESET.escape() + getResourceTypeColor(clientPlayerBoard.getDeposit().get(1).getType()) + RESOURCE.escape() + ANSI_RESET.escape());
        else {
            System.out.println("\t\t\t" + ANSI_RESET.escape() + JOLLY.escape() + ANSI_RESET.escape());

        }
        System.out.print("\t\t\t" + HORIZ_POPE.escape());

        System.out.println("   \t\t\t" + JOLLY.escape());

        int z = 0;
        for (z = 0; z < clientPlayerBoard.getDeposit().getNumberOfResourcesOnFloor(2); z++)
            System.out.print("\t\t" + getResourceTypeColor(clientPlayerBoard.getDeposit().get(2).getType()) + RESOURCE.escape() + ANSI_RESET.escape());
        for (; z < 2; z++) {
            System.out.print("\t\t" + JOLLY.escape() + ANSI_RESET.escape());
        }
        System.out.print("\t\t\t-> " + JOLLY.escape());
        String color = ANSI_RESET.escape();
        for (Stack<DevelopmentCard> stack : clientPlayerBoard.getDevelopmentCards()) {
            System.out.print("\t\t");
            if (!stack.empty()) {
                //if there is at least a card i'll get its outline color
                color = getDevelopmentTypeColor(stack.peek().getType());

            } else {
                //if no card is present i'll use the default outline
                color = ANSI_RESET.escape();
            }
            System.out.print(color + UP_LEFT.escape());
            for (int i = 0; i < MAX_SPACES; i++) {
                System.out.print(BOLD_HORIZ.escape());
            }
            System.out.print(color + UP_RIGHT.escape() + "\t" + ANSI_RESET.escape());
        }


        System.out.print("\n\t\t");

        for (int i = 0; i < 16; i++)
            System.out.print(HORIZ_POPE.escape());

        System.out.print("\t\t" + JOLLY.escape() + "   \t");

        for (Stack<DevelopmentCard> stack : clientPlayerBoard.getDevelopmentCards()) {
            System.out.print("\t\t");
            if (!stack.empty()) {
                //if there is at least a card i'll get its outline color
                color = getDevelopmentTypeColor(stack.peek().getType());
                System.out.print(color + BOLD_VERTICAL.escape());
                for (ResourceType resourceType : stack.peek().getCost().keySet()) {
                    System.out.print(getResourceTypeColor(resourceType) + stack.peek().getCost().get(resourceType) + RESOURCE.escape() + ANSI_RESET.escape());
                }
                if (stack.peek().getCost().keySet().size() > 2)
                    System.out.print(color + "\t\t" + BOLD_VERTICAL.escape() + ANSI_RESET.escape() + "\t");
                else if (stack.peek().getCost().keySet().size() > 1)
                    System.out.print(color + "\t\t" + BOLD_VERTICAL.escape() + ANSI_RESET.escape() + "\t");
                else {
                    System.out.print(color + "\t\t" + BOLD_VERTICAL.escape() + ANSI_RESET.escape() + "\t");

                }
            } else {
                //if no card is present i'll use the default outline
                System.out.print(BOLD_VERTICAL.escape() + "\t\t" + BOLD_VERTICAL.escape() + "\t");
            }

        }


        System.out.print("\n");
        for (z = 0; z < clientPlayerBoard.getDeposit().getNumberOfResourcesOnFloor(3); z++)
            System.out.print("       \t" + getResourceTypeColor(clientPlayerBoard.getDeposit().get(3).getType()) + RESOURCE.escape() + ANSI_RESET.escape());
        for (; z < 3; z++) {
            System.out.print("       \t" + JOLLY.escape() + ANSI_RESET.escape());

        }
        System.out.print("\t\t");
        for (Stack<DevelopmentCard> stack : clientPlayerBoard.getDevelopmentCards()) {
            System.out.print("\t\t");
            if (!stack.empty()) {
                //if there is at least a card i'll get its outline color
                color = getDevelopmentTypeColor(stack.peek().getType());
                System.out.print(color + BOLD_VERTICAL.escape() + ANSI_RESET.escape());
                for (int j = 0; j < stack.peek().getLevel(); j++) {
                    System.out.print(color + LEVEL.escape() + ANSI_RESET.escape());
                }
                System.out.print("\t");

                for (int j = 0; j < stack.peek().getLevel(); j++) {
                    System.out.print(color + LEVEL.escape() + ANSI_RESET.escape());
                }
                System.out.print(color + "\t" + BOLD_VERTICAL.escape() + ANSI_RESET.escape() + "\t");
            } else {
                //if no card is present i'll use the default outline
                System.out.print(BOLD_VERTICAL.escape() + "\t\t" + BOLD_VERTICAL.escape() + "\t");
            }

        }
        System.out.print("\n\t");
        for (int i = 0; i < 32; i++)
            System.out.print(HORIZ_POPE.escape());
        System.out.print("\t\t");
        for (Stack<DevelopmentCard> stack : clientPlayerBoard.getDevelopmentCards()) {
            System.out.print("\t\t");
            if (!stack.empty()) {
                color = getDevelopmentTypeColor(stack.peek().getType());
            } else {
                color = ANSI_RESET.escape();
            }
            System.out.print(color + BOLD_VERTICAL.escape() + "\t\t" + BOLD_VERTICAL.escape() + "\t");
        }
        System.out.print("\n\t\t\t\t\t\t\t\t\t");

        ArrayList<HashMap<ResourceType, Integer>> results = new ArrayList<>();
        ArrayList<Integer> faithResults = new ArrayList<>();//maps of results for every card in the first row
        //first of all i fill the map with card information so that i can use them later
        for (Stack<DevelopmentCard> stack : clientPlayerBoard.getDevelopmentCards()) {
            //check if a card has faithpoint production
            HashMap<ResourceType, Integer> map = new HashMap<>();
            if (stack.size() > 0) {
                long count = stack.peek().getProductionResults().stream().filter(xa -> (xa instanceof FaithPoint)).count();
                faithResults.add((int) count);
                //add the remaining to resource

                for (Producible p : stack.peek().getProductionResults()) {
                    if (!(p instanceof FaithPoint)) {

                        if (map.containsKey(((Resource) p).getType())) {
                            map.put(((Resource) p).getType(), map.get(((Resource) p).getType()) + 1);
                        } else {
                            map.put(((Resource) p).getType(), 1);
                        }


                    }
                }
            }
            results.add(map);
        }

        for (int i = 0; i < 3; i++) {
            if (clientPlayerBoard.getDevelopmentCards().get(i).size() > 0) {
                color = getDevelopmentTypeColor(clientPlayerBoard.getDevelopmentCards().get(i).peek().getType());
                System.out.print(color + BOLD_VERTICAL.escape() + ANSI_RESET.escape());
                for (ResourceType resourceType : clientPlayerBoard.getDevelopmentCards().get(i).peek().getProductionRequirements().keySet()) {
                    System.out.print(getResourceTypeColor(resourceType) + clientPlayerBoard.getDevelopmentCards().get(i).peek().getProductionRequirements().get(resourceType) +
                            RESOURCE.escape() + ANSI_RESET.escape() + " ");
                }
                showGameBoardCardUtil(results, faithResults, i);
                if (clientPlayerBoard.getDevelopmentCards().get(i).peek().getProductionRequirements().keySet().size() > 1)
                    System.out.print(color + "\t" + BOLD_VERTICAL.escape() + ANSI_RESET.escape() + "\t\t\t");
                else
                    System.out.print("\t" + color + BOLD_VERTICAL.escape() + ANSI_RESET.escape() + "\t\t\t");
            } else {
                System.out.print(ANSI_RESET.escape() + BOLD_VERTICAL.escape() + "\t\t" + BOLD_VERTICAL.escape() + "\t\t\t");
            }
        }
        System.out.print("\n\t");
        Map<ResourceType, List<Resource>> strongbox = clientPlayerBoard.getStrongbox().getAll();
        for (ResourceType resourceType : ResourceType.values()) {
            if (strongbox.containsKey(resourceType))
                System.out.print(getResourceTypeColor(resourceType) + strongbox.get(resourceType).size() + RESOURCE.escape() + ANSI_RESET.escape() + "\t");
            else
                System.out.print(getResourceTypeColor(resourceType) + "0" + RESOURCE.escape() + ANSI_RESET.escape() + "\t");

        }
        System.out.print("\t\t\t\t");
        for (int i = 0; i < 3; i++) {
            if (clientPlayerBoard.getDevelopmentCards().get(i).size() > 0) {
                color = getDevelopmentTypeColor(clientPlayerBoard.getDevelopmentCards().get(i).peek().getType());
                System.out.print(color + BOLD_VERTICAL.escape() + ANSI_RESET.escape() + "\t");
                if (results.get(i).keySet().size() > 0) {
                    for (ResourceType resourceType : results.get(i).keySet()) {
                        System.out.print(getResourceTypeColor(resourceType) + results.get(i).get(resourceType) + RESOURCE.escape() + ANSI_RESET.escape());
                        results.get(i).remove(resourceType);
                        break;
                    }

                }
                System.out.print("\t" + color + BOLD_VERTICAL.escape() + ANSI_RESET.escape() + "\t\t\t");
            } else {
                System.out.print(ANSI_RESET.escape() + BOLD_VERTICAL.escape() + "\t\t" + BOLD_VERTICAL.escape() + "\t\t\t");
            }
        }
        System.out.print("\n\t\t\t\t\t\t\t\t\t");

        for (int i = 0; i < 3; i++) {
            if (clientPlayerBoard.getDevelopmentCards().get(i).size() > 0) {
                color = getDevelopmentTypeColor(clientPlayerBoard.getDevelopmentCards().get(i).peek().getType());
                System.out.print(color + BOLD_VERTICAL.escape() + ANSI_RESET.escape() + "\t");
                if (results.get(i).keySet().size() > 0) {
                    for (ResourceType resourceType : results.get(i).keySet()) {
                        System.out.print(getResourceTypeColor(resourceType) + results.get(i).get(resourceType) + RESOURCE.escape() + ANSI_RESET.escape());
                        results.get(i).remove(resourceType);
                        break;
                    }

                }
                System.out.print("\t" + color + BOLD_VERTICAL.escape() + ANSI_RESET.escape() + "\t\t\t");
            } else {
                System.out.print(ANSI_RESET.escape() + BOLD_VERTICAL.escape() + "\t\t" + BOLD_VERTICAL.escape() + "\t\t\t");
            }
        }
        System.out.print("\n\t\t\t\t\t\t\t\t\t");

        for (int j = 0; j < 3; j++) {
            if (clientPlayerBoard.getDevelopmentCards().get(j).size() > 0) {
                color = getDevelopmentTypeColor(clientPlayerBoard.getDevelopmentCards().get(j).peek().getType());
                System.out.print(color + BOLD_VERTICAL.escape() + ANSI_RESET.escape() + "\t");
                System.out.print((clientPlayerBoard.getDevelopmentCards().get(j).peek().getVictoryPoints()));
                System.out.print("\t" + color + BOLD_VERTICAL.escape() + ANSI_RESET.escape() + "\t\t\t");
            } else {
                System.out.print(ANSI_RESET.escape() + BOLD_VERTICAL.escape() + "\t\t" + BOLD_VERTICAL.escape() + "\t\t\t");
            }

        }
        System.out.print("\n\t\t\t\t\t\t\t\t\t");

        for (int j = 0; j < 3; j++) {
            if (clientPlayerBoard.getDevelopmentCards().get(j).size() > 0) {
                color = getDevelopmentTypeColor(clientPlayerBoard.getDevelopmentCards().get(j).peek().getType());
                System.out.print(color + DOWN_LEFT.escape());
                for (int k = 0; k < MAX_SPACES; k++)
                    System.out.print(color + BOLD_HORIZ.escape());
                System.out.print(color + DOWN_RIGHT.escape() + "\t" + ANSI_RESET.escape() + "\t\t");
            } else {
                System.out.print(ANSI_RESET.escape() + DOWN_LEFT.escape());
                for (int i = 0; i < MAX_SPACES; i++) {
                    System.out.print(BOLD_HORIZ.escape());
                }
                System.out.print(ANSI_RESET.escape() + DOWN_RIGHT.escape() + "\t\t\t");
            }
        }
        System.out.print("\n\t\t\t\t\t\t\t\t\t");

        for (int j = 0; j < 3; j++) {
            if (clientPlayerBoard.getDevelopmentCards().get(j).size() > 1) {
                int ind=1;
                if(clientPlayerBoard.getDevelopmentCards().get(j).size()==2)
                    ind = 0;
                else
                    ind=1;
                color = getDevelopmentTypeColor(clientPlayerBoard.getDevelopmentCards().get(j).get(ind).getType());
                System.out.print(color + BOLD_VERTICAL.escape() + ANSI_RESET.escape() + "\t");

                System.out.print((clientPlayerBoard.getDevelopmentCards().get(j).get(ind).getVictoryPoints()));
                System.out.print("\t" + color + BOLD_VERTICAL.escape() + ANSI_RESET.escape() + "\t\t\t");
            }
        }
        System.out.print("\n\t\t\t\t\t\t\t\t\t");
        for (int j = 0; j < 3; j++) {
            if (clientPlayerBoard.getDevelopmentCards().get(j).size() > 1) {
                int ind=1;
                if(clientPlayerBoard.getDevelopmentCards().get(j).size()==2)
                    ind = 0;
                else
                    ind=1;
                color = getDevelopmentTypeColor(clientPlayerBoard.getDevelopmentCards().get(j).get(ind).getType());
                System.out.print(color + DOWN_LEFT.escape());
                for (int k = 0; k < MAX_SPACES; k++)
                    System.out.print(color + BOLD_HORIZ.escape());
                System.out.print(color + DOWN_RIGHT.escape() + "\t" + ANSI_RESET.escape() + "\t\t");
            }
        }
        System.out.print("\n\t\t\t\t\t\t\t\t\t");
        for (int j = 0; j < 3; j++) {
            if (clientPlayerBoard.getDevelopmentCards().get(j).size() > 2) {
                color = getDevelopmentTypeColor(clientPlayerBoard.getDevelopmentCards().get(j).get(0).getType());
                System.out.print(color + BOLD_VERTICAL.escape() + ANSI_RESET.escape() + "\t");
                System.out.print((clientPlayerBoard.getDevelopmentCards().get(j).get(0).getVictoryPoints()));
                System.out.print("\t" + color + BOLD_VERTICAL.escape() + ANSI_RESET.escape() + "\t\t\t");
            }
        }
        System.out.print("\n\t\t\t\t\t\t\t\t\t");
        for (int j = 0; j < 3; j++) {
            if (clientPlayerBoard.getDevelopmentCards().get(j).size() > 2) {
                color = getDevelopmentTypeColor(clientPlayerBoard.getDevelopmentCards().get(j).get(0).getType());
                System.out.print(color + DOWN_LEFT.escape());
                for (int k = 0; k < MAX_SPACES; k++)
                    System.out.print(color + BOLD_HORIZ.escape());
                System.out.print(color + DOWN_RIGHT.escape() + "\t" + ANSI_RESET.escape() + "\t\t");
            }
        }
        System.out.println();
    }

    /**
     * Shows a simplified version of the otherPlayer's board where the popeRoad is not shown
     *
     * @param nickname:         the player of whom to show the board without the popeRoad
     * @param clientPlayerBoard the player board
     */
    public void showOtherPlayerBoard(String nickname, ClientPlayerBoard clientPlayerBoard) {
        int position = clientPlayerBoard.getPopeRoad().getCurrentPositionIndex();
        System.out.println(nickname + " is on cell number " + position);

        showLowerBoard(clientPlayerBoard);
        Map<LeaderCard, Boolean> map = new HashMap<>();
        if (clientPlayerBoard.getActiveLeaderCards() != null) {
            for (LeaderCard leaderCard : clientPlayerBoard.getActiveLeaderCards()) {
                map.put(leaderCard, true);
            }
            showLeaderCards(map);
        }
    }

    public void showPopeRoad(ClientPlayer player) {
        int cell_width = 6;
        int section_card_width = 12;
        int x;
        String normalColor = ANSI_RESET.escape();

        int position = player.getPositionIndex();
        //--first row
        showEmptyCells(2, cell_width);
        if (position == 4) {
            x = 1;
        } else
            x = -1;
        showFullCells(1, "top", cell_width, normalColor, x, 0);
        if (position <= 8 && position >= 5) {
            x = 4 - (position - 5);
        } else
            x = -1;
        showFullCells(4, "top", cell_width, ANSI_GREEN.escape(), x, 0);
        if (position == 9) {
            x = 1;
        } else
            x = -1;
        showFullCells(1, "top", cell_width, normalColor, x, 0);
        showEmptyCells(1, cell_width);
        showFullCells(1, "top", section_card_width, ANSI_YELLOW.escape(), -1, 0);
        showEmptyCells(1, cell_width);
        if (position == 18) {
            x = 1;
        } else
            x = -1;
        showFullCells(1, "top", cell_width, normalColor, x, 0);
        if (position <= 24 && position >= 19) {
            x = 6 - (position - 19);
        } else
            x = -1;
        showFullCells(6, "top", cell_width, ANSI_RED.escape(), x, 0);
        System.out.print("\n");

        //--second row
        showEmptyCells(2, cell_width);
        if (position == 4) {
            x = 1;
        } else
            x = -1;
        showFullCells(1, "middle", cell_width, normalColor, x, 0);
        if (position <= 8 && position >= 5) {
            x = 4 - (position - 5);
        } else
            x = -1;
        showFullCells(1, "middle", cell_width, ANSI_GREEN.escape(), x, 0);
        showFullCells(1, "middle", cell_width, ANSI_GREEN.escape(), x, 2);
        showFullCells(2, "middle", cell_width, ANSI_GREEN.escape(), x, 0);
        if (position == 9) {
            x = 1;
        } else
            x = -1;
        showFullCells(1, "middle", cell_width, normalColor, x, 4);
        showEmptyCells(1, cell_width);
        showFullCells(1, "middle", section_card_width, ANSI_YELLOW.escape(), -1, 0);
        showEmptyCells(1, cell_width);
        if (position == 18) {
            x = 1;
        } else
            x = -1;
        showFullCells(1, "middle", cell_width, normalColor, x, 12);
        if (position <= 24 && position >= 19) {
            x = 6 - (position - 19);
        } else
            x = -1;
        showFullCells(2, "middle", cell_width, ANSI_RED.escape(), x, 0);
        showFullCells(1, "middle", cell_width, ANSI_RED.escape(), x, 16);
        showFullCells(2, "middle", cell_width, ANSI_RED.escape(), x, 0);
        showFullCells(1, "middle", cell_width, ANSI_RED.escape(), x, 20);
        System.out.print("\n");

        //--third row
        showEmptyCells(2, cell_width);
        if (position == 4) {
            x = 1;
        } else
            x = -1;
        showFullCells(1, "bottom", cell_width, normalColor, x, 0);
        if (position <= 8 && position >= 5) {
            x = 4 - (position - 5);
        } else
            x = -1;
        showFullCells(4, "bottom", cell_width, ANSI_GREEN.escape(), x, 0);
        if (position == 9) {
            x = 1;
        } else
            x = -1;
        showFullCells(1, "bottom", cell_width, normalColor, x, 0);
        showEmptyCells(1, cell_width);
        showFullCells(1, "middle", section_card_width, ANSI_YELLOW.escape(), -1, 0);
        showEmptyCells(1, cell_width);
        if (position == 18) {
            x = 1;
        } else
            x = -1;
        showFullCells(1, "bottom", cell_width, normalColor, x, 0);
        if (position <= 24 && position >= 19) {
            x = 6 - (position - 19);
        } else
            x = -1;
        showFullCells(6, "bottom", cell_width, ANSI_RED.escape(), x, 0);
        System.out.print("\n");

        //--fourth row
        showEmptyCells(2, cell_width);
        if (position == 3) {
            x = 1;
        } else
            x = -1;
        showFullCells(1, "top", cell_width, normalColor, x, 0);
        showEmptyCells(1, cell_width);
        showFullCells(1, "top", section_card_width, ANSI_GREEN.escape(), -1, 0);
        showEmptyCells(1, cell_width);
        if (position == 10) {
            x = 1;
        } else
            x = -1;
        showFullCells(1, "top", cell_width, normalColor, x, 0);
        showEmptyCells(1, cell_width);
        showFullCells(1, "middle", section_card_width, ANSI_YELLOW.escape(), -1, 0);
        showEmptyCells(1, cell_width);
        if (position == 17) {
            x = 1;
        } else
            x = -1;
        showFullCells(1, "top", cell_width, normalColor, x, 0);
        showEmptyCells(2, cell_width);
        showFullCells(1, "top", section_card_width, ANSI_RED.escape(), -1, 0);
        showEmptyCells(2, cell_width);
        System.out.print("\n");
        //--fifth row
        showEmptyCells(2, cell_width);
        if (position == 3) {
            x = 1;
        } else
            x = -1;
        showFullCells(1, "middle", cell_width, normalColor, x, 1);
        showEmptyCells(1, cell_width);
        showFullCells(1, "middle", section_card_width, ANSI_GREEN.escape(), -1, 0);
        showEmptyCells(1, cell_width);
        if (position == 10) {
            x = 1;
        } else
            x = -1;
        showFullCells(1, "middle", cell_width, normalColor, x, 0);
        showEmptyCells(1, cell_width);
        showFullCells(1, "bottom", section_card_width, ANSI_YELLOW.escape(), -1, 0);
        showEmptyCells(1, cell_width);
        if (position == 17) {
            x = 1;
        } else
            x = -1;
        showFullCells(1, "middle", cell_width, normalColor, x, 0);
        showEmptyCells(2, cell_width);
        showFullCells(1, "middle", section_card_width, ANSI_RED.escape(), -1, 0);
        showEmptyCells(2, cell_width);
        System.out.print("\n");
        //-sixth row
        showEmptyCells(2, cell_width);
        if (position == 3) {
            x = 1;
        } else
            x = -1;
        showFullCells(1, "bottom", cell_width, normalColor, x, 0);
        showEmptyCells(1, cell_width);
        showFullCells(1, "middle", section_card_width, ANSI_GREEN.escape(), -1, 0);
        showEmptyCells(1, cell_width);
        if (position == 10) {
            x = 1;
        } else
            x = -1;
        showFullCells(1, "bottom", cell_width, normalColor, x, 0);
        showEmptyCells(5, cell_width);
        if (position == 17) {
            x = 1;
        } else
            x = -1;
        showFullCells(1, "bottom", cell_width, normalColor, x, 0);
        showEmptyCells(2, cell_width);
        showFullCells(1, "middle", section_card_width, ANSI_RED.escape(), -1, 0);
        showEmptyCells(2, cell_width);
        System.out.print("\n");
        //--seventh row
        if (position <= 2) {
            x = 3 - position;
        } else
            x = -1;
        showFullCells(3, "top", cell_width, normalColor, x, 0);
        showEmptyCells(1, cell_width);
        showFullCells(1, "middle", section_card_width, ANSI_GREEN.escape(), -1, 0);
        showEmptyCells(1, cell_width);
        if (position == 11) {
            x = 1;
        } else
            x = -1;
        showFullCells(1, "top", cell_width, normalColor, x, 0);
        if (position <= 16 && position >= 12) {
            x = 5 - (position - 12);
        } else
            x = -1;
        showFullCells(5, "top", cell_width, ANSI_YELLOW.escape(), x, 0);
        showEmptyCells(2, cell_width);
        showFullCells(1, "middle", section_card_width, ANSI_RED.escape(), -1, 0);
        System.out.print("\n");
        //-eighth row
        if (position <= 2) {
            x = 3 - position;
        } else
            x = -1;
        showFullCells(3, "middle", cell_width, normalColor, x, 0);
        showEmptyCells(1, cell_width);
        showFullCells(1, "bottom", section_card_width, ANSI_GREEN.escape(), -1, 0);
        showEmptyCells(1, cell_width);
        if (position == 11) {
            x = 1;
        } else
            x = -1;
        showFullCells(1, "middle", cell_width, normalColor, x, 0);
        if (position <= 16 && position >= 12) {
            x = 5 - (position - 12);
        } else
            x = -1;
        showFullCells(1, "middle", cell_width, ANSI_YELLOW.escape(), x, 6);
        showFullCells(2, "middle", cell_width, ANSI_YELLOW.escape(), x, 0);
        showFullCells(1, "middle", cell_width, ANSI_YELLOW.escape(), x, 9);
        showFullCells(1, "middle", cell_width, ANSI_YELLOW.escape(), x, 0);
        showEmptyCells(2, cell_width);
        showFullCells(1, "bottom", section_card_width, ANSI_RED.escape(), -1, 0);
        System.out.print("\n");
        //-ninth row
        if (position <= 2) {
            x = 3 - position;
        } else
            x = -1;
        showFullCells(3, "bottom", cell_width, normalColor, x, 0);
        showEmptyCells(5, cell_width);
        if (position == 11) {
            x = 1;
        } else
            x = -1;
        showFullCells(1, "bottom", cell_width, normalColor, x, 0);
        if (position <= 16 && position >= 12) {
            x = 5 - (position - 12);
        } else
            x = -1;
        showFullCells(5, "bottom", cell_width, ANSI_YELLOW.escape(), x, 0);
        System.out.print("\n");
    }

    /**
     * This method shows the user's leader cards
     */
    public void showLeaderCards(List<LeaderCard> leaderCards) {


        String color;
        showFullLineTop(leaderCards.size());
        System.out.print("\n");
        for (int i = 0; i < leaderCards.size(); i++) {//for every card in the list
            LeaderCard leaderCard = leaderCards.get(i);
            System.out.print(BOLD_VERTICAL.escape() + " ");
            int spaces = MAX_SPACES; //max numbers of spaces per card on a row
            for (Integer j : leaderCard.getCostDevelopment().keySet()) {//checking costDevelopment
                for (DevelopmentCardType k : leaderCard.getCostDevelopment().get(j).keySet()) {
                    System.out.print(leaderCard.getCostDevelopment().get(j).get(k));
                    spaces--;
                    switch (k) {
                        case BLUE:
                            System.out.print(ANSI_BLUE.escape() + DEVELOPMENTCARD.escape() + ANSI_RESET.escape());
                            break;
                        case GREEN:
                            System.out.print(ANSI_GREEN.escape() + DEVELOPMENTCARD.escape() + ANSI_RESET.escape());
                            break;
                        case PURPLE:
                            System.out.print(ANSI_PURPLE.escape() + DEVELOPMENTCARD.escape() + ANSI_RESET.escape());
                            break;
                        case YELLOW:
                            System.out.print(ANSI_YELLOW.escape() + DEVELOPMENTCARD.escape() + ANSI_RESET.escape());
                            break;
                    }
                    spaces--;
                }
                if (j != 0) {
                    spaces -= j;
                    for (int h = 0; h < j; h++)
                        System.out.print(LEVEL.escape());
                }

            }
            //checking costResources
            for (ResourceType resourceType : leaderCard.getCostResource().keySet()) {
                System.out.print(getResourceTypeColor(resourceType) + leaderCard.getCostResource().get(resourceType) + RESOURCE.escape() + ANSI_RESET.escape());
            }
            System.out.print("\t\t" + BOLD_VERTICAL.escape() + "\t");
        }
        System.out.print("\n");
        showBlankLine(leaderCards.size());
        System.out.print("\n");
        showBlankLine(leaderCards.size());
        System.out.print("\n");
        for (int i = 0; i < leaderCards.size(); i++) {
            System.out.print(BOLD_VERTICAL.escape() + "\t" + leaderCards.get(i).getVictoryPoints() + "\t" + BOLD_VERTICAL.escape() + "\t");
        }
        System.out.print("\n");
        showBlankLine(leaderCards.size());
        System.out.print("\n");
        for (int i = 0; i < leaderCards.size(); i++) {
            LeaderCardType type = leaderCards.get(i).getLeaderType();
            switch (type) {
                case DISCOUNT:
                    ResourceType resourceType = ((Discount) leaderCards.get(i)).getDiscountType();
                    color = getResourceTypeColor(resourceType);
                    System.out.print(BOLD_VERTICAL.escape() + "\t-" + ((Discount) leaderCards.get(i)).getDiscountAmount() + " " + color + RESOURCE.escape() + ANSI_RESET.escape() + "\t" + BOLD_VERTICAL.escape() + "\t");
                    break;
                case EXTRA_DEPOSIT:
                    color = getResourceTypeColor(((ExtraDeposit) leaderCards.get(i)).getStorageType());
                    System.out.print(BOLD_VERTICAL.escape() + "\t" + color + SQUARE.escape() + " " + SQUARE.escape() + ANSI_RESET.escape() + "\t" + BOLD_VERTICAL.escape() + "\t");
                    break;
                case EXTRA_PRODUCTION:
                    int spaces = MAX_SPACES;
                    for (ResourceType resourceType1 : ((ExtraProduction) leaderCards.get(i)).getProductionRequirement().keySet()) {
                        System.out.print(BOLD_VERTICAL.escape() + ((ExtraProduction) leaderCards.get(i)).getProductionRequirement().get(resourceType1) + getResourceTypeColor(resourceType1) +
                                RESOURCE.escape() + ANSI_RESET.escape());
                        spaces -= 3;
                    }
                    System.out.print("->");
                    spaces -= 2;
                    for (Producible producible : ((ExtraProduction) leaderCards.get(i)).getProductionResult()) {
                        String flag = "";
                        if (producible instanceof Resource) {
                            if (((Resource) producible).getType() == null) {
                                flag = JOLLY.escape();
                                color = ANSI_RESET.escape();
                            } else {
                                flag = RESOURCE.escape();
                                color = getResourceTypeColor(((Resource) producible).getType());
                            }
                        } else {
                            //faithpoint
                            flag = CROSS.escape();
                            color = ANSI_RED.escape();
                        }
                        System.out.print(color + "1" + flag + ANSI_RESET.escape());
                        spaces -= 3;
                    }
                    System.out.print("\t" + BOLD_VERTICAL.escape() + "\t");
                    break;
                case WHITE_TO_RESOURCE:
                    System.out.print(BOLD_VERTICAL.escape() + "\t" + ANSI_WHITE.escape() + RESOURCE.escape() + ANSI_RESET.escape() + " -> " + getResourceTypeColor(((WhiteToResource) leaderCards.get(i)).getResult())
                            + RESOURCE.escape() + ANSI_RESET.escape() + "\t" + BOLD_VERTICAL.escape() + "\t");
                    break;
            }
        }
        System.out.print("\n");
        showBlankLine(leaderCards.size());
        System.out.print("\n");
        showFullLineBottom(leaderCards.size());
        System.out.print("\n");
    }

    public String getResourceTypeColor(ResourceType resourceType) {
        String color = "";
        switch (resourceType) {
            case COIN:
                color = ANSI_YELLOW.escape();
                break;
            case STONE:
                color = GREY.escape();
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


    public void showBlankLine(int n) {
        for (int i = 0; i < n; i++) {
            System.out.print(BOLD_VERTICAL.escape() + "\t\t" + BOLD_VERTICAL.escape() + "\t");
        }

    }

    public void showBlankLine(int n, String color) {
        for (int i = 0; i < n; i++) {
            System.out.print(color + BOLD_VERTICAL.escape() + "\t\t" + BOLD_VERTICAL.escape() + "\t" + ANSI_RESET.escape());
        }

    }

    public void showFullLineTop(int n) {
        for (int i = 0; i < n; i++) {
            System.out.print(UP_LEFT.escape());
            for (int j = 0; j < MAX_SPACES; j++)
                System.out.print(BOLD_HORIZ.escape());
            System.out.print(UP_RIGHT.escape() + "\t");
        }

    }

    public void showFullLineTop(int n, String color) {
        for (int i = 0; i < n; i++) {
            System.out.print(color + UP_LEFT.escape());
            for (int j = 0; j < MAX_SPACES; j++)
                System.out.print(BOLD_HORIZ.escape());
            System.out.print(UP_RIGHT.escape() + "\t" + ANSI_RESET.escape());
        }

    }

    public void showFullLineBottom(int n) {
        for (int i = 0; i < n; i++) {
            System.out.print(DOWN_LEFT.escape());
            for (int j = 0; j < MAX_SPACES; j++)
                System.out.print(BOLD_HORIZ.escape());
            System.out.print(DOWN_RIGHT.escape() + "\t");
        }

    }

    public void showFullLineBottom(int n, String color) {
        for (int i = 0; i < n; i++) {
            System.out.print(color + DOWN_LEFT.escape());
            for (int j = 0; j < MAX_SPACES; j++)
                System.out.print(BOLD_HORIZ.escape());
            System.out.print(DOWN_RIGHT.escape() + "\t" + ANSI_RESET.escape());
        }

    }

    /**
     * This method prints all the type of resources available in a match
     */
    public void showAllAvailableResources() {
        for (ResourceType resourceType : ResourceType.values()) {
            System.out.println(getResourceTypeColor(resourceType) + RESOURCE.escape() + " " + resourceType.toString() + ANSI_RESET.escape());
        }
    }

    /**
     * This method prints all the type of resources available in a match
     *
     * @param resources the resources to show
     */
    public void showAllAvailableResources(List<Resource> resources) {
        for (Resource resource : resources) {
            System.out.println(getResourceTypeColor(resource.getType()) + RESOURCE.escape() + " " + resource.getType().toString() + ANSI_RESET.escape());
        }
    }

    /**
     * This method shows the player personal board
     */
    public void showBoard(ClientGameBoard board, ClientPlayer player) {
        showPopeRoad(player);
        showLowerBoard(player.getPlayerBoard());
    }

    public void showFullCells(int x, String where, int cell_width, String color, int PV, int punti) {
        switch (where) {
            case "top":
                while (x > 0) {
                    if (x == PV) {
                        showTopPopeRoadCell(cell_width, playerColor);

                    } else
                        showTopPopeRoadCell(cell_width, color);
                    x--;
                }
                break;
            case "bottom":
                while (x > 0) {
                    if (x == PV)
                        showBottomPopeRoadCell(cell_width, playerColor);
                    else
                        showBottomPopeRoadCell(cell_width, color);
                    x--;
                }
                break;
            case "middle":
                while (x > 0) {
                    if (x == PV)
                        showMiddlePopeRoadCell(cell_width, playerColor, punti);
                    else
                        showMiddlePopeRoadCell(cell_width, color, punti);
                    x--;
                }
                break;
        }
    }

    public void showEmptyCells(int x, int cell_width) {
        int numOfTabs = 0;
        numOfTabs = getNumOfTabs(numOfTabs, x * cell_width);
    }

    public int getNumOfTabs(int numOfTabs, int x) {
        while (x > 8) {
            numOfTabs++;
            x -= 8;
        }
        while (x > 0) {
            System.out.print(" ");
            x--;
        }
        while (numOfTabs >= 0) {
            System.out.print("\t");
            numOfTabs--;
        }
        return numOfTabs;
    }

    public void showBottomPopeRoadCell(int width, String color) {
        System.out.print(color + DOWN_LEFT_POPE.escape());
        width -= 2; //cutting off the corners
        while (width >= 0) {
            System.out.print(color + HORIZ_POPE.escape());
            width--;
        }
        System.out.print(color + DOWN_RIGHT_POPE.escape() + " ");
    }

    public void showMiddlePopeRoadCell(int width, String color, int pv) {

        System.out.print(color + VERTICAL_POPE.escape());
        if (pv == 0) {
            while (width > 1) {
                System.out.print(" ");
                width--;
            }
        } else {
            System.out.print(" " + pv + " ");
            width -= 2;
            width -= String.valueOf(pv).length();
            while (width > 1) {
                System.out.print(" ");
                width--;
            }
        }
        System.out.print(color + VERTICAL_POPE.escape() + " ");

    }

    public void showTopPopeRoadCell(int width, String color) {
        System.out.print(color + UP_LEFT_POPE.escape());
        width -= 2; //cutting off the corners
        while (width >= 0) {
            System.out.print(color + HORIZ_POPE.escape());
            width--;
        }
        System.out.print(color + UP_RIGHT_POPE.escape() + " ");
    }

    /**
     * This method shows the common game board
     */
    public void showGameBoard(ClientGameBoard gameBoard) {
        String color;
        for (int i = 0; i < gameBoard.getCardMarketColumns(); i++) {
            if (gameBoard.getCardMarket().getStack(0, i).getListOfCards().size() > 0) {
                color = getDevelopmentTypeColor(gameBoard.getCardMarket().getCard(0, i).getType());
            } else { //stack is empty
                color = ANSI_RESET.escape();
            }
            System.out.print(color + UP_LEFT.escape());
            for (int j = 0; j < MAX_SPACES; j++)
                System.out.print(color + BOLD_HORIZ.escape());
            System.out.print(color + UP_RIGHT.escape() + "\t" + ANSI_RESET.escape());
        }
        showMarbleMarketLine(0, gameBoard);

        for (int i = 0; i < gameBoard.getCardMarketColumns(); i++) {
            if (gameBoard.getCardMarket().getStack(0, i).getListOfCards().size() > 0) {
                color = getDevelopmentTypeColor(gameBoard.getCardMarket().getCard(0, i).getType());
                System.out.print(color + BOLD_VERTICAL.escape() + ANSI_RESET.escape());
                for (ResourceType resourceType : gameBoard.getCardMarket().getCard(0, i).getCost().keySet()) {
                    System.out.print(getResourceTypeColor(resourceType) + gameBoard.getCardMarket().getCard(0, i).getCost().get(resourceType) + RESOURCE.escape() + ANSI_RESET.escape());
                }
                System.out.print(color + "\t\t" + BOLD_VERTICAL.escape() + "\t" + ANSI_RESET.escape());
            } else {
                color = ANSI_RESET.escape();
                System.out.print(color + BOLD_VERTICAL.escape() + "\t\t" + BOLD_VERTICAL.escape() + "\t" + ANSI_RESET.escape());
            }


        }
        showMarbleMarketLine(1, gameBoard);

        for (int i = 0; i < gameBoard.getCardMarketColumns(); i++) {
            if (gameBoard.getCardMarket().getStack(0, i).getListOfCards().size() > 0) {
                color = getDevelopmentTypeColor(gameBoard.getCardMarket().getCard(0, i).getType());
                System.out.print(color + BOLD_VERTICAL.escape() + ANSI_RESET.escape());
                for (int j = 0; j < gameBoard.getCardMarket().getCard(0, i).getLevel(); j++) {
                    System.out.print(color + LEVEL.escape() + ANSI_RESET.escape());
                }
                System.out.print("\t");
                for (int j = 0; j < gameBoard.getCardMarket().getCard(0, i).getLevel(); j++) {
                    System.out.print(color + LEVEL.escape() + ANSI_RESET.escape());
                }
                System.out.print(color + "\t" + BOLD_VERTICAL.escape() + ANSI_RESET.escape() + "\t");
            } else {
                showBlankLine(1);
            }
        }

        showMarbleMarketLine(2, gameBoard);

        for (int i = 0; i < gameBoard.getCardMarketColumns(); i++) {
            if (gameBoard.getCardMarket().getStack(0, i).getListOfCards().size() > 0) {
                color = getDevelopmentTypeColor(gameBoard.getCardMarket().getCard(0, i).getType());
                System.out.print(color + BOLD_VERTICAL.escape() + "\t\t" + BOLD_VERTICAL.escape() + "\t" + ANSI_RESET.escape());
            } else {
                showBlankLine(1);
            }
        }
        System.out.print("\t");

        for (int i = 0; i < gameBoard.getMarbleMarketColumns(); i++) {
            System.out.print(UP_ARROW.escape() + " ");
        }
        System.out.print("\n");

        ArrayList<HashMap<ResourceType, Integer>> results = new ArrayList<>();
        ArrayList<Integer> faithResults = new ArrayList<>();//maps of results for every card in the first row
        //first of all i fill the map with card information so that i can use them later
        for (int i = 0; i < gameBoard.getCardMarketColumns(); i++) {
            //check if a card has faithpoint production
            long count;
            if (gameBoard.getCardMarket().getStack(0, i).getListOfCards().size() > 0) {
                count = gameBoard.getCardMarket().getCard(0, i).getProductionResults().stream().filter(x -> (x instanceof FaithPoint)).count();
            } else
                count = 0;
            faithResults.add((int) count);
            //add the remaining to resource
            HashMap<ResourceType, Integer> map = new HashMap<>();
            if (gameBoard.getCardMarket().getStack(0, i).getListOfCards().size() > 0) {
                for (Producible p : gameBoard.getCardMarket().getCard(0, i).getProductionResults()) {
                    if (!(p instanceof FaithPoint)) {

                        if (map.containsKey(((Resource) p).getType())) {
                            map.put(((Resource) p).getType(), map.get(((Resource) p).getType()) + 1);
                        } else {
                            map.put(((Resource) p).getType(), 1);
                        }


                    }
                }

            }
            results.add(map);
        }

        for (int i = 0; i < gameBoard.getCardMarketColumns(); i++) {

            if (gameBoard.getCardMarket().getStack(0, i).getListOfCards().size() > 0) {
                color = getDevelopmentTypeColor(gameBoard.getCardMarket().getCard(0, i).getType());
                System.out.print(color + BOLD_VERTICAL.escape() + ANSI_RESET.escape());
                for (ResourceType resourceType : gameBoard.getCardMarket().getCard(0, i).getProductionRequirements().keySet()) {
                    System.out.print(getResourceTypeColor(resourceType) + gameBoard.getCardMarket().getCard(0, i).getProductionRequirements().get(resourceType) +
                            RESOURCE.escape() + ANSI_RESET.escape() + " ");
                }
                showGameBoardCardUtil(results, faithResults, i);
                System.out.print("\t" + color + BOLD_VERTICAL.escape() + ANSI_RESET.escape() + "\t");
            } else {
                showBlankLine(1);
            }
        }
        showCardsUtil(gameBoard, results);
        showCardsUtil(gameBoard, results);
        System.out.print("\n");

        for (int i = 0; i < gameBoard.getCardMarketColumns(); i++) {
            if (gameBoard.getCardMarket().getStack(0, i).getListOfCards().size() > 0) {
                color = getDevelopmentTypeColor(gameBoard.getCardMarket().getCard(0, i).getType());
                System.out.print(color + BOLD_VERTICAL.escape() + ANSI_RESET.escape() + "\t");
                System.out.print(gameBoard.getCardMarket().getCard(0, i).getVictoryPoints());
                System.out.print("\t" + color + BOLD_VERTICAL.escape() + ANSI_RESET.escape() + "\t");
            } else {
                showBlankLine(1);
            }
        }
        System.out.print("\n");

        for (int i = 0; i < gameBoard.getCardMarketColumns(); i++) {
            if (gameBoard.getCardMarket().getStack(0, i).getListOfCards().size() > 0) {
                color = getDevelopmentTypeColor(gameBoard.getCardMarket().getCard(0, i).getType());
                System.out.print(color + DOWN_LEFT.escape());
                for (int j = 0; j < MAX_SPACES; j++)
                    System.out.print(color + BOLD_HORIZ.escape());
                System.out.print(color + DOWN_RIGHT.escape() + "\t" + ANSI_RESET.escape());
            } else {
                showFullLineBottom(1);
            }
        }
        System.out.print("\n");

        //now the fun begins: all other rows need to be printed column by column
        for (int i = 1; i < gameBoard.getCardMarketRow(); i++) {
            for (int j = 0; j < gameBoard.getCardMarketColumns(); j++) {
                if (gameBoard.getCardMarket().getStack(i, j).getListOfCards().size() > 0) {
                    color = getDevelopmentTypeColor(gameBoard.getCardMarket().getCard(i, j).getType());
                    System.out.print(color + UP_LEFT.escape());
                    for (int k = 0; k < MAX_SPACES; k++)
                        System.out.print(color + BOLD_HORIZ.escape());
                    System.out.print(color + UP_RIGHT.escape() + "\t" + ANSI_RESET.escape());
                } else {
                    showFullLineTop(1);
                }
            }
            System.out.print("\n");
            for (int k = 0; k < gameBoard.getCardMarketColumns(); k++) {
                if (gameBoard.getCardMarket().getStack(i, k).getListOfCards().size() > 0) {
                    color = getDevelopmentTypeColor(gameBoard.getCardMarket().getCard(i, k).getType());
                    System.out.print(color + BOLD_VERTICAL.escape() + ANSI_RESET.escape());
                    for (ResourceType resourceType : gameBoard.getCardMarket().getCard(i, k).getCost().keySet()) {
                        System.out.print(getResourceTypeColor(resourceType) + gameBoard.getCardMarket().getCard(i, k).getCost().get(resourceType) + RESOURCE.escape() + ANSI_RESET.escape());
                    }
                    if (gameBoard.getCardMarket().getCard(i, k).getCost().keySet().size() > 1)
                        if (gameBoard.getCardMarket().getCard(i, k).getCost().keySet().size() > 2)
                            System.out.print(color + "\t\t" + BOLD_VERTICAL.escape() + "\t" + ANSI_RESET.escape());
                        else
                            System.out.print(color + "\t\t" + BOLD_VERTICAL.escape() + "\t" + ANSI_RESET.escape());
                    else
                        System.out.print(color + "\t\t" + BOLD_VERTICAL.escape() + "\t" + ANSI_RESET.escape());
                } else {
                    showBlankLine(1);
                }

            }
            System.out.print("\n");
            for (int k = 0; k < gameBoard.getCardMarketColumns(); k++) {
                if (gameBoard.getCardMarket().getStack(i, k).getListOfCards().size() > 0) {
                    color = getDevelopmentTypeColor(gameBoard.getCardMarket().getCard(i, k).getType());
                    System.out.print(color + BOLD_VERTICAL.escape() + ANSI_RESET.escape());
                    for (int j = 0; j < gameBoard.getCardMarket().getCard(i, k).getLevel(); j++) {
                        System.out.print(color + LEVEL.escape() + ANSI_RESET.escape());
                    }
                    System.out.print("\t");
                    for (int j = 0; j < gameBoard.getCardMarket().getCard(i, k).getLevel(); j++) {
                        System.out.print(color + LEVEL.escape() + ANSI_RESET.escape());
                    }
                    System.out.print("\t");
                    System.out.print(color + BOLD_VERTICAL.escape() + ANSI_RESET.escape() + "\t");
                } else {
                    showBlankLine(1);
                }
            }
            System.out.print("\n");
            for (int k = 0; k < gameBoard.getCardMarketColumns(); k++) {
                if (gameBoard.getCardMarket().getStack(i, k).getListOfCards().size() > 0) {
                    color = getDevelopmentTypeColor(gameBoard.getCardMarket().getCard(i, k).getType());
                    System.out.print(color + BOLD_VERTICAL.escape() + "\t\t" + BOLD_VERTICAL.escape() + "\t" + ANSI_RESET.escape());
                } else {
                    showBlankLine(1);
                }
            }
            System.out.print("\n");

            results = new ArrayList<>();
            faithResults = new ArrayList<>();

            for (int j = 0; j < gameBoard.getCardMarketColumns(); j++) {
                //check if a card has faithpoint production
                long count;
                if (gameBoard.getCardMarket().getStack(i, j).getListOfCards().size() > 0) {
                    count = gameBoard.getCardMarket().getCard(i, j).getProductionResults().stream().filter(x -> (x instanceof FaithPoint)).count();
                } else {
                    count = 0;
                }
                faithResults.add((int) count);
                //add the remaining to resource
                HashMap<ResourceType, Integer> map = new HashMap<>();
                if (gameBoard.getCardMarket().getStack(i, j).getListOfCards().size() > 0) {
                    for (Producible p : gameBoard.getCardMarket().getCard(i, j).getProductionResults()) {
                        if (!(p instanceof FaithPoint)) {
                            if (map.containsKey(((Resource) p).getType())) {
                                map.put(((Resource) p).getType(), map.get(((Resource) p).getType()) + 1);
                            } else {
                                map.put(((Resource) p).getType(), 1);
                            }
                        }
                    }

                }
                results.add(map);
            }

            for (int j = 0; j < gameBoard.getCardMarketColumns(); j++) {
                if (gameBoard.getCardMarket().getStack(i, j).getListOfCards().size() > 0) {
                    color = getDevelopmentTypeColor(gameBoard.getCardMarket().getCard(i, j).getType());
                    System.out.print(color + BOLD_VERTICAL.escape() + ANSI_RESET.escape());
                    for (ResourceType resourceType : gameBoard.getCardMarket().getCard(i, j).getProductionRequirements().keySet()) {
                        System.out.print(getResourceTypeColor(resourceType) + gameBoard.getCardMarket().getCard(i, j).getProductionRequirements().get(resourceType) +
                                RESOURCE.escape() + ANSI_RESET.escape() + " ");
                    }
                    showGameBoardCardUtil(results, faithResults, j);
                    if (gameBoard.getCardMarket().getCard(i, j).getProductionRequirements().keySet().size() > 1)
                        System.out.print(color + "\t" + BOLD_VERTICAL.escape() + ANSI_RESET.escape() + "\t");
                    else
                        System.out.print("\t" + color + BOLD_VERTICAL.escape() + ANSI_RESET.escape() + "\t");
                } else {
                    showBlankLine(1);
                }
            }
            showCardsUtil(gameBoard, results, i);
            showCardsUtil(gameBoard, results, i);
            System.out.print("\n");

            for (int j = 0; j < gameBoard.getCardMarketColumns(); j++) {
                if (gameBoard.getCardMarket().getStack(i, j).getListOfCards().size() > 0) {
                    color = getDevelopmentTypeColor(gameBoard.getCardMarket().getCard(i, j).getType());
                    System.out.print(color + BOLD_VERTICAL.escape() + ANSI_RESET.escape() + "\t");
                    System.out.print(gameBoard.getCardMarket().getCard(i, j).getVictoryPoints());
                    System.out.print("\t" + color + BOLD_VERTICAL.escape() + ANSI_RESET.escape() + "\t");
                } else {
                    showBlankLine(1);
                }
            }
            System.out.print("\n");

            for (int j = 0; j < gameBoard.getCardMarketColumns(); j++) {
                if (gameBoard.getCardMarket().getStack(i, j).getListOfCards().size() > 0) {
                    color = getDevelopmentTypeColor(gameBoard.getCardMarket().getCard(i, j).getType());
                    System.out.print(color + DOWN_LEFT.escape());
                    for (int k = 0; k < MAX_SPACES; k++)
                        System.out.print(color + BOLD_HORIZ.escape());
                    System.out.print(color + DOWN_RIGHT.escape() + "\t" + ANSI_RESET.escape());
                } else {
                    showFullLineBottom(1);
                }
            }
            System.out.print("\n");
        }
    }

    public void showGameBoardCardUtil(ArrayList<HashMap<ResourceType, Integer>> results, ArrayList<Integer> faithResults, int j) {

        if (faithResults.get(j) > 0) {
            System.out.print("->");
            System.out.print(ANSI_RED.escape() + faithResults.get(j) + CROSS.escape() + ANSI_RESET.escape());

        } else {
            System.out.print("->");
            for (ResourceType resourceType : results.get(j).keySet()) {
                System.out.print(getResourceTypeColor(resourceType) + results.get(j).get(resourceType) + RESOURCE.escape() + ANSI_RESET.escape());
                results.get(j).remove(resourceType);
                break;
            }
        }
    }

    public void showCardsUtil(ClientGameBoard gameBoard, ArrayList<HashMap<ResourceType, Integer>> results) /*throws NonExistentCardException */ {
        System.out.print("\n");

        for (int i = 0; i < gameBoard.getCardMarketColumns(); i++) {
            if (gameBoard.getCardMarket().getStack(0, i).getListOfCards().size() > 0) {
                String color = getDevelopmentTypeColor(gameBoard.getCardMarket().getCard(0, i).getType());
                System.out.print(color + BOLD_VERTICAL.escape() + ANSI_RESET.escape() + "\t");
                if (results.get(i).keySet().size() > 0) {
                    for (ResourceType resourceType : results.get(i).keySet()) {
                        System.out.print(getResourceTypeColor(resourceType) + results.get(i).get(resourceType) + RESOURCE.escape() + ANSI_RESET.escape());
                        results.get(i).remove(resourceType);
                        break;
                    }

                }
                System.out.print("\t" + color + BOLD_VERTICAL.escape() + ANSI_RESET.escape() + "\t");
            } else {
                showBlankLine(1);
            }
        }
    }

    public void showCardsUtil(ClientGameBoard gameBoard, ArrayList<HashMap<ResourceType, Integer>> results, int i) /*throws NonExistentCardException */ {
        System.out.print("\n");

        for (int j = 0; j < gameBoard.getCardMarketColumns(); j++) {
            if (gameBoard.getCardMarket().getStack(i, j).getListOfCards().size() > 0) {
                String color = getDevelopmentTypeColor(gameBoard.getCardMarket().getCard(i, j).getType());
                System.out.print(color + BOLD_VERTICAL.escape() + ANSI_RESET.escape() + "\t");
                if (results.get(j).keySet().size() > 0) {
                    for (ResourceType resourceType : results.get(j).keySet()) {
                        System.out.print(getResourceTypeColor(resourceType) + results.get(j).get(resourceType) + RESOURCE.escape() + ANSI_RESET.escape() + "\t");
                        results.get(j).remove(resourceType);
                        break;
                    }

                    System.out.print(color + BOLD_VERTICAL.escape() + ANSI_RESET.escape() + "\t");
                } else {
                    System.out.print("\t" + color + BOLD_VERTICAL.escape() + ANSI_RESET.escape() + "\t");

                }
            } else {
                showBlankLine(1);
            }
        }
    }


    public void showMarbleMarketLine(int n, ClientGameBoard gameBoard) {
        System.out.print("\t");
        for (int i = 0; i < gameBoard.getMarbleMarketColumns(); i++) {
            System.out.print(getMarbleTypeColor(gameBoard.getMarket().getMarble(n, i)) + RESOURCE.escape() + ANSI_RESET.escape() + " ");
        }
        System.out.print(LEFT_ARROW.escape());
        if (n == 0) {
            System.out.println(getMarbleTypeColor(gameBoard.getMarket().getFreeMarble()) + RESOURCE.escape() + ANSI_RESET.escape());
        } else
            System.out.print("\n");
    }

    public String getMarbleTypeColor(Marble marble) {
        String color = "";
        switch (marble.getColor()) {
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
                color = GREY.escape();
                break;
            case WHITE:
                color = ANSI_RESET.escape();
                break;
        }
        return color;
    }

    public String getDevelopmentTypeColor(DevelopmentCardType developmentCardType) {
        String color = "";
        switch (developmentCardType) {
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

    /**
     * This method shows the titlescreen
     */
    public void showTitle() {

        System.out.print(ANSI_BLUE.escape() +
                "___  ___          _                         __  ______                  \n" +
                "|  \\/  |         | |                       / _| | ___ \\                O \n" +
                "| .  . | __ _ ___| |_ ___ _ __ ___    ___ | |_  | |_/ /___ _ __   __ _ _ ___ ___  __ _ _ __   ___ ___ \n" +
                "| |\\/| |/ _` / __| __/ _ \\ '__/ __|  / _ \\|  _| |    // _ \\ '_ \\ / _` | / __/ __|/ _` | '_ \\ / __/ _ \\\n" +
                "| |  | | (_| \\__ \\ ||  __/ |  \\__ \\ | (_) | |   | |\\ \\  __/ | | | (_| | \\__ \\__ \\ (_| | | | | (_|  __/\n" +
                "\\_|  |_/\\__,_|___/\\__\\___|_|  |___/  \\___/|_|   \\_| \\_\\___|_| |_|\\__,_|_|___/___/\\__,_|_| |_|\\___\\___|\n" +
                "                                                                                                                  \n" +
                "By Nemanja Antonic, Chiara Buonagurio and René Bwanika" + Formatting.ColorCode.ANSI_RESET.escape());
    }

    /**
     * This method displays an extra deposit
     *
     * @param auxiliaryDeposit: extra deposit to be shown
     */
    public void showExtraDeposit(AuxiliaryDeposit auxiliaryDeposit) {
        System.out.print("Extra Deposit:\n");
        ArrayList<Resource> contents = (ArrayList<Resource>) auxiliaryDeposit.getAuxiliaryDeposit();
        for (Resource resource : contents) {
            System.out.print(getResourceTypeColor(resource.getType()) + RESOURCE.escape() + ANSI_RESET.escape() + " ");
        }
        System.out.print("\n");
    }

}
