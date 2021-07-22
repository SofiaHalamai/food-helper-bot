package ua.com.alevel.food_supporter_bot.util;

import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class KeyboardMarkup {

    public static InlineKeyboardMarkup getInlineMessageButtons() {
        InlineKeyboardMarkup inlineKeyboardMarkup = new InlineKeyboardMarkup();

        InlineKeyboardButton buttonReady = new InlineKeyboardButton().setText("I'm ready");
        InlineKeyboardButton buttonNo = new InlineKeyboardButton().setText("No, thanks");

        buttonReady.setCallbackData("buttonReady");
        buttonNo.setCallbackData("buttonNo");

        List<InlineKeyboardButton> keyboardButtons = new ArrayList<>();
        keyboardButtons.add(buttonReady);
        keyboardButtons.add(buttonNo);

        inlineKeyboardMarkup.setKeyboard(Collections.singletonList(keyboardButtons));

        return inlineKeyboardMarkup;
    }

    public static InlineKeyboardMarkup getGenderButtonsMarkup() {
        InlineKeyboardMarkup inlineKeyboardMarkup = new InlineKeyboardMarkup();
        InlineKeyboardButton buttonMale = new InlineKeyboardButton().setText("Male");
        InlineKeyboardButton buttonFemale = new InlineKeyboardButton().setText("Female");

        buttonMale.setCallbackData("buttonMale");
        buttonFemale.setCallbackData("buttonFemale");

        List<InlineKeyboardButton> keyboardButtons = new ArrayList<>();
        keyboardButtons.add(buttonMale);
        keyboardButtons.add(buttonFemale);

        inlineKeyboardMarkup.setKeyboard(Collections.singletonList(keyboardButtons));

        return inlineKeyboardMarkup;
    }

    public static InlineKeyboardMarkup getGoalButtonsMarkup() {
        InlineKeyboardMarkup inlineKeyboardMarkup = new InlineKeyboardMarkup();
        InlineKeyboardButton buttonLoseW = new InlineKeyboardButton().setText("Lose weight");
        InlineKeyboardButton buttonGainW = new InlineKeyboardButton().setText("Gain weight");
        InlineKeyboardButton buttonMaintainW = new InlineKeyboardButton().setText("Maintain weight");

        buttonLoseW.setCallbackData("buttonLoseW");
        buttonGainW.setCallbackData("buttonGainW");
        buttonMaintainW.setCallbackData("buttonMaintainW");

        List<InlineKeyboardButton> keyboardButtonsFirstRow = new ArrayList<>();
        keyboardButtonsFirstRow.add(buttonLoseW);
        keyboardButtonsFirstRow.add(buttonGainW);

        List<InlineKeyboardButton> keyboardButtonsSecondRow = new ArrayList<>();

        keyboardButtonsSecondRow.add(buttonMaintainW);

        List<List<InlineKeyboardButton>> rowList = new ArrayList<>();
        rowList.add(keyboardButtonsFirstRow);
        rowList.add(keyboardButtonsSecondRow);

        inlineKeyboardMarkup.setKeyboard(rowList);

        return inlineKeyboardMarkup;
    }

    public static InlineKeyboardMarkup getLevelPhysicalActivityButtonsMarkup() {
        InlineKeyboardMarkup inlineKeyboardMarkup = new InlineKeyboardMarkup();
        InlineKeyboardButton buttonSedentary = new InlineKeyboardButton().setText("Sedentary lifestyle");
        InlineKeyboardButton buttonSimpleWorkouts = new InlineKeyboardButton().setText("Workouts 1-3 times");
        InlineKeyboardButton buttonLoadWorkouts = new InlineKeyboardButton().setText("Workouts 3-5 times");
        InlineKeyboardButton buttonIntensiveWorkouts = new InlineKeyboardButton().setText("Workouts 6-7 times");

        buttonSedentary.setCallbackData("buttonSedentary");
        buttonSimpleWorkouts.setCallbackData("buttonSimpleWorkouts");
        buttonLoadWorkouts.setCallbackData("buttonLoadWorkouts");
        buttonIntensiveWorkouts.setCallbackData("buttonIntensiveWorkouts");

        List<InlineKeyboardButton> keyboardButtonsFirstRow = new ArrayList<>();
        keyboardButtonsFirstRow.add(buttonSedentary);
        keyboardButtonsFirstRow.add(buttonSimpleWorkouts);

        List<InlineKeyboardButton> keyboardButtonsSecondRow = new ArrayList<>();

        keyboardButtonsSecondRow.add(buttonLoadWorkouts);
        keyboardButtonsSecondRow.add(buttonIntensiveWorkouts);

        List<List<InlineKeyboardButton>> rowList = new ArrayList<>();
        rowList.add(keyboardButtonsFirstRow);
        rowList.add(keyboardButtonsSecondRow);

        inlineKeyboardMarkup.setKeyboard(rowList);

        return inlineKeyboardMarkup;
    }
}
