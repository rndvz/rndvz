package pl.edu.agh.rndvz.controllers;

import org.neo4j.driver.internal.InternalPath;
import org.neo4j.driver.v1.types.Entity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
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

    private final UserRepository userRepository;
    private final ChatRepository chatRepository;
    private final MessageRepository messageRepository;

    @Autowired
    public ChatController(UserRepository userRepository, ChatRepository chatRepository, MessageRepository messageRepository) {
        this.userRepository = userRepository;
        this.chatRepository = chatRepository;
        this.messageRepository = messageRepository;
    }

    /**
     *
     * @param message is derived from json like:
     *
     * @return
     */
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
