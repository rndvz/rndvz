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
import pl.edu.agh.rndvz.model.jsonMappings.JsonID;
import pl.edu.agh.rndvz.persistence.UserRepository;

import static org.hamcrest.Matchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class RestTests {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private UserRepository userRepository;

    @Before
    public void deleteAllBeforeTests() {
        userRepository.deleteAll();
    }

    @Test
    public void shouldReturnRepositoryIndex() throws Exception {

        mockMvc.perform(get("/")).andDo(print()).andExpect(status().isOk()).andExpect(
                jsonPath("$._links.users").exists());
    }

    @Test
    public void shouldCreateEntity() throws Exception {

        mockMvc.perform(post("/users").content(
                "{\"login\": \"Frodo\"}")).andExpect(
                status().isCreated()).andExpect(
                header().string("Location", containsString("users/")));
    }

    @Test
    public void shouldRetrieveEntity() throws Exception {

        MvcResult mvcResult = mockMvc.perform(post("/users").content(
                "{\"login\": \"Frodo\"}")).andExpect(
                status().isCreated()).andReturn();

        String location = mvcResult.getResponse().getHeader("Location");
        mockMvc.perform(get(location)).andExpect(status().isOk()).andExpect(
                jsonPath("$.login").value("Frodo"));
    }

    @Test
    public void shouldPartiallyUpdateEntity() throws Exception {
        MvcResult mvcResult = mockMvc.perform(post("/users").content(
                "{\"login\": \"Frodo\"}")).andExpect(
                status().isCreated()).andReturn();

        String location = mvcResult.getResponse().getHeader("Location");

        mockMvc.perform(put(location).content(
                "{\"login\": \"Bilbo\"}")).andExpect(
                status().isNoContent());

        mockMvc.perform(get(location)).andExpect(status().isOk()).andExpect(
                jsonPath("$.login").value("Bilbo"));
    }


    @Test
    public void shouldDeleteEntity() throws Exception {

        MvcResult mvcResult = mockMvc.perform(post("/users").content(
                "{ \"firstName\": \"Bilbo\"}")).andExpect(
                status().isCreated()).andReturn();

        String location = mvcResult.getResponse().getHeader("Location");
        mockMvc.perform(delete(location)).andExpect(status().isNoContent());

        mockMvc.perform(get(location)).andExpect(status().isNotFound());
    }


    @Test
    public void shouldReturnUserWhenGivenLogin() throws Exception {
        mockMvc.perform(post("/users").content(
                "{\"login\": \"Frodo\"}")).andExpect(
                status().isCreated());

        mockMvc.perform(get("/users/search/findByLogin/Frodo")).andExpect(
                status().isOk()).andExpect(jsonPath("$.login").value("Frodo"));

    }

    @Test
    public void shouldReturnIDWhenGivenLogin() throws Exception {
        mockMvc.perform(post("/users").content(
                "{\"login\": \"Frodo\"}")).andExpect(
                status().isCreated()).andReturn();

        MvcResult mvcResult = mockMvc.perform(get("/users/search/findByLogin/Frodo")).andExpect(
                status().isOk()).andReturn();

        String jsonString = mvcResult.getResponse().getContentAsString();
        System.out.println(jsonString);
        ObjectMapper mapper = new ObjectMapper();

        User user = mapper.readValue(jsonString, User.class);

        mockMvc.perform(get("/users/search/getIDbyLogin/Frodo")).andExpect(
                status().isOk()).andExpect(jsonPath("$.id").value(user.getId()));

    }

    @Test
    public void shouldtrueIfLoginExists() throws Exception {
        mockMvc.perform(post("/users").content(
                "{\"login\": \"Frodo\"}")).andExpect(
                status().isCreated()).andReturn();

        mockMvc.perform(get("/users/loginExists/Frodo")).andExpect(
                status().isOk()).andExpect(jsonPath("$.value").value(true));

    }


    @Test
    public void shouldReturnTrueIfIdExists() throws Exception {
        mockMvc.perform(post("/users").content(
                "{\"login\": \"Frodo\"}")).andExpect(
                status().isCreated()).andReturn();

        MvcResult mvcResult = mockMvc.perform(get("/users/search/findByLogin/Frodo")).andExpect(
                status().isOk()).andReturn();

        String jsonString = mvcResult.getResponse().getContentAsString();
        System.out.println(jsonString);
        ObjectMapper mapper = new ObjectMapper();

        User user = mapper.readValue(jsonString, User.class);

        mockMvc.perform(get("/exists/{ID}", user.getId())).andExpect(
                status().isOk()).andExpect(jsonPath("$.value").value(true));

    }


    @Test
    public void shouldReturnFalseIfLoginNotExist() throws Exception {

        mockMvc.perform(get("/users/loginExists/asjdhfaoieurhe")).andExpect(
                status().isOk()).andExpect(jsonPath("$.value").value(false));

    }


    @Test
    public void shouldReturnFalseIfIdNotExist() throws Exception {

        mockMvc.perform(get("/exists/{ID}", 12341232)).andExpect(
                status().isOk()).andExpect(jsonPath("$.value").value(false));

    }


}