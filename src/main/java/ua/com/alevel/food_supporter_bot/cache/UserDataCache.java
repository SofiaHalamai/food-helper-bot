package ua.com.alevel.food_supporter_bot.cache;

import org.springframework.stereotype.Component;
import ua.com.alevel.food_supporter_bot.botapi.states.BotState;
import ua.com.alevel.food_supporter_bot.model.entity.User;

import java.util.HashMap;
import java.util.Map;

@Component
public class UserDataCache implements DataCache {
    private final Map<Integer, BotState> usersBotStates;
    private final Map<Integer, User> users;

    public UserDataCache() {
        usersBotStates = new HashMap<>();
        users = new HashMap<>();
    }

    @Override
    public void setUsersCurrentBotState(int userId, BotState botState) {
        usersBotStates.put(userId, botState);
    }

    @Override
    public BotState getUsersCurrentBotState(int userId) {
        BotState botState = usersBotStates.get(userId);
        if (botState == null) {
            botState = BotState.COMMON_INFO;
        }
        return botState;
    }

    @Override
    public User getUserData(int userId) {
        User user = users.get(userId);
        if (user == null) {
            user = new User();
        }
        return user;
    }

    @Override
    public void saveUser(int userId, User user) {
        users.put(userId, user);
    }
}
