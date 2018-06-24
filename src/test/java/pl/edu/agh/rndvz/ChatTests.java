package pl.edu.agh.rndvz;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import pl.edu.agh.rndvz.model.User;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class ChatTests extends AbstractTest {


    @Test
    public void shouldReturnChat() throws Exception {

        // Chat should be created on match
        createMatch();

        User user1 = userRepository.findUserByLogin(USER1).get();
        User user2 = userRepository.findUserByLogin(USER2).get();
        String content1 = "{\"from\":" + user1.getId() + ",\"to\":" + user2.getId() + "}";

        mockMvc.perform(get("/chats")
                .header("Content-Type", "application/json")
                .content(content1))
                .andExpect(
                        status().isOk());
    }

}
