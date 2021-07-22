package ua.com.alevel.food_supporter_bot.appconfig;

import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.telegram.telegrambots.bots.DefaultBotOptions;
import org.telegram.telegrambots.meta.ApiContext;
import ua.com.alevel.food_supporter_bot.botapi.FoodSupporterTelegramBot;
import ua.com.alevel.food_supporter_bot.botapi.TelegramFacade;

@Setter
@Getter
@Configuration
public class BotConfig {
    @Value("${telegrambot.webHookPath}")
    private String webHookPath;
    @Value("${telegrambot.userName}")
    private String botUserName;
    @Value("${telegrambot.botToken}")
    private String botToken;

    @Bean
    public FoodSupporterTelegramBot foodHelperTelegramBot(TelegramFacade telegramFacade) {
        DefaultBotOptions options = ApiContext.getInstance(DefaultBotOptions.class);

        FoodSupporterTelegramBot foodSupporterTelegramBot = new FoodSupporterTelegramBot(options, telegramFacade);
        foodSupporterTelegramBot.setBotUsername(botUserName);
        foodSupporterTelegramBot.setBotToken(botToken);
        foodSupporterTelegramBot.setBotPath(webHookPath);

        return foodSupporterTelegramBot;
    }

    @Bean
    public MessageSource messageSource() {
        ReloadableResourceBundleMessageSource messageSource
                = new ReloadableResourceBundleMessageSource();
        messageSource.setBasename("classpath:messages");
        messageSource.setDefaultEncoding("UTF-8");
        return messageSource;
    }
}

