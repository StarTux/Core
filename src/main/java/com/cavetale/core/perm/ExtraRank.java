package com.cavetale.core.perm;

import com.cavetale.core.font.DefaultFont;
import com.cavetale.core.message.AltTextSupplier;
import java.util.Collection;
import java.util.EnumSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import lombok.Getter;
import net.kyori.adventure.text.Component;
import static net.kyori.adventure.text.Component.text;
import static net.kyori.adventure.text.format.NamedTextColor.*;

@Getter
public enum ExtraRank implements Rank {
    BUILDER(DefaultFont.BUILDER),
    HEAD_BUILDER(new AltTextSupplier() {
            @Override public Component asComponent() {
                return text("[HeadBuilder]", LIGHT_PURPLE);
            }
            @Override public Component getAltText() {
                return text("[HeadBuilder]", LIGHT_PURPLE);
            }
        }),
    DUTYMODE(new AltTextSupplier() {
            @Override public Component asComponent() {
                return text("[Dutymode]", YELLOW);
            }
            @Override public Component getAltText() {
                return text("[Dutymode]", YELLOW);
            }
        }),
    GOAT(DefaultFont.GOAT),
    STREAMER(new AltTextSupplier() {
            @Override public Component asComponent() {
                return text("[Streamer]", BLUE);
            }
            @Override public Component getAltText() {
                return text("[Streamer]", BLUE);
            }
        }),
    PROMO(DefaultFont.PROMO),
    ;

    public static final List<String> KEYS;
    public static final Map<String, ExtraRank> KEY_MAP;
    public final String key;
    public final AltTextSupplier altText;

    ExtraRank(final AltTextSupplier altText) {
        this.key = name().toLowerCase().replace("_", "-");
        this.altText = altText;
    }

    static {
        KEYS = Stream.of(ExtraRank.values())
            .map(s -> s.key)
            .collect(Collectors.toUnmodifiableList());
        KEY_MAP = Stream.of(ExtraRank.values())
            .collect(Collectors.toUnmodifiableMap(ExtraRank::getKey, Function.identity()));
    }

    public static Set<ExtraRank> ofPlayer(UUID uuid) {
        Collection<String> groups = Perm.get().getGroups(uuid);
        if (groups.isEmpty()) return EnumSet.noneOf(ExtraRank.class);
        Set<ExtraRank> set = EnumSet.noneOf(ExtraRank.class);
        for (String group : groups) {
            ExtraRank it = KEY_MAP.get(group);
            if (it != null) set.add(it);
        }
        return set;
    }

    @Override
    public Component asComponent() {
        return altText.asComponent();
    }

    @Override
    public Component getAltText() {
        return altText.getAltText();
    }
}
