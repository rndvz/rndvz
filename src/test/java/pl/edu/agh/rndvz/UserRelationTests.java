package pl.edu.agh.rndvz;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import pl.edu.agh.rndvz.model.User;
import pl.edu.agh.rndvz.persistence.UserRepository;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class UserRelationTests {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private UserRepository userRepository;

    @Before
    public void deleteAllBeforeTests() {
        userRepository.deleteAll();
    }


    @Test
    public void shouldAcceptButNotMatch() throws Exception {
        User user1 = createAndGet("Frodo");
        User user2 = createAndGet("Gandalf");
        String content = "{\"from\":" + user1.getId() + ",\"to\":" + user2.getId() + "}";

        mockMvc.perform(post("/accept")
                .header("Content-Type", "application/json")
                .content(content))
                .andExpect(
                        status().isOk()).andExpect(jsonPath("$.value").value(false));

    }


    @Test
    public void shouldMatch() throws Exception {
        User user1 = createAndGet("Frodo");
        User user2 = createAndGet("Gandalf");
        String content1 = "{\"from\":" + user1.getId() + ",\"to\":" + user2.getId() + "}";
        String content2 = "{\"from\":" + user2.getId() + ",\"to\":" + user1.getId() + "}";

        mockMvc.perform(post("/accept")
                .header("Content-Type", "application/json")
                .content(content1))
                .andExpect(
                        status().isOk());
        mockMvc.perform(post("/accept")
                .header("Content-Type", "application/json")
                .content(content2))
                .andExpect(
                        status().isOk()).andExpect(jsonPath("$.value").value(true));

    }

    @Test
    public void shouldBlock() throws Exception {
        User user1 = createAndGet("Frodo");
        User user2 = createAndGet("Gandalf");
        String content1 = "{\"from\":" + user1.getId() + ",\"to\":" + user2.getId() + "}";

        mockMvc.perform(post("/block")
                .header("Content-Type", "application/json")
                .content(content1))
                .andExpect(
                        status().isOk())
                .andExpect(jsonPath("$.login").value(user1.getLogin()));


    }

    private User createAndGet(String login) throws Exception {
        mockMvc.perform(post("/users").content(
                "{\"login\": \"" + login + "\"}")).andExpect(
                status().isCreated()).andReturn();

        MvcResult result1 = mockMvc.perform(get("/users/search/findByLogin/" + login)).andExpect(
                status().isOk()).andReturn();

        String jsonString = result1.getResponse().getContentAsString();
        System.out.println(jsonString);
        ObjectMapper mapper = new ObjectMapper();

        return mapper.readValue(jsonString, User.class);
    }

    @Test
    public void shouldReturnErrorOnAcceptWhenUserNotExist() throws Exception {
        String content = "{\"from\":12345,\"to\":543221}";

        mockMvc.perform(post("/accept")
                .header("Content-Type", "application/json")
                .content(content))
                .andExpect(
                        status().isBadRequest())
                .andExpect(jsonPath("$.message").value("No user with given id"));

    }

    @Test
    public void shouldReturnErrorOnBlockWhenUserNotExist() throws Exception {
        String content = "{\"from\":12345,\"to\":543221}";

        mockMvc.perform(post("/block")
                .header("Content-Type", "application/json")
                .content(content))
                .andExpect(
                        status().isBadRequest())
                .andExpect(jsonPath("$.message").value("No user with given id"));

    }


}
