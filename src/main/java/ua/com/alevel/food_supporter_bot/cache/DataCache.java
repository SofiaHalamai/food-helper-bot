package ua.com.alevel.food_supporter_bot.cache;


import ua.com.alevel.food_supporter_bot.botapi.states.BotState;
import ua.com.alevel.food_supporter_bot.model.entity.User;

public interface DataCache {
    void setUsersCurrentBotState(int userId, BotState botState);

    BotState getUsersCurrentBotState(int userId);

    User getUserData(int userId);

    void saveUser(int userId, User user);
}
