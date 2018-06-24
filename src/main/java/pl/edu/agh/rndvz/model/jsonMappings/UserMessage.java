package pl.edu.agh.rndvz.model.jsonMappings;

public class UserMessage {
    private String text;
    private Long from;
    private Long to;

    public UserMessage() {
    }


    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
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
