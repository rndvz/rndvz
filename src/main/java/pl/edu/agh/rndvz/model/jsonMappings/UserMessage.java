package pl.edu.agh.rndvz.model.jsonMappings;

public class UserMessage {
    private String text;
    private ChatMessage chatMessage;

    public UserMessage() {
    }

    public UserMessage(String text, ChatMessage chatMessage) {
        this.text = text;
        this.chatMessage = chatMessage;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public ChatMessage getChatMessage() {
        return chatMessage;
    }

    public void setChatMessage(ChatMessage chatMessage) {
        this.chatMessage = chatMessage;
    }

    public Long getFrom() {
        return chatMessage.getFrom();
    }


    public Long getTo() {
        return chatMessage.getTo();
    }

    public int getMessagesToReturn() {
        return chatMessage.getMessagesToReturn();
    }

}
