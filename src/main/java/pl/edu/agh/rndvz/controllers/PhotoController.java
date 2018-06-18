package pl.edu.agh.rndvz.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.edu.agh.rndvz.model.*;
import pl.edu.agh.rndvz.model.jsonMappings.PhotoMessage;
import pl.edu.agh.rndvz.persistence.UserRepository;


@RestController
public class PhotoController {
    @Autowired
    UserRepository userRepository;

    @RequestMapping(value = "/users/{id}/upload", method = RequestMethod.POST)
    public @ResponseBody
    ResponseEntity getFile(@PathVariable Long id, @RequestBody PhotoMessage photoMessage) {
        try {
            User user = userRepository.findById(id).orElseThrow(IllegalArgumentException::new);
            Photo photo = new Photo(photoMessage.getName(), photoMessage.getPhoto());
            user.getPhotos().add(photo);
            userRepository.save(user);
            return ResponseEntity.ok("uploaded");
        } catch (IllegalArgumentException ex) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("No user with given id");
        }

    }


}
