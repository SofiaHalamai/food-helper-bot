package ua.com.alevel.food_supporter_bot.service.food.impl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ua.com.alevel.food_supporter_bot.exception.FoodNotFoundException;
import ua.com.alevel.food_supporter_bot.model.Food;
import ua.com.alevel.food_supporter_bot.model.entity.User;
import ua.com.alevel.food_supporter_bot.model.response.FoodResponse;
import ua.com.alevel.food_supporter_bot.repository.FoodRepository;
import ua.com.alevel.food_supporter_bot.service.food.FoodService;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
public class FoodServiceImpl implements FoodService {

    private final FoodRepository foodRepository;

    public FoodServiceImpl(FoodRepository foodRepository) {
        this.foodRepository = foodRepository;
    }

    @Override
    @Transactional
    public List<FoodResponse> getBreakfasts(User user) {
        log.info("Start find breakfasts");
        Integer calories = user.getCalories();
        List<Food> breakfasts = foodRepository.
                findTop7ByMealAndCaloriesBetween("breakfast",
                        (int) (calories * 0.20), (int) (calories * 0.3));
        if (breakfasts.size() < 2) {
            breakfasts = foodRepository.
                    findTop7ByMealAndCaloriesBetween("breakfast",
                            (int) (calories * 0.15), (int) (calories * 0.25));
        }
        log.info("Finish find breakfasts");
        if (breakfasts.size() > 1 && breakfasts.size() < 7) {
            return increaseInTheNumberOfFoods(breakfasts)
                    .stream().map(FoodResponse::fromFood).
                            collect(Collectors.toList());
        }
        return breakfasts.stream().map(FoodResponse::fromFood).
                collect(Collectors.toList());
    }


    @Override
    @Transactional
    public List<FoodResponse> getLunches(User user) {
        log.info("Start find lunches");
        Integer calories = user.getCalories();
        List<Food> lunches = foodRepository
                .findTop7ByMealAndCaloriesBetween("lunch",
                        (int) (calories * 0.30), (int) (calories * 0.40));
        if (lunches.size() < 2) {
            lunches = foodRepository.
                    findTop7ByMealAndCaloriesBetween("lunch",
                            (int) (calories * 0.17), (int) (calories * 0.29));
        }
        log.info("Finish find lunches");
        if (lunches.size() > 1 && lunches.size() < 7) {
            return increaseInTheNumberOfFoods(lunches)
                    .stream().map(FoodResponse::fromFood).
                            collect(Collectors.toList());
        }
        return lunches.stream().map(FoodResponse::fromFood).
                collect(Collectors.toList());
    }

    @Override
    @Transactional
    public List<FoodResponse> getDinner(User user) {
        log.info("Start find dinners");
        Integer calories = user.getCalories();
        List<Food> dinners = foodRepository.
                findTop7ByMealAndCaloriesBetween("dinner",
                        (int) (calories * 0.20), (int) (calories * 0.30));
        if (dinners.size() < 2) {
            dinners = foodRepository.
                    findTop7ByMealAndCaloriesBetween("dinner",
                            (int) (calories * 0.15), (int) (calories * 0.25));
        }
        log.info("Finish find dinners");
        if (dinners.size() > 1 && dinners.size() < 7) {
            return increaseInTheNumberOfFoods(dinners).
                    stream().map(FoodResponse::fromFood).
                    collect(Collectors.toList());
        }
        return dinners.stream().map(FoodResponse::fromFood).
                collect(Collectors.toList());
    }

    @Override
    @Transactional()
    public String findRecipeByName(String name) throws FoodNotFoundException {
        log.info("Start find recipe by name");
        return foodRepository.findFoodByName(name).map(Food::getRecipe)
                .orElseThrow(() -> new FoodNotFoundException(name));
    }

    private List<Food> increaseInTheNumberOfFoods(List<Food> foods) {
        while (foods.size() != 7) {
            for (int i = 0; i < foods.size(); i++) {
                foods.add(foods.get(i));
                if (foods.size() == 7)
                    break;
            }
        }
        return foods;
    }
}


