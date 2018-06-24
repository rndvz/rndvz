package pl.edu.agh.rndvz.controllers;

import org.springframework.web.bind.annotation.CrossOrigin;
import pl.edu.agh.rndvz.model.jsonMappings.Relation;
import pl.edu.agh.rndvz.model.User;
import pl.edu.agh.rndvz.persistence.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RestController
public class BlockedController {
    private final UserRepository userRepository;

    @Autowired
    public BlockedController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @CrossOrigin(origins = "*")
    @PostMapping(value = "/block", consumes = APPLICATION_JSON_VALUE)
    public ResponseEntity block(@RequestBody Relation rel) {

        Optional<User> startUserOptional = userRepository.findById(rel.getFrom());
        Optional<User> endUserOptional = userRepository.findById(rel.getTo());

        try {
            User startUser = startUserOptional.orElseThrow(IllegalArgumentException::new);
            User endUser = endUserOptional.orElseThrow(IllegalArgumentException::new);

            endUser.decreaseRate(startUser.getAvgRate());

            blockUsers(startUser, endUser);

            return ResponseEntity.ok(startUser);

        } catch (IllegalArgumentException ex) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("No user with given id");
        }
    }

    private void blockUsers(User startUser, User endUser) {
        startUser.getBlocked().add(endUser);
        endUser.getBlocked().add(startUser);

        userRepository.save(startUser);
        userRepository.save(endUser);
    }

}
