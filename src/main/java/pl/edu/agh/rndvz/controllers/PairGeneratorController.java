package pl.edu.agh.rndvz.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.edu.agh.rndvz.model.User;
import pl.edu.agh.rndvz.persistence.UserRepository;

import java.text.SimpleDateFormat;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

@RestController
public class PairGeneratorController {
    private final UserRepository userRepository;

    @Autowired
    public PairGeneratorController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @CrossOrigin(origins = "http://localhost:4200")
    @RequestMapping(value = "/users/{id}/next/{howMany}", method = RequestMethod.GET)
    public ResponseEntity getNext(@PathVariable Long id, @PathVariable Integer howMany) {
        Optional<User> optUser = userRepository.findById(id);
        User user;
        List<User> users;
        if (optUser.isPresent()) {

            user = optUser.get();
            SimpleDateFormat dt = new SimpleDateFormat("yyyy-MM-dd");
            String birthDate = dt.format(user.getBirthDate());
            users = userRepository.getPossiblePairs(id,
                    howMany,
                    user.getAvgRate(),
                    user.getAcceptedRateDifference(),
                    user.getSex(),
                    user.getSexPreference(),
                    birthDate,
                    user.getAcceptedYearDifference(),
                    user.getLatitude(),
                    user.getLongitude(),
                    user.getAcceptedDistance());
        } else
            users = new LinkedList<>();


        return new ResponseEntity<>(users, HttpStatus.OK);
    }

}
