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

    @RequestMapping(value = "/users/{id}/next/{howMany}", method = RequestMethod.GET)
    public ResponseEntity getNext(@PathVariable Long id, @PathVariable Integer howMany) {
        List<User> users = userRepository.getPossiblePairs(id, howMany);

        return new ResponseEntity<>(users, HttpStatus.OK);
    }

}
