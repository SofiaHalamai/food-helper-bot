package ua.com.alevel.food_supporter_bot.util;

import com.vdurmont.emoji.EmojiParser;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public enum Emojis {
    SPARKLES(EmojiParser.parseToUnicode(":sparkles:")),
    WINK(EmojiParser.parseToUnicode(":wink:")),
    CRY(EmojiParser.parseToUnicode(":cry:"));

    private String emojiName;

    @Override
    public String toString() {
        return emojiName;
    }
}
