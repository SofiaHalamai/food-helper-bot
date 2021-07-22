package ua.com.alevel.food_supporter_bot.cache;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import ua.com.alevel.food_supporter_bot.botapi.states.BotState;
import ua.com.alevel.food_supporter_bot.model.entity.User;

import static org.junit.jupiter.api.Assertions.*;


class UserDataCacheTest {

    private static final UserDataCache userDataCache = new UserDataCache();

    @BeforeAll
    static void init() {
        BotState botState = BotState.SHOW_MAIN_MENU;
        User user;
        for (int i = 0; i < 10; i++) {
            userDataCache.setUsersCurrentBotState(i + 1, botState);
            user = new User("Male", 67.5, 170, i+20, "Lose weight", 1.2, 2000, 150, 44, 250);
            userDataCache.saveUser(i, user);
        }
    }

    @Test
    void setUsersCurrentBotState() {
        BotState botState = BotState.SHOW_HELP_INFO;
        userDataCache.setUsersCurrentBotState(11, botState);
        BotState resultState = userDataCache.getUsersCurrentBotState(11);

        assertNotNull(resultState);
        assertEquals(botState, resultState);
    }

    @Test
    void getUsersCurrentBotState() {
        BotState botState = userDataCache.getUsersCurrentBotState(1);
        BotState botState1 = userDataCache.getUsersCurrentBotState(2);
        BotState botState2 = userDataCache.getUsersCurrentBotState(123);

        assertEquals(botState, botState1);
        assertEquals(BotState.COMMON_INFO, botState2);
    }

    @Test
    void getUserData() {
        User user = userDataCache.getUserData(2);

        assertNotNull(user);
        assertEquals("Male", user.getGender());
    }

    @Test
    void saveUser() {
        User user = userDataCache.getUserData(13);

        assertNull(user.getAge());
        user.setAge(18);
        user.setGender("Female");
        userDataCache.saveUser(13, user);
        user = userDataCache.getUserData(13);

        assertEquals(18, user.getAge());
        assertEquals("Female", user.getGender());
    }
}