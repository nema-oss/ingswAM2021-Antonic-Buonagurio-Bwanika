package it.polimi.ingsw.messages;

/**
 * This class manage the creation of different type of messages
 */
public class MessageWriter{

    private MessageType messageType;
    private Message messageOut;

    public MessageWriter(MessageType messageType) {
        this.messageType = messageType;
        createMessage(messageType);
    }

    /**
     * This method create a Message of type
     * @param messageType
     */
    private void createMessage(MessageType messageType) {

    }

    public Message getMessage() {
        return messageOut;
    }
}
