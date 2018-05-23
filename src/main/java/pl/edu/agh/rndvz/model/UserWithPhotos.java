package pl.edu.agh.rndvz.model;

import java.util.List;

public class UserWithPhotos {

    private User user;

    private List<String> photosList;

    public UserWithPhotos() {
    }

    public UserWithPhotos(User user, List<String> photosList) {
        this.user = user;
        this.photosList = photosList;
    }

    public User getUser() {
        return user;
    }

    public List<String> getPhotosList() {
        return photosList;
    }
}
