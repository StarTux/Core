package com.cavetale.core.font;

import net.kyori.adventure.key.Key;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.ComponentLike;
import net.kyori.adventure.text.JoinConfiguration;
import net.kyori.adventure.text.format.TextColor;

public enum GuiOverlay {
    BLANK(new DefaultFont[] {
            DefaultFont.GUI_BLANK_9,
            DefaultFont.GUI_BLANK_18,
            DefaultFont.GUI_BLANK_27,
            DefaultFont.GUI_BLANK_36,
            DefaultFont.GUI_BLANK_45,
            DefaultFont.GUI_BLANK_54,
        }),
    TOP_BAR(new DefaultFont[] {
            DefaultFont.GUI_TOP_BAR_9,
            DefaultFont.GUI_TOP_BAR_18,
            DefaultFont.GUI_TOP_BAR_27,
            DefaultFont.GUI_TOP_BAR_36,
            DefaultFont.GUI_TOP_BAR_45,
            DefaultFont.GUI_TOP_BAR_54,
        }),
    RAID_REWARD(new DefaultFont[] {
            null,
            null,
            DefaultFont.GUI_RAID_REWARD,
            null,
            null,
            null,
        });

    public final DefaultFont[] scales;

    GuiOverlay(final DefaultFont[] scales) {
        this.scales = scales;
    }

    public Component make(int guiSize, TextColor color, ComponentLike title) {
        int index = guiSize / 9 - 1;
        if (index < 0 || index >= scales.length) {
            throw new IllegalArgumentException("Index out of bounds: " + index);
        }
        DefaultFont glyph = scales[index];
        if (glyph == null) {
            throw new IllegalArgumentException("Size not available: " + guiSize);
        }
        ComponentLike overlay = Component.text()
            .font(Key.key("cavetale:default"))
            .color(color)
            .content(DefaultFont.BACKSPACE_10.string + glyph.string + DefaultFont.BACKSPACE_171.string);
        return Component.join(JoinConfiguration.noSeparators(), overlay, title);
    }
}
