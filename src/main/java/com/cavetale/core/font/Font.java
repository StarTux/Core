package com.cavetale.core.font;

public interface Font {
    int MIN_ASCENT = -32768;

    String getFilename();
    int getAscent();
    int getHeight();
    char getCharacter();
    GlyphPolicy getPolicy();
}
