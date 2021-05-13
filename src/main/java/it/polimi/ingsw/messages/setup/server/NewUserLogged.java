package it.polimi.ingsw.messages.setup.server;

import it.polimi.ingsw.messages.setup.SetupMessage;
import it.polimi.ingsw.view.client.View;
import it.polimi.ingsw.view.server.VirtualView;

import java.io.Serializable;

/**
 * Message sent to the other clients when a new player joins the match
 */
public class NewUserLogged implements SetupMessage, Serializable {


    private final String nickname;

    public NewUserLogged(String nickname){
        this.nickname = nickname;
    }


    /**
     * This method execute the message on the virtual view
     * @param virtualView the virtual view
     */
    @Override
    public void execute(VirtualView virtualView) {

    }

    /**
     * This method execute the message on the view
     * @param view the user view
     */
    @Override
    public void execute(View view) {
        view.showNewUserLogged(nickname);
    }
}
