package com.cavetale.core.font;

import java.util.EnumMap;
import java.util.Map;
import lombok.Getter;
import net.kyori.adventure.key.Key;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.ComponentLike;
import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.text.format.Style;
import org.bukkit.Art;
import static com.cavetale.core.util.CamelCase.toCamelCase;

@Getter
public enum VanillaPaintings implements Font, ComponentLike {
    ALBAN(Art.ALBAN, 0xE186, 16),
    AZTEC2(Art.AZTEC2, 0xE187, 16),
    AZTEC(Art.AZTEC, 0xE188, 16),
    BOMB(Art.BOMB, 0xE189, 16),
    BURNING_SKULL(Art.BURNING_SKULL, 0xE18A, 16),
    BUST(Art.BUST, 0xE18B, 16),
    COURBET(Art.COURBET, 0xE18C, 32),
    CREEBET(Art.CREEBET, 0xE18D, 32),
    DONKEY_KONG(Art.DONKEY_KONG, 0xE18E, 21),
    FIGHTERS(Art.FIGHTERS, 0xE18F, 32),
    GRAHAM(Art.GRAHAM, 0xE190, 8),
    KEBAB(Art.KEBAB, 0xE191, 16),
    MATCH(Art.MATCH, 0xE192, 16),
    PIGSCENE(Art.PIGSCENE, 0xE193, 16),
    PLANT(Art.PLANT, 0xE194, 16),
    POINTER(Art.POINTER, 0xE195, 16),
    POOL(Art.POOL, 0xE196, 32),
    SEA(Art.SEA, 0xE197, 32),
    SKELETON(Art.SKELETON, 0xE198, 21),
    SKULL_AND_ROSES(Art.SKULL_AND_ROSES, 0xE199, 16),
    STAGE(Art.STAGE, 0xE19A, 16),
    SUNSET(Art.SUNSET, 0xE19B, 32),
    VOID(Art.VOID, 0xE19C, 16),
    WANDERER(Art.WANDERER, 0xE19D, 8),
    WASTELAND(Art.WASTELAND, 0xE19E, 16),
    WITHER(Art.WITHER, 0xE19F, 16),
    EARTH(Art.EARTH, 0xE1A0, 16),
    WIND(Art.WIND, 0xE1A1, 16),
    WATER(Art.WATER, 0xE1A2, 16),
    FIRE(Art.FIRE, 0xE1A3, 16),
    ;

    public final Art art;
    public final String emojiName;
    public final String filename;
    public final int ascent = 8;
    public final int height = 8;
    public final char character;
    public final Component component;
    public final Component displayName;
    private static final Map<Art, VanillaPaintings> ART_MAP = new EnumMap<>(Art.class);
    public final int pixelWidth;

    VanillaPaintings(final Art art, final int character, final int pixelWidth) {
        this.art = art;
        this.emojiName = name().toLowerCase() + "_painting";
        this.filename = "minecraft:painting/" + name().toLowerCase();
        this.character = (char) character;
        this.component = Component.text((char) character)
            .style(Style.style()
                   .font(Key.key("cavetale:default"))
                   .color(NamedTextColor.WHITE));
        this.displayName = Component.text(toCamelCase(" ", this));
        this.pixelWidth = pixelWidth;
    }

    static {
        for (VanillaPaintings it : values()) {
            ART_MAP.put(it.art, it);
        }
    }

    public static VanillaPaintings of(Art art) {
        return ART_MAP.get(art);
    }

    public static Component componentOf(Art art) {
        VanillaPaintings inst = ART_MAP.get(art);
        return inst != null
            ? inst.component
            : Component.empty();
    }

    @Override
    public int getRows() {
        return 1;
    }

    @Override
    public GlyphPolicy getPolicy() {
        return GlyphPolicy.PUBLIC;
    }

    @Override
    public Component asComponent() {
        return component;
    }

    @Override
    public String getCategory() {
        return "Painting";
    }
}
