package pl.edu.agh.rndvz;

import org.junit.Test;
import pl.edu.agh.rndvz.model.User;

import static org.hamcrest.Matchers.hasSize;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class PhotoTest extends AbstractTest {

    @Test
    public void shouldAddPhoto() throws Exception {
        User user = userRepository.findUserByLogin(USER1).get();

        String content = "{\"name\":\"lalala\",\"photo\":\"photo1\"}";

        mockMvc.perform(post("/users/{id}/upload", user.getId())
                .header("Content-Type", "application/json")
                .content(content))
                .andExpect(
                        status().isOk())
                .andExpect(jsonPath("$.message").value("uploaded"));
    }
}
