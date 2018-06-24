package pl.edu.agh.rndvz.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.edu.agh.rndvz.model.Chat;
import pl.edu.agh.rndvz.model.jsonMappings.Relation;
import pl.edu.agh.rndvz.persistence.ChatRepository;

import java.util.Optional;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RestController
public class ChatController {

    private final ChatRepository chatRepository;

    @Autowired
    public ChatController(ChatRepository chatRepository) {
        this.chatRepository = chatRepository;
    }


    /**
     * @param relation is derived from json like:
     *                '{ "from":78, "to":54}'
     *
     *
     * @return Chat object as json
     */
    @CrossOrigin(origins = "*")
    @GetMapping(value = "/chats", consumes = APPLICATION_JSON_VALUE)
    public ResponseEntity getMessages(@RequestBody Relation relation) {
        Optional<Chat> optionalChat = chatRepository.findByUsers(relation.getFrom(), relation.getTo());
        return optionalChat
                .map(chat -> chatRepository.findById(chat.getId()))
                .map(chat -> ResponseEntity
                        .status(HttpStatus.OK)
                        .body(chat))
                .orElseGet(Utils::noChatFound);
    }


}
