package it.polimi.ingsw.messages.setup.server;

import it.polimi.ingsw.messages.setup.SetupMessage;
import it.polimi.ingsw.view.client.View;
import it.polimi.ingsw.view.server.VirtualView;

import java.io.Serializable;

/**
 * message sent after the server checks if the login is correct
 * @author Nemanja Antonic
 */
public class LoginDoneMessage implements Serializable, SetupMessage {
    private final String user;
    boolean accepted;

    /**
     * Server-side constructor to create the message
     * @param accepted: result of the request
     */
    public LoginDoneMessage(String user, boolean accepted) {
        this.user = user;
        this.accepted = accepted;
    }

    @Override
    public void execute(VirtualView virtualView) {
    }

    /**
     * Execute the request client side
     * @param view: receiver view
     */
    public void execute(View view){
        view.showLoginDone(user);
    }


    /**
     * Get the accepted attribute
     * @return accepted
     */
    public boolean isAccepted() {
        return accepted;
    }

    public String getUser() {
        return user;
    }
}
