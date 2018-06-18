package pl.edu.agh.rndvz.model.jsonMappings;

public class ChatMessage {

    private int messagesToReturn;
    private Long from;
    private Long to;

    public ChatMessage() {
    }

    public ChatMessage(int messagesToReturn, Long from, Long to) {
        this.messagesToReturn = messagesToReturn;
        this.from = from;
        this.to = to;
    }

    public int getMessagesToReturn() {
        return messagesToReturn;
    }

    public void setMessagesToReturn(int messagesToReturn) {
        this.messagesToReturn = messagesToReturn;
    }

    public Long getFrom() {
        return from;
    }

    public void setFrom(Long from) {
        this.from = from;
    }

    public Long getTo() {
        return to;
    }

    public void setTo(Long to) {
        this.to = to;
    }


}
