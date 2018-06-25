package pl.edu.agh.rndvz;

import org.junit.Before;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import pl.edu.agh.rndvz.model.User;
import pl.edu.agh.rndvz.persistence.UserRepository;

import java.text.SimpleDateFormat;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public abstract class AbstractTest {

    protected static String USER1 = "l1";
    protected static String USER2 = "l2";
    protected static String PASSWORD1 = "p1";


    @Autowired
    protected MockMvc mockMvc;

    @Autowired
    protected UserRepository userRepository;

    @Before
    public void initialize() throws Exception {
        userRepository.deleteAll();
        User user1 = new User();
        user1.setLogin(USER1);
        user1.setPassword(PASSWORD1);
        user1.setAcceptedDistance(2);
        user1.setAcceptedRateDifference(2);
        user1.setAcceptedYearDifference(2);
        user1.setAvgRate(5.0);
        user1.setLatitude(15.0);
        user1.setLongitude(15.0);
        user1.setSex("male");
        user1.setSexPreference("all");
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        user1.setBirthDate(sdf.parse("2000-11-11"));
        User user2 = new User();
        user2.setLogin(USER2);
        userRepository.save(user1);
        userRepository.save(user2);
    }

    protected void createMatch() throws Exception {
        User user1 = userRepository.findUserByLogin(USER1).get();
        User user2 = userRepository.findUserByLogin(USER2).get();


        String content1 = "{\"from\":" + user1.getId() + ",\"to\":" + user2.getId() + "}";
        String content2 = "{\"from\":" + user2.getId() + ",\"to\":" + user1.getId() + "}";

        // creating a match
        mockMvc.perform(post("/accept")
                .header("Content-Type", "application/json")
                .content(content1))
                .andExpect(
                        status().isOk());
        mockMvc.perform(post("/accept")
                .header("Content-Type", "application/json")
                .content(content2))
                .andExpect(
                        status().isOk());
    }

}
