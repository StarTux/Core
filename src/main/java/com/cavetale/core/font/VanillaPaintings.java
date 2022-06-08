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
    ALBAN(Art.ALBAN, 0xE186),
    AZTEC2(Art.AZTEC2, 0xE187),
    AZTEC(Art.AZTEC, 0xE188),
    BOMB(Art.BOMB, 0xE189),
    BURNING_SKULL(Art.BURNING_SKULL, 0xE18A),
    BUST(Art.BUST, 0xE18B),
    COURBET(Art.COURBET, 0xE18C),
    CREEBET(Art.CREEBET, 0xE18D),
    DONKEY_KONG(Art.DONKEY_KONG, 0xE18E),
    FIGHTERS(Art.FIGHTERS, 0xE18F),
    GRAHAM(Art.GRAHAM, 0xE190),
    KEBAB(Art.KEBAB, 0xE191),
    MATCH(Art.MATCH, 0xE192),
    PIGSCENE(Art.PIGSCENE, 0xE193),
    PLANT(Art.PLANT, 0xE194),
    POINTER(Art.POINTER, 0xE195),
    POOL(Art.POOL, 0xE196),
    SEA(Art.SEA, 0xE197),
    SKELETON(Art.SKELETON, 0xE198),
    SKULL_AND_ROSES(Art.SKULL_AND_ROSES, 0xE199),
    STAGE(Art.STAGE, 0xE19A),
    SUNSET(Art.SUNSET, 0xE19B),
    VOID(Art.VOID, 0xE19C),
    WANDERER(Art.WANDERER, 0xE19D),
    WASTELAND(Art.WASTELAND, 0xE19E),
    WITHER(Art.WITHER, 0xE19F),
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

    VanillaPaintings(final Art art, final int character) {
        this.art = art;
        this.emojiName = name().toLowerCase() + "_painting";
        this.filename = "minecraft:painting/" + name().toLowerCase();
        this.character = (char) character;
        this.component = Component.text((char) character)
            .style(Style.style()
                   .font(Key.key("cavetale:default"))
                   .color(NamedTextColor.WHITE));
        this.displayName = Component.text(toCamelCase(" ", this));
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
