package it.polimi.ingsw.messages;

//TODO == each Message should implement this class and Serializable and should contains just the minimum amount of info
// to complete the required action
/**
 * this interface represent the message sent between client and server
 */
public interface Message {
    MessageType getType();
}
