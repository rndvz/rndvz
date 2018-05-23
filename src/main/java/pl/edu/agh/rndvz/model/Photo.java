package pl.edu.agh.rndvz.model;

import org.neo4j.ogm.annotation.GeneratedValue;
import org.neo4j.ogm.annotation.Id;
import org.neo4j.ogm.annotation.NodeEntity;

@NodeEntity
public class Photo {
    @Id
    @GeneratedValue
    private Long id;

    private String photoPath;

    public Photo() {
    }

    public Photo(String photoPath) {

        this.photoPath = photoPath;
    }

    public Long getId() {

        return id;
    }

    public String getPhotoPath() {

        return photoPath;
    }

    public void setPhotoPath(String photoPath) {
        this.photoPath = photoPath;
    }
}
