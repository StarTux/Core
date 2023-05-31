package com.cavetale.core.font;

import net.kyori.adventure.text.Component;

public interface Font {
    int MIN_ASCENT = -32768;

    String getEmojiName();
    Component getComponent();
    Component getDisplayName();
    String getCategory();
    String getFilename();
    int getAscent();
    int getHeight();
    int getRows();
    char getCharacter();
    GlyphPolicy getPolicy();
    int getPixelWidth();
}
