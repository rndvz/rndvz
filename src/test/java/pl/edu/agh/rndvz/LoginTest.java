package pl.edu.agh.rndvz;


import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import pl.edu.agh.rndvz.model.User;

import static org.hamcrest.Matchers.hasSize;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class LoginTest extends AbstractTest {

    @Test
    public void shouldReturnTrueOnGoodCredentials() throws Exception {

        String content1 = "{\"login\":\"" + USER1 + "\",\"password\":\"" + PASSWORD1 + "\"}";

        mockMvc.perform(post("/users/login")
                .header("Content-Type", "application/json")
                .content(content1))
                .andExpect(
                        status().isOk())
                .andExpect(jsonPath("$.value").value(true));
    }

    @Test
    public void shouldReturnFalseOnBadCredentials() throws Exception {

        String content1 = "{\"login\":\"" + USER1 + "\",\"password\":\"ashdashdsad\"}";

        mockMvc.perform(post("/users/login")
                .header("Content-Type", "application/json")
                .content(content1))
                .andExpect(
                        status().isOk())
                .andExpect(jsonPath("$.value").value(false));
    }

    @Test
    public void shouldReturnErrorIfLoginNotExist() throws Exception {

        String content1 = "{\"login\":\"noSuchUser\",\"password\":\"noSuchPassword\"}";

        mockMvc.perform(post("/users/login")
                .header("Content-Type", "application/json")
                .content(content1))
                .andExpect(
                        status().isBadRequest())
                .andExpect(jsonPath("$.message").value("No user with that login"));
    }

}
