package com.cavetale.core.perm;

import com.cavetale.core.font.DefaultFont;
import com.cavetale.core.message.AltTextSupplier;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import lombok.Getter;
import net.kyori.adventure.text.Component;

@Getter
public enum StaffRank implements Rank {
    TRUSTED(DefaultFont.TRUSTED),
    MODERATOR(DefaultFont.MODERATOR),
    ADMIN(DefaultFont.ADMIN);

    public static final List<String> KEYS;
    public static final Map<String, StaffRank> KEY_MAP;

    public final String key;
    public final AltTextSupplier altText;

    StaffRank(final AltTextSupplier altText) {
        this.key = name().toLowerCase();
        this.altText = altText;
    }

    static {
        KEYS = Stream.of(StaffRank.values())
            .map(s -> s.key)
            .collect(Collectors.toUnmodifiableList());
        KEY_MAP = Stream.of(StaffRank.values())
            .collect(Collectors.toUnmodifiableMap(StaffRank::getKey, Function.identity()));
    }

    public static StaffRank ofPlayer(UUID uuid) {
        Collection<String> groups = Perm.get().getGroups(uuid);
        if (groups.isEmpty()) return null;
        for (String group : groups) {
            StaffRank it = KEY_MAP.get(group);
            if (it != null) return it;
        }
        return null;
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
