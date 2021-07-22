package ua.com.alevel.food_supporter_bot.service.calculator.impl;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ua.com.alevel.food_supporter_bot.model.entity.User;
import ua.com.alevel.food_supporter_bot.model.response.FoodResponse;
import ua.com.alevel.food_supporter_bot.service.calculator.CalculatorCPFCService;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;


class CalculatorCPFCServiceImplTest {

    private CalculatorCPFCService calculatorCPFCService;
    private User user;

    @BeforeEach
    void setUp() {
        calculatorCPFCService = new CalculatorCPFCServiceImpl();
        user = mock (User.class);
    }

    @Test
    void calculateCPFCForUser() {
        when(user.getGender()).thenReturn("Female");
        when(user.getWeight()).thenReturn(50.0);
        when(user.getHeight()).thenReturn(150);
        when(user.getAge()).thenReturn(18);
        when(user.getGoal()).thenReturn("Maintain weight");
        when(user.getLevelPhysicalActivity()).thenReturn(1.2);

        calculatorCPFCService.calculateCPFCForUser(user);

        verify(user, times(1)).setCalories(any());
        verify(user, times(1)).setProtein(any());
        verify(user, times(1)).setFat(any());
        verify(user, times(1)).setCarbs(any());
    }

    @Test
    void calculateCaloriesForSnackShouldReturnZero() {
        FoodResponse food = mock(FoodResponse.class);
        List<FoodResponse> anyMeals = new ArrayList<>();
        anyMeals.add(food);

        when(food.getCalories()).thenReturn(0);
        when(user.getCalories()).thenReturn(7);

        Integer result = calculatorCPFCService.calculateCaloriesForSnack(user, anyMeals, anyMeals, anyMeals);
        assertThat(result).isEqualTo(0);
    }

    @Test
    void calculateCaloriesForSnackShouldReturnNonZero() {
        FoodResponse food = mock(FoodResponse.class);
        List<FoodResponse> anyMeals = new ArrayList<>();
        anyMeals.add(food);

        when(food.getCalories()).thenReturn(0);
        when(user.getCalories()).thenReturn(1000);

        Integer result = calculatorCPFCService.calculateCaloriesForSnack(user, anyMeals, anyMeals, anyMeals);
        assertThat(result).isNotEqualTo(0);
    }
}