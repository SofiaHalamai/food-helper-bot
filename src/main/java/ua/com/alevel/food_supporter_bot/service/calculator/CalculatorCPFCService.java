package ua.com.alevel.food_supporter_bot.service.calculator;

import ua.com.alevel.food_supporter_bot.model.entity.User;
import ua.com.alevel.food_supporter_bot.model.response.FoodResponse;

import java.util.List;

public interface CalculatorCPFCService {

    User calculateCPFCForUser(User user);

    Integer calculateCaloriesForSnack(User user, List<FoodResponse> breakfasts,
                                      List<FoodResponse> lunches, List<FoodResponse> dinners);
}
