package com.cavetale.core.event.hud;

import java.util.Set;
import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import net.kyori.adventure.bossbar.BossBar;
import net.kyori.adventure.text.Component;

@Getter @RequiredArgsConstructor
public final class PlayerBossBarEntry implements Comparable<PlayerBossBarEntry> {
    private final int priority;
    @NonNull private final Component title;
    @NonNull private final BossBar.Color color;
    @NonNull private final BossBar.Overlay overlay;
    @NonNull private final Set<BossBar.Flag> flags;
    private final float progress;

    @Override
    public int compareTo(PlayerBossBarEntry other) {
        return Integer.compare(priority, other.priority);
    }

    public boolean isSimilar(PlayerBossBarEntry other) {
        return progress == other.progress
            && title.equals(other.title)
            && color == other.color
            && overlay == other.overlay
            && flags.equals(other.flags);
    }
}
