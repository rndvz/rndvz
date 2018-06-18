package pl.edu.agh.rndvz.model;

import org.neo4j.ogm.annotation.GeneratedValue;
import org.neo4j.ogm.annotation.Id;
import org.neo4j.ogm.annotation.NodeEntity;
import org.neo4j.ogm.annotation.Relationship;

@NodeEntity
public class TextMessage {
    private static final String FOLLOWS = "follows";
    private static final String PRECEDS = "precedes";

    @Id
    @GeneratedValue
    private Long id;


    @Relationship(type = FOLLOWS)
    private TextMessage previousMessage;

    @Relationship(type = PRECEDS)
    private TextMessage nextMessage;

    public TextMessage() {
    }

    public TextMessage(TextMessage previousMessage, TextMessage nextMessage) {
        this.previousMessage = previousMessage;
        this.nextMessage = nextMessage;
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

    public Long getId() {
        return id;
    }

    // tworzenie chatu jak jest match
    // get n wiadomości
    // dodaj wiadomość -od do tekst
}
