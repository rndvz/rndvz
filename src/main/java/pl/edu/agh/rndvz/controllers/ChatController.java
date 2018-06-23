package pl.edu.agh.rndvz.controllers;

import org.neo4j.driver.internal.InternalPath;
import org.neo4j.driver.v1.types.Entity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.edu.agh.rndvz.model.Chat;
import pl.edu.agh.rndvz.model.TextMessage;
import pl.edu.agh.rndvz.model.User;
import pl.edu.agh.rndvz.model.jsonMappings.ChatMessage;
import pl.edu.agh.rndvz.model.jsonMappings.UserMessage;
import pl.edu.agh.rndvz.persistence.ChatRepository;
import pl.edu.agh.rndvz.persistence.MessageRepository;
import pl.edu.agh.rndvz.persistence.UserRepository;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RestController
public class ChatController {

    private final ChatRepository chatRepository;

    @Autowired
    public ChatController(ChatRepository chatRepository) {
        this.chatRepository = chatRepository;
    }


    //get rid of messagestoreturn. change into relation. change messagecontroller?
    /**
     * @param message is derived from json like:
     *                '{"messagesToReturn" : 3, "from":78, "to":54}'
     *
     *
     * @return Chat as json
     */
    @CrossOrigin(origins = "*")
    @GetMapping(value = "/chats", consumes = APPLICATION_JSON_VALUE)
    public ResponseEntity getMessages(@RequestBody ChatMessage message) {
        Optional<Chat> optionalChat = chatRepository.findByUsers(message.getFrom(), message.getTo());
        return optionalChat
                .map(chat -> chatRepository.findById(chat.getId()))
                .map(chat -> ResponseEntity
                        .status(HttpStatus.OK)
                        .body(chat))
                .orElseGet(Utils::noChatFound);
    }


}
