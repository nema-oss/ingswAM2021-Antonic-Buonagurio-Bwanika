package it.polimi.ingsw.messages.setup.server;

import it.polimi.ingsw.messages.setup.SetupMessage;
import it.polimi.ingsw.messages.setup.SetupMessageType;
import it.polimi.ingsw.model.cards.leadercards.LeaderCard;
import it.polimi.ingsw.view.client.View;
import it.polimi.ingsw.view.server.VirtualView;

import java.io.Serializable;
import java.util.List;


/**
 * message sent when a client has to choose which leaderCards to keep
 * @author Nemanja Antonic
 */
public class ChooseLeadersMessage implements Serializable, SetupMessage {

    private final SetupMessageType messageType;
    private List<LeaderCard> choice;
    private String user;
    private boolean accepted;


    /**
     * Server-side constructor to create the message
     * @param choice: the target LeaderCard
     * @param accepted: the result of the request
     */
    public ChooseLeadersMessage(String user, List<LeaderCard> choice, boolean accepted) {
        this.choice = choice;
        this.user = user;
        this.accepted = accepted;
        messageType = SetupMessageType.CHOOSE_LEADERS;
    }
    /**
     * Execute the request client side
     * @param view: receiver view
     */
    public void execute(View view){
        if(isAccepted())
            view.showLeaderCardsSelectionAccepted(choice);
        else
            view.setLeaderCardChoice(choice);
    }
    /**
     * Execute the request server side
     * @param virtualView: receiver view
     */
    public void execute(VirtualView virtualView){
        virtualView.chooseLeaderCards(user,choice);
    }

    /**
     * Get the message type
     * @return the message type
     */
    public SetupMessageType getType() {
        return messageType;
    }
    /**
     * Get the accepted attribute
     * @return accepted
     */
    public boolean isAccepted() {
        return accepted;
    }
}
