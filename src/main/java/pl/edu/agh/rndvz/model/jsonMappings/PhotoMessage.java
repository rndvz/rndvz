package pl.edu.agh.rndvz.model.jsonMappings;

public class PhotoMessage {
    private String name;
    private String photo;


    public PhotoMessage() {
    }

    public PhotoMessage(String photo) {

        this.photo = photo;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }


    public String getPhoto() {

        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }
}
