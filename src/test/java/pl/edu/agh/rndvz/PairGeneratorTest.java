package pl.edu.agh.rndvz;


import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import pl.edu.agh.rndvz.model.User;

import java.text.SimpleDateFormat;

import static org.hamcrest.Matchers.hasSize;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class PairGeneratorTest extends AbstractTest{
    protected static String USER3 = "l3";
    protected static String PASSWORD3 = "p3";
    @Before
    public void initialize() throws Exception {
        super.initialize();
        User user3 = new User();
        user3.setLogin(USER3);
        user3.setPassword(PASSWORD3);
        user3.setAcceptedDistance(2);
        user3.setAcceptedRateDifference(2);
        user3.setAcceptedYearDifference(2);
        user3.setAvgRate(5.0);
        user3.setLatitude(15.0);
        user3.setLongitude(15.0);
        user3.setSex("male");
        user3.setSexPreference("all");
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        user3.setBirthDate(sdf.parse("2000-11-11"));
        userRepository.save(user3);
        createMatch();

    }

    @Test
    public void shouldReturnOneIgnoreOne() throws Exception {
        User user3 = userRepository.findUserByLogin(USER3).get();

        mockMvc.perform(get("/users/{id}/next/{howMany}",user3.getId(),4))
                .andExpect(
                        status().isOk())
                .andExpect(jsonPath("$.users").isArray())
                .andExpect(jsonPath("$.users", hasSize(1)));
    }


}
