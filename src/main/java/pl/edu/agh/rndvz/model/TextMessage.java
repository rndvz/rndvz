package pl.edu.agh.rndvz.model;

import org.neo4j.ogm.annotation.GeneratedValue;
import org.neo4j.ogm.annotation.Id;
import org.neo4j.ogm.annotation.NodeEntity;
import org.neo4j.ogm.annotation.Relationship;
import pl.edu.agh.rndvz.model.jsonMappings.UserMessage;

@NodeEntity
public class TextMessage {
    private static final String FOLLOWS = "follows";
    private static final String PRECEDS = "precedes";
    private static final String SENDS = "sends";
    private static final String RECEIVES = "receives";


    @Id
    @GeneratedValue
    private Long id;

    private String text;


    @Relationship(type = FOLLOWS)
    private TextMessage previousMessage;

    @Relationship(type = PRECEDS)
    private TextMessage nextMessage;

    @Relationship(type = SENDS)
    private User sender;

    @Relationship(type = RECEIVES)
    private User receiver;

    public TextMessage() {
    }

    public TextMessage(String text, TextMessage previousMessage, User sender, User receiver) {
        this(text, previousMessage, null, sender, receiver);
    }

    public TextMessage(String text, TextMessage previousMessage, TextMessage nextMessage, User sender, User receiver) {
        this.text = text;
        this.previousMessage = previousMessage;
        this.nextMessage = nextMessage;
        this.sender = sender;
        this.receiver = receiver;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }


    public TextMessage getPreviousMessage() {
        return previousMessage;
    }

    public void setPreviousMessage(TextMessage previousMessage) {
        this.previousMessage = previousMessage;
    }

    public TextMessage getNextMessage() {
        return nextMessage;
    }

    public void setNextMessage(TextMessage nextMessage) {
        this.nextMessage = nextMessage;
    }

    public User getSender() {
        return sender;
    }

    public void setSender(User sender) {
        this.sender = sender;
    }

    public User getReceiver() {
        return receiver;
    }

    public void setReceiver(User receiver) {
        this.receiver = receiver;
    }

    public Long getId() {
        return id;
    }

    // tworzenie chatu jak jest match
    // get n wiadomości
    // dodaj wiadomość -od do tekst
}
