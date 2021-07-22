package ua.com.alevel.food_supporter_bot.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ua.com.alevel.food_supporter_bot.model.Food;

import java.util.List;
import java.util.Optional;

@Repository
public interface FoodRepository extends JpaRepository<Food, Long> {

    List<Food> findTop7ByMealAndCaloriesBetween(String meal, Integer start, Integer end);

    Optional<Food> findFoodByName (String name);

}