package ua.com.alevel.food_supporter_bot.botapi.handlers.requests;

import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import ua.com.alevel.food_supporter_bot.botapi.states.BotState;

public interface InputMessageHandler {
    SendMessage handle(Message message);

    BotState getHandlerName();
}
