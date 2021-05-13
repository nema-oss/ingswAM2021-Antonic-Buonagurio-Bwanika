package it.polimi.ingsw.messages.setup.server;

import it.polimi.ingsw.messages.setup.SetupMessage;
import it.polimi.ingsw.view.client.View;
import it.polimi.ingsw.view.server.VirtualView;

import java.io.Serializable;

public class LoginRefusedMessage implements SetupMessage, Serializable {

    private final boolean isFirstPlayer;

    public LoginRefusedMessage(boolean isFirstPlayer){
        this.isFirstPlayer = isFirstPlayer;
    }

    @Override
    public void execute(VirtualView virtualView) {
    }

    /**
     * This method execute the login failed scene on view
     * @param view the view
     */
    @Override
    public void execute(View view) {
        view.showLoginFailed(isFirstPlayer);
    }
}
