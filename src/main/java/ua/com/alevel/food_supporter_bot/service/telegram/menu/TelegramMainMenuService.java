package ua.com.alevel.food_supporter_bot.service.telegram.menu;

import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardButton;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;

import java.util.ArrayList;
import java.util.List;

@Service
public class TelegramMainMenuService {

    public SendMessage getMainMenuMessage(long chatId, String textMessage) {
        ReplyKeyboardMarkup replyKeyboardMarkup = getMainMenuKeyboard();
        return createMessageWithKeyboard(chatId, textMessage, replyKeyboardMarkup);
    }

    private ReplyKeyboardMarkup getMainMenuKeyboard() {

        final ReplyKeyboardMarkup replyKeyboardMarkup = new ReplyKeyboardMarkup();
        replyKeyboardMarkup.setSelective(true);
        replyKeyboardMarkup.setResizeKeyboard(true);
        replyKeyboardMarkup.setOneTimeKeyboard(false);

        List<KeyboardRow> keyboard = new ArrayList<>();

        KeyboardRow rowFirst = new KeyboardRow();
        KeyboardRow rowSecond = new KeyboardRow();
        KeyboardRow rowThird = new KeyboardRow();
        KeyboardRow rowFourth = new KeyboardRow();
        rowFirst.add(new KeyboardButton("Get a menu for 7 days"));
        rowSecond.add(new KeyboardButton("Get information about replacement products"));
        rowThird.add(new KeyboardButton("Find a recipe"));
        rowFourth.add(new KeyboardButton("Help"));
        keyboard.add(rowFirst);
        keyboard.add(rowSecond);
        keyboard.add(rowThird);
        keyboard.add(rowFourth);
        replyKeyboardMarkup.setKeyboard(keyboard);
        return replyKeyboardMarkup;
    }

    private SendMessage createMessageWithKeyboard(final long chatId,
                                                  String textMessage,
                                                  final ReplyKeyboardMarkup replyKeyboardMarkup) {
        final SendMessage sendMessage = new SendMessage();
        sendMessage.enableMarkdown(true);
        sendMessage.setChatId(chatId);
        sendMessage.setText(textMessage);
        if (replyKeyboardMarkup != null) {
            sendMessage.setReplyMarkup(replyKeyboardMarkup);
        }
        return sendMessage;
    }
}

