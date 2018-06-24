package pl.edu.agh.rndvz.controllers;

import org.glassfish.jersey.internal.guava.Lists;
import org.neo4j.driver.internal.InternalPath;
import org.neo4j.driver.v1.types.Entity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.edu.agh.rndvz.model.Chat;
import pl.edu.agh.rndvz.model.TextMessage;
import pl.edu.agh.rndvz.model.User;
import pl.edu.agh.rndvz.model.jsonMappings.UserMessage;
import pl.edu.agh.rndvz.persistence.ChatRepository;
import pl.edu.agh.rndvz.persistence.MessageRepository;
import pl.edu.agh.rndvz.persistence.UserRepository;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

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

    /**
     * @param messageID id of the last message.
     *                  That means, that this message and the earlier ones will be returned.
     * @return MessageList Object as JSON. Messages are sorted.
     * First message in list is the oldest, last message in list is the newest.
     */
    @CrossOrigin(origins = "*")
    @GetMapping(value = "/messages/{messageID}")
    public ResponseEntity getMessageFrom(@PathVariable Long messageID) {

        return toMessageListResponse(messageID);
    }


    /**
     * @param message is derived from json like
     *                '{  "text" : "hopsasa",
     *                "from":78,"to":54} }'
     * @return MessageList object as JSON. Messages are sorted.
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
        long maxSize = 0;
        List<Entity> longestPath = new LinkedList<>();

        for (Map<String, InternalPath> m : paths) {

            // query is like "match p=..."
            InternalPath internalPath = m.get("p");

            List<Entity> currentEntities = Lists.newArrayList(internalPath.nodes());
            long currentSize = currentEntities.size();

            if (currentSize >= maxSize) {
                maxSize = currentSize;
                longestPath = currentEntities;
            }
        }

        List<TextMessage> textMessages =
                longestPath
                        .stream()
                        .map(Entity::id)
                        .map(messageRepository::findById)
                        .map(Optional::get)  // these IDs are in path returned by neo4j -> the must exist
                        .collect(Collectors.toList());

        return ResponseEntity.ok(Utils.toJson(textMessages));
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
