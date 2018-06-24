package pl.edu.agh.rndvz.controllers;

import pl.edu.agh.rndvz.model.Chat;
import pl.edu.agh.rndvz.model.jsonMappings.Relation;
import pl.edu.agh.rndvz.model.User;
import pl.edu.agh.rndvz.persistence.ChatRepository;
import pl.edu.agh.rndvz.persistence.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RestController
public class AcceptedController {

    private final UserRepository userRepository;
    private final ChatRepository chatRepository;

    @Autowired
    public AcceptedController(UserRepository userRepository, ChatRepository chatRepository) {
        this.userRepository = userRepository;
        this.chatRepository = chatRepository;
    }


    @CrossOrigin(origins = "*")
    @PostMapping(value = "/accept", consumes = APPLICATION_JSON_VALUE)
    public ResponseEntity accept(@RequestBody Relation rel) {


        Optional<User> startUserOptional = userRepository.findById(rel.getFrom());
        Optional<User> endUserOptional = userRepository.findById(rel.getTo());

        try {
            User startUser = startUserOptional.orElseThrow(IllegalArgumentException::new);
            User endUser = endUserOptional.orElseThrow(IllegalArgumentException::new);

            boolean isMatched = acceptUser(startUser, endUser);

            return ResponseEntity.ok(Utils.toJson(isMatched));

        } catch (IllegalArgumentException ex) {
            return ResponseEntity.badRequest().body("No user with given id");
        }

    }

    private boolean acceptUser(User startUser, User endUser) {
        boolean isMatched = false;
        if (startUser.isNotBlockedFor(endUser)) {
            startUser.getAcceptedByMe().add(endUser);

            endUser.increaseRate(startUser.getAvgRate());

            if (startUser.canMatchWith(endUser)) {

                startUser.getMatched().add(endUser);
                endUser.getMatched().add(startUser);

                // since now users are can talk to each other
                Chat chat = new Chat();
                chat.addSpeaker(startUser);
                chat.addSpeaker(endUser);
                chatRepository.save(chat);

                isMatched = true;
            }

            userRepository.save(endUser);
            userRepository.save(startUser);

        }
        return isMatched;
    }
}
