package pl.edu.agh.rndvz.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import pl.edu.agh.rndvz.model.User;
import pl.edu.agh.rndvz.persistence.UserRepository;

import java.util.List;

@RestController
public class PairGeneratorController {
    @Autowired
    UserRepository userRepository;

    @RequestMapping(value = "/users/{id}/next", method = RequestMethod.GET)
    public ResponseEntity getNext(@PathVariable Long id) {
        List<User> users = userRepository.getNextPossiblePairs(id, 2);
        if (users.get(0).getId().equals(id)) {
            if (users.size() == 2) {
                return new ResponseEntity<>(users.get(1), HttpStatus.OK);
            } else {
                return ResponseEntity.status(HttpStatus.NO_CONTENT).body("No more users to display");
            }
        }else return new ResponseEntity<>(users.get(0), HttpStatus.OK);


    }

}
