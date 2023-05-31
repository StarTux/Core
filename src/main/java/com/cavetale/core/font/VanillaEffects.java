package com.cavetale.core.font;

import java.util.EnumMap;
import java.util.IdentityHashMap;
import java.util.Map;
import lombok.Getter;
import net.kyori.adventure.key.Key;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.ComponentLike;
import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.text.format.Style;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.potion.PotionType;
import static com.cavetale.core.util.CamelCase.toCamelCase;

@Getter
public enum VanillaEffects implements Font, ComponentLike {
    ABSORPTION(PotionEffectType.ABSORPTION, 0xE167),
    BAD_OMEN(PotionEffectType.BAD_OMEN, 0xE168),
    BLINDNESS(PotionEffectType.BLINDNESS, 0xE169),
    CONDUIT_POWER(PotionEffectType.CONDUIT_POWER, 0xE16A),
    DOLPHINS_GRACE(PotionEffectType.DOLPHINS_GRACE, 0xE16B),
    FIRE_RESISTANCE(PotionEffectType.FIRE_RESISTANCE, PotionType.FIRE_RESISTANCE, 0xE16C),
    GLOWING(PotionEffectType.GLOWING, 0xE16D),
    HASTE(PotionEffectType.FAST_DIGGING, 0xE16E),
    HEALTH_BOOST(PotionEffectType.HEALTH_BOOST, 0xE16F),
    HERO_OF_THE_VILLAGE(PotionEffectType.HERO_OF_THE_VILLAGE, 0xE170),
    HUNGER(PotionEffectType.HUNGER, 0xE171),
    INSTANT_DAMAGE(PotionEffectType.HARM, PotionType.INSTANT_DAMAGE, 0xE172),
    INSTANT_HEALTH(PotionEffectType.HEAL, 0xE173),
    INVISIBILITY(PotionEffectType.INVISIBILITY, PotionType.INVISIBILITY, 0xE174),
    JUMP_BOOST(PotionEffectType.JUMP, PotionType.JUMP, 0xE175),
    LEVITATION(PotionEffectType.LEVITATION, 0xE176),
    LUCK(PotionEffectType.LUCK, PotionType.LUCK, 0xE177),
    MINING_FATIGUE(PotionEffectType.SLOW_DIGGING, 0xE178),
    NAUSEA(PotionEffectType.CONFUSION, 0xE179),
    NIGHT_VISION(PotionEffectType.NIGHT_VISION, PotionType.NIGHT_VISION, 0xE17A),
    POISON(PotionEffectType.POISON, PotionType.POISON, 0xE17B),
    REGENERATION(PotionEffectType.REGENERATION, PotionType.REGEN, 0xE17C),
    RESISTANCE(PotionEffectType.DAMAGE_RESISTANCE, 0xE17D),
    SATURATION(PotionEffectType.SATURATION, 0xE17E),
    SLOW_FALLING(PotionEffectType.SLOW_FALLING, PotionType.SLOW_FALLING, 0xE17F),
    SLOWNESS(PotionEffectType.SLOW, PotionType.SLOWNESS, 0xE180),
    SPEED(PotionEffectType.SPEED, PotionType.SPEED, 0xE181),
    STRENGTH(PotionEffectType.INCREASE_DAMAGE, PotionType.STRENGTH, 0xE182),
    UNLUCK(PotionEffectType.UNLUCK, 0xE183),
    WATER_BREATHING(PotionEffectType.WATER_BREATHING, PotionType.WATER_BREATHING, 0xE184),
    WEAKNESS(PotionEffectType.WEAKNESS, PotionType.WEAKNESS, 0xE185),
    ;

    public final PotionEffectType potionEffectType;
    public final PotionType potionType;
    public final String emojiName;
    public final String filename;
    public final int ascent = 8;
    public final int height = 9; // texture size = 18x18
    public final char character;
    public final Component component;
    public final Component displayName;
    private static final Map<PotionEffectType, VanillaEffects> EFFECT_MAP = new IdentityHashMap<>();
    private static final Map<PotionType, VanillaEffects> POTION_MAP = new EnumMap<>(PotionType.class);

    VanillaEffects(final PotionEffectType potionEffectType, final PotionType potionType, final int character) {
        this.potionEffectType = potionEffectType;
        this.potionType = potionType;
        this.emojiName = name().toLowerCase() + "_effect";
        this.filename = "minecraft:mob_effect/" + name().toLowerCase();
        this.character = (char) character;
        this.component = Component.text((char) character)
            .style(Style.style()
                   .font(Key.key("cavetale:default"))
                   .color(NamedTextColor.WHITE));
        this.displayName = Component.text(toCamelCase(" ", this));
    }

    VanillaEffects(final PotionEffectType potionEffectType, final int character) {
        this(potionEffectType, null, character);
    }

    static {
        for (VanillaEffects it : values()) {
            EFFECT_MAP.put(it.potionEffectType, it);
            if (it.potionType != null) {
                POTION_MAP.put(it.potionType, it);
            }
        }
    }

    public static VanillaEffects of(PotionEffectType type) {
        return EFFECT_MAP.get(type);
    }

    public static VanillaEffects of(PotionType type) {
        return POTION_MAP.get(type);
    }

    public static Component componentOf(PotionEffectType type) {
        VanillaEffects inst = EFFECT_MAP.get(type);
        return inst != null
            ? inst.component
            : Component.empty();
    }

    public static Component componentOf(PotionType type) {
        VanillaEffects inst = POTION_MAP.get(type);
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
        return "Potion Effect";
    }

    @Override
    public int getPixelWidth() {
        return 18;
    }
}
