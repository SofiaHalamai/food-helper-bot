package ua.com.alevel.food_supporter_bot.model.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class User {

    private String gender;

    private Double weight;

    private Integer height;

    private Integer age;

    private String goal;

    private Double levelPhysicalActivity;

    private Integer calories;

    private Integer protein;

    private Integer fat;

    private Integer carbs;

}
