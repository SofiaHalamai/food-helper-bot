package ua.com.alevel.food_supporter_bot.service.food;

import ua.com.alevel.food_supporter_bot.exception.FoodNotFoundException;
import ua.com.alevel.food_supporter_bot.model.entity.User;
import ua.com.alevel.food_supporter_bot.model.response.FoodResponse;

import java.util.List;

public interface FoodService {

    List<FoodResponse> getBreakfasts(User user);

    List<FoodResponse> getLunches(User user);

    List<FoodResponse> getDinner(User user);

    String findRecipeByName(String name) throws FoodNotFoundException;
}
