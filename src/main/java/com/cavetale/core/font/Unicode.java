package com.cavetale.core.font;

public enum Unicode {
    COPYRIGHT('\u00A9'),
    TRADEMARK('\u2122'),
    SMALL_HEART('\u2665'),
    SMALL_EMPTY_HEART('\u2661'),
    HEART('\u2764'),
    SMILE('\u263A'),
    SMILE_INVERT('\u263B'),
    FROWN('\u2639'),
    DICE_1('\u2680'),
    DICE_2('\u2681'),
    DICE_3('\u2682'),
    DICE_4('\u2683'),
    DICE_5('\u2684'),
    DICE_6('\u2685'),
    STAR('\u2605'),
    EMPTY_STAR('\u2606'),
    SHAMROCK('\u2618'),
    CLOUD('\u2601'),
    YIN_YANG('\u262F'),
    SKULL('\u2620'),
    PEACE('\u262E'),
    QUARTER_NOTE('\u2669'),
    EIGHTH_NOTE('\u266A'),
    SUN('\u2660'),
    PILCROW('\u00B6'),
    REVERSE_PILCROW('\u204B'),
    EURO('\u20AC'),
    EURO_CENT('\u20A0'),
    CENT('\u00A2'),
    CHECKERS_WHITE('\u26C0'),
    CHECKERS_WHITE_KING('\u26C1'),
    CHECKERS_BLACK('\u26C2'),
    CHECKERS_BLACK_KING('\u26C3'),
    ARROW_LEFT('\u2190'),
    ARROW_UP('\u2110'),
    ARROW_RIGHT('\u2192'),
    ARROW_DOWN('\u2193'),
    ARROW_LEFT_RIGHT('\u2194'),
    ARROW_UP_DOWN('\u2195'),
    ARROW_UP_LEFT('\u2196'),
    ARROW_UP_RIGHT('\u2197'),
    ARROW_DOWN_RIGHT('\u2198'),
    ARROW_DOWN_LEFT('\u2199');

    public final String key;
    public final char character;

    Unicode(final char character) {
        this.key = name().toLowerCase();
        this.character = character;
    }
}
