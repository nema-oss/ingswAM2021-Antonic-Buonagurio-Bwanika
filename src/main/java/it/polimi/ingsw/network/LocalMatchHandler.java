package it.polimi.ingsw.network;


import it.polimi.ingsw.controller.MatchController;
import it.polimi.ingsw.messages.Message;
import it.polimi.ingsw.view.client.View;
import it.polimi.ingsw.view.server.VirtualView;

/**
 * Handle a local match by executing locally the messages
 */
public class LocalMatchHandler {

    private VirtualView virtualView;
    private final View view;

    public LocalMatchHandler(View view) {
        this.view = view;
        createLocalMatch();
    }

    private void createLocalMatch(){
        virtualView = new VirtualView(new MatchController(), 1, new FakeServer());
        virtualView.setLocalMatch(true, this);
    }

    public void executeMessageFromClient(Message message){
        System.out.println(message);
        message.execute(virtualView);
    }

    public void executeMessageFromServer(Message message){
        System.out.println(message);
        message.execute(view);
    }

}
