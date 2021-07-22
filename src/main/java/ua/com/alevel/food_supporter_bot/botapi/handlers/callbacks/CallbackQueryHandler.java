package ua.com.alevel.food_supporter_bot.botapi.handlers.callbacks;

import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.AnswerCallbackQuery;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.objects.CallbackQuery;
import ua.com.alevel.food_supporter_bot.botapi.states.BotState;
import ua.com.alevel.food_supporter_bot.cache.UserDataCache;
import ua.com.alevel.food_supporter_bot.model.entity.User;
import ua.com.alevel.food_supporter_bot.service.message.ReplyMessagesService;
import ua.com.alevel.food_supporter_bot.service.telegram.menu.TelegramMainMenuService;

import static ua.com.alevel.food_supporter_bot.util.Emojis.SPARKLES;
import static ua.com.alevel.food_supporter_bot.util.Emojis.WINK;

@Component
public class CallbackQueryHandler {
    private final UserDataCache userDataCache;
    private final TelegramMainMenuService telegramMainMenuService;
    private final ReplyMessagesService messagesService;

    public CallbackQueryHandler(UserDataCache userDataCache, ReplyMessagesService messagesService) {
        this.userDataCache = userDataCache;
        this.messagesService = messagesService;
        this.telegramMainMenuService = new TelegramMainMenuService();
    }

    public BotApiMethod<?> processCallbackQuery(CallbackQuery buttonQuery) {
        final long chatId = buttonQuery.getMessage().getChatId();
        final int userId = buttonQuery.getFrom().getId();
        BotApiMethod<?> callBackAnswer = telegramMainMenuService.getMainMenuMessage(chatId, "You can use main menu for navigation");

        User user = userDataCache.getUserData(userId);
        String button = buttonQuery.getData();
        switch (button) {
            case "buttonReady":
                break;
            case "buttonNo":
                callBackAnswer = sendAnswerButtonNo(buttonQuery);
                break;
            case "buttonMale":
                user.setGender("Male");
                userDataCache.setUsersCurrentBotState(userId, BotState.ASK_GOAL);
                callBackAnswer = messagesService.getReplyMessage(chatId, "message.askHeight", SPARKLES);
                userDataCache.saveUser(userId, user);
                break;
            case "buttonFemale":
                user.setGender("Female");
                userDataCache.setUsersCurrentBotState(userId, BotState.ASK_GOAL);
                callBackAnswer = messagesService.getReplyMessage(chatId, "message.askHeight", SPARKLES);
                userDataCache.saveUser(userId, user);
                break;
            case "buttonLoseW":
                user.setGoal("Lose weight");
                userDataCache.saveUser(userId, user);
                userDataCache.setUsersCurrentBotState(userId, BotState.ASK_LEVEL_PHYSICAL_ACTIVITY);
                callBackAnswer = messagesService.getReplyMessage(chatId, "message.askWeight", SPARKLES);
                break;
            case "buttonGainW":
                user.setGoal("Gain weight");
                userDataCache.saveUser(userId, user);
                userDataCache.setUsersCurrentBotState(userId, BotState.ASK_LEVEL_PHYSICAL_ACTIVITY);
                callBackAnswer = messagesService.getReplyMessage(chatId, "message.askWeight", SPARKLES);
                break;
            case "buttonMaintainW":
                user.setGoal("Maintain weight");
                userDataCache.saveUser(userId, user);
                userDataCache.setUsersCurrentBotState(userId, BotState.ASK_LEVEL_PHYSICAL_ACTIVITY);
                callBackAnswer = messagesService.getReplyMessage(chatId, "message.askWeight", SPARKLES);
                break;
            case "buttonSedentary":
                user.setLevelPhysicalActivity(1.2);
                userDataCache.saveUser(userId, user);
                userDataCache.setUsersCurrentBotState(userId, BotState.CALC_MENU);
                callBackAnswer = messagesService.getReplyMessage(chatId, "message.checkData", SPARKLES, WINK);
                break;
            case "buttonSimpleWorkouts":
                user.setLevelPhysicalActivity(1.375);
                userDataCache.saveUser(userId, user);
                userDataCache.setUsersCurrentBotState(userId, BotState.CALC_MENU);
                callBackAnswer = messagesService.getReplyMessage(chatId, "message.checkData", SPARKLES, WINK);
                break;
            case "buttonLoadWorkouts":
                user.setLevelPhysicalActivity(1.55);
                userDataCache.saveUser(userId, user);
                userDataCache.setUsersCurrentBotState(userId, BotState.CALC_MENU);
                callBackAnswer = messagesService.getReplyMessage(chatId, "message.checkData", SPARKLES, WINK);
                break;
            case "buttonIntensiveWorkouts":
                user.setLevelPhysicalActivity(1.725);
                userDataCache.saveUser(userId, user);
                userDataCache.setUsersCurrentBotState(userId, BotState.CALC_MENU);
                callBackAnswer = messagesService.getReplyMessage(chatId, "message.checkData", SPARKLES, WINK);
                break;
        }
        return callBackAnswer;
    }

    private AnswerCallbackQuery sendAnswerButtonNo(CallbackQuery callbackquery) {
        AnswerCallbackQuery answerCallbackQuery = new AnswerCallbackQuery();
        answerCallbackQuery.setCallbackQueryId(callbackquery.getId());
        answerCallbackQuery.setShowAlert(true);
        answerCallbackQuery.setText("I'll wait for you");
        return answerCallbackQuery;
    }
}
