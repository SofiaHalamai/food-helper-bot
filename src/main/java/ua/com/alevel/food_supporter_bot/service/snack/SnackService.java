package ua.com.alevel.food_supporter_bot.service.snack;

import ua.com.alevel.food_supporter_bot.model.response.SnackResponse;

import java.util.List;

public interface SnackService {

    List<SnackResponse> getSnacks(Integer calories);

}