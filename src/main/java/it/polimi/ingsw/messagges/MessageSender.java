package it.polimi.ingsw.messagges;

import java.net.Socket;

public class MessageSender {

    private Socket socket;
    private MessageType messageType;

    public MessageSender(Socket client, MessageType messageType) {
        this.socket = client;
        this.messageType = messageType;
    }


    /**
     * This method send a message of type messageType to the socket. It return false/true if the operation is
     * successful or not
     */
    public boolean sendMsg() {
        return false;
    }
}
