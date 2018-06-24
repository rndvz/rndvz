package pl.edu.agh.rndvz;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MvcResult;
import pl.edu.agh.rndvz.model.TextMessage;
import pl.edu.agh.rndvz.model.User;
import pl.edu.agh.rndvz.model.jsonMappings.MessageList;
import pl.edu.agh.rndvz.persistence.ChatRepository;
import pl.edu.agh.rndvz.persistence.MessageRepository;

import static org.hamcrest.Matchers.hasSize;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class MessageTests extends AbstractTest {

    @Autowired
    MessageRepository messageRepository;
    @Autowired
    ChatRepository chatRepository;

    @Before
    public void deleteAllBeforeTests() {
        messageRepository.deleteAll();
        chatRepository.deleteAll();
        super.deleteAllBeforeTests();
    }

    @Test
    public void shouldAddMessage() throws Exception {

        createMatch();

        User user1 = userRepository.findUserByLogin(USER1).get();
        User user2 = userRepository.findUserByLogin(USER2).get();

        String content1 = "{\"text\":\"lalala\",\"from\":" + user1.getId() + ",\"to\":" + user2.getId() + "}";

        mockMvc.perform(post("/messages")
                .header("Content-Type", "application/json")
                .content(content1))
                .andExpect(
                        status().isOk())
                .andExpect(jsonPath("$.messages").isArray())
                .andExpect(jsonPath("$.messages", hasSize(1)));

        mockMvc.perform(post("/messages")
                .header("Content-Type", "application/json")
                .content(content1))
                .andExpect(
                        status().isOk())
                .andExpect(jsonPath("$.messages").isArray())
                .andExpect(jsonPath("$.messages", hasSize(2)));
    }


    @Test
    public void shouldGetMessages() throws Exception {

        createMatch();

        User user1 = userRepository.findUserByLogin(USER1).get();
        User user2 = userRepository.findUserByLogin(USER2).get();

        String content1 = "{\"text\":\"lalala\",\"from\":" + user1.getId() + ",\"to\":" + user2.getId() + "}";
        MvcResult result = null;
        for (int i = 0; i < 5; i++)
            result = mockMvc.perform(post("/messages")
                    .header("Content-Type", "application/json")
                    .content(content1))
                    .andExpect(
                            status().isOk())
                    .andReturn();

        String jsonString = result.getResponse().getContentAsString();
        System.out.println(jsonString);
        ObjectMapper mapper = new ObjectMapper();

        MessageList messageList = mapper.readValue(jsonString, MessageList.class);
        int messageListSize = messageList.getMessages().size();

        TextMessage firstMessage = messageList.getMessages().get(0);
        TextMessage lastMessage = messageList.getMessages().get(messageListSize - 1);

        mockMvc.perform(get("/messages/" + firstMessage.getId()))
                .andExpect(
                        status().isOk())
                .andExpect(jsonPath("$.messages").isArray())
                .andExpect(jsonPath("$.messages", hasSize(1)));


        mockMvc.perform(get("/messages/" + lastMessage.getId()))
                .andExpect(
                        status().isOk())
                .andExpect(jsonPath("$.messages").isArray())
                .andExpect(jsonPath("$.messages", hasSize(messageListSize)));
    }


}
