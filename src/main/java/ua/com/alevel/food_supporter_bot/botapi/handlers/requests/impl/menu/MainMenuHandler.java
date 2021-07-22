package ua.com.alevel.food_supporter_bot.botapi.handlers.requests.impl.menu;

import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import ua.com.alevel.food_supporter_bot.botapi.handlers.requests.InputMessageHandler;
import ua.com.alevel.food_supporter_bot.botapi.states.BotState;
import ua.com.alevel.food_supporter_bot.service.telegram.menu.TelegramMainMenuService;

@Component
public class MainMenuHandler implements InputMessageHandler {
    private final TelegramMainMenuService telegramMainMenuService;

    public MainMenuHandler(TelegramMainMenuService telegramMainMenuService) {
        this.telegramMainMenuService = telegramMainMenuService;
    }

    @Override
    public SendMessage handle(Message message) {
        return processUsersInput(message);
    }

    @Override
    public BotState getHandlerName() {
        return BotState.SHOW_MAIN_MENU;
    }

    private SendMessage processUsersInput(Message inputMsg) {
        long chatId = inputMsg.getChatId();
        return telegramMainMenuService.getMainMenuMessage(chatId, "");
    }
}
