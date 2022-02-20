package com.cavetale.core.font;

import lombok.Getter;
import net.kyori.adventure.key.Key;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.JoinConfiguration;
import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.text.format.Style;
import net.kyori.adventure.text.format.TextColor;
import static com.cavetale.core.font.Globals.*;

/**
 * GUI codes start at 0xE001.
 * Other titles start at 0xE100.
 * Due to superstition, the XX00 codes have been omitted.
 */
@Getter
public enum DefaultFont implements Font {
    /** Inventory title to left edge. */
    BACKSPACE_10("mytems:item/space", -32768, -10, '\uE001', GlyphPolicy.HIDDEN),
    /** Inventory right edge to title. */
    BACKSPACE_171("mytems:item/space", -32768, -171, '\uE002', GlyphPolicy.HIDDEN),
    BACKSPACE_3("mytems:item/space", -32768, -3, '\uE003', GlyphPolicy.HIDDEN),
    /** Inventory right edge to left. */
    BACKSPACE_179("mytems:item/space", -32768, -179, '\uE004', GlyphPolicy.HIDDEN),
    /** Top Bar right edge to left. */
    BACKSPACE_174("mytems:item/space", -32768, -174, '\uE005', GlyphPolicy.HIDDEN),
    /** Top Bar right edge to title. */
    BACKSPACE_166("mytems:item/space", -32768, -166, '\uE006', GlyphPolicy.HIDDEN),
    GUI_RAID_REWARD("mytems:item/gui_raid_reward", 130, 256, '\uE101', GlyphPolicy.HIDDEN),
    // Blank
    GUI_BLANK_9("mytems:item/gui_blank_9", 130, 256, '\uE011', GlyphPolicy.HIDDEN),
    GUI_BLANK_18("mytems:item/gui_blank_18", 130, 256, '\uE012', GlyphPolicy.HIDDEN),
    GUI_BLANK_27("mytems:item/gui_blank_27", 130, 256, '\uE013', GlyphPolicy.HIDDEN),
    GUI_BLANK_36("mytems:item/gui_blank_36", 130, 256, '\uE014', GlyphPolicy.HIDDEN),
    GUI_BLANK_45("mytems:item/gui_blank_45", 130, 256, '\uE015', GlyphPolicy.HIDDEN),
    GUI_BLANK_54("mytems:item/gui_blank_54", 130, 256, '\uE016', GlyphPolicy.HIDDEN),
    // Top Bar
    GUI_TOP_BAR_9("mytems:item/gui_top_bar_9", 130, 256, '\uE017', GlyphPolicy.HIDDEN),
    GUI_TOP_BAR_18("mytems:item/gui_top_bar_18", 130, 256, '\uE018', GlyphPolicy.HIDDEN),
    GUI_TOP_BAR_27("mytems:item/gui_top_bar_27", 130, 256, '\uE019', GlyphPolicy.HIDDEN),
    GUI_TOP_BAR_36("mytems:item/gui_top_bar_36", 130, 256, '\uE01A', GlyphPolicy.HIDDEN),
    GUI_TOP_BAR_45("mytems:item/gui_top_bar_45", 130, 256, '\uE01B', GlyphPolicy.HIDDEN),
    GUI_TOP_BAR_54("mytems:item/gui_top_bar_54", 130, 256, '\uE01C', GlyphPolicy.HIDDEN),
    // Highlight Gui Slot
    GUI_HIGHLIGHT_SLOT_0("mytems:item/gui_highlight_slot_0", 130, 256, '\uE126', GlyphPolicy.HIDDEN),
    GUI_HIGHLIGHT_SLOT_1("mytems:item/gui_highlight_slot_1", 130, 256, '\uE127', GlyphPolicy.HIDDEN),
    GUI_HIGHLIGHT_SLOT_2("mytems:item/gui_highlight_slot_2", 130, 256, '\uE128', GlyphPolicy.HIDDEN),
    GUI_HIGHLIGHT_SLOT_3("mytems:item/gui_highlight_slot_3", 130, 256, '\uE129', GlyphPolicy.HIDDEN),
    GUI_HIGHLIGHT_SLOT_4("mytems:item/gui_highlight_slot_4", 130, 256, '\uE12A', GlyphPolicy.HIDDEN),
    GUI_HIGHLIGHT_SLOT_5("mytems:item/gui_highlight_slot_5", 130, 256, '\uE12B', GlyphPolicy.HIDDEN),
    GUI_HIGHLIGHT_SLOT_6("mytems:item/gui_highlight_slot_6", 130, 256, '\uE12C', GlyphPolicy.HIDDEN),
    GUI_HIGHLIGHT_SLOT_7("mytems:item/gui_highlight_slot_7", 130, 256, '\uE12D', GlyphPolicy.HIDDEN),
    GUI_HIGHLIGHT_SLOT_8("mytems:item/gui_highlight_slot_8", 130, 256, '\uE12E', GlyphPolicy.HIDDEN),
    GUI_HIGHLIGHT_SLOT_9("mytems:item/gui_highlight_slot_9", 130, 256, '\uE12F', GlyphPolicy.HIDDEN),
    GUI_HIGHLIGHT_SLOT_10("mytems:item/gui_highlight_slot_10", 130, 256, '\uE130', GlyphPolicy.HIDDEN),
    GUI_HIGHLIGHT_SLOT_11("mytems:item/gui_highlight_slot_11", 130, 256, '\uE131', GlyphPolicy.HIDDEN),
    GUI_HIGHLIGHT_SLOT_12("mytems:item/gui_highlight_slot_12", 130, 256, '\uE132', GlyphPolicy.HIDDEN),
    GUI_HIGHLIGHT_SLOT_13("mytems:item/gui_highlight_slot_13", 130, 256, '\uE133', GlyphPolicy.HIDDEN),
    GUI_HIGHLIGHT_SLOT_14("mytems:item/gui_highlight_slot_14", 130, 256, '\uE134', GlyphPolicy.HIDDEN),
    GUI_HIGHLIGHT_SLOT_15("mytems:item/gui_highlight_slot_15", 130, 256, '\uE135', GlyphPolicy.HIDDEN),
    GUI_HIGHLIGHT_SLOT_16("mytems:item/gui_highlight_slot_16", 130, 256, '\uE136', GlyphPolicy.HIDDEN),
    GUI_HIGHLIGHT_SLOT_17("mytems:item/gui_highlight_slot_17", 130, 256, '\uE137', GlyphPolicy.HIDDEN),
    GUI_HIGHLIGHT_SLOT_18("mytems:item/gui_highlight_slot_18", 130, 256, '\uE138', GlyphPolicy.HIDDEN),
    GUI_HIGHLIGHT_SLOT_19("mytems:item/gui_highlight_slot_19", 130, 256, '\uE139', GlyphPolicy.HIDDEN),
    GUI_HIGHLIGHT_SLOT_20("mytems:item/gui_highlight_slot_20", 130, 256, '\uE13A', GlyphPolicy.HIDDEN),
    GUI_HIGHLIGHT_SLOT_21("mytems:item/gui_highlight_slot_21", 130, 256, '\uE13B', GlyphPolicy.HIDDEN),
    GUI_HIGHLIGHT_SLOT_22("mytems:item/gui_highlight_slot_22", 130, 256, '\uE13C', GlyphPolicy.HIDDEN),
    GUI_HIGHLIGHT_SLOT_23("mytems:item/gui_highlight_slot_23", 130, 256, '\uE13D', GlyphPolicy.HIDDEN),
    GUI_HIGHLIGHT_SLOT_24("mytems:item/gui_highlight_slot_24", 130, 256, '\uE13E', GlyphPolicy.HIDDEN),
    GUI_HIGHLIGHT_SLOT_25("mytems:item/gui_highlight_slot_25", 130, 256, '\uE13F', GlyphPolicy.HIDDEN),
    GUI_HIGHLIGHT_SLOT_26("mytems:item/gui_highlight_slot_26", 130, 256, '\uE140', GlyphPolicy.HIDDEN),
    GUI_HIGHLIGHT_SLOT_27("mytems:item/gui_highlight_slot_27", 130, 256, '\uE141', GlyphPolicy.HIDDEN),
    GUI_HIGHLIGHT_SLOT_28("mytems:item/gui_highlight_slot_28", 130, 256, '\uE142', GlyphPolicy.HIDDEN),
    GUI_HIGHLIGHT_SLOT_29("mytems:item/gui_highlight_slot_29", 130, 256, '\uE143', GlyphPolicy.HIDDEN),
    GUI_HIGHLIGHT_SLOT_30("mytems:item/gui_highlight_slot_30", 130, 256, '\uE144', GlyphPolicy.HIDDEN),
    GUI_HIGHLIGHT_SLOT_31("mytems:item/gui_highlight_slot_31", 130, 256, '\uE145', GlyphPolicy.HIDDEN),
    GUI_HIGHLIGHT_SLOT_32("mytems:item/gui_highlight_slot_32", 130, 256, '\uE146', GlyphPolicy.HIDDEN),
    GUI_HIGHLIGHT_SLOT_33("mytems:item/gui_highlight_slot_33", 130, 256, '\uE147', GlyphPolicy.HIDDEN),
    GUI_HIGHLIGHT_SLOT_34("mytems:item/gui_highlight_slot_34", 130, 256, '\uE148', GlyphPolicy.HIDDEN),
    GUI_HIGHLIGHT_SLOT_35("mytems:item/gui_highlight_slot_35", 130, 256, '\uE149', GlyphPolicy.HIDDEN),
    GUI_HIGHLIGHT_SLOT_36("mytems:item/gui_highlight_slot_36", 130, 256, '\uE14A', GlyphPolicy.HIDDEN),
    GUI_HIGHLIGHT_SLOT_37("mytems:item/gui_highlight_slot_37", 130, 256, '\uE14B', GlyphPolicy.HIDDEN),
    GUI_HIGHLIGHT_SLOT_38("mytems:item/gui_highlight_slot_38", 130, 256, '\uE14C', GlyphPolicy.HIDDEN),
    GUI_HIGHLIGHT_SLOT_39("mytems:item/gui_highlight_slot_39", 130, 256, '\uE14D', GlyphPolicy.HIDDEN),
    GUI_HIGHLIGHT_SLOT_40("mytems:item/gui_highlight_slot_40", 130, 256, '\uE14E', GlyphPolicy.HIDDEN),
    GUI_HIGHLIGHT_SLOT_41("mytems:item/gui_highlight_slot_41", 130, 256, '\uE14F', GlyphPolicy.HIDDEN),
    GUI_HIGHLIGHT_SLOT_42("mytems:item/gui_highlight_slot_42", 130, 256, '\uE150', GlyphPolicy.HIDDEN),
    GUI_HIGHLIGHT_SLOT_43("mytems:item/gui_highlight_slot_43", 130, 256, '\uE151', GlyphPolicy.HIDDEN),
    GUI_HIGHLIGHT_SLOT_44("mytems:item/gui_highlight_slot_44", 130, 256, '\uE152', GlyphPolicy.HIDDEN),
    GUI_HIGHLIGHT_SLOT_45("mytems:item/gui_highlight_slot_45", 130, 256, '\uE153', GlyphPolicy.HIDDEN),
    GUI_HIGHLIGHT_SLOT_46("mytems:item/gui_highlight_slot_46", 130, 256, '\uE154', GlyphPolicy.HIDDEN),
    GUI_HIGHLIGHT_SLOT_47("mytems:item/gui_highlight_slot_47", 130, 256, '\uE155', GlyphPolicy.HIDDEN),
    GUI_HIGHLIGHT_SLOT_48("mytems:item/gui_highlight_slot_48", 130, 256, '\uE156', GlyphPolicy.HIDDEN),
    GUI_HIGHLIGHT_SLOT_49("mytems:item/gui_highlight_slot_49", 130, 256, '\uE157', GlyphPolicy.HIDDEN),
    GUI_HIGHLIGHT_SLOT_50("mytems:item/gui_highlight_slot_50", 130, 256, '\uE158', GlyphPolicy.HIDDEN),
    GUI_HIGHLIGHT_SLOT_51("mytems:item/gui_highlight_slot_51", 130, 256, '\uE159', GlyphPolicy.HIDDEN),
    GUI_HIGHLIGHT_SLOT_52("mytems:item/gui_highlight_slot_52", 130, 256, '\uE15A', GlyphPolicy.HIDDEN),
    GUI_HIGHLIGHT_SLOT_53("mytems:item/gui_highlight_slot_53", 130, 256, '\uE15B', GlyphPolicy.HIDDEN),
    // Flags
    BRITAIN("mytems:item/britain", 7, 8, '\uE106'),
    SPAIN("mytems:item/spain", 7, 8, '\uE107'),
    MEXICO("mytems:item/mexico", 7, 8, '\uE108'),
    USA("mytems:item/usa", 7, 8, '\uE109'),
    AUSTRIA("mytems:item/austria", 7, 8, '\uE10B'),
    BELGIUM("mytems:item/belgium", 7, 8, '\uE10C'),
    DENMARK("mytems:item/denmark", 7, 8, '\uE10D'),
    EUROPE("mytems:item/europe", 7, 8, '\uE10E'),
    FRANCE("mytems:item/france", 7, 8, '\uE10F'),
    GERMANY("mytems:item/germany", 7, 8, '\uE110'),
    IRELAND("mytems:item/ireland", 7, 8, '\uE111'),
    ITALY("mytems:item/italy", 7, 8, '\uE112'),
    NORWAY("mytems:item/norway", 7, 8, '\uE113'),
    POLAND("mytems:item/poland", 7, 8, '\uE114'),
    SWEDEN("mytems:item/sweden", 7, 8, '\uE115'),
    SWITZERLAND("mytems:item/switzerland", 7, 8, '\uE116'),
    PRIDE_FLAG("mytems:item/pride_flag", 7, 8, '\uE119'),
    TRANS_PRIDE_FLAG("mytems:item/trans_pride_flag", 7, 8, '\uE11A'),
    ENGLAND("mytems:item/england", 7, 8, '\uE11B'),
    CANADA("mytems:item/canada", 7, 8, (char) 0xE163),
    AUSTRALIA("mytems:item/australia", 7, 8, (char) 0xE164),
    NETHERLANDS("mytems:item/netherlands", 7, 8, (char) 0xE165),
    UKRAINE("mytems:item/ukraine", 7, 8, (char) 0xE166),
    // Ranks
    // Buttons
    BACK_BUTTON("mytems:item/ui_buttons", 9, 12, '\uE11E'),
    OK_BUTTON("mytems:item/ui_buttons", 9, 12, '\uE11F'),
    CANCEL_BUTTON("mytems:item/ui_buttons", 9, 12, '\uE120'),
    ABORT_BUTTON("mytems:item/ui_buttons", 9, 12, '\uE121'),
    START_BUTTON("mytems:item/ui_buttons", 9, 12, '\uE122'),
    STOP_BUTTON("mytems:item/ui_buttons", 9, 12, '\uE123'),
    ACCEPT_BUTTON("mytems:item/ui_buttons", 9, 12, '\uE124'),
    DECLINE_BUTTON("mytems:item/ui_buttons", 9, 12, '\uE125'),
    YES_BUTTON("mytems:item/ui_buttons", 9, 12, '\uE15F'),
    NO_BUTTON("mytems:item/ui_buttons", 9, 12, '\uE160'),
    // Ranks
    ADMIN(MYTEMS_ITEM_RANKS, 7, 8, (char) 0xE10A),
    MODERATOR(MYTEMS_ITEM_RANKS, 7, 8, (char) 0xE117),
    TRUSTED(MYTEMS_ITEM_RANKS, 7, 8, (char) 0xE118),
    BUILDER(MYTEMS_ITEM_RANKS, 7, 8, (char) 0xE11C),
    GOAT(MYTEMS_ITEM_RANKS, 6, 8, (char) 0xE11D),
    CELESTIAL(MYTEMS_ITEM_RANKS, 7, 8, (char) 0xE161),
    YOUTUBER(MYTEMS_ITEM_RANKS, 7, 8, (char) 0xE162),
    FRIENDLY(MYTEMS_ITEM_RANKS, 7, 8, (char) 0xE15C),
    MEMBER(MYTEMS_ITEM_RANKS, 7, 8, (char) 0xE15D),
    SPELEOLOGIST(MYTEMS_ITEM_RANKS, 7, 8, (char) 0xE15E);
    // Next title unicode character: (char) 0xE167

    public final String filename;
    public final int ascent;
    public final int height;
    public final char character;
    public final String string;
    public final GlyphPolicy policy;
    public final Component component;

    DefaultFont(final String filename, final int ascent, final int height, final char character, final GlyphPolicy policy) {
        this.filename = filename;
        this.ascent = ascent;
        this.height = height;
        this.character = character;
        this.string = "" + character;
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

    public static Component guiBlankOverlay(int guiSize, TextColor color, Component title) {
        return Component.join(JoinConfiguration.noSeparators(),
                              guiBlankOverlay(guiSize, color),
                              title);
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
