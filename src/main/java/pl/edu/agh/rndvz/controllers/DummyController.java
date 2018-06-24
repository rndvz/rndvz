package pl.edu.agh.rndvz.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import pl.edu.agh.rndvz.model.User;
import pl.edu.agh.rndvz.model.jsonMappings.Relation;
import pl.edu.agh.rndvz.persistence.ChatRepository;
import pl.edu.agh.rndvz.persistence.UserRepository;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;
import static org.springframework.http.MediaType.TEXT_PLAIN_VALUE;

@RestController

public class DummyController {
    private final UserRepository userRepository;

    @Autowired
    public DummyController(UserRepository userRepository) {
        this.userRepository = userRepository;

    }


    @CrossOrigin(origins = "*")
    @PostMapping(value = "/dummy", consumes = TEXT_PLAIN_VALUE)
    public ResponseEntity accept(@RequestBody String login) {
        User user = new User();
        user.setLogin(login);
        userRepository.save(user);
        return ResponseEntity.ok(user);
    }

}
