package it.polimi.ingsw.messages;

//TODO == each Message should implement this class and Serializable and should contains just the minimum amount of info
// to complete the required action

import it.polimi.ingsw.view.client.View;
import it.polimi.ingsw.view.server.VirtualView;

/**
 * this interface represent the message sent between client and server
 */
public interface Message {

    void execute(VirtualView virtualView);
    void execute(View view);

}
