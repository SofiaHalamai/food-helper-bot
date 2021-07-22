package ua.com.alevel.food_supporter_bot.botapi;

import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.ResourceUtils;
import org.telegram.telegrambots.bots.DefaultBotOptions;
import org.telegram.telegrambots.bots.TelegramWebhookBot;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.methods.send.SendPhoto;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.io.File;
import java.io.FileNotFoundException;

@Setter
@Slf4j
public class FoodSupporterTelegramBot extends TelegramWebhookBot {
    private String botUsername;
    private String botToken;
    private String botPath;

    private TelegramFacade telegramFacade;

    public FoodSupporterTelegramBot(DefaultBotOptions botOptions, TelegramFacade telegramFacade) {
        super(botOptions);
        this.telegramFacade = telegramFacade;
    }

    @Override
    public BotApiMethod<?> onWebhookUpdateReceived(Update update) {
        return telegramFacade.handleUpdate(update);
    }

    public void sendPhoto(long chatId, String imagePath) {
        File image = null;
        try {
            image = ResourceUtils.getFile("classpath:" + imagePath);
        } catch (FileNotFoundException e) {
            log.error("Images not found: ", e);
        }
        SendPhoto sendPhoto = new SendPhoto().setPhoto(image);
        sendPhoto.setChatId(chatId);
        try {
            execute(sendPhoto);
        } catch (TelegramApiException e) {
            log.error("Image sending error: ", e);
        }
    }

    @Override
    public String getBotUsername() {
        return botUsername;
    }

    @Override
    public String getBotToken() {
        return botToken;
    }

    @Override
    public String getBotPath() {
        return botPath;
    }

}
