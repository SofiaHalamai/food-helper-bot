package ua.com.alevel.food_supporter_bot.service.food.impl;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ua.com.alevel.food_supporter_bot.exception.FoodNotFoundException;
import ua.com.alevel.food_supporter_bot.model.Food;
import ua.com.alevel.food_supporter_bot.model.entity.User;
import ua.com.alevel.food_supporter_bot.model.response.FoodResponse;
import ua.com.alevel.food_supporter_bot.repository.FoodRepository;
import ua.com.alevel.food_supporter_bot.service.food.FoodService;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class FoodServiceImplTest {

    private FoodService foodService;
    private FoodRepository foodRepository;
    private User user;
    private Food food;

    @BeforeEach
    void setUp() {
        foodRepository = mock(FoodRepository.class);
        foodService = new FoodServiceImpl(foodRepository);
        user = mock(User.class);
        food = new Food(1L, "name", "anyMeal",
                500, 2.2, 3.3, 4.4, "recipe");
    }

    @Test
    void getBreakfasts() {
        List<Food> breakfasts = new ArrayList<>();
        for (int i = 0; i < 2; i++) {
            breakfasts.add(food);
        }

        when(user.getCalories()).thenReturn(1400);
        when(foodRepository.findTop7ByMealAndCaloriesBetween(any(), any(), any())).thenReturn(breakfasts);

        List<FoodResponse> foodResponses = foodService.getBreakfasts(user);
        Optional<FoodResponse> presentResponse = Optional.ofNullable(foodResponses.get(1));

        assertThat(presentResponse).hasValueSatisfying(foodResponse ->
                assertFoodMatchesResponse(food, foodResponse));
    }

    @Test
    void getLunches() {
        List<Food> lunches = new ArrayList<>();
        for (int i = 0; i < 2; i++) {
            lunches.add(food);
        }

        when(user.getCalories()).thenReturn(1400);
        when(foodRepository.findTop7ByMealAndCaloriesBetween(any(), any(), any())).thenReturn(lunches);

        List<FoodResponse> foodResponses = foodService.getLunches(user);
        Optional<FoodResponse> presentResponse = Optional.ofNullable(foodResponses.get(1));

        assertThat(presentResponse).hasValueSatisfying(foodResponse ->
                assertFoodMatchesResponse(food, foodResponse));
    }

    @Test
    void getDinner() {
        List<Food> dinners = new ArrayList<>();
        for (int i = 0; i < 2; i++) {
            dinners.add(food);
        }

        when(user.getCalories()).thenReturn(1400);
        when(foodRepository.findTop7ByMealAndCaloriesBetween(any(), any(), any())).thenReturn(dinners);

        List<FoodResponse> foodResponses = foodService.getDinner(user);
        Optional<FoodResponse> presentResponse = Optional.ofNullable(foodResponses.get(1));

        assertThat(presentResponse).hasValueSatisfying(foodResponse ->
                assertFoodMatchesResponse(food, foodResponse));
    }

    @Test
    void findRecipeByName() throws FoodNotFoundException {
        when(foodRepository.findFoodByName("name")).thenReturn(Optional.of(food));

        String recipe = foodService.findRecipeByName("name");
        verify(foodRepository).findFoodByName("name");
        assertThat(recipe).isEqualTo(food.getRecipe());
    }

    private static void assertFoodMatchesResponse(Food food, FoodResponse foodResponse) {
        assertThat(foodResponse.getName()).isEqualTo(food.getName());
        assertThat(foodResponse.getCalories()).isEqualTo(food.getCalories());
        assertThat(foodResponse.getProtein()).isEqualTo(food.getProtein());
        assertThat(foodResponse.getFat()).isEqualTo(food.getFat());
        assertThat(foodResponse.getCarbs()).isEqualTo(food.getCarbs());
    }
}