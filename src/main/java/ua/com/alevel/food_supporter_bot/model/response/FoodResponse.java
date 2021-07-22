package ua.com.alevel.food_supporter_bot.model.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import ua.com.alevel.food_supporter_bot.model.Food;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class FoodResponse {

    private String name;

    private String meal;

    private Integer calories;

    private Double protein;

    private Double fat;

    private Double carbs;

    public static FoodResponse fromFood(Food food) {
        return new FoodResponse(
                food.getName(),
                food.getMeal(),
                food.getCalories(),
                food.getProtein(),
                food.getFat(),
                food.getCarbs()
        );
    }

}
