package com.cavetale.core.font;

public enum GlyphPolicy {
    PUBLIC,
    HIDDEN;

    public boolean entails(GlyphPolicy other) {
        return other == this || ordinal() >= other.ordinal();
    }
}
