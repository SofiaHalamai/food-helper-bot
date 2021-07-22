package ua.com.alevel.food_supporter_bot.botapi.handlers.requests.impl.user;

import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import ua.com.alevel.food_supporter_bot.botapi.handlers.requests.InputMessageHandler;
import ua.com.alevel.food_supporter_bot.botapi.states.BotState;
import ua.com.alevel.food_supporter_bot.cache.UserDataCache;
import ua.com.alevel.food_supporter_bot.model.entity.User;
import ua.com.alevel.food_supporter_bot.model.response.FoodResponse;
import ua.com.alevel.food_supporter_bot.model.response.SnackResponse;
import ua.com.alevel.food_supporter_bot.service.calculator.CalculatorCPFCService;
import ua.com.alevel.food_supporter_bot.service.food.FoodService;
import ua.com.alevel.food_supporter_bot.service.snack.SnackService;

import java.util.ArrayList;
import java.util.List;

@Component
public class CalcMenuHandler implements InputMessageHandler {
    private final CalculatorCPFCService calculatorCPFCService;
    private final FoodService foodService;
    private final UserDataCache userDataCache;
    private final SnackService snackService;

    public CalcMenuHandler(CalculatorCPFCService calculatorCPFCService,
                           FoodService foodService, UserDataCache userDataCache,
                           SnackService snackService) {
        this.calculatorCPFCService = calculatorCPFCService;
        this.foodService = foodService;
        this.userDataCache = userDataCache;
        this.snackService = snackService;
    }

    @Override
    public SendMessage handle(Message message) {
        return processUsersInput(message);
    }

    @Override
    public BotState getHandlerName() {
        return BotState.CALC_MENU;
    }

    private SendMessage processUsersInput(Message inputMsg) {
        long chatId = inputMsg.getChatId();
        int userId = inputMsg.getFrom().getId();
        SendMessage replyToUser;
        List<SnackResponse> snacks = new ArrayList<>();
        User user = calculatorCPFCService.calculateCPFCForUser(userDataCache.getUserData(userId));

        List<FoodResponse> breakfasts = foodService.getBreakfasts(user);
        List<FoodResponse> lunches = foodService.getLunches(user);
        List<FoodResponse> dinners = foodService.getDinner(user);

        Integer caloriesForSnack = calculatorCPFCService
                .calculateCaloriesForSnack(user, breakfasts, lunches, dinners);
        if (caloriesForSnack != 0) {
            snacks = snackService.getSnacks(caloriesForSnack);
        }
        userDataCache.setUsersCurrentBotState(userId, BotState.SHOW_MAIN_MENU);
        replyToUser = new SendMessage(chatId,
                fillInTheFieldsWithMenu(user, breakfasts, lunches, dinners, snacks));
        return replyToUser;
    }

    private String fillInTheFieldsWithMenu (User user, List<FoodResponse> breakfasts,
                                                   List<FoodResponse> lunches, List<FoodResponse> dinners,
                                                   List<SnackResponse> snacks){
        StringBuilder menuForUser = new StringBuilder();
        menuForUser.append("Your daily calorie intake: ").append(user.getCalories())
                .append("/").append(user.getProtein())
                .append("/").append(user.getFat())
                .append("/").append(user.getCarbs());
        menuForUser.append("\nThe numbers indicated according to the name of " +
                "the dishes are the amount of calories / protein / fat / carbohydrates\n");
        for (int i = 0; i < 7; i++) {
            menuForUser.append("\nDAY ").append(i + 1).append("\n");
            menuForUser.append("BREAKFAST: ").append(breakfasts.get(i).getName())
                    .append("\t\t").append(breakfasts.get(i).getCalories()).append("/")
                    .append(breakfasts.get(i).getProtein()).append("/")
                    .append(breakfasts.get(i).getFat()).append("/")
                    .append(breakfasts.get(i).getCarbs()).append("\n");
            menuForUser.append("LUNCH: ").append(lunches.get(i).getName())
                    .append("\t\t").append(lunches.get(i).getCalories()).append("/")
                    .append(lunches.get(i).getProtein()).append("/")
                    .append(lunches.get(i).getFat()).append("/")
                    .append(lunches.get(i).getCarbs()).append("\n");
            menuForUser.append("DINNER: ").append(dinners.get(i).getName())
                    .append("\t\t").append(dinners.get(i).getCalories()).append("/")
                    .append(dinners.get(i).getProtein()).append("/")
                    .append(dinners.get(i).getFat()).append("/")
                    .append(dinners.get(i).getCarbs()).append("\n");
            if (snacks.size() == 7) {
                menuForUser.append("SNACK - 100g: ").append(snacks.get(i).getName())
                        .append("\t\t").append(snacks.get(i).getCalories()).append("/")
                        .append(snacks.get(i).getProtein()).append("/")
                        .append(snacks.get(i).getFat()).append("/")
                        .append(snacks.get(i).getCarbs()).append("\n");
            }
        }
        return String.valueOf(menuForUser);
    }
}
