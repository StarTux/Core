package com.cavetale.core.font;

import java.util.ArrayList;
import java.util.List;
import lombok.RequiredArgsConstructor;
import net.kyori.adventure.key.Key;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.ComponentLike;
import net.kyori.adventure.text.JoinConfiguration;
import net.kyori.adventure.text.TextComponent;
import net.kyori.adventure.text.format.TextColor;

@RequiredArgsConstructor
public enum GuiOverlay {
    BLANK(new DefaultFont[] {
            DefaultFont.GUI_BLANK_9,
            DefaultFont.GUI_BLANK_18,
            DefaultFont.GUI_BLANK_27,
            DefaultFont.GUI_BLANK_36,
            DefaultFont.GUI_BLANK_45,
            DefaultFont.GUI_BLANK_54,
        },
        DefaultFont.BACKSPACE_179,
        DefaultFont.BACKSPACE_171),
    TOP_BAR(new DefaultFont[] {
            DefaultFont.GUI_TOP_BAR_9,
            DefaultFont.GUI_TOP_BAR_18,
            DefaultFont.GUI_TOP_BAR_27,
            DefaultFont.GUI_TOP_BAR_36,
            DefaultFont.GUI_TOP_BAR_45,
            DefaultFont.GUI_TOP_BAR_54,
        },
        DefaultFont.BACKSPACE_174,
        DefaultFont.BACKSPACE_166),
    RAID_REWARD(new DefaultFont[] {
            null,
            null,
            DefaultFont.GUI_RAID_REWARD,
            null,
            null,
            null,
        },
        DefaultFont.BACKSPACE_179,
        DefaultFont.BACKSPACE_171);

    public final DefaultFont[] scales;
    public final DefaultFont backToLeftEdge;
    public final DefaultFont backToTitle;

    public DefaultFont getScale(int guiSize) {
        int index = guiSize / 9 - 1;
        if (index < 0 || index >= scales.length) {
            throw new IllegalArgumentException("Index out of bounds: " + index);
        }
        DefaultFont glyph = scales[index];
        if (glyph == null) {
            throw new IllegalArgumentException("Size not available: " + guiSize);
        }
        return glyph;
    }

    public Component make(int guiSize, TextColor color, ComponentLike title) {
        DefaultFont glyph = getScale(guiSize);
        ComponentLike overlay = Component.text()
            .font(Key.key("cavetale:default"))
            .color(color)
            .content(DefaultFont.BACKSPACE_10.string + glyph.string + backToTitle.string);
        return Component.join(JoinConfiguration.noSeparators(), overlay, title);
    }

    public Builder builder(final int guiSize, final TextColor color) {
        Builder result = new Builder(guiSize);
        return result.layer(this, color);
    }

    public static Builder builder(final int guiSize) {
        return new Builder(guiSize);
    }

    @RequiredArgsConstructor
    public static final class Builder {
        protected final int guiSize;
        protected final List<Layer> layers = new ArrayList<>();
        protected Component title = Component.empty();

        public Builder layer(GuiOverlay overlay, TextColor color) {
            layers.add(new Layer(overlay, color));
            return this;
        }

        public Builder title(Component text) {
            this.title = text;
            return this;
        }

        public Component build() {
            TextComponent.Builder result = Component.text().font(Key.key("cavetale:default"));
            for (int i = 0; i < layers.size(); i += 1) {
                Layer layer = layers.get(i);
                StringBuilder text = new StringBuilder();
                if (i == 0) text.append(DefaultFont.BACKSPACE_10.string);
                text.append(layer.overlay.getScale(guiSize).string);
                if (i < layers.size() - 1) {
                    text.append(layer.overlay.backToLeftEdge.string);
                } else {
                    text.append(layer.overlay.backToTitle.string);
                }
                result.append(Component.text(text.toString(), layer.color));
            }
            return Component.join(JoinConfiguration.noSeparators(), result, title);
        }
    }

    public static record Layer(GuiOverlay overlay, TextColor color) { }
}
