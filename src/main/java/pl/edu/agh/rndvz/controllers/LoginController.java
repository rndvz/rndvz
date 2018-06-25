package pl.edu.agh.rndvz.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.edu.agh.rndvz.model.User;
import pl.edu.agh.rndvz.model.jsonMappings.Credentials;
import pl.edu.agh.rndvz.persistence.UserRepository;

import java.util.Optional;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RestController
public class LoginController {
    private final UserRepository userRepository;


    @Autowired
    public LoginController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @CrossOrigin(origins = "*")
    @PostMapping(value = "/users/login", consumes = APPLICATION_JSON_VALUE)
    public @ResponseBody ResponseEntity authenticate(@RequestBody Credentials credentials) {
        Optional<User> optionalUser = userRepository.findUserByLogin(credentials.getLogin());
        return optionalUser
                .map(user -> toAuthenticationResponse(user, credentials))
                .orElseGet(Utils::noUserFoundforGivenLogin);
    }

    private ResponseEntity toAuthenticationResponse(User user, Credentials credentials) {
        if (user.getPassword().equals(credentials.getPassword()))
            return ResponseEntity.ok(Utils.toJson(true));
        else return ResponseEntity.ok(Utils.toJson(false));


    }


}
