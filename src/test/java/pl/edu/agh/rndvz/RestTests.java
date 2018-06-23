package pl.edu.agh.rndvz;


import org.junit.Test;
import org.junit.runner.RunWith;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
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

//    @Before
//    public void deleteAllBeforeTests() throws Exception {
//        userRepository.deleteAll();
//    }

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

//    @Test
//    public void shouldQueryEntity() throws Exception {
//
//        mockMvc.perform(post("/people").content(
//                "{\"login\": \"Frodo\"}")).andExpect(
//                status().isCreated());
//
//        mockMvc.perform(
//                get("/people/search/findByLastName?name={name}", "Baggins")).andExpect(
//                status().isOk()).andExpect(
//                jsonPath("$._embedded.people[0].firstName").value(
//                        "Frodo"));
//    }

//    @Test
//    public void shouldUpdateEntity() throws Exception {
//
//        MvcResult mvcResult = mockMvc.perform(post("/users").content(
//                "{\"login\": \"Frodo\"}")).andExpect(
//                status().isCreated()).andReturn();
//
//        String location = mvcResult.getResponse().getHeader("Location");
//
//        mockMvc.perform(put(location).content(
//                "{\"login\": \"Bilbo\"}")).andExpect(
//                status().isNoContent());
//
//        mockMvc.perform(get(location)).andExpect(status().isOk()).andExpect(
//                jsonPath("$.login").value("Bilbo"));
//    }

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
}