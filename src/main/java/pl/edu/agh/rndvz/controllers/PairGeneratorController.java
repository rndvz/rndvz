package pl.edu.agh.rndvz.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.edu.agh.rndvz.model.User;
import pl.edu.agh.rndvz.model.jsonMappings.UserList;
import pl.edu.agh.rndvz.persistence.UserRepository;

import java.text.SimpleDateFormat;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

import static pl.edu.agh.rndvz.controllers.Utils.noUserFoundforGivenID;
import static pl.edu.agh.rndvz.controllers.Utils.toJsonUserList;
import static pl.edu.agh.rndvz.controllers.Utils.wrapWithResponseEntity;

@RestController
public class PairGeneratorController {
    private final UserRepository userRepository;

    @Autowired
    public PairGeneratorController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @CrossOrigin(origins = "*")
    @RequestMapping(value = "/users/{id}/next/{howMany}", method = RequestMethod.GET)
    public ResponseEntity getNext(@PathVariable Long id, @PathVariable Integer howMany) {
        Optional<User> optUser = userRepository.findById(id);
        return optUser
                .map(user -> findPairCandidates(user,id,howMany))
                .map(UserList::new)
                .map(Utils::wrapWithResponseEntity)
                .orElse(noUserFoundforGivenID());
    }
    private List<User> findPairCandidates(User user, Long id, Integer howMany){
        SimpleDateFormat dt = new SimpleDateFormat("yyyy-MM-dd");
        String birthDate = dt.format(user.getBirthDate());
        return userRepository.getPossiblePairs(id,
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
    }

}
