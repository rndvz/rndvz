package pl.edu.agh.rndvz.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.neo4j.ogm.annotation.*;
import org.neo4j.ogm.annotation.typeconversion.DateString;

import static org.neo4j.ogm.annotation.Relationship.INCOMING;
import static org.neo4j.ogm.annotation.Relationship.OUTGOING;
import static org.neo4j.ogm.annotation.Relationship.UNDIRECTED;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@NodeEntity
public class User {
    @Id
    @GeneratedValue
    private Long id;

    private String firstName;

    @DateString("yyyy-MM-dd")
    private Date birthDate;
    private String description;
    //    private coordinates
    private String sex; // chenge on enum
    private String sexPreference; // change on enum
    private double avgRate;
    private int acceptedVariation;

    @JsonIgnore
    @Relationship(type = "accepted", direction = INCOMING)
    private Set<User> acceptedMe = new HashSet<>();

    @JsonIgnore
    @Relationship(type = "accepted", direction = OUTGOING)
    private Set<User> acceptedByMe = new HashSet<>();

    @JsonIgnore
    @Relationship(type = "matched", direction = OUTGOING)
    private Set<User> matched = new HashSet<>();

    @JsonIgnore
    @Relationship(type = "blocked", direction = OUTGOING)
    private Set<User> blocked = new HashSet<>();


    public User() {
    }


    public User(String firstName, Date birthDate, String description, String sex, String sexPreference, double avgRate, int acceptedVariation) {
        this.firstName = firstName;
        this.birthDate = birthDate;
        this.description = description;
        this.sex = sex;
        this.sexPreference = sexPreference;
        this.avgRate = avgRate;
        this.acceptedVariation = acceptedVariation;
    }

    public Long getId() {

        return id;
    }

    public Set<User> getAcceptedMe() {
        return acceptedMe;
    }

    public Set<User> getAcceptedByMe() {
        return acceptedByMe;
    }

    public Set<User> getMatched() {
        return matched;
    }

    public Set<User> getBlocked() {
        return blocked;
    }

    public String getFirstName() {


        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public Date getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(Date birthDate) {
        this.birthDate = birthDate;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getSexPreference() {
        return sexPreference;
    }

    public void setSexPreference(String sexPreference) {
        this.sexPreference = sexPreference;
    }

    public double getAvgRate() {
        return avgRate;
    }

    public void setAvgRate(double avgRate) {
        this.avgRate = avgRate;
    }

    public int getAcceptedVariation() {
        return acceptedVariation;
    }

    public void setAcceptedVariation(int acceptedVariation) {
        this.acceptedVariation = acceptedVariation;
    }

    public boolean isBlockedFor(User endUser) {
        return blocked.contains(endUser);
    }

    public boolean isNotBlockedFor(User endUser) {
        return !blocked.contains(endUser);
    }

    public boolean canMatchWith(User endUser) {
        return acceptedMe.contains(endUser) && acceptedByMe.contains(endUser);
    }

    public void increaseRate(double otherRate) {
        if(otherRate > avgRate)
            avgRate+= (otherRate-avgRate)/10;
        else avgRate+= (otherRate-avgRate)/20;
    }

    public void decreaseRate(double otherRate) {
        if(otherRate > avgRate)
            avgRate-= (otherRate-avgRate)/10;
        else avgRate-= (otherRate-avgRate)/20;
    }
}
