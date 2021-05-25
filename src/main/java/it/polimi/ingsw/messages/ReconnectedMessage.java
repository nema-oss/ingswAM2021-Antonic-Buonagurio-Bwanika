package it.polimi.ingsw.messages;

import it.polimi.ingsw.view.client.View;
import it.polimi.ingsw.view.server.VirtualView;

public class ReconnectedMessage implements Message {

    private final String user;

    public ReconnectedMessage(String user){
        this.user = user;
    }
    @Override
    public void execute(VirtualView virtualView) {

    }

    @Override
    public void execute(View view) {
        view.showReconnectionToMatch();
        view.showLoginDone(user);
    }
}
