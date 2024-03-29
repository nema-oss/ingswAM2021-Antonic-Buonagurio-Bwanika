package it.polimi.ingsw.messages.utils;

import it.polimi.ingsw.messages.Message;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.Socket;

/**
 * This class send message on the socket
 */
public class MessageSender {

    private Socket socket;
    private Message messageOutput;

    public MessageSender(Socket client, Message message) {
        this.socket = client;
        this.messageOutput = message;
    }

    /**
     * This method send a message to the socket. It return false/true if the operation is
     * successful or not
     * @param outputStream the socket output
     * @return true if the message has been sent successfully
     */
    public synchronized boolean sendMsg(ObjectOutputStream outputStream) {

        if(!socket.isClosed()){
            try{
                outputStream.writeObject( messageOutput);
                outputStream.flush();
                outputStream.reset();
                return true;
            }catch (IOException e){
                System.out.println("Can't send message on socket");
                try {
                    socket.close();
                } catch (IOException ioException) {
                    ioException.printStackTrace();
                }
            }
        }
        return false;
    }
}
