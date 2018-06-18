package pl.edu.agh.rndvz.controllers;

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
import pl.edu.agh.rndvz.model.jsonMappings.Relation;
import pl.edu.agh.rndvz.model.jsonMappings.UserMessage;
import pl.edu.agh.rndvz.persistence.ChatRepository;
import pl.edu.agh.rndvz.persistence.UserRepository;

import java.util.Optional;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RestController
public class ChatController {

    private final UserRepository userRepository;
    private final ChatRepository chatRepository;

    @Autowired
    public ChatController(UserRepository userRepository, ChatRepository chatRepository) {
        this.userRepository = userRepository;
        this.chatRepository = chatRepository;
    }


    @GetMapping(value = "/chats", consumes = APPLICATION_JSON_VALUE)
    public ResponseEntity getMessages(@RequestBody ChatMessage message) {
        return null;
    }

    @PostMapping(value = "/chats", consumes = APPLICATION_JSON_VALUE)
    public ResponseEntity newMessage(@RequestBody UserMessage message) {
        Optional<Chat> chat = chatRepository.findByUsers(message.getFrom(), message.getTo());
        chat.ifPresent(c -> saveMessage(c, message));

        return ResponseEntity.status(HttpStatus.OK).body("No user with given id");

    }

    private void saveMessage(Chat chat, UserMessage message) {
        // load relationships into chat
        chatRepository.findById(chat.getId());

        TextMessage textMessage = new TextMessage();
        textMessage.setText(message.getText());

        // at this point users with these IDs must exist
        User sender = userRepository.findById(message.getFrom()).get();
        User receiver = userRepository.findById(message.getTo()).get();

        textMessage.setSender(sender);
        textMessage.setReceiver(receiver);

        textMessage.setPreviousMessage(chat.getLastMessage());

        if (chat.hasLastMessage())
            chat.getLastMessage().setNextMessage(textMessage);

        chat.setLastMessage(textMessage);

        if (chat.hasNoMessages())
            chat.setFirstMessage(textMessage);

        chatRepository.save(chat);

    }
}
