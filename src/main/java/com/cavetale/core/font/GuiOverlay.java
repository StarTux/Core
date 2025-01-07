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
import static net.kyori.adventure.text.format.NamedTextColor.*;

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
    WHITE(new DefaultFont[] {
            DefaultFont.GUI_WHITE_9,
            DefaultFont.GUI_WHITE_18,
            DefaultFont.GUI_WHITE_27,
            DefaultFont.GUI_WHITE_36,
            DefaultFont.GUI_WHITE_45,
            DefaultFont.GUI_WHITE_54,
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
    TITLE_BAR(new DefaultFont[] {
            DefaultFont.GUI_TITLE_BAR,
            DefaultFont.GUI_TITLE_BAR,
            DefaultFont.GUI_TITLE_BAR,
            DefaultFont.GUI_TITLE_BAR,
            DefaultFont.GUI_TITLE_BAR,
            DefaultFont.GUI_TITLE_BAR,
        },
        DefaultFont.BACKSPACE_174,
        DefaultFont.BACKSPACE_166),
    TAB_BG(DefaultFont.GUI_TAB_BG),
    TAB_0(DefaultFont.GUI_TAB_0),
    TAB_1(DefaultFont.GUI_TAB_1),
    TAB_2(DefaultFont.GUI_TAB_2),
    TAB_3(DefaultFont.GUI_TAB_3),
    TAB_4(DefaultFont.GUI_TAB_4),
    TAB_5(DefaultFont.GUI_TAB_5),
    TAB_6(DefaultFont.GUI_TAB_6),
    TAB_7(DefaultFont.GUI_TAB_7),
    TAB_8(DefaultFont.GUI_TAB_8),
    RAID_REWARD(new DefaultFont[] {
            null,
            null,
            DefaultFont.GUI_RAID_REWARD,
            null,
            null,
            null,
        },
        DefaultFont.BACKSPACE_179,
        DefaultFont.BACKSPACE_171),
    HOLES(new DefaultFont[] {
            DefaultFont.GUI_HOLES_9,
            DefaultFont.GUI_HOLES_18,
            DefaultFont.GUI_HOLES_27,
            DefaultFont.GUI_HOLES_36,
            DefaultFont.GUI_HOLES_45,
            DefaultFont.GUI_HOLES_54,
        },
        DefaultFont.BACKSPACE_179,
        DefaultFont.BACKSPACE_171),
    ITEM_COLLECTION(new DefaultFont[] {null, null, null, null, null, DefaultFont.GUI_ITEM_COLLECTION},
                    DefaultFont.BACKSPACE_179, DefaultFont.BACKSPACE_171),
    DAILY_GAME_RED(new DefaultFont[] {null, null, null, null, null, DefaultFont.GUI_DAILY_GAME_RED},
                   DefaultFont.BACKSPACE_179, DefaultFont.BACKSPACE_171),
    DAILY_GAME_LUDO(new DefaultFont[] {null, null, null, null, null, DefaultFont.GUI_DAILY_GAME_LUDO},
                    DefaultFont.BACKSPACE_179, DefaultFont.BACKSPACE_171),
    DAILY_GAME_SPIRAL(new DefaultFont[] {null, null, null, null, null, DefaultFont.GUI_DAILY_GAME_SPIRAL},
                      DefaultFont.BACKSPACE_179, DefaultFont.BACKSPACE_171),
    CRAFTING_PARITY(DefaultFont.GUI_CRAFT_PARITY),
    COMBINER(DefaultFont.GUI_COMBINER),
    CHECKERED_8(DefaultFont.GUI_CHECKERED_8),
    RACE_MAP_MENU(DefaultFont.GUI_RACE_MAP_MENU),
    ;

    public final DefaultFont[] scales;
    public final DefaultFont backToLeftEdge;
    public final DefaultFont backToTitle;

    GuiOverlay(final DefaultFont f) {
        this(new DefaultFont[] {f, f, f, f, f, f}, DefaultFont.BACKSPACE_179, DefaultFont.BACKSPACE_171);
    }

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

    public static GuiOverlay tab(int tab) {
        return switch (tab) {
        case 0 -> TAB_0;
        case 1 -> TAB_1;
        case 2 -> TAB_2;
        case 3 -> TAB_3;
        case 4 -> TAB_4;
        case 5 -> TAB_5;
        case 6 -> TAB_6;
        case 7 -> TAB_7;
        case 8 -> TAB_8;
        default -> throw new IllegalArgumentException("tab=" + tab);
        };
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
            layers.add(new Layer(overlay.getScale(guiSize), color,
                                 overlay.backToLeftEdge, overlay.backToTitle));
            return this;
        }

        public Builder highlightSlot(int slotIndex, TextColor color) {
            int ordinal = DefaultFont.GUI_HIGHLIGHT_SLOT_0.ordinal();
            DefaultFont glyph = DefaultFont.values()[ordinal + slotIndex];
            layers.add(new Layer(glyph, color, DefaultFont.BACKSPACE_179, DefaultFont.BACKSPACE_171));
            return this;
        }

        public Builder highlightSlot(int x, int y, TextColor color) {
            return highlightSlot(x + y * 9, color);
        }

        public Builder tab(int tab, TextColor fg, TextColor bg) {
            return layer(GuiOverlay.TAB_BG, bg)
                .layer(GuiOverlay.tab(tab), fg);
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
                text.append(layer.glyph.string);
                if (i < layers.size() - 1) {
                    text.append(layer.backToLeftEdge.string);
                } else {
                    text.append(layer.backToTitle.string);
                }
                result.append(Component.text(text.toString(), layer.color));
            }
            return Component.join(JoinConfiguration.noSeparators(), result, title);
        }
    }

    private record Layer(DefaultFont glyph, TextColor color,
                         DefaultFont backToLeftEdge, DefaultFont backToTitle) { }
}
