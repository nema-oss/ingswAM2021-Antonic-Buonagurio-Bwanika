package it.polimi.ingsw.messagges;

import it.polimi.ingsw.view.client.View;
import it.polimi.ingsw.view.server.VirtualView;

import java.net.Socket;

/**
 * this class manage the sending of messages from server to client, the parsing of client's request and the creation of
 * new messages
 */

public class MessageManager {


    public void parseMessage(VirtualView virtualView, Message message) {
    }

    public void parseMessageFromServer(Socket server, Message message, View view) {
    }
}
