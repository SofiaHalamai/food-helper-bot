package ua.com.alevel.food_supporter_bot.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ua.com.alevel.food_supporter_bot.model.Snack;

import java.util.List;

@Repository
public interface SnackRepository extends JpaRepository <Snack, Long> {

    List<Snack> findTop7ByCaloriesBetween (Integer start, Integer end);

}
