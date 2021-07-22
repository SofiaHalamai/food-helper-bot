package ua.com.alevel.food_supporter_bot.model.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import ua.com.alevel.food_supporter_bot.model.Snack;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class SnackResponse {

    private String name;

    private Integer calories;

    private Double protein;

    private Double fat;

    private Double carbs;

    public static SnackResponse fromSnack(Snack snack) {
        return new SnackResponse(
                snack.getName(),
                snack.getCalories(),
                snack.getProtein(),
                snack.getFat(),
                snack.getCarbs()
        );
    }
}
