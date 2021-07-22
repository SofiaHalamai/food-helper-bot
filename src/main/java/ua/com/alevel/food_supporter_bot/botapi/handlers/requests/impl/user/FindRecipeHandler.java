package ua.com.alevel.food_supporter_bot.botapi.handlers.requests.impl.user;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;

import ua.com.alevel.food_supporter_bot.botapi.handlers.requests.InputMessageHandler;
import ua.com.alevel.food_supporter_bot.botapi.states.BotState;
import ua.com.alevel.food_supporter_bot.cache.UserDataCache;
import ua.com.alevel.food_supporter_bot.exception.FoodNotFoundException;
import ua.com.alevel.food_supporter_bot.service.food.FoodService;
import ua.com.alevel.food_supporter_bot.service.message.ReplyMessagesService;

import static ua.com.alevel.food_supporter_bot.util.Emojis.CRY;
import static ua.com.alevel.food_supporter_bot.util.Emojis.SPARKLES;

@Component
@Slf4j
public class FindRecipeHandler implements InputMessageHandler {
    private final FoodService foodService;
    private final ReplyMessagesService messagesService;
    private final UserDataCache userDataCache;

    public FindRecipeHandler(FoodService foodService, ReplyMessagesService messagesService,
                             UserDataCache userDataCache) {
        this.foodService = foodService;
        this.messagesService = messagesService;
        this.userDataCache = userDataCache;
    }

    @Override
    public SendMessage handle(Message message) {
        if (userDataCache.getUsersCurrentBotState(message.getFrom().getId()).equals(BotState.FIND_RECIPE)) {
            userDataCache.setUsersCurrentBotState(message.getFrom().getId(), BotState.ASK_NAME_FOOD);
        }
        return processUsersInput(message);
    }

    @Override
    public BotState getHandlerName() {
        return BotState.FIND_RECIPE;
    }

    private SendMessage processUsersInput(Message inputMsg) {
        String usersAnswer = inputMsg.getText();
        int userId = inputMsg.getFrom().getId();
        long chatId = inputMsg.getChatId();

        BotState botState = userDataCache.getUsersCurrentBotState(userId);
        SendMessage replyToUser = null;
        switch (botState) {
            case ASK_NAME_FOOD:
                replyToUser = (SendMessage) messagesService.getReplyMessage(chatId,
                        "message.askNameFood", SPARKLES);
                userDataCache.setUsersCurrentBotState(userId, BotState.SHOW_RECIPE);
                break;
            case SHOW_RECIPE:
                String recipe;
                try {
                    recipe = foodService.findRecipeByName (usersAnswer);
                } catch (FoodNotFoundException e) {
                    log.error("Recipe not found", e);
                    return (SendMessage) messagesService.getReplyMessage(chatId,
                            "message.recipeNotFound", CRY);
                }
                replyToUser = new SendMessage(chatId, recipe);
                userDataCache.setUsersCurrentBotState(userId, BotState.SHOW_MAIN_MENU);
                break;
        }
        return replyToUser;
    }
}
