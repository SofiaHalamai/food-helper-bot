package ua.com.alevel.food_supporter_bot.h2.services;

import org.apache.commons.collections.CollectionUtils;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;
import ua.com.alevel.food_supporter_bot.model.entity.User;
import ua.com.alevel.food_supporter_bot.model.response.FoodResponse;
import ua.com.alevel.food_supporter_bot.model.response.SnackResponse;
import ua.com.alevel.food_supporter_bot.service.calculator.CalculatorCPFCService;
import ua.com.alevel.food_supporter_bot.service.food.FoodService;
import ua.com.alevel.food_supporter_bot.service.snack.SnackService;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.NONE)
@ActiveProfiles({"h2db", "debug"})
//@Sql(scripts = {"/schema.sql", "/data.sql"})
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class ServiceFoodAndSnackTests {

    @Autowired
    private FoodService foodService;

    @Autowired
    private CalculatorCPFCService calculatorCPFCService;

    @Autowired
    private SnackService snackService;

    private static User user1;
    private static User user2;
    private static User user3;
    private static final int expectedSizeAsNumberOfDaysInWeek = 7;


    @BeforeAll
    static void initUserAndCalcCPFC() {
        user1 = new User();
        user1.setGender("Male");
        user1.setWeight(90.0);
        user1.setHeight(180);
        user1.setAge(50);
        user1.setGoal("Gain weight");
        user1.setLevelPhysicalActivity(1.55);

        user2 = new User();
        user2.setGender("Female");
        user2.setWeight(51.0);
        user2.setHeight(156);
        user2.setAge(19);
        user2.setGoal("Lose weight");
        user2.setLevelPhysicalActivity(1.2);

        user3 = new User();
        user3.setGender("Male");
        user3.setWeight(67.0);
        user3.setHeight(180);
        user3.setAge(35);
        user3.setGoal("Maintain weight");
        user3.setLevelPhysicalActivity(1.375);
    }

    @Test
    @Order(1)
    void calcCPFCForDifferentUsers() {
        user1 = calculatorCPFCService.calculateCPFCForUser(user1);
        assertThat(user1.getCalories()).isNotNull();
        assertThat(user1.getProtein()).isNotNull();
        assertThat(user1.getFat()).isNotNull();
        assertThat(user1.getCarbs()).isNotNull();

        user2 = calculatorCPFCService.calculateCPFCForUser(user2);
        assertThat(user2.getCalories()).isNotNull();
        assertThat(user2.getProtein()).isNotNull();
        assertThat(user2.getFat()).isNotNull();
        assertThat(user2.getCarbs()).isNotNull();

        user3 = calculatorCPFCService.calculateCPFCForUser(user3);
        assertThat(user3.getCalories()).isNotNull();
        assertThat(user3.getProtein()).isNotNull();
        assertThat(user3.getFat()).isNotNull();
        assertThat(user3.getCarbs()).isNotNull();
    }

    @Test
    void testGetBreakfastsForDifferentUsers() {
        List<FoodResponse> breakfasts = foodService.getBreakfasts(user1);
        Assertions.assertTrue(CollectionUtils.isNotEmpty(breakfasts));
        var result = breakfasts.size();
        assertEquals(expectedSizeAsNumberOfDaysInWeek, result);
        for (FoodResponse breakfast : breakfasts) {
            assertThat(breakfast.getMeal()).isEqualTo("breakfast");
        }

        breakfasts = foodService.getBreakfasts(user2);
        Assertions.assertTrue(CollectionUtils.isNotEmpty(breakfasts));
        result = breakfasts.size();
        assertEquals(expectedSizeAsNumberOfDaysInWeek, result);
        for (FoodResponse breakfast : breakfasts) {
            assertThat(breakfast.getMeal()).isEqualTo("breakfast");
        }

        breakfasts = foodService.getBreakfasts(user3);
        Assertions.assertTrue(CollectionUtils.isNotEmpty(breakfasts));
        result = breakfasts.size();
        assertEquals(expectedSizeAsNumberOfDaysInWeek, result);
        for (FoodResponse breakfast : breakfasts) {
            assertThat(breakfast.getMeal()).isEqualTo("breakfast");
        }
    }

    @Test
    void testGetLunchesForDifferentUsers() {
        List<FoodResponse> lunches = foodService.getLunches(user1);
        Assertions.assertTrue(CollectionUtils.isNotEmpty(lunches));
        var result = lunches.size();
        assertEquals(expectedSizeAsNumberOfDaysInWeek, result);
        for (FoodResponse lunch : lunches) {
            assertThat(lunch.getMeal()).isEqualTo("lunch");
        }

        lunches = foodService.getLunches(user2);
        Assertions.assertTrue(CollectionUtils.isNotEmpty(lunches));
        result = lunches.size();
        assertEquals(expectedSizeAsNumberOfDaysInWeek, result);
        for (FoodResponse lunch : lunches) {
            assertThat(lunch.getMeal()).isEqualTo("lunch");
        }

        lunches = foodService.getLunches(user3);
        Assertions.assertTrue(CollectionUtils.isNotEmpty(lunches));
        result = lunches.size();
        assertEquals(expectedSizeAsNumberOfDaysInWeek, result);
        for (FoodResponse lunch : lunches) {
            assertThat(lunch.getMeal()).isEqualTo("lunch");
        }
    }

    @Test
    void testGetDinnersForDIfferentUsers() {
        List<FoodResponse> dinners = foodService.getDinner(user1);
        Assertions.assertTrue(CollectionUtils.isNotEmpty(dinners));
        var result = dinners.size();
        assertEquals(expectedSizeAsNumberOfDaysInWeek, result);
        for (FoodResponse dinner : dinners) {
            assertThat(dinner.getMeal()).isEqualTo("dinner");
        }

        dinners = foodService.getDinner(user2);
        Assertions.assertTrue(CollectionUtils.isNotEmpty(dinners));
        result = dinners.size();
        assertEquals(expectedSizeAsNumberOfDaysInWeek, result);
        for (FoodResponse dinner : dinners) {
            assertThat(dinner.getMeal()).isEqualTo("dinner");
        }

        dinners = foodService.getDinner(user3);
        Assertions.assertTrue(CollectionUtils.isNotEmpty(dinners));
        result = dinners.size();
        assertEquals(expectedSizeAsNumberOfDaysInWeek, result);
        for (FoodResponse dinner : dinners) {
            assertThat(dinner.getMeal()).isEqualTo("dinner");
        }
    }

    @Test
    void testGetSnacksForDifferentUsers() {
        List<FoodResponse> b = foodService.getBreakfasts(user1);
        List<FoodResponse> l = foodService.getLunches(user1);
        List<FoodResponse> d = foodService.getDinner(user1);
        Integer caloriesForSnack = calculatorCPFCService.calculateCaloriesForSnack(user1, b, l, d);
        assertThat(caloriesForSnack).isNotEqualTo(0);
        List<SnackResponse> s = snackService.getSnacks(caloriesForSnack);
        Assertions.assertTrue(CollectionUtils.isNotEmpty(s));

        b = foodService.getBreakfasts(user2);
        l = foodService.getLunches(user2);
        d = foodService.getDinner(user2);
        caloriesForSnack = calculatorCPFCService.calculateCaloriesForSnack(user2, b, l, d);
        assertThat(caloriesForSnack).isNotEqualTo(0);
        s = snackService.getSnacks(caloriesForSnack);
        Assertions.assertTrue(CollectionUtils.isNotEmpty(s));

        b = foodService.getBreakfasts(user3);
        l = foodService.getLunches(user3);
        d = foodService.getDinner(user3);
        caloriesForSnack = calculatorCPFCService.calculateCaloriesForSnack(user3, b, l, d);
        assertThat(caloriesForSnack).isNotEqualTo(0);
        s = snackService.getSnacks(caloriesForSnack);
        Assertions.assertTrue(CollectionUtils.isNotEmpty(s));
    }
}
