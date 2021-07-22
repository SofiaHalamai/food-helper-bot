package ua.com.alevel.food_supporter_bot.botapi.states;

import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import ua.com.alevel.food_supporter_bot.botapi.handlers.requests.InputMessageHandler;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class BotStateContext {
    private Map<BotState, InputMessageHandler> messageHandlers = new HashMap<>();

    public BotStateContext(List<InputMessageHandler> messageHandlers) {
        messageHandlers.forEach(handler -> this.messageHandlers.put(handler.getHandlerName(), handler));
    }

    public SendMessage processInputMessage(BotState currentState, Message message) {
        InputMessageHandler currentMessageHandler = findMessageHandler(currentState);
        return currentMessageHandler.handle(message);
    }

    private InputMessageHandler findMessageHandler(BotState currentState) {
        if (isFillingInformationAboutUserState(currentState)) {
            return messageHandlers.get(BotState.FILLING_USER_INFO);
        }
        if (isFillingInfoAboutNameFood(currentState))
            return messageHandlers.get(BotState.FIND_RECIPE);
        return messageHandlers.get(currentState);
    }

    private boolean isFillingInformationAboutUserState(BotState currentState) {
        switch (currentState) {
            case ASK_AGE:
            case ASK_GENDER:
            case ASK_HEIGHT:
            case ASK_WEIGHT:
            case ASK_GOAL:
            case ASK_LEVEL_PHYSICAL_ACTIVITY:
            case FILLING_USER_INFO:
                return true;
            default:
                return false;
        }
    }

    private boolean isFillingInfoAboutNameFood(BotState currentState) {
        switch (currentState) {
            case ASK_NAME_FOOD:
            case SHOW_RECIPE:
                return true;
            default:
                return false;
        }
    }
}
