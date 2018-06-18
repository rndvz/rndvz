package pl.edu.agh.rndvz.model;

import org.neo4j.ogm.annotation.GeneratedValue;
import org.neo4j.ogm.annotation.Id;
import org.neo4j.ogm.annotation.NodeEntity;
import org.neo4j.ogm.annotation.Relationship;

import java.util.HashSet;
import java.util.Set;

@NodeEntity
public class Chat {
    private static final String CONTAINS = "contains";
    private static final String STARTS_AT = "startsAt";
    private static final String ENDS_AT = "endsAt";


    @Id
    @GeneratedValue
    private Long id;

    @Relationship(type = CONTAINS)
    private Set<User> speakers = new HashSet<>();

    @Relationship(type = STARTS_AT)
    private TextMessage firstMessage;

    @Relationship(type = ENDS_AT)
    private TextMessage lastMessage;

    public Chat() {
    }

    public Chat(TextMessage firstMessage, TextMessage lastMessage) {
        this.firstMessage = firstMessage;
        this.lastMessage = lastMessage;
    }

    public Long getId() {
        return id;
    }

    public Set<User> getSpeakers() {
        return speakers;
    }

    public void addSpeaker(User speaker) {
        this.speakers.add(speaker);
    }

    public TextMessage getFirstMessage() {
        return firstMessage;
    }

    public void setFirstMessage(TextMessage firstMessage) {
        this.firstMessage = firstMessage;
    }

    public TextMessage getLastMessage() {
        return lastMessage;
    }

    public void setLastMessage(TextMessage lastMessage) {
        this.lastMessage = lastMessage;
    }
}