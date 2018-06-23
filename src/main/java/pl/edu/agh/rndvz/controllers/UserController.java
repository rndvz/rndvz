package pl.edu.agh.rndvz.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.edu.agh.rndvz.model.User;
import pl.edu.agh.rndvz.persistence.UserRepository;

import java.util.Optional;

@RestController
public class UserController {


    private final UserRepository userRepository;

    @Autowired
    public UserController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @CrossOrigin(origins = "http://localhost:4200")
    @GetMapping(value = "/users/search/findByLogin/{login}")
    ResponseEntity findByLogin(@PathVariable String login) {
        Optional<User> user = userRepository.findUserByLogin(login);

        return user.map(Utils::wrapWithResponseEntity)
                .orElseGet(Utils::noUserFoundforGivenLogin);
    }


    @CrossOrigin(origins = "http://localhost:4200")
    @GetMapping(value = "/users/search/getIDbyLogin/{login}")
    ResponseEntity getIDbyLogin(@PathVariable String login) {
        Optional<User> user = userRepository.findUserByLogin(login);

        return user.map(User::getId)
                .map(Utils::wrapWithResponseEntity)
                .orElseGet(Utils::noUserFoundforGivenLogin);
    }


}