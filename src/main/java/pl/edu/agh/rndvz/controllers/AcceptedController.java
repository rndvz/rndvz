package pl.edu.agh.rndvz.controllers;

import pl.edu.agh.rndvz.model.Relation;
import pl.edu.agh.rndvz.model.User;
import pl.edu.agh.rndvz.persistence.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RestController
public class AcceptedController {

    @Autowired
    UserRepository userRepository;


//    @RequestMapping(value = "/accepted")
//    public User greeting() {
//        User u1 = userRepository.findUserByFirstName("Teiiidst");
//        User u2 = userRepository.findUserByFirstName("T");
//
//        u1.getAcceptedByMe().add(u2);
//        userRepository.save(u1);
//        return u1;
//
//    }

    @PostMapping(value = "/accept", consumes = APPLICATION_JSON_VALUE)
    public ResponseEntity accept(@RequestBody Relation rel) {


        Optional<User> startUserOptional = userRepository.findById(rel.getFrom());
        Optional<User> endUserOptional = userRepository.findById(rel.getTo());

        try {
            User startUser = startUserOptional.orElseThrow(IllegalArgumentException::new);
            User endUser = endUserOptional.orElseThrow(IllegalArgumentException::new);
            boolean isMatched =acceptUser(startUser, endUser);
            return new ResponseEntity<>(isMatched, HttpStatus.OK);

        } catch (IllegalArgumentException ex) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("No user with given id");
        }

    }

    private boolean acceptUser(User startUser, User endUser) {
        boolean isMatched = false;
        if (startUser.isNotBlockedFor(endUser)) {
            startUser.getAcceptedByMe().add(endUser);
            userRepository.save(startUser);
            endUser.increaseRate(startUser.getAvgRate());
            userRepository.save(endUser);
            if (startUser.canMatchWith(endUser)) {
                startUser.getMatched().add(endUser);
                endUser.getMatched().add(startUser);
                isMatched =true;

            }
            userRepository.save(endUser);
            userRepository.save(startUser);

        }
        return isMatched;
    }
}
