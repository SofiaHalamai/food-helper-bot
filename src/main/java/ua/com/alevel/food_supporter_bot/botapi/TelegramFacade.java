package ua.com.alevel.food_supporter_bot.botapi;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.CallbackQuery;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import ua.com.alevel.food_supporter_bot.botapi.handlers.callbacks.CallbackQueryHandler;
import ua.com.alevel.food_supporter_bot.botapi.states.BotState;
import ua.com.alevel.food_supporter_bot.botapi.states.BotStateContext;
import ua.com.alevel.food_supporter_bot.cache.UserDataCache;

@Slf4j
@Component
public class TelegramFacade {
    private final BotStateContext botStateContext;
    private final UserDataCache userDataCache;
    private final CallbackQueryHandler callbackQueryHandler;
    private final FoodSupporterTelegramBot foodSupporterTelegramBot;

    public TelegramFacade(BotStateContext botStateContext, UserDataCache userDataCache,
                          CallbackQueryHandler callbackQueryHandler,
                          @Lazy FoodSupporterTelegramBot foodSupporterTelegramBot) {
        this.botStateContext = botStateContext;
        this.userDataCache = userDataCache;
        this.callbackQueryHandler = callbackQueryHandler;
        this.foodSupporterTelegramBot = foodSupporterTelegramBot;
    }

    public BotApiMethod<?> handleUpdate(Update update) {
        SendMessage replyMessage = null;

        if (update.hasCallbackQuery()) {
            CallbackQuery callbackQuery = update.getCallbackQuery();
            log.info("New callbackQuery from User: {}, userId: {}, with data: {}", update.getCallbackQuery().getFrom().getUserName(),
                    callbackQuery.getFrom().getId(), update.getCallbackQuery().getData());
            return callbackQueryHandler.processCallbackQuery(callbackQuery);
        }


        Message message = update.getMessage();
        if (message != null && message.hasText()) {
            log.info("New message from User:{}, chatId: {},  with text: {}",
                    message.getFrom().getUserName(), message.getChatId(), message.getText());
            replyMessage = handleInputMessage(message);
        }

        return replyMessage;
    }

    private SendMessage handleInputMessage(Message message) {
        String inputMsg = message.getText();
        int userId = message.getFrom().getId();
        long chatId = message.getChatId();
        BotState botState;
        SendMessage replyMessage;

        switch (inputMsg) {
            case "/start":
                botState = BotState.COMMON_INFO;
                break;
            case "Get a menu for 7 days":
                botState = BotState.FILLING_USER_INFO;
                break;
            case "Get information about replacement products":
                foodSupporterTelegramBot.sendPhoto(chatId, "static/images/replacement_products_rules.jpg");
                botState = BotState.SHOW_MAIN_MENU;
                break;
            case "Find a recipe":
                botState = BotState.FIND_RECIPE;
                break;
            case "Help":
                botState = BotState.SHOW_HELP_INFO;
                break;
            default:
                botState = userDataCache.getUsersCurrentBotState(userId);
                break;
        }

        userDataCache.setUsersCurrentBotState(userId, botState);

        replyMessage = botStateContext.processInputMessage(botState, message);

        return replyMessage;
    }
}
