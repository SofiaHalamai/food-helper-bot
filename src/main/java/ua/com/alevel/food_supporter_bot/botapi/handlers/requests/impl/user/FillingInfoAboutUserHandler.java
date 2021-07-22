package ua.com.alevel.food_supporter_bot.botapi.handlers.requests.impl.user;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import ua.com.alevel.food_supporter_bot.botapi.handlers.requests.InputMessageHandler;
import ua.com.alevel.food_supporter_bot.botapi.states.BotState;
import ua.com.alevel.food_supporter_bot.cache.UserDataCache;
import ua.com.alevel.food_supporter_bot.exception.IncorrectDataAboutUserException;
import ua.com.alevel.food_supporter_bot.model.entity.User;
import ua.com.alevel.food_supporter_bot.service.message.ReplyMessagesService;

import static ua.com.alevel.food_supporter_bot.util.Emojis.CRY;
import static ua.com.alevel.food_supporter_bot.util.Emojis.SPARKLES;
import static ua.com.alevel.food_supporter_bot.util.KeyboardMarkup.*;


@Component
@Slf4j
public class FillingInfoAboutUserHandler implements InputMessageHandler {
    private final UserDataCache userDataCache;
    private final ReplyMessagesService messagesService;

    public FillingInfoAboutUserHandler(UserDataCache userDataCache,
                                       ReplyMessagesService messagesService) {
        this.userDataCache = userDataCache;
        this.messagesService = messagesService;

    }

    @Override
    public SendMessage handle(Message message) {
        if (userDataCache.getUsersCurrentBotState(message.getFrom().getId())
                .equals(BotState.FILLING_USER_INFO)) {
            userDataCache.setUsersCurrentBotState(message.getFrom().getId(), BotState.ASK_AGE);
        }
        return processUsersInput(message);
    }

    @Override
    public BotState getHandlerName() {
        return BotState.FILLING_USER_INFO;
    }

    private SendMessage processUsersInput(Message inputMsg) {
        String usersAnswer = inputMsg.getText();
        int userId = inputMsg.getFrom().getId();
        long chatId = inputMsg.getChatId();

        User user = userDataCache.getUserData(userId);
        BotState botState = userDataCache.getUsersCurrentBotState(userId);
        SendMessage replyToUser = null;
        try {
            switch (botState) {
                case ASK_AGE:
                    userDataCache.setUsersCurrentBotState(userId, BotState.ASK_GENDER);
                    replyToUser = (SendMessage) messagesService
                            .getReplyMessage(chatId, "message.askAge", SPARKLES);
                    userDataCache.saveUser(userId, user);
                    break;
                case ASK_GENDER:
                    if (checkAge(Integer.parseInt(usersAnswer))) {
                        user.setAge(Integer.parseInt(usersAnswer));
                        replyToUser = (SendMessage) messagesService
                                .getReplyMessage(chatId, "message.askGender", SPARKLES);
                        replyToUser.setReplyMarkup(getGenderButtonsMarkup());
                    } else
                        replyToUser = (SendMessage) messagesService
                                .getReplyMessage(chatId, "message.incorrectAge", CRY);
                    break;
                case ASK_GOAL:
                    if (checkHeight(Integer.parseInt(usersAnswer))) {
                        replyToUser = (SendMessage) messagesService
                                .getReplyMessage(chatId, "message.askGoal", SPARKLES);
                        user.setHeight(Integer.parseInt(usersAnswer));
                        replyToUser.setReplyMarkup(getGoalButtonsMarkup());
                    } else
                        replyToUser = (SendMessage) messagesService
                                .getReplyMessage(chatId, "message.incorrectHeight", CRY);
                    break;
                case ASK_LEVEL_PHYSICAL_ACTIVITY:
                    if (checkWeight(Double.parseDouble(usersAnswer))) {
                        replyToUser = (SendMessage) messagesService
                                .getReplyMessage(chatId,
                                        "message.askLevelPhysicalActivity", SPARKLES);
                        user.setWeight(Double.parseDouble(usersAnswer));
                        replyToUser.setReplyMarkup(getLevelPhysicalActivityButtonsMarkup());
                    } else
                        replyToUser = (SendMessage) messagesService
                                .getReplyMessage(chatId, "message.incorrectWeight", CRY);
                    break;
            }
        } catch (NumberFormatException e) {
            log.error("Incorrect input (number format)", e);
            replyToUser = (SendMessage) messagesService
                    .getReplyMessage(chatId, "message.incorrectFormat", CRY);
        }
        return replyToUser;
    }

    private boolean checkAge(Integer age) {
        if (age < 18 || age > 60) {
            log.error("Incorrect age ", new IncorrectDataAboutUserException(age));
            return false;
        }
        return true;
    }

    private boolean checkHeight(Integer height) {
        if (height < 150 || height > 200) {
            log.error("Incorrect height ", new IncorrectDataAboutUserException(height));
            return false;
        }
        return true;
    }

    private boolean checkWeight(Double weight) {
        if (weight < 45 || weight > 120) {
            log.error("Incorrect weight ", new IncorrectDataAboutUserException(weight));
            return false;
        }
        return true;
    }
}



