package pl.edu.agh.rndvz.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.neo4j.ogm.annotation.*;
import org.neo4j.ogm.annotation.typeconversion.DateString;

import static org.neo4j.ogm.annotation.Relationship.INCOMING;
import static org.neo4j.ogm.annotation.Relationship.OUTGOING;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@NodeEntity
public class User {
    @Id
    @GeneratedValue
    private Long id;

    private String login;

    @DateString("yyyy-MM-dd")
    private Date birthDate;

    private String description;

    private String sex;

    private String sexPreference;

    private double avgRate;

    private int acceptedRateDifference;

    private int acceptedYearDifference;

    private int acceptedDistance;

    private double latitude;

    private double longitude;

    @JsonIgnore
    @Relationship(type = "accepted", direction = INCOMING)
    private Set<User> acceptedMe = new HashSet<>();

    @Relationship(type = "has_photo")
    private Set<Photo> photos = new HashSet<>();

    @JsonIgnore
    @Relationship(type = "accepted")
    private Set<User> acceptedByMe = new HashSet<>();

    @JsonIgnore
    @Relationship(type = "matched")
    private Set<User> matched = new HashSet<>();

    @JsonIgnore
    @Relationship(type = "blocked")
    private Set<User> blocked = new HashSet<>();


    public User() {
    }


    public User(String login,
                Date birthDate,
                String description,
                String sex,
                String sexPreference,
                double avgRate,
                int acceptedRateDifference,
                int acceptedYearDifference,
                int acceptedDistance,
                double latitude,
                double longitude
    ) {
        this.login = login;
        this.birthDate = birthDate;
        this.description = description;
        this.sex = sex;
        this.sexPreference = sexPreference;
        this.avgRate = avgRate;
        this.acceptedRateDifference = acceptedRateDifference;
        this.acceptedYearDifference = acceptedYearDifference;
        this.acceptedDistance = acceptedDistance;
        this.latitude = latitude;
        this.longitude = longitude;

    }

    public Long getId() {

        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
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

    public Set<Photo> getPhotos() {
        return photos;
    }

    public void setPhotos(Set<Photo> photos) {
        this.photos = photos;
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

    public int getAcceptedRateDifference() {
        return acceptedRateDifference;
    }

    public void setAcceptedRateDifference(int acceptedRateDifference) {
        this.acceptedRateDifference = acceptedRateDifference;
    }

    public int getAcceptedYearDifference() {
        return acceptedYearDifference;
    }

    public void setAcceptedYearDifference(int acceptedYearDifference) {
        this.acceptedYearDifference = acceptedYearDifference;
    }


    public int getAcceptedDistance() {
        return acceptedDistance;
    }

    public void setAcceptedDistance(int acceptedDistance) {
        this.acceptedDistance = acceptedDistance;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
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
        if (otherRate > avgRate)
            avgRate += otherRate / 10;
        else avgRate += otherRate / 20;
    }

    public void decreaseRate(double otherRate) {
        if (otherRate > avgRate)
            avgRate -= otherRate / 10;
        else avgRate -= otherRate / 20;
    }
}
