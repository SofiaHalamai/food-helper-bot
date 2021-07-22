package ua.com.alevel.food_supporter_bot.exception;

public class FoodNotFoundException extends Exception {

    public FoodNotFoundException(String name) {
        super("Food: " + name + " was not found!");
    }

}
