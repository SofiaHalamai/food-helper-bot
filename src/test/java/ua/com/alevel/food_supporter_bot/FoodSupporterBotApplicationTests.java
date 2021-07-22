package ua.com.alevel.food_supporter_bot;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.NONE)
@ActiveProfiles({"h2db", "debug"})
class FoodSupporterBotApplicationTests {


    @Test
    void testContextLoads() {
    }
}
