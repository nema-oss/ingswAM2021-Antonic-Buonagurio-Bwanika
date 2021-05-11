package it.polimi.ingsw.messages.setup;

import it.polimi.ingsw.model.cards.leadercards.LeaderCard;
import it.polimi.ingsw.view.client.View;
import it.polimi.ingsw.view.server.VirtualView;

import java.io.Serializable;

/**
 * message sent when a client has to choose which leaderCards to keep
 * @author Nemanja Antonic
 */
public class ChooseLeadersMessage implements Serializable, SetupMessage{
    private final SetupMessageType messageType;
    private LeaderCard choice;
    private boolean accepted;

    /**
     * Server-side constructor to create the message
     * @param choice: the target LeaderCard
     * @param accepted: the result of the request
     */
    public ChooseLeadersMessage(LeaderCard choice, boolean accepted) {
        this.choice = choice;
        this.accepted = accepted;
        messageType = SetupMessageType.CHOOSE_LEADERS;
    }
    /**
     * Execute the request client side
     * @param view: receiver view
     */
    public void execute(View view){
        //method in view to show the choice
    }
    /**
     * Execute the request server side
     * @param virtualView: receiver view
     */
    public void execute(VirtualView virtualView){
        //method in virtualView
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
