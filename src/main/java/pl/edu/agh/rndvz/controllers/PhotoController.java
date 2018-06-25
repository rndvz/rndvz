package pl.edu.agh.rndvz.controllers;

import com.fasterxml.jackson.annotation.JsonMerge;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.edu.agh.rndvz.model.*;
import pl.edu.agh.rndvz.model.jsonMappings.JsonMessage;
import pl.edu.agh.rndvz.model.jsonMappings.PhotoMessage;
import pl.edu.agh.rndvz.persistence.UserRepository;

import static pl.edu.agh.rndvz.controllers.Utils.noUserFoundforGivenID;


@RestController
public class PhotoController {
    private final UserRepository userRepository;

    @Autowired
    public PhotoController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @CrossOrigin(origins = "*")
    @RequestMapping(value = "/users/{id}/upload", method = RequestMethod.POST)
    public @ResponseBody
    ResponseEntity getFile(@PathVariable Long id, @RequestBody PhotoMessage photoMessage) {
        try {
            User user = userRepository.findById(id).orElseThrow(IllegalArgumentException::new);
            Photo photo = new Photo(photoMessage.getName(), photoMessage.getPhoto());
            user.getPhotos().add(photo);
            userRepository.save(user);
            return ResponseEntity.ok(new JsonMessage("uploaded"));
        } catch (IllegalArgumentException ex) {
            return noUserFoundforGivenID();
        }

    }


}
