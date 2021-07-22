package ua.com.alevel.food_supporter_bot.botapi.handlers.requests.impl.info;

import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import ua.com.alevel.food_supporter_bot.botapi.handlers.requests.InputMessageHandler;
import ua.com.alevel.food_supporter_bot.botapi.states.BotState;
import ua.com.alevel.food_supporter_bot.service.message.ReplyMessagesService;

import static ua.com.alevel.food_supporter_bot.util.Emojis.SPARKLES;

@Component
public class HelpInfoHandler implements InputMessageHandler {
    private ReplyMessagesService messagesService;

    public HelpInfoHandler(ReplyMessagesService messagesService) {
        this.messagesService = messagesService;
    }

    @Override
    public SendMessage handle(Message message) {
        return processUsersInput(message);
    }

    @Override
    public BotState getHandlerName() {
        return BotState.SHOW_HELP_INFO;
    }

    private SendMessage processUsersInput(Message inputMsg) {
        long chatId = inputMsg.getChatId();
        SendMessage replyToUser = (SendMessage) messagesService.getReplyMessage(chatId, "message.help", SPARKLES);
        return replyToUser;
    }
}
