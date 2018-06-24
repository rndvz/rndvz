package pl.edu.agh.rndvz.model.jsonMappings;

import pl.edu.agh.rndvz.model.TextMessage;

import java.util.List;

public class MessageList {
    private List<TextMessage> messages;

    public MessageList() {
    }

    public MessageList(List<TextMessage> messages) {
        this.messages = messages;
    }

    public List<TextMessage> getMessages() {
        return messages;
    }

    public void setMessages(List<TextMessage> messages) {
        this.messages = messages;
    }
}
