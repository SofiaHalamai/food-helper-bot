package ua.com.alevel.food_supporter_bot.service.calculator.impl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ua.com.alevel.food_supporter_bot.model.entity.User;
import ua.com.alevel.food_supporter_bot.model.response.FoodResponse;
import ua.com.alevel.food_supporter_bot.service.calculator.CalculatorCPFCService;

import java.util.List;

@Service
@Slf4j
public class CalculatorCPFCServiceImpl implements CalculatorCPFCService {

    @Override
    public User calculateCPFCForUser(User user) {
        log.info("Start calculate CPFC for user");
        String gender = user.getGender();
        Double weight = user.getWeight();
        Integer height = user.getHeight();
        Integer age = user.getAge();
        double BRM;
        if (gender.equals("Male"))
            BRM = 88.36 + (13.4 * weight) + (4.8 * height) - (5.7 * age);
        else
            BRM = 447.6 + (9.2 * weight) + (3.1 * height) - (4.3 * age);
        switch (user.getGoal()) {
            case "Lose weight":
                BRM = BRM * 0.9;
                break;
            case "Gain weight":
                BRM = BRM * 1.15;
                break;
            case "Maintain weight":
                break;
        }
        BRM = BRM * user.getLevelPhysicalActivity();
        user.setCalories((int) BRM);
        user.setProtein((int) (BRM * 0.3 / 4));
        user.setFat((int) (BRM * 0.2 / 9));
        user.setCarbs((int) (BRM * 0.5 / 4));
        log.info("Finish calculate CPFC for user");
        return user;
    }

    @Override
    public Integer calculateCaloriesForSnack(User user, List<FoodResponse> breakfasts,
                                             List<FoodResponse> lunches, List<FoodResponse> dinners) {
        log.info("Start calculate calories for snack");
        Integer caloriesFromMainMeals = 0;
        Integer commonUserCalories = user.getCalories();
        for (FoodResponse breakfast : breakfasts) {
            caloriesFromMainMeals += breakfast.getCalories();
        }
        for (FoodResponse lunch : lunches) {
            caloriesFromMainMeals += lunch.getCalories();
        }
        for (FoodResponse dinner : dinners) {
            caloriesFromMainMeals += dinner.getCalories();
        }
        log.info("Finish calculate calories for snack");
        if ((commonUserCalories * 7 - caloriesFromMainMeals) / 7 < 40)
            return 0;
        return (commonUserCalories * 7 - caloriesFromMainMeals)/7;
    }
}
