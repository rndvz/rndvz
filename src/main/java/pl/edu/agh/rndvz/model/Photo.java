package pl.edu.agh.rndvz.model;

import org.neo4j.ogm.annotation.GeneratedValue;
import org.neo4j.ogm.annotation.Id;
import org.neo4j.ogm.annotation.NodeEntity;

@NodeEntity
public class Photo {
    @Id
    @GeneratedValue
    private Long id;

    private String name;
    private String photo;


    public Photo() {
    }

    public Photo(String name,String photo) {
        this.name =name;
        this.photo = photo;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }


    public Long getId() {

        return id;
    }

    public String getPhoto() {

        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }
}
