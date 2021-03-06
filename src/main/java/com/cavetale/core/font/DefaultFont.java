package com.cavetale.core.font;

import lombok.Getter;
import net.kyori.adventure.key.Key;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.text.format.Style;
import net.kyori.adventure.text.format.TextColor;

/**
 * GUI codes start at 0xE001.
 * Other titles start at 0xE100.
 * Due to superstition, the XX00 codes have been omitted.
 */
@Getter
public enum DefaultFont implements Font {
    BACKSPACE_10("mytems:item/space", -32768, -10, '\uE001', GlyphPolicy.HIDDEN), // Inv title to left edge
    BACKSPACE_171("mytems:item/space", -32768, -171, '\uE002', GlyphPolicy.HIDDEN), // Inv right edge to title
    BACKSPACE_3("mytems:item/space", -32768, -3, '\uE003', GlyphPolicy.HIDDEN),
    GUI_RAID_REWARD("mytems:item/gui_raid_reward", 130, 256, '\uE101', GlyphPolicy.HIDDEN),
    GUI_BLANK_9("mytems:item/gui_blank_9", 130, 256, '\uE011', GlyphPolicy.HIDDEN),
    GUI_BLANK_18("mytems:item/gui_blank_18", 130, 256, '\uE012', GlyphPolicy.HIDDEN),
    GUI_BLANK_27("mytems:item/gui_blank_27", 130, 256, '\uE013', GlyphPolicy.HIDDEN),
    GUI_BLANK_36("mytems:item/gui_blank_36", 130, 256, '\uE014', GlyphPolicy.HIDDEN),
    GUI_BLANK_45("mytems:item/gui_blank_45", 130, 256, '\uE015', GlyphPolicy.HIDDEN),
    GUI_BLANK_54("mytems:item/gui_blank_54", 130, 256, '\uE016', GlyphPolicy.HIDDEN),
    @Deprecated EASTER_EGG("mytems:item/easter_egg", 8, 8, '\uE102'),
    @Deprecated EASTER_BUNNY("mytems:item/easter_token", 8, 8, '\uE103'),
    @Deprecated KITTY_COIN("mytems:item/kitty_coin", 8, 8, '\uE104'),
    @Deprecated EARTH("mytems:item/earth", 8, 8, '\uE105'),
    // Flags
    BRITAIN("mytems:item/britain", 8, 8, '\uE106'),
    SPAIN("mytems:item/spain", 8, 8, '\uE107'),
    MEXICO("mytems:item/mexico", 8, 8, '\uE108'),
    USA("mytems:item/usa", 8, 8, '\uE109'),
    AUSTRIA("mytems:item/austria", 8, 8, '\uE10B'),
    BELGIUM("mytems:item/belgium", 8, 8, '\uE10C'),
    DENMARK("mytems:item/denmark", 8, 8, '\uE10D'),
    EUROPE("mytems:item/europe", 8, 8, '\uE10E'),
    FRANCE("mytems:item/france", 8, 8, '\uE10F'),
    GERMANY("mytems:item/germany", 8, 8, '\uE110'),
    IRELAND("mytems:item/ireland", 8, 8, '\uE111'),
    ITALY("mytems:item/italy", 8, 8, '\uE112'),
    NORWAY("mytems:item/norway", 8, 8, '\uE113'),
    POLAND("mytems:item/poland", 8, 8, '\uE114'),
    SWEDEN("mytems:item/sweden", 8, 8, '\uE115'),
    SWITZERLAND("mytems:item/switzerland", 8, 8, '\uE116'),
    PRIDE_FLAG("mytems:item/pride_flag", 7, 8, '\uE119'),
    TRANS_PRIDE_FLAG("mytems:item/trans_pride_flag", 7, 8, '\uE11A'),
    ENGLAND("mytems:item/england", 8, 8, '\uE11B'),
    // Ranks
    ADMIN("mytems:item/admin", 7, 8, '\uE10A'),
    MODERATOR("mytems:item/moderator", 7, 8, '\uE117'),
    TRUSTED("mytems:item/trusted", 7, 8, '\uE118');
    // Next title unicode character: 0xE11C

    public final String filename;
    public final int ascent;
    public final int height;
    public final char character;
    public final GlyphPolicy policy;
    public final Component component;

    DefaultFont(final String filename, final int ascent, final int height, final char character, final GlyphPolicy policy) {
        this.filename = filename;
        this.ascent = ascent;
        this.height = height;
        this.character = character;
        this.policy = policy;
        this.component = Component.text(character)
            .style(Style.style()
                   .font(Key.key("cavetale:default"))
                   .color(TextColor.color(0xFFFFFF)));
    }

    DefaultFont(final String filename, final int ascent, final int height, final char character) {
        this(filename, ascent, height, character, GlyphPolicy.PUBLIC);
    }

    public static Component guiOverlay(DefaultFont glyph, TextColor color) {
        return Component.text()
            .font(Key.key("cavetale:default"))
            .color(color)
            .content("" + BACKSPACE_10.character + glyph.character + BACKSPACE_171.character)
            .build();
    }

    public static Component guiOverlay(DefaultFont glyph) {
        return guiOverlay(glyph, NamedTextColor.WHITE);
    }

    public static Component guiBlankOverlay(int guiSize, TextColor color) {
        DefaultFont glyph;
        switch (guiSize) {
        case 9: glyph = DefaultFont.GUI_BLANK_9; break;
        case 18: glyph = DefaultFont.GUI_BLANK_18; break;
        case 27: glyph = DefaultFont.GUI_BLANK_27; break;
        case 36: glyph = DefaultFont.GUI_BLANK_36; break;
        case 45: glyph = DefaultFont.GUI_BLANK_45; break;
        case 54: glyph = DefaultFont.GUI_BLANK_54; break;
        default: throw new IllegalArgumentException("guiSize=" + guiSize);
        }
        return Component.text()
            .font(Key.key("cavetale:default"))
            .color(color)
            .content("" + BACKSPACE_10.character + glyph.character + BACKSPACE_171.character)
            .build();
    }

    public static Component guiBlankOverlay(int guiSize) {
        return guiBlankOverlay(guiSize, NamedTextColor.WHITE);
    }

    public static DefaultFont of(String key) {
        try {
            return DefaultFont.valueOf(key.toUpperCase());
        } catch (IllegalArgumentException iae) {
            return null;
        }
    }
}
