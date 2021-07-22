package ua.com.alevel.food_supporter_bot.exception;

public class IncorrectDataAboutUserException extends Exception {

    public IncorrectDataAboutUserException (Integer number) {
        super("Incorrect age or height: " + number);
    }

    public IncorrectDataAboutUserException (Double number) {
        super("Incorrect weight: " + number);
    }

}

