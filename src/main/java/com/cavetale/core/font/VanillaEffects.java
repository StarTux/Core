package com.cavetale.core.font;

import java.util.function.Supplier;
import lombok.Getter;
import net.kyori.adventure.key.Key;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.ComponentLike;
import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.text.format.Style;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.potion.PotionType;
import static com.cavetale.core.CorePlugin.plugin;
import static com.cavetale.core.util.CamelCase.toCamelCase;

@Getter
public enum VanillaEffects implements Font, ComponentLike {
    ABSORPTION(() -> PotionEffectType.ABSORPTION, 0xE167),
    BAD_OMEN(() -> PotionEffectType.BAD_OMEN, 0xE168),
    BLINDNESS(() -> PotionEffectType.BLINDNESS, 0xE169),
    CONDUIT_POWER(() -> PotionEffectType.CONDUIT_POWER, 0xE16A),
    DOLPHINS_GRACE(() -> PotionEffectType.DOLPHINS_GRACE, 0xE16B),
    FIRE_RESISTANCE(() -> PotionEffectType.FIRE_RESISTANCE, 0xE16C),
    GLOWING(() -> PotionEffectType.GLOWING, 0xE16D),
    HASTE(() -> PotionEffectType.HASTE, 0xE16E),
    HEALTH_BOOST(() -> PotionEffectType.HEALTH_BOOST, 0xE16F),
    HERO_OF_THE_VILLAGE(() -> PotionEffectType.HERO_OF_THE_VILLAGE, 0xE170),
    HUNGER(() -> PotionEffectType.HUNGER, 0xE171),
    INSTANT_DAMAGE(() -> PotionEffectType.INSTANT_DAMAGE, 0xE172),
    INSTANT_HEALTH(() -> PotionEffectType.INSTANT_HEALTH, 0xE173),
    INVISIBILITY(() -> PotionEffectType.INVISIBILITY, 0xE174),
    JUMP_BOOST(() -> PotionEffectType.JUMP_BOOST, 0xE175),
    LEVITATION(() -> PotionEffectType.LEVITATION, 0xE176),
    LUCK(() -> PotionEffectType.LUCK, 0xE177),
    MINING_FATIGUE(() -> PotionEffectType.MINING_FATIGUE, 0xE178),
    NAUSEA(() -> PotionEffectType.NAUSEA, 0xE179),
    NIGHT_VISION(() -> PotionEffectType.NIGHT_VISION, 0xE17A),
    POISON(() -> PotionEffectType.POISON, 0xE17B),
    REGENERATION(() -> PotionEffectType.REGENERATION, 0xE17C),
    RESISTANCE(() -> PotionEffectType.RESISTANCE, 0xE17D),
    SATURATION(() -> PotionEffectType.SATURATION, 0xE17E),
    SLOW_FALLING(() -> PotionEffectType.SLOW_FALLING, 0xE17F),
    SLOWNESS(() -> PotionEffectType.SLOWNESS, 0xE180),
    SPEED(() -> PotionEffectType.SPEED, 0xE181),
    STRENGTH(() -> PotionEffectType.STRENGTH, 0xE182),
    UNLUCK(() -> PotionEffectType.UNLUCK, 0xE183),
    WATER_BREATHING(() -> PotionEffectType.WATER_BREATHING, 0xE184),
    WEAKNESS(() -> PotionEffectType.WEAKNESS, 0xE185),
    WIND_CHARGED(() -> PotionEffectType.WIND_CHARGED, 0xE009),
    WITHER(() -> PotionEffectType.WITHER, 0xE00A),
    RAID_OMEN(() -> PotionEffectType.RAID_OMEN, 0xE00B),
    INFESTED(() -> PotionEffectType.INFESTED, 0xE00C),
    WEAVING(() -> PotionEffectType.WEAVING, 0xE00D),
    TRIAL_OMEN(() -> PotionEffectType.TRIAL_OMEN, 0xE00E),
    DARKNESS(() -> PotionEffectType.DARKNESS, 0xE00F),
    OOZING(() -> PotionEffectType.OOZING, 0xE010),
    ;

    public final Supplier<PotionEffectType> potionEffectTypeSupplier;
    public final Supplier<PotionType> potionTypeSupplier;
    public final String emojiName;
    public final String filename;
    public final int ascent = 8;
    public final int height = 9; // texture size = 18x18
    public final char character;
    public final Component component;
    public final Component displayName;

    VanillaEffects(final Supplier<PotionEffectType> potionEffectTypeSupplier, final Supplier<PotionType> potionTypeSupplier, final int character) {
        this.potionEffectTypeSupplier = potionEffectTypeSupplier;
        this.potionTypeSupplier = potionTypeSupplier;
        this.emojiName = name().toLowerCase() + "_effect";
        this.filename = "minecraft:mob_effect/" + name().toLowerCase();
        this.character = (char) character;
        this.component = Component.text((char) character)
            .style(Style.style()
                   .font(Key.key("cavetale:default"))
                   .color(NamedTextColor.WHITE));
        this.displayName = Component.text(toCamelCase(" ", this));
    }

    VanillaEffects(final Supplier<PotionEffectType> potionEffectTypeSupplier, final int character) {
        this(potionEffectTypeSupplier, () -> null, character);
    }

    public static VanillaEffects of(PotionEffectType type) {
        for (var it : values()) {
            if (it.getPotionEffectType() == type) return it;
        }
        return null;
    }

    @Deprecated
    public static VanillaEffects of(PotionType type) {
        for (var it : values()) {
            if (type.getPotionEffects().contains(it.getPotionEffectType())) return it;
        }
        return null;
    }

    public static Component componentOf(PotionEffectType type) {
        VanillaEffects inst = of(type);
        return inst != null
            ? inst.component
            : Component.empty();
    }

    public static Component componentOf(PotionType type) {
        VanillaEffects inst = of(type);
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

    public PotionEffectType getPotionEffectType() {
        return potionEffectTypeSupplier.get();
    }

    public static void test() {
        for (var it : PotionEffectType.values()) {
            if (of(it) == null) {
                plugin().getLogger().warning("No VanillaEffects: PotionEffectType." + it.getName());
            }
        }
    }
}
