package pl.edu.agh.rndvz.controllers;

import org.neo4j.driver.internal.InternalPath;
import org.neo4j.driver.v1.types.Entity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import pl.edu.agh.rndvz.model.Chat;
import pl.edu.agh.rndvz.model.TextMessage;
import pl.edu.agh.rndvz.model.User;
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
public class MessageController {

    private final UserRepository userRepository;
    private final ChatRepository chatRepository;
    private final MessageRepository messageRepository;

    @Autowired
    public MessageController(UserRepository userRepository, ChatRepository chatRepository, MessageRepository messageRepository) {
        this.userRepository = userRepository;
        this.chatRepository = chatRepository;
        this.messageRepository = messageRepository;
    }


    @PostMapping(value = "/messages/{messageID}", consumes = APPLICATION_JSON_VALUE)
    public ResponseEntity getMessageFrom(@PathVariable Long messageID) {

        return toMessageListResponse(messageID);
    }


    /**
     * @param message is derived from json like
     *                '{  "text" : "hopsasa",
     *                "chatMessage": {"messagesToReturn" : 3,"from":78,"to":54} }'
     * @return ResponseEnity with list of TextMessages as json. Messages are sorted.
     * First message in list is the oldest, last message in list is the newest.
     */
    @CrossOrigin(origins = "*")
    @PostMapping(value = "/messages", consumes = APPLICATION_JSON_VALUE)
    public ResponseEntity newMessage(@RequestBody UserMessage message) {

        Optional<Chat> optionalChat = chatRepository.findByUsers(message.getFrom(), message.getTo());

        optionalChat.ifPresent(chat -> saveMessage(chat, message));

        return optionalChat
                .map(chat -> toMessageListResponse(chat.getLastMessage().getId()))
                .orElseGet(Utils::noChatFound);
    }


    private ResponseEntity toMessageListResponse(Long startMessageID) {
        Iterable<Map<String, InternalPath>> paths = messageRepository.getLastMessages(startMessageID);

        // actually it should contain just 1 result
        for (Map<String, InternalPath> m : paths) {
            // query is like "match p=..."
            InternalPath internalPath = m.get("p");

            List<TextMessage> textMessages = StreamSupport.stream(internalPath.nodes().spliterator(), false)
                    .map(Entity::id)
                    .map(messageRepository::findById)
                    .map(Optional::get)  // these IDs are in path returned by neo4j -> the must exist
                    .collect(Collectors.toList());

            return ResponseEntity
                    .status(HttpStatus.OK)
                    .body(textMessages);
        }

        return Utils.noPathFound();
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
